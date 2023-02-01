package org.dissan.models.job;

import org.jetbrains.annotations.NotNull;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ShiftScheduling {
    private final List<ShiftApply> appliances;
    public ShiftScheduling() {
        this.appliances = new ArrayList<>();
    }
    public void newEmployee(@NotNull ShiftApply appliance){
        Date today = new Date();
        String jobDate = appliance.getApplyDate();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        if (jobDate.equals(dateFormat.format(today))) {
            appliances.add(appliance);
        }
    }
    public List<ShiftApply> getAppliances() {
        return this.appliances;
    }
    public void addSchedule(@NotNull List<ShiftApply> applyList) {
        for (ShiftApply appliance: applyList
             ) {
            this.newEmployee(appliance);
        }
    }
}
