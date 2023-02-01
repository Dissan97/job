package org.AgneseDissan.stateMachines.cliMachine;

import org.AgneseDissan.beans.ScheduleBean;
import org.AgneseDissan.cli.io.Output;
import org.AgneseDissan.exceptions.InvalidDateException;
import org.AgneseDissan.graphicControllers.GraphicScheduler;
import org.AgneseDissan.models.job.ShiftApply;
import org.AgneseDissan.models.users.Employer;
import org.AgneseDissan.models.users.User;
import org.AgneseDissan.stateMachines.JobStateMachine;
import org.AgneseDissan.stateMachines.JobStates;

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
