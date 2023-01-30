package org.agnese_dissan.beans;

import org.agnese_dissan.models.job.Shift;
import org.agnese_dissan.models.job.ShiftApply;

import java.util.List;

public class JobApplierBean {

    private List<Shift> shiftList;
    private List<ShiftApply> shiftApplyList;
    public void setShiftList(List<Shift> shiftList) {
        this.shiftList = shiftList;
    }

    public List<Shift> getShiftList() {
        return shiftList;
    }

    public void setAppliances(List<ShiftApply> pullAppliances) {
        this.shiftApplyList = pullAppliances;
    }

    public List<ShiftApply> getShiftApplyList() {
        return shiftApplyList;
    }
}
