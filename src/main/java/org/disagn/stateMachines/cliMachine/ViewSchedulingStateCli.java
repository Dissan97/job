package org.disagn.stateMachines.cliMachine;

import org.disagn.beans.ShiftSchedulerBean;
import org.disagn.cli.io.Output;
import org.disagn.exceptions.InvalidDateException;
import org.disagn.graphicControllers.ShiftSchedulerGraphic;
import org.disagn.models.job.ShiftApply;
import org.disagn.models.users.Employer;
import org.disagn.models.users.User;
import org.disagn.stateMachines.JobStateMachine;
import org.disagn.stateMachines.JobStates;

import java.util.List;
import java.util.Objects;

public class ViewSchedulingStateCli extends JobStateMachine {

    private final Employer employer;

    public ViewSchedulingStateCli(User user) throws InvalidDateException {
        this.employer = new Employer(user.getUsername(), user.getPassword(), user.getName(), user.getSurname(), user.getDateOfBirth(), user.getCityOfBirth());
    }

    @Override
    public void nextState(JobStates state) {
        if (Objects.requireNonNull(state) == JobStates.VIEW_SCHEDULING) {
            this.viewScheduling();
        }
    }

    /**
     * Function that show now day candidates for a specific job
     */
    private void viewScheduling(){
        ShiftSchedulerGraphic controller = new ShiftSchedulerGraphic(this.employer);
        ShiftSchedulerBean scheduleBean = controller.getBean();
        controller.getScheduling();
        List<ShiftApply> appliances = scheduleBean.getSchedules();
        //PRINT APPLIANCES
        Output.print(appliances.toString());
    }
}
