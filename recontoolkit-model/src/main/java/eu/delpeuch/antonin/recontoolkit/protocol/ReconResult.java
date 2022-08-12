
package eu.delpeuch.antonin.recontoolkit.protocol;

import java.util.List;
import java.util.Objects;

import org.apache.commons.lang3.Validate;

import com.google.gson.annotations.SerializedName;

/**
 * The response to a reconciliation query, which consists of a list of reconciliation candidates if the query was
 * successful.
 * 
 * @author antonin
 *
 */
public class ReconResult {

    @SerializedName("result")
    private final List<ReconCandidate> candidates;

    /**
     * Constructs a successful reconciliation result.
     * 
     * @param candidates
     *            the list of reconciliation candidates (possibly empty)
     */
    public ReconResult(List<ReconCandidate> candidates) {
        super();
        Validate.notNull(candidates);
        this.candidates = candidates;
    }

    /**
     * The candidates returned by the service for the corresponding recon query.
     */
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
