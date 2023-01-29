package org.agnese_dissan.stateMachines.cliMachine;

import org.agnese_dissan.cli.io.Output;
import org.agnese_dissan.exceptions.InvalidDateException;
import org.agnese_dissan.graphicControllers.ShiftGraphicManager;
import org.agnese_dissan.models.users.Employee;
import org.agnese_dissan.models.users.User;
import org.agnese_dissan.stateMachines.JobStateMachine;
import org.agnese_dissan.stateMachines.JobStates;

public class ShiftManagerStateCli extends JobStateMachine {
    private Employee employee;
    private final ShiftGraphicManager shiftGraphicManager;
    public ShiftManagerStateCli(User user) {

        try {
            this.employee = new Employee(user);
        } catch (InvalidDateException e) {
            e.printStackTrace();
        }
        this.shiftGraphicManager = new ShiftGraphicManager();
    }

    @Override
    public void nextState(JobStates state) {
        switch (state){
            case VIEW_APPLIES -> this.viewApplies(this.employee);
            case APPLY_SHIFT -> this.applyShift(this.employee);
        }
    }

    //TODO IMPLEMENT METHOD
    private void applyShift(Employee employee) {
        Output.println(employee.toString());
        Output.println(this.shiftGraphicManager.toString());
    }

    //TODO IMPLEMENT METHOD
    private void viewApplies(Employee employee) {
        Output.println(employee.toString());
    }




}
