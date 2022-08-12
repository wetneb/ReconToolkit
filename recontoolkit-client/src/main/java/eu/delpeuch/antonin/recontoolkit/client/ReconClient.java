
package eu.delpeuch.antonin.recontoolkit.client;

import java.util.List;

import eu.delpeuch.antonin.recontoolkit.api.ReconService;
import eu.delpeuch.antonin.recontoolkit.protocol.Manifest;
import eu.delpeuch.antonin.recontoolkit.protocol.ReconQuery;
import eu.delpeuch.antonin.recontoolkit.protocol.ReconResult;

public class ReconClient implements ReconService {

    private final String endpoint;

    public ReconClient(String endpoint) {
        this.endpoint = endpoint;
    }

    @Override
    public Manifest getManifest() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<ReconResult> reconcile(List<ReconQuery> queries) {
        // TODO Auto-generated method stub
        return null;
    }

}
