package org.disagn.states.commandline.employer;

import org.disagn.cli.io.Input;
import org.disagn.cli.io.Printer;
import org.disagn.decorator.PageContainer;
import org.disagn.graphics.ApplicationAcceptorGraphic;
import org.disagn.models.job.ShiftApply;
import org.disagn.models.users.User;
import org.disagn.states.JobAbstractState;
import org.disagn.states.commandline.AccountStateCli;
import org.disagn.states.commandline.CliMachine;

public class ManageApplicantStateCli extends JobAbstractState {


    private final User user;
    private final String page;
    private final ShiftApply apply;
    private final User employee;
    protected ManageApplicantStateCli(User user, ShiftApply apply, User employee) {
        this.user = user;
        this.apply = apply;
        this.employee = employee;
        PageContainer container = new PageContainer("USER INFORMATION", user);
        this.page = container.display();
    }

    @Override
    public void entry(CliMachine stateMachine) {


        ApplicationAcceptorGraphic acceptorController = new ApplicationAcceptorGraphic();

        String line = Input.getInfo(this.page, "accept this employee y to hire - n to demise");
        if (line.equalsIgnoreCase("yes") || line.equalsIgnoreCase("y")){
            try {
                acceptorController.manageCandidate(this.employee, this.user, this.apply, true);
                Printer.pageMessage(this.page, "user accepted", true);
            }catch (Exception e){
                Printer.exception(e);
            }
        }else if (line.equalsIgnoreCase("no") || line.equalsIgnoreCase("n")) {
            try {
                acceptorController.manageCandidate(this.employee, this.user, apply, true);
                Printer.pageMessage(this.page, "user refused", true);
            } catch (Exception e) {
                Printer.exception(e);
            }
        }
    }

    public void showAccount() {
        CliMachine stateMachine = new CliMachine(this.user);
        stateMachine.setCurrentState(this);
        stateMachine.setHome();
        AccountStateCli accountStateCli = new AccountStateCli(this.employee);
        stateMachine.changeState(accountStateCli);
    }
}
