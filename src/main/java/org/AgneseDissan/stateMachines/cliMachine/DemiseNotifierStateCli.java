package org.AgneseDissan.stateMachines.cliMachine;

import org.AgneseDissan.cli.io.Output;
import org.AgneseDissan.daos.DaoManager;
import org.AgneseDissan.exceptions.InvalidDateException;
import org.AgneseDissan.models.job.DemiseMessages;
import org.AgneseDissan.models.users.Assistant;
import org.AgneseDissan.models.users.Employee;
import org.AgneseDissan.models.users.User;
import org.AgneseDissan.stateMachines.JobStateMachine;
import org.AgneseDissan.stateMachines.JobStates;

import java.util.List;

public class DemiseNotifierStateCli extends JobStateMachine implements Runnable{

    private final Thread thread;
    private Employee employee;
    private Assistant assistant;
    private final User user;

    public DemiseNotifierStateCli(User user) {
        this.thread = new Thread(this);
        this.user = user;
        try {
        switch (user.getUserType()){

                case EMPLOYEE -> this.employee = new Employee(user);
                case ASSISTANT -> this.assistant = new Assistant(user);

        }
        } catch (InvalidDateException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void nextState(JobStates state) {
        if (state == null){
            this.thread.start();
        }
    }

    @Override
    public void run() {
        //TODO implements a semaphore ?? to avoid cs problems use controller cause this isn't a controller
        DaoManager daoManager = DaoManager.getDaoManager();
        List< DemiseMessages> demiseMessagesList = daoManager.checkMessage(this.user);
        if (demiseMessagesList != null) {
            Output.pageMessage("Notify", "Theres pending issues", true);
            int index = 0;
            for (DemiseMessages messages : demiseMessagesList) {
                Output.println("[" + index + "] Code: " + messages.getShiftCode() + " | " + "Employee: " + messages.getSender() + " | " + "Issue: " + messages.getIssues().name() + " | Motivation:\n" + messages.getMotivation());
            }
        }

    }
}
