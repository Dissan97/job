package org.dissan.stateMachines.cliMachine;

import org.dissan.cli.io.Output;
import org.dissan.exceptions.InvalidDateException;
import org.dissan.graphicControllers.ShiftGraphicManager;
import org.dissan.models.users.Employee;
import org.dissan.models.users.User;
import org.dissan.stateMachines.JobStateMachine;
import org.dissan.stateMachines.JobStates;

public class ShiftManagerStateCli extends JobStateMachine {

    //TODO MANAGE THIS CLASS
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
