package org.disagn.stateMachines.cliMachine.employer;

import org.disagn.beans.AccountBean;
import org.disagn.beans.JobApplierBean;
import org.disagn.cli.io.Input;
import org.disagn.cli.io.Output;
import org.disagn.decorator.PageContainer;
import org.disagn.graphicControllers.ApplicationAcceptorGraphic;
import org.disagn.models.job.ShiftApply;
import org.disagn.models.users.User;
import org.disagn.stateMachines.JobAbstractState;
import org.disagn.stateMachines.cliMachine.AccountStateCli;
import org.disagn.stateMachines.cliMachine.CliMachine;


import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class ViewApplicantsStateCli extends JobAbstractState {
    private final User user;
    private List<User> userList;
    private List<ShiftApply> applyList;
    private final String page;
    private User chooseUser;
    private ApplicationAcceptorGraphic acceptorController;
    public ViewApplicantsStateCli(User user) {
        this.user = user;
        PageContainer container = new PageContainer("MANAGE APPLICANTS", this.user);
        this.page = container.display();
    }

    @Override
    public void entry(CliMachine stateMachine) {

        this.acceptorController = new ApplicationAcceptorGraphic();
        try {
            this.acceptorController.getUserData(this.user);
        } catch (FileNotFoundException e) {
            Output.exception(e);
        }

        AccountBean accountBean = this.acceptorController.getAccountBean();
        JobApplierBean applierBean = this.acceptorController.getApplierBean();
        this.applyList = applierBean.getShiftApplyList();
        this.userList = accountBean.getListEmployees();

        List<String> applies = new ArrayList<>();

        for (ShiftApply apply:
             applyList) {
            applies.add(apply.toString());
        }
        if (applies.size() < 1){
            Output.pageMessage(page, "There is no appliances yet", false);
            stateMachine.getBack();
        }
        String cmd;

        while (true){
            Output.printList(page, applies);
            Output.pageMessage(page, "select an apply or quit or exit to leave the procedure", false);
            cmd = Input.getCmd(applies);

            if (cmd.equals("EXIT") || cmd.equals("QUIT")){
                break;
            }

            if (applies.contains(cmd)){
                ShiftApply apply = loadUserData(cmd);
                if (apply != null) {
                    ManageApplicantStateCli applicantStateCli = new ManageApplicantStateCli(this.user, apply, this.chooseUser);
                    applicantStateCli.showAccount();

                    break;
                }
            }



        }
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
