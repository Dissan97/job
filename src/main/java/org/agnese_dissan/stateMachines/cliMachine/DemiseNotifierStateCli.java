package org.agnese_dissan.stateMachines.cliMachine;

import org.agnese_dissan.cli.io.Output;
import org.agnese_dissan.daos.DaoManager;
import org.agnese_dissan.exceptions.InvalidDateException;
import org.agnese_dissan.models.job.DemiseMessages;
import org.agnese_dissan.models.users.Assistant;
import org.agnese_dissan.models.users.Employee;
import org.agnese_dissan.models.users.User;
import org.agnese_dissan.stateMachines.JobStateMachine;
import org.agnese_dissan.stateMachines.JobStates;

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
