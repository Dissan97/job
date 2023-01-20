package org.agnese_dissan.controllers;

import org.agnese_dissan.Macros;
import org.agnese_dissan.beans.DemiseBean;
import org.agnese_dissan.graphicControllers.DemiseGraphicController;

public class DemiseController {

    private final DemiseGraphicController graphic;
    private DemiseBean bean;

    private static DemiseController me = null;

    private DemiseController(DemiseGraphicController graphic){
        this.graphic = graphic;
        this.bean = this.graphic.getDemiseBean();
    }

    public static DemiseController getController(DemiseGraphicController graphic) {
        if (me == null){
            me = new DemiseController(graphic);
        }
        return me;
    }

    public void demiseNotify(Macros which){
        this.graphic.notifyDemise(which);
    }
}
