package org.agnese_dissan.graphicControllers;

import org.agnese_dissan.beans.ShiftBean;
import org.agnese_dissan.controllers.ShiftPublisher;

public class ShiftPublisherGraphic {

    private final ShiftPublisher shiftPublisher;
    private ShiftBean bean;
    public ShiftPublisherGraphic() {
        this.shiftPublisher = new ShiftPublisher();
        this.bean = new ShiftBean();
    }

    public void setBean(ShiftBean bean) {
        this.bean = bean;
    }

    public void publishShift(){
        shiftPublisher.publish(this.bean.getEmployer().getUsername(),this.bean.getName(), this.bean.getJobPlace(), this.bean.getDateTime(), this.bean.getDescription());
    }

    public ShiftBean getBean() {
        return  this.bean;
    }
}

