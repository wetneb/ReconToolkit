
package eu.delpeuch.antonin.recontoolkit.api;

import java.util.List;

import eu.delpeuch.antonin.recontoolkit.protocol.Manifest;
import eu.delpeuch.antonin.recontoolkit.protocol.ReconQuery;
import eu.delpeuch.antonin.recontoolkit.protocol.ReconResult;

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
    Manifest getManifest();

    /**
     * Executes reconciliation queries on the service. Equivalent to making a POST request on the root endpoint of the
     * service, with the JSON serialization of those requests as payload.
     */
    List<ReconResult> reconcile(List<ReconQuery> queries);

    // TODO: add support for suggest, data extension, preview
}
