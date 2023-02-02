package org.dissan.beans;

import org.dissan.models.job.Demise;
import org.dissan.models.job.ShiftApply;

import java.util.List;

public class DemiseBean {



    private List<Demise> demiseList;
    public List<ShiftApply> getApplications() {
        return null;
    }

    public List<Demise> getDemiseList() {
        return demiseList;
    }
    public void setDemises(List<Demise> demiseList) {
        this.demiseList = demiseList;
    }
}
