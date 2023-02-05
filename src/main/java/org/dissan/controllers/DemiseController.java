package org.dissan.controllers;


import org.dissan.Macros;
import org.dissan.beans.DemiseBean;
import org.dissan.cli.io.Output;
import org.dissan.daos.DaoManager;
import org.dissan.exceptions.ElementAlreadyComputedException;
import org.dissan.exceptions.InvalidDateException;
import org.dissan.interfaces.DAO;
import org.dissan.models.job.Demise;
import org.dissan.models.users.User;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DemiseController {

    private final DemiseBean bean;
    //private DemiseBean bean;
    
    public DemiseController(DemiseBean bean){
        this.bean = bean;
        //this.bean = this.graphic.getDemiseBean();
    }

    public void sendDemise(Demise demise) throws IOException, ElementAlreadyComputedException {
        //Should be applied extra control to view if that demise is already sent
        DAO dao = DaoManager.getDaoManager();
        try {
            this.pullDemises(new User());
        } catch (InvalidDateException e) {
            Output.println("Something wrong");
        }

        List<Demise> demiseList;

        demiseList = this.bean.getDemiseList();

        for (Demise d:
             demiseList) {
            if (demise.getApplication().equals(d.getApplication())){
                throw new ElementAlreadyComputedException();
            }
        }
        demise.sent();
        dao.updateDemise(demise);
        dao.pushDemise(demise);
    }

    public void pullDemises(User user) throws FileNotFoundException {
        DAO dao = DaoManager.getDaoManager();
        List<Demise> demiseList;
        Macros type = user.getUserType();
        List<Demise> updatedDemiseList = new ArrayList<>();
        if (type == Macros.EMPLOYEE) {
            demiseList = dao.pullEmployeeDemise(user.getUsername());
            for (Demise demise:
                    demiseList) {
                if (!demise.isAccepted() && !demise.isSent()){
                    updatedDemiseList.add(demise);
                }
            }
        }else if (type == Macros.ASSISTANT){
            demiseList = dao.pullDemises();
            for (Demise demise:
                    demiseList) {
                if (!demise.isAccepted()){
                    updatedDemiseList.add(demise);
                }
            }
        }

        this.bean.setDemises(updatedDemiseList);
    }

    public void acceptDemise() throws IOException {
        DAO dao = DaoManager.getDaoManager();
        List<Demise> demiseList = dao.pullDemises();
        Demise demise = this.bean.getPendingDemise();
        for (Demise d:
             demiseList) {
            if (d.getApplication().equals(demise.getApplication()) && d.isAccepted()){
                //return
                Output.println("already accepted");
            }
        }
        dao.updateDemise(demise);
    }

    public void refuseDemise() throws Exception {
        DAO dao = DaoManager.getDaoManager();
        Demise demise = this.bean.getPendingDemise();
        if (checkDemiseInList(demise, dao)){
            dao.removeDemise(demise);
        }
    }

    private boolean checkDemiseInList(Demise demise, DAO dao) throws FileNotFoundException {
        List<Demise> demiseList = dao.pullDemises();
        for (Demise d:
             demiseList) {
            if (d.getApplication().equals(demise.getApplication())){
                return true;
            }
        }
        return false;
    }
}
