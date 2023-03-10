package org.disagn.controllers;


import org.disagn.Macros;
import org.disagn.beans.DemiseBean;
import org.disagn.cli.io.Printer;
import org.disagn.daos.DaoManager;
import org.disagn.exceptions.ElementAlreadyComputedException;
import org.disagn.exceptions.InvalidDateException;
import org.disagn.interfaces.DAO;
import org.disagn.models.job.Demise;
import org.disagn.models.users.User;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DemiseController {

    private final DemiseBean bean;
    
    public DemiseController(DemiseBean bean){
        this.bean = bean;
    }

    public void sendDemise(Demise demise) throws IOException, ElementAlreadyComputedException {
        //Should be applied extra control to view if that demise is already sent
        DAO dao = DaoManager.getDaoManager();
        try {
            this.pullDemises(new User());
        } catch (InvalidDateException e) {
            Printer.print("Something wrong");
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
                Printer.print("already accepted");
            }
        }
        dao.updateDemise(demise);
    }

    public void refuseDemise() throws IOException {
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
