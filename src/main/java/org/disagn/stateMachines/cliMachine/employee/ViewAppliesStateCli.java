package org.disagn.stateMachines.cliMachine.employee;


import org.disagn.beans.JobApplierBean;
import org.disagn.cli.io.Input;
import org.disagn.cli.io.Output;
import org.disagn.exceptions.InvalidDateException;
import org.disagn.graphicControllers.JobApplierGraphic;
import org.disagn.models.job.ShiftApply;
import org.disagn.models.users.Employee;
import org.disagn.stateMachines.JobAbstractState;
import org.disagn.stateMachines.cliMachine.CliMachine;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;


public class ViewAppliesStateCli extends JobAbstractState {
    private final Employee user;
    private JobApplierGraphic controller;

    public ViewAppliesStateCli(Employee employee) {
        this.user = employee;
    }

    @Override
    public void entry(CliMachine stateMachine) {
        this.controller = new JobApplierGraphic();
        JobApplierBean bean = this.controller.getBean();

        String page = "View applies: " + this.user.getUsername();

        try {
            this.controller.pullAppliances(user);
        } catch (FileNotFoundException e) {
            Output.exception(e);
        }
        List<ShiftApply> applyList = bean.getShiftApplyList();
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
        stateMachine.getBack();
    }

    private void demiseAppliance(List<ShiftApply> applyList) {
        List<String> appliances = new ArrayList<>();

        String page = "Demise appliance: " + this.user.getUsername();

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
                        this.controller.removeAppliance(apply);
                        Output.pageMessage(page, "appliance removed", true);
                    } catch (InvalidDateException e) {
                        Output.pageMessage(page, "Application in demise pending state" , true);
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
