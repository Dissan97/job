package org.dissan.beans;

import org.dissan.models.job.ShiftApply;
import org.dissan.models.job.ShiftScheduling;
import java.util.List;

public class ScheduleBean {
    private List<ShiftApply> appliances;

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
