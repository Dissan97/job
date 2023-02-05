package org.disagn.stateMachines.cliMachine;

import org.disagn.beans.JobApplierBean;
import org.disagn.cli.io.Input;
import org.disagn.cli.io.Output;
import org.disagn.exceptions.InvalidDateException;
import org.disagn.graphicControllers.JobApplierGraphic;
import org.disagn.models.job.Shift;
import org.disagn.models.job.ShiftApply;
import org.disagn.models.users.Employee;
import org.disagn.models.users.User;
import org.disagn.stateMachines.JobStateMachine;
import org.disagn.stateMachines.JobStates;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class ShiftManagerStateCli extends JobStateMachine {

    private Employee employee;
    private JobApplierGraphic jobApplierController;
    private JobApplierBean jobApplierBean;

    public ShiftManagerStateCli(User user) {
        super.setAccountInfo(user);
        try {
            this.employee = new Employee(user);
            this.jobApplierController = new JobApplierGraphic();
            this.jobApplierBean = this.jobApplierController.getBean();
        } catch (InvalidDateException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void nextState(JobStates state) {
        switch (state){
            case VIEW_APPLIES -> this.viewApplies(this.employee);
            case APPLY_SHIFT -> this.applyShift(this.employee);
        }
    }

    private void applyShift(User user) {
        List<Shift> shiftList = new ArrayList<>();
        try {
            this.jobApplierController.pullShifts(user);
            shiftList = this.jobApplierBean.getShiftList();
        } catch (FileNotFoundException e) {
            Output.exception(e);
        }
        String page = "Apply shift: " + this.getAccount();
        List<String> availableShifts = new ArrayList<>();

        for (Shift s:
             shiftList) {
            availableShifts.add(s.getCode());
        }

        if (shiftList.size() == 0){
            Output.pageMessage(page,"There' s no available shifts", true);
            return;
        }


        String cmd = "";
        while (!cmd.equals("exit") && !cmd.equals("quit")) {
            Output.printList(page, availableShifts);
            Output.pageMessage(page, "print exit or quit to leave the procedure", false);
            cmd = Input.getCmd(availableShifts);

            Shift shift = null;

            if (availableShifts.contains(cmd)){

                for (Shift s:
                     shiftList) {
                    if (s.getCode().equals(cmd)){
                        shift = s;
                    }
                }

                try {
                    this.jobApplierController.pushAppliance(shift, this.employee);
                    Output.pageMessage(page, "[" + cmd + "] applied", true);
                } catch (Exception e){
                    Output.exception(e);
                }
                break;
            }
            Output.pageMessage(page, "[" +cmd + "] not found in the shift list", true);

        }


    }

    private void viewApplies(User user) {

        this.jobApplierBean = this.jobApplierController.getBean();

        String page = "View applies: " + this.getAccount();

        try {
            this.jobApplierController.pullAppliances(user);
        } catch (FileNotFoundException e) {
            Output.exception(e);
        }
        List<ShiftApply> applyList = this.jobApplierBean.getShiftApplyList();
        Output.pageMessage(page, "apply list", true);
        for (ShiftApply apply:
             applyList) {
            Output.println("Apply: " + apply.toString() + "\nEmployer: " + apply.getEmployer() + "\nShift date: " + apply.getShiftDate());
        }

        Output.pageMessage(page, "Want to demise some appliance yes to continue", false);
        String line = Input.line();

        if (line.equalsIgnoreCase("yes") || line.equals("y") && applyList.size() > 0){
            this.demiseAppliance(applyList);
        }
    }

    private void demiseAppliance(List<ShiftApply> applyList) {
        List<String> appliances = new ArrayList<>();

        String page = "Demise appliance: " + this.getAccount();

        for (ShiftApply a:
             applyList) {
            appliances.add(a.toString());
        }




        String cmd;

        do {
            Output.printList(page, appliances);
            Output.pageMessage(page, "Choose an appliance", false);
            cmd = Input.getCmd(appliances);
            if (appliances.contains(cmd)){

                ShiftApply apply = this.getApply(cmd, applyList);
                if (apply != null) {
                    try {
                        this.jobApplierController.removeAppliance(apply);
                        Output.pageMessage(page, "appliance removed", true);
                    } catch (InvalidDateException e) {
                        Output.pageMessage(page, "Application in demise pendig state" , true);
                    } catch (ParseException | IOException e) {
                        e.printStackTrace();
                    }finally {
                        cmd = "quit";
                    }
                }
            }
        }while (!cmd.equalsIgnoreCase("quit") && !cmd.equalsIgnoreCase("exit"));
    }

    private ShiftApply getApply(String cmd, List<ShiftApply> applyList){
        ShiftApply apply = null;
        for (ShiftApply a:
                applyList) {
            if (a.toString().equals(cmd)){
                apply = a;
            }
        }
        return apply;
    }

}
