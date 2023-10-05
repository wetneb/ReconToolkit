
package eu.delpeuch.antonin.recontoolkit.protocol;

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

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.Validate;

import java.util.List;
import java.util.Objects;

/**
 * The response to a reconciliation query, which consists of a list of reconciliation candidates if the query was
 * successful.
 * 
 * @author antonin
 *
 */
public class ReconResult {

    private final List<ReconCandidate> candidates;

    /**
     * Constructs a successful reconciliation result.
     * 
     * @param candidates
     *            the list of reconciliation candidates (possibly empty)
     */
    @JsonCreator
    public ReconResult(
            @JsonProperty("result") List<ReconCandidate> candidates) {
        super();
        Validate.notNull(candidates);
        this.candidates = candidates;
    }

    /**
     * The candidates returned by the service for the corresponding recon query.
     */
    @JsonProperty("result")
    public List<ReconCandidate> getCandidates() {
        return candidates;
    }

    @Override
    public int hashCode() {
        return Objects.hash(candidates);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        ReconResult other = (ReconResult) obj;
        return Objects.equals(candidates, other.candidates);
    }

    @Override
    public String toString() {
        return "ReconResult [candidates=" + candidates + "]";
    }

}
