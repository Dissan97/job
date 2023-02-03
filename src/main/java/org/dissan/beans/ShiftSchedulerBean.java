package org.dissan.beans;

import org.dissan.models.job.ShiftApply;

import java.util.ArrayList;
import java.util.List;

public class ShiftSchedulerBean {
    private List<String> dateList;
    private List<ShiftApply> scheduleList;

    public ShiftSchedulerBean() {
        this.dateList = new ArrayList<>();
        this.scheduleList = new ArrayList<>();
    }

    public List<ShiftApply> getSchedules() {
        return this.scheduleList;
    }
    public void setSchedules(List<ShiftApply> scheduleList) {
        this.scheduleList = scheduleList;
    }

    public void setDateList(List<String> dateList) {
        this.dateList = dateList;
    }

    public List<String> getDateList() {
        return dateList;
    }

}
