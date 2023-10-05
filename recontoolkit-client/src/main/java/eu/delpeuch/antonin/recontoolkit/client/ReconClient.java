
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

import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;

import com.fasterxml.jackson.core.type.TypeReference;
import eu.delpeuch.antonin.recontoolkit.api.ReconService;
import eu.delpeuch.antonin.recontoolkit.model.Entity;
import eu.delpeuch.antonin.recontoolkit.model.Property;
import eu.delpeuch.antonin.recontoolkit.model.Type;
import eu.delpeuch.antonin.recontoolkit.protocol.*;
import eu.delpeuch.antonin.recontoolkit.utils.JsonUtils;
import okhttp3.*;
import org.apache.commons.lang3.Validate;

/**
 * A reconciliation client, which reconciles by making HTTP queries to a service implementing
 * <a href="https://www.w3.org/community/reports/reconciliation/CG-FINAL-specs-0.2-20230410/">the v0.2 API</a>.
 */
public class ReconClient implements ReconService {

    public static final String DEFAULT_USER_AGENT = "ReconToolkit";
    private final static OkHttpClient sharedClient = new OkHttpClient();
    public static final String APPLICATION_JSON = "application/json";

    private final String endpoint;
    private Manifest manifest;
    private final OkHttpClient client;

    /**
     * Constructor.
     *
     * @param endpoint
     *            the URL of the reconciliation service to use
     */
    public ReconClient(String endpoint) {
        this.endpoint = endpoint;
        this.manifest = null;
        this.client = sharedClient.newBuilder()
                .addInterceptor(chain -> chain.proceed(chain.request().newBuilder()
                        .addHeader("user-agent", DEFAULT_USER_AGENT)
                        .build()))
                .build();
    }

    @Override
    public Manifest getManifest() throws IOException {
        if (manifest == null) {
            Request request = new Request.Builder()
                    .url(endpoint)
                    .addHeader("accept", APPLICATION_JSON)
                    .build();

            Call call = client.newCall(request);
            try (Response response = call.execute()) {
                manifest = JsonUtils.mapper.readValue(response.body().byteStream(), Manifest.class);
            }
        }
        return manifest;
    }

    @Override
    public List<ReconResult> reconcile(List<ReconQuery> queries) throws IOException {
        if (queries.isEmpty()) {
            return Collections.emptyList();
        }
        if (getManifest().getBatchSize() > 0 && queries.size() > getManifest().getBatchSize()) {
            throw new IllegalArgumentException(
                    String.format(
                            "Reconciliation batch too large (%d queries): maximum size supported by the service is %d queries",
                            queries.size(), getManifest().getBatchSize()));
        }

        Map<String, ReconQuery> indexedQueries = new HashMap<>();
        for (ReconQuery query : queries) {
            indexedQueries.put("q" + (indexedQueries.size() + 1), query);
        }

        Request request = new Request.Builder()
                .url(endpoint)
                .addHeader("accept", APPLICATION_JSON)
                .post(new FormBody.Builder().add("queries",
                        JsonUtils.mapper.writeValueAsString(indexedQueries)).build())
                .build();

        Call call = client.newCall(request);
        try (Response response = call.execute()) {
            Map<String, ReconResult> results = JsonUtils.mapper.readValue(response.body().byteStream(), new TypeReference<>() {
            });
            List<ReconResult> resultList = new ArrayList<>();
            for (ReconQuery query : queries) {
                // TODO check that the response was indeed returned!
                resultList.add(results.get("q" + (resultList.size() + 1)));
            }
            return resultList;
        }
    }

    @Override
    public List<Entity> suggestEntities(String prefix, int cursor) throws IOException {
        if (!supportsSuggestingEntities()) {
            throw new IllegalStateException("The service does not support suggesting entities");
        }
        return suggestSomething(prefix, cursor,
                getManifest()
                        .getSuggestOptions()
                        .getEntitySuggestMetadata()
                        .getFullServiceUrl(),
                new TypeReference<>() {
                });
    }

    @Override
    public List<Property> suggestProperties(String prefix, int cursor) throws IOException {
        if (!supportsSuggestingProperties()) {
            throw new IllegalStateException("The service does not support suggesting entities");
        }
        return suggestSomething(prefix, cursor,
                getManifest()
                        .getSuggestOptions()
                        .getPropertySuggestMetadata()
                        .getFullServiceUrl(),
                new TypeReference<>() {
                });
    }

    @Override
    public List<Type> suggestTypes(String prefix, int cursor) throws IOException {
        if (!supportsSuggestingTypes()) {
            throw new IllegalStateException("The service does not support suggesting entities");
        }
        return suggestSomething(prefix, cursor,
                getManifest()
                        .getSuggestOptions()
                        .getTypeSuggestMetadata()
                        .getFullServiceUrl(),
                new TypeReference<>() {
                });
    }

    /**
     * Internal helper for all suggest queries
     */
    private <T> List<T> suggestSomething(String prefix, int cursor, String url, TypeReference<SuggestResponse<T>> valueTypeRef)
            throws IOException {
        Validate.notNull(prefix);
        String fullUrl = HttpUrl.parse(url)
                .newBuilder()
                .addQueryParameter("prefix", prefix)
                .addQueryParameter("cursor", Integer.toString(cursor))
                .build()
                .toString();

        Request request = new Request.Builder()
                .url(fullUrl)
                .addHeader("accept", APPLICATION_JSON)
                .get()
                .build();

        Call call = client.newCall(request);
        try (Response response = call.execute()) {
            SuggestResponse<T> parsed = JsonUtils.mapper.readValue(response.body().byteStream(), valueTypeRef);
            return parsed.getResults();
        }
    }

    @Override
    public String previewEntity(String entityId) {
        return null;
    }

    @Override
    public DataExtensionResponse extendData(DataExtensionQuery query) {
        return null;
    }

}
