package org.agnese_dissan.controllers;


import org.agnese_dissan.cli.io.Output;
import org.agnese_dissan.graphicControllers.DemiseGraphicController;

public class DemiseController {

    private DemiseGraphicController graphic;
    //private DemiseBean bean;

    private static DemiseController me = null;

    private DemiseController(DemiseGraphicController graphic){
        this.graphic = graphic;
        //TODO REMOVE this
        Output.println(this.graphic.toString());
        //this.bean = this.graphic.getDemiseBean();
    }

    /**
    public void setUpDemise(ShiftApply application , Employee employee){

    }
    //TODO Implements this methods
    public void outCome(boolean result){

    }*/

    public static DemiseController getController(DemiseGraphicController graphic) {
        if (me == null){
            me = new DemiseController(graphic);
        } else {
            me.graphic = graphic;
            //me.bean = me.graphic.getDemiseBean();
        }
        return me;
    }

}
