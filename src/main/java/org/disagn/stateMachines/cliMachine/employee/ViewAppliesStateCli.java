package org.disagn.stateMachines.cliMachine.employee;


import org.disagn.beans.JobApplierBean;
import org.disagn.cli.io.Input;
import org.disagn.cli.io.Output;
import org.disagn.decorator.PageContainer;
import org.disagn.exceptions.InvalidDateException;
import org.disagn.graphicControllers.JobApplierGraphic;
import org.disagn.models.job.ShiftApply;
import org.disagn.models.users.Employee;
import org.disagn.stateMachines.JobAbstractState;
import org.disagn.stateMachines.cliMachine.CliMachine;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;


public class ViewAppliesStateCli extends JobAbstractState {
    private final Employee user;
    private final String page;
    private String[] columnNames;
    private String[][] rowEntries;
    private String tableName;
    private JobApplierGraphic controller;

    public ViewAppliesStateCli(Employee employee) {
        this.user = employee;
        PageContainer container = new PageContainer("DEMISE MANAGER", this.user);
        this.page = container.display();
    }

    @Override
    public void entry(CliMachine stateMachine) {
        this.controller = new JobApplierGraphic();
        JobApplierBean bean = this.controller.getBean();

        try {
            this.controller.pullAppliances(user);
        } catch (FileNotFoundException e) {
            Output.exception(e);
        }
        List<ShiftApply> applyList = bean.getShiftApplyList();
        Output.pageMessage(page, "apply list", true);
        int rows = applyList.size();
        this.columnNames = new String[5];
        this.columnNames[0] = "num";
        this.columnNames[1] = "shift";
        this.columnNames[2] = "employer";
        this.columnNames[3] = "shift date";
        this.columnNames[4] = "accepted";
        this.rowEntries = new String[rows][5];
        int i = 0;
        for (ShiftApply apply:
                applyList) {
            this.rowEntries[i][0] = String.valueOf(i);
            this.rowEntries[i][1] = apply.getShift();
            this.rowEntries[i][2] = apply.getEmployer();
            this.rowEntries[i][3] = apply.getShiftDate();
            this.rowEntries[i][4] = String.valueOf(apply.isAccepted());
            i++;
            //Output.println("Apply: " + apply.toString() + "\nEmployer: " + apply.getEmployer() + "\nShift date: " + apply.getShiftDate());
        }
        this.tableName = "Applies";
        Output.printTable(this.page, this.tableName, this.columnNames, this.rowEntries);

        Output.pageMessage(page, "Want to demise some appliance yes to continue", false);
        String line = Input.line();

        if (line.equalsIgnoreCase("yes") || line.equals("y") && applyList.size() > 0){
            this.demiseAppliance(applyList);
        }
        stateMachine.getBack();
    }

    private void demiseAppliance(List<ShiftApply> applyList) {
        List<String> appliances = new ArrayList<>();

        PageContainer container = new PageContainer("DEMISE APPLIANCE", this.user);
        String page = container.display();

        for (ShiftApply a:
                applyList) {
            appliances.add(a.toString());
        }

        String cmd;

        do {
            Output.printTable(page, this.tableName, this.columnNames, this.rowEntries);
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
                    } catch (ParseException | IOException | SQLException e) {
                        Output.exception(e);
                    } finally {
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
