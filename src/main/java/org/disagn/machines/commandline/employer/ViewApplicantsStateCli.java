package org.disagn.machines.commandline.employer;

import org.disagn.beans.AccountBean;
import org.disagn.beans.JobApplierBean;
import org.disagn.cli.io.Input;
import org.disagn.cli.io.Output;
import org.disagn.decorator.PageContainer;
import org.disagn.graphics.ApplicationAcceptorGraphic;
import org.disagn.models.job.ShiftApply;
import org.disagn.models.users.User;
import org.disagn.machines.JobAbstractState;
import org.disagn.machines.commandline.CliMachine;


import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class ViewApplicantsStateCli extends JobAbstractState {
    private final User user;
    private List<User> userList;
    private List<ShiftApply> applyList;
    private final String page;
    private User chooseUser;

    public ViewApplicantsStateCli(User user) {
        this.user = user;
        PageContainer container = new PageContainer("MANAGE APPLICANTS", this.user);
        this.page = container.display();
    }

    @Override
    public void entry(CliMachine stateMachine) {

        ApplicationAcceptorGraphic acceptorController = new ApplicationAcceptorGraphic();
        try {
            acceptorController.getUserData(this.user);
        } catch (FileNotFoundException e) {
            Output.exception(e);
        }

        AccountBean accountBean = acceptorController.getAccountBean();
        JobApplierBean applierBean = acceptorController.getApplierBean();
        this.applyList = applierBean.getShiftApplyList();
        this.userList = accountBean.getListEmployees();

        List<String> applies = new ArrayList<>();

        int rows = applyList.size();
        String[] columnNames = new String[5];
        columnNames[0] = "num";
        columnNames[1] = "shift";
        columnNames[2] = "employee";
        columnNames[3] = "shift date";
        columnNames[4] = "accepted";
        String[][] rowEntries = new String[rows][5];
        int i = 0;

        for (ShiftApply apply:
             applyList) {
            applies.add(apply.toString());
            rowEntries[i][0] = String.valueOf(i);
            rowEntries[i][1] = apply.getShift();
            rowEntries[i][2] = apply.getEmployee();
            rowEntries[i][3] = apply.getShiftDate();
            rowEntries[i][4] = String.valueOf(apply.isAccepted());
            i++;
        }
        if (applies.isEmpty()){
            Output.pageMessage(page, "There is no appliances yet", false);
            stateMachine.getBack();
        }

        Output.printTable(this.page, "Applicants", columnNames, rowEntries);

        String cmd;

        do {
            Output.pageMessage(page, "select an apply or quit or exit to leave the procedure", false);
            cmd = Input.getCmd(applies);


            if (applies.contains(cmd)) {
                ShiftApply apply = loadUserData(cmd);
                if (apply != null) {
                    ManageApplicantStateCli applicantStateCli = new ManageApplicantStateCli(this.user, apply, this.chooseUser);
                    applicantStateCli.showAccount();

                    return;
                }
            }


        } while (!cmd.equals("EXIT") && !cmd.equals("QUIT"));
        stateMachine.getBack();

    }

    private ShiftApply loadUserData(String appliance) {

        String employee = "";
        ShiftApply shiftApply = null;
        for (ShiftApply apply:
             this.applyList) {
            if (apply.toString().equals(appliance)){
                employee = apply.getEmployee();
                shiftApply = apply;
            }
        }
        for (User u:
             this.userList) {
            if (u.getUsername().equals(employee)){
                this.chooseUser = u;
            }
        }
        return shiftApply;
    }
}
