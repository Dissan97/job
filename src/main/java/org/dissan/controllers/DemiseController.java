package org.dissan.controllers;


import org.dissan.cli.io.Output;
import org.dissan.graphicControllers.DemiseGraphicController;

public class DemiseController {

    private final DemiseGraphicController graphic;
    //private DemiseBean bean;
    
    public DemiseController(DemiseGraphicController graphic){
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


}