package org.AgneseDissan.stateMachines.cliMachine;

import org.AgneseDissan.cli.io.Output;
import org.AgneseDissan.exceptions.InvalidDateException;
import org.AgneseDissan.graphicControllers.ShiftGraphicManager;
import org.AgneseDissan.models.users.Employee;
import org.AgneseDissan.models.users.User;
import org.AgneseDissan.stateMachines.JobStateMachine;
import org.AgneseDissan.stateMachines.JobStates;

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
