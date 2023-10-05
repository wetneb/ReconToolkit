
package eu.delpeuch.antonin.recontoolkit.api;

/*-
 * #%L
 * ReconToolkit data model
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
import eu.delpeuch.antonin.recontoolkit.protocol.*;

import java.io.IOException;
import java.util.List;

/**
 * A Java interface which mirrors the interface offered by a reconciliation service. It is abstracted away from its HTTP
 * implementation and offered as a native Java interface, for instance to implement and use a reconciliation service in
 * the same process.
 * 
 * @author antonin
 *
 */
public interface ReconService {

    /**
     * The manifest of the service. Equivalent to making a GET request on the root endpoint of the service.
     */
    Manifest getManifest() throws IOException;

    /**
     * Executes reconciliation queries on the service.
     *
     * @param queries
     *            the list of reconciliation queries to execute. If the service specifies a maximum batch size
     *            ({@link Manifest#getBatchSize()}) then the list must not be longer than this limit.
     * @return the list of responses, in the same order
     */
    List<ReconResult> reconcile(List<ReconQuery> queries) throws IOException;

    /// Suggest services

    /**
     * @return whether suggesting entities is supported
     * @throws IOException
     *             if fetching the manifest fails
     * @see #suggestEntities(String, int)
     */
    default boolean supportsSuggestingEntities() throws IOException {
        Manifest manifest = getManifest();
        return (manifest != null
                && manifest.getSuggestOptions() != null
                && manifest.getSuggestOptions().getEntitySuggestMetadata() != null);
    }

    /**
     * Returns a couple of entities matching the supplied name. This is meant to be used for auto-completion, therefore
     * a prefix search in the set of entities is appropriate, although not mandated by the specs.
     *
     * @param prefix
     *            the string to search for
     * @param cursor
     *            the number of results to skip, useful for fetching more results
     * @return a list of entities matching the search term. The entities should be sorted by decreasing relevance.
     */
    List<Entity> suggestEntities(String prefix, int cursor) throws IOException;

    /**
     * Returns a couple of entities matching the supplied name. This is meant to be used for auto-completion, therefore
     * a prefix search in the set of entities is appropriate, although not mandated by the specs.
     *
     * @param prefix
     *            the string to search for
     * @return a list of entities matching the search term. The entities should be sorted by decreasing relevance.
     */
    default List<Entity> suggestEntities(String prefix) throws IOException {
        return suggestEntities(prefix, 0);
    }

    /**
     * @return whether suggesting properties is supported
     * @throws IOException
     *             if fetching the manifest fails
     * @see #suggestProperties(String, int)
     */
    default boolean supportsSuggestingProperties() throws IOException {
        Manifest manifest = getManifest();
        return (manifest != null
                && manifest.getSuggestOptions() != null
                && manifest.getSuggestOptions().getPropertySuggestMetadata() != null);
    }

    /**
     * Returns a couple of properties matching the supplied name. This is meant to be used for auto-completion,
     * therefore a prefix search in the set of properties is appropriate, although not mandated by the specs.
     *
     * @param prefix
     *            the string to search for
     * @param cursor
     *            the number of results to skip, useful for fetching more results
     * @return a list of properties matching the search term. The properties should be sorted by decreasing relevance.
     */
    List<Property> suggestProperties(String prefix, int cursor) throws IOException;

    /**
     * Returns a couple of entities matching the supplied name. This is meant to be used for auto-completion, therefore
     * a prefix search in the set of properties is appropriate, although not mandated by the specs.
     *
     * @param prefix
     *            the string to search for
     * @return a list of properties matching the search term. The properties should be sorted by decreasing relevance.
     */
    default List<Property> suggestProperties(String prefix) throws IOException {
        return suggestProperties(prefix, 0);
    }

    /**
     * @return whether suggesting properties is supported
     * @throws IOException
     *             if fetching the manifest fails
     * @see #suggestProperties(String, int)
     */
    default boolean supportsSuggestingTypes() throws IOException {
        Manifest manifest = getManifest();
        return (manifest != null
                && manifest.getSuggestOptions() != null
                && manifest.getSuggestOptions().getTypeSuggestMetadata() != null);
    }

    /**
     * Returns a couple of types matching the supplied name. This is meant to be used for auto-completion, therefore a
     * prefix search in the set of types is appropriate, although not mandated by the specs.
     *
     * @param prefix
     *            the string to search for
     * @param cursor
     *            the number of results to skip, useful for fetching more results
     * @return a list of types matching the search term. The types should be sorted by decreasing relevance.
     */
    List<Type> suggestTypes(String prefix, int cursor) throws IOException;

    /**
     * Returns a couple of types matching the supplied name. This is meant to be used for auto-completion, therefore a
     * prefix search in the set of types is appropriate, although not mandated by the specs.
     *
     * @param prefix
     *            the string to search for
     * @return a list of types matching the search term. The types should be sorted by decreasing relevance.
     */
    default List<Type> suggestTypes(String prefix) throws IOException {
        return suggestTypes(prefix, 0);
    }

    /// Preview

    /**
     * Returns an HTML page representing the entity. This is meant to be inserted in a web app by means of an iframe,
     * whose suggested dimensions are specified in the {@link Manifest}.
     *
     * @param entityId
     *            the id of the entity to preview
     * @return an HTML page previewing the entity
     */
    String previewEntity(String entityId) throws IOException;

    /// Data extension

    /**
     * Fetches property values given a set of entity ids and a set of property ids.
     *
     * @param query
     *            the property and entity ids. Properties can also be configured with optional settings
     * @return a grid of property values for each entity and property requested
     */
    DataExtensionResponse extendData(DataExtensionQuery query) throws IOException;

}
