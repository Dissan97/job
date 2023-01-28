package org.agnese_dissan.cli.cliMachine;

import org.agnese_dissan.Macros;
import org.agnese_dissan.cli.io.Input;
import org.agnese_dissan.cli.io.Output;
import org.agnese_dissan.exceptions.InvalidDateException;
import org.agnese_dissan.models.users.Assistant;
import org.agnese_dissan.models.users.Employee;
import org.agnese_dissan.models.users.User;

import java.util.ArrayList;
import java.util.List;

public class DemiseMangerState extends CliStateMachine{

    private Employee employee = null;
    private Assistant assistant = null;
    private ShiftManagerState shiftManagerState = null;
    private DemiseNotifierState demiseNotifierState = null;
    public DemiseMangerState(User user) {
        try {
            this.demiseNotifierState = new DemiseNotifierState(user);
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
    public void nextState(CliStates state) {


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
            this.shiftManagerState = new ShiftManagerState(this.employee);
        }
        this.shiftManagerState.nextState(CliStates.VIEW_APPLIES);
    }
}
