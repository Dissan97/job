package org.disagn.beans;

import org.disagn.models.job.Demise;
import java.util.List;

public class DemiseBean {



    private List<Demise> demiseList;
    private Demise pendingDemise;

    public List<Demise> getDemiseList() {
        return demiseList;
    }
    public void setDemises(List<Demise> demiseList) {
        this.demiseList = demiseList;
    }

    public void setPendingDemise(Demise chooseDemise) {
        this.pendingDemise = chooseDemise;
    }

    public Demise getPendingDemise() {
        return pendingDemise;
    }

    public String getPendingDemiseApplication() {
        return this.pendingDemise.getApplication();
    }

    public String getPendingDemiseEmployee() {
        return this.pendingDemise.getEmployee();
    }

    public String getPendingDemiseMotivation(){
        return this.pendingDemise.getMotivation();
    }



    public void pendingDemiseAcceptance(Demise demise, boolean b) {
        this.pendingDemise = demise;
        this.pendingDemise.setAccepted(b);
    }
}
