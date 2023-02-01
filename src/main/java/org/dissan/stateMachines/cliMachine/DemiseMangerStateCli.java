package org.dissan.stateMachines.cliMachine;

import org.dissan.cli.io.Input;
import org.dissan.cli.io.Output;
import org.dissan.exceptions.InvalidDateException;
import org.dissan.models.users.Assistant;
import org.dissan.models.users.Employee;
import org.dissan.models.users.User;
import org.dissan.stateMachines.JobStateMachine;
import org.dissan.stateMachines.JobStates;

import java.util.ArrayList;
import java.util.List;

public class DemiseMangerStateCli extends JobStateMachine {

    private Employee employee = null;
    private Assistant assistant = null;
    private ShiftManagerStateCli shiftManagerState = null;
    private DemiseNotifierStateCli demiseNotifierState = null;
    public DemiseMangerStateCli(User user) {
        try {
            this.demiseNotifierState = new DemiseNotifierStateCli(user);
            this.demiseNotifierState.nextState(null);
            switch (user.getUserType()) {
                case EMPLOYEE -> this.employee = new Employee(user);
                case ASSISTANT -> this.assistant = new Assistant(user);
            }
        } catch (InvalidDateException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void nextState(JobStates state) {


        if (this.employee != null){
            this.demiseShift();
        }
        if (this.assistant != null){
            switch (state) {
                case CONTROL_DEMISE ->   this.manageShiftDemise();
                case DEMISE_SHIFT -> {}
            }
        }
    }

    private void manageShiftDemise() {
    }

    private void demiseShift() {
        List<String> commandList = new ArrayList<>();
        //DemiseGraphicController graphicController = new DemiseGraphicController();
        Output.println("hello");
        //DemiseBean bean = graphicController.getDemiseBean();


        commandList.add("PRINT_CANDIDATES");
        commandList.add("CHOOSE_SHIFT");
        commandList.add("HELP");
        commandList.add("EXIT");

        String page = "DEMISE"+ super.getAccount();

        Output.pageMessage(page, "choose a command type help to list commands", true);
        while (true) {

            Output.pageMessage(page,"",false);
            String line = Input.getCmd(commandList);

            switch (line) {
                case "PRINT_MYAPPLICATION" ->
                    //TODO implements this
                    this.checkShiftManager();

                case "CHOOSE_SHIFT" -> {
                    //TODO implements this
                    this.checkShiftManager();
                    Output.println("CHOOSE");
                }
                case "HELP" ->
                    Output.printList(page, commandList);

                case "EXIT" -> {
                    return;
                }
            }

        }
    }

    private void checkShiftManager(){
        if (this.shiftManagerState == null){
            this.shiftManagerState = new ShiftManagerStateCli(this.employee);
        }
        this.shiftManagerState.nextState(JobStates.VIEW_APPLIES);
    }
}
