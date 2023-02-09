package org.disagn.stateMachines.cliMachine.employee;


import org.disagn.beans.JobApplierBean;
import org.disagn.cli.io.Input;
import org.disagn.cli.io.Output;
import org.disagn.decorator.PageContainer;
import org.disagn.graphicControllers.JobApplierGraphic;
import org.disagn.models.job.Shift;
import org.disagn.models.users.Employee;
import org.disagn.stateMachines.JobAbstractState;
import org.disagn.stateMachines.cliMachine.CliMachine;


import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;


public class ApplyShift extends JobAbstractState {

    private final Employee user;
    private String page;

    public ApplyShift(Employee employee) {
        super();
        this.user = employee;
        PageContainer container = new PageContainer("DEMISE MANAGER", this.user);
        this.page = container.display();
    }

    @Override
    public void entry(CliMachine stateMachine) {
        JobApplierGraphic controller = new JobApplierGraphic();
        JobApplierBean bean = controller.getBean();

        List<Shift> shiftList = new ArrayList<>();
        try {
            controller.pullShifts(this.user);
            shiftList = bean.getShiftList();
        } catch (FileNotFoundException e) {
            Output.exception(e);
        }
        this.page = "Apply shift: " + this.user.getUsername();
        List<String> availableShifts = new ArrayList<>();

        for (Shift s :
                shiftList) {
            availableShifts.add(s.getCode());
        }

        if (shiftList.size() == 0) {
            Output.pageMessage(page, "There' s no available shifts", true);
            return;
        }

        applyShift(availableShifts, shiftList, controller);

        stateMachine.getBack();
    }

    private void applyShift(List<String> availableShifts, List<Shift> shiftList, JobApplierGraphic controller) {
        String cmd;
        while (true) {
            Output.printList(page, availableShifts);
            Output.pageMessage(page, "print exit or quit to leave the procedure", false);
            cmd = Input.getCmd(availableShifts);

            if (cmd.equals("EXIT") || cmd.equals("QUIT")){
                break;
            }
            Shift shift = null;

            if (availableShifts.contains(cmd)) {

                for (Shift s :
                        shiftList) {
                    if (s.getCode().equals(cmd)) {
                        shift = s;
                    }
                }

                try {
                    controller.pushAppliance(shift, this.user);
                    Output.pageMessage(page, "[" + cmd + "] applied", true);
                } catch (Exception e) {
                    Output.exception(e);
                }
                break;
            }
            Output.pageMessage(page, "[" + cmd + "] not found in the shift list", true);
        }
    }

}
