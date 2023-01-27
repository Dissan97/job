package org.agnese_dissan.beans;

import org.agnese_dissan.models.job.ShiftApply;
import org.agnese_dissan.models.job.ShiftScheduling;
import java.util.List;

public class ScheduleBean {
    private List<ShiftApply> appliances;
    public ScheduleBean() {

    }
    public List<ShiftApply> getAppliances() {
        if (appliances != null) {
            return appliances;
        }
        return null;
    }
    public void addScheduling(ShiftScheduling scheduling) {
        this.appliances = scheduling.getAppliances();
    }
}
