
package eu.delpeuch.antonin.recontoolkit.client;

/*-
 * #%L
 * ReconToolkit client
 * %%
 * Copyright (C) 2022 - 2023 ReconToolkit Developers
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

import eu.delpeuch.antonin.recontoolkit.model.Entity;
import eu.delpeuch.antonin.recontoolkit.model.Property;
import eu.delpeuch.antonin.recontoolkit.model.Type;
import eu.delpeuch.antonin.recontoolkit.protocol.Manifest;
import eu.delpeuch.antonin.recontoolkit.protocol.ReconQuery;
import eu.delpeuch.antonin.recontoolkit.protocol.ReconResult;
import eu.delpeuch.antonin.recontoolkit.utils.JsonUtils;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import org.testng.annotations.Test;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertThrows;

public class ReconClientTest {

    @Test
    public void testManifest() throws IOException, InterruptedException {
        try (MockWebServer server = new MockWebServer()) {
            server.enqueue(mockResponseFromFile("responses/manifest.json"));

            ReconClient SUT = new ReconClient(server.url("/reconcile").toString());

            Manifest fetched = SUT.getManifest();

            assertEquals(fetched.getName(), "VIAF");
            assertEquals(fetched.getIdentifierSpace(), "http://vocab.getty.edu/doc/#GVP_URLs_and_Prefixes");

            RecordedRequest request = server.takeRequest();
            assertEquals(request.getPath(), "/reconcile");
            assertEquals(request.getMethod(), "GET");
            assertEquals(request.getHeader("accept"), "application/json");
            assertEquals(request.getHeader("user-agent"), ReconClient.DEFAULT_USER_AGENT);

            // the manifest is cached, so fetching it again does not generate any other request
            Manifest fetchedAgain = SUT.getManifest();
            assertEquals(fetchedAgain, fetched);
        }
    }

    @Test
    public void testManifest404() throws IOException {
        try (MockWebServer server = new MockWebServer()) {
            server.enqueue(new MockResponse().setResponseCode(404));

            ReconClient SUT = new ReconClient(server.url("/reconcile").toString());

            assertThrows(IOException.class, SUT::getManifest);
        }
    }

    @Test
    public void testReconcile() throws IOException, InterruptedException {
        try (MockWebServer server = new MockWebServer()) {
            server.enqueue(mockResponseFromFile("responses/manifest.json"));
            server.enqueue(mockResponseFromFile("responses/recon_response.json"));

            ReconClient SUT = new ReconClient(server.url("/reconcile").toString());

            List<ReconQuery> reconQueries = new ArrayList<>();
            reconQueries.add(ReconQuery.Builder.aReconQuery()
                    .withQuery("Hans-Eberhard Urbaniak")
                    .build());
            reconQueries.add(ReconQuery.Builder.aReconQuery()
                    .withQuery("Ernst Schwanhold")
                    .build());

            List<ReconResult> reconResults = SUT.reconcile(reconQueries);
            assertEquals(reconResults.get(0).getCandidates().get(0).getName(), "Urbaniak, Regina");
            assertEquals(reconResults.get(0).getCandidates().get(0).getId(), "120333937");

            // the first request fetches the manifest (required to check we are not submitting too many requests at a
            // time)
            RecordedRequest request = server.takeRequest();
            assertEquals(request.getPath(), "/reconcile");
            assertEquals(request.getMethod(), "GET");

            // the second does reconciliation
            request = server.takeRequest();
            assertEquals(request.getPath(), "/reconcile");
            assertEquals(request.getMethod(), "POST");
            assertEquals(request.getHeader("accept"), "application/json");
            assertEquals(request.getHeader("user-agent"), ReconClient.DEFAULT_USER_AGENT);
        }
    }

    @Test
    public void testReconcileEmptyBatch() throws IOException, InterruptedException {
        try (MockWebServer server = new MockWebServer()) {
            ReconClient SUT = new ReconClient(server.url("/reconcile").toString());

            List<ReconResult> reconResults = SUT.reconcile(Collections.emptyList());

            assertEquals(reconResults, Collections.emptyList());
        }
    }

    @Test
    public void testReconcileBigBatch() throws IOException {
        try (MockWebServer server = new MockWebServer()) {
            server.enqueue(mockResponseFromFile("responses/manifest_with_batch_size.json"));

            ReconClient SUT = new ReconClient(server.url("/reconcile").toString());

            List<ReconQuery> queries = Arrays.asList(
                    ReconQuery.Builder.aReconQuery().withQuery("Hans-Eberhard Urbaniak").build(),
                    ReconQuery.Builder.aReconQuery().withQuery("Ernst Schwanhold").build());

            assertThrows(IllegalArgumentException.class, () -> SUT.reconcile(queries));
        }
    }

    @Test
    public void testSuggestEntity() throws IOException, InterruptedException {
        try (MockWebServer server = new MockWebServer()) {
            server.enqueue(mockResponseFromFile("responses/manifest_with_suggest.json", server.url("").toString()));
            server.enqueue(mockResponseFromFile("responses/suggest_entities.json"));

            ReconClient SUT = new ReconClient(server.url("/reconcile").toString());

            List<Entity> response = SUT.suggestEntities("Autu", 10);

            List<Entity> expectedResponse = Arrays.asList(
                    new Entity("123", "Autumn Leaves", "song translated by Jonny Mercer", null),
                    new Entity("456", "Autumn in New York", "tune by Vernon Duke", null));

            assertEquals(response, expectedResponse);

            // the first request fetches the manifest (required to fetch the URL of the suggest service)
            RecordedRequest request = server.takeRequest();
            assertEquals(request.getPath(), "/reconcile");
            assertEquals(request.getMethod(), "GET");
            // the second request fetches the Suggest results
            request = server.takeRequest();
            assertEquals(request.getPath(), "/suggest/entity?prefix=Autu&cursor=10");
            assertEquals(request.getMethod(), "GET");
        }
    }

    @Test
    public void testSuggestProperty() throws IOException, InterruptedException {
        try (MockWebServer server = new MockWebServer()) {
            server.enqueue(mockResponseFromFile("responses/manifest_with_suggest.json", server.url("").toString()));
            server.enqueue(mockResponseFromFile("responses/suggest_properties.json"));

            ReconClient SUT = new ReconClient(server.url("/reconcile").toString());

            List<Property> response = SUT.suggestProperties("sim", 0);

            List<Property> expectedResponse = Arrays.asList(
                    new Property("123", "similar to", "very vague meaning"),
                    new Property("456", "simpler than", "also pretty ill-defined"));

            assertEquals(response, expectedResponse);

            // the first request fetches the manifest (required to fetch the URL of the suggest service)
            RecordedRequest request = server.takeRequest();
            assertEquals(request.getPath(), "/reconcile");
            assertEquals(request.getMethod(), "GET");
            // the second request fetches the Suggest results
            request = server.takeRequest();
            assertEquals(request.getPath(), "/suggest/property?prefix=sim&cursor=0");
            assertEquals(request.getMethod(), "GET");
        }
    }

    @Test
    public void testSuggestTypes() throws IOException, InterruptedException {
        try (MockWebServer server = new MockWebServer()) {
            server.enqueue(mockResponseFromFile("responses/manifest_with_suggest.json", server.url("").toString()));
            server.enqueue(mockResponseFromFile("responses/suggest_types.json"));

            ReconClient SUT = new ReconClient(server.url("/reconcile").toString());

            List<Type> response = SUT.suggestTypes("perso", 0);

            List<Type> expectedResponse = Arrays.asList(
                    new Type("abc", "person", "a human", null),
                    new Type("def", "personality", "a celebrity", null));

            assertEquals(response, expectedResponse);

            // the first request fetches the manifest (required to fetch the URL of the suggest service)
            RecordedRequest request = server.takeRequest();
            assertEquals(request.getPath(), "/reconcile");
            assertEquals(request.getMethod(), "GET");
            // the second request fetches the Suggest results
            request = server.takeRequest();
            assertEquals(request.getPath(), "/suggest/type?prefix=perso&cursor=0");
            assertEquals(request.getMethod(), "GET");
        }
    }

    protected String jsonFromFile(String filename) throws IOException {
        InputStream stream = ReconClientTest.class.getClassLoader().getResourceAsStream(filename);
        if (stream == null) {
            throw new IllegalArgumentException("Test resource " + filename + " not found");
        }
        return new String(stream.readAllBytes(), StandardCharsets.UTF_8);
    }

    protected MockResponse mockResponseFromFile(String filename) throws IOException {
        return mockResponseFromFile(filename, "");
    }

    protected MockResponse mockResponseFromFile(String filename, String serverUrl) throws IOException {
        String baseUrl = "".equals(serverUrl) ? "" : serverUrl.substring(0, serverUrl.length() - 1);
        return new MockResponse()
                .setHeader("content-type", "application/json")
                .setBody(jsonFromFile(filename).replace("TEST_SERVICE_URL", baseUrl));
    }
}
