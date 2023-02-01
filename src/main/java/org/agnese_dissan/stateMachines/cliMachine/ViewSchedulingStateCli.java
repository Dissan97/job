package org.agnese_dissan.stateMachines.cliMachine;

import org.agnese_dissan.beans.ScheduleBean;
import org.agnese_dissan.cli.io.Output;
import org.agnese_dissan.exceptions.InvalidDateException;
import org.agnese_dissan.graphicControllers.GraphicScheduler;
import org.agnese_dissan.models.job.ShiftApply;
import org.agnese_dissan.models.users.Employer;
import org.agnese_dissan.models.users.User;
import org.agnese_dissan.stateMachines.JobStateMachine;
import org.agnese_dissan.stateMachines.JobStates;

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
