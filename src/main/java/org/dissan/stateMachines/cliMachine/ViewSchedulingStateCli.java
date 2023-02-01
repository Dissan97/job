package org.dissan.stateMachines.cliMachine;

import org.dissan.beans.ScheduleBean;
import org.dissan.cli.io.Output;
import org.dissan.exceptions.InvalidDateException;
import org.dissan.graphicControllers.GraphicScheduler;
import org.dissan.models.job.ShiftApply;
import org.dissan.models.users.Employer;
import org.dissan.models.users.User;
import org.dissan.stateMachines.JobStateMachine;
import org.dissan.stateMachines.JobStates;

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
        GraphicScheduler controller = new GraphicScheduler(this.employer);
        ScheduleBean scheduleBean = controller.getBean();
        controller.getScheduling();
        List<ShiftApply> appliances = scheduleBean.getAppliances();
        //PRINT APPLIANCES
        Output.print(appliances.toString());
    }
}
