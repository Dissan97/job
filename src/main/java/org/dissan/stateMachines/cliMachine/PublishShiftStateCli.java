package org.dissan.stateMachines.cliMachine;

import org.dissan.beans.ShiftBean;
import org.dissan.cli.io.Input;
import org.dissan.cli.io.Output;
import org.dissan.exceptions.InvalidDateException;
import org.dissan.graphicControllers.ShiftPublisherGraphic;
import org.dissan.gui.GuiManager;
import org.dissan.gui.GuiStarter;
import org.dissan.models.users.Employer;
import org.dissan.models.users.User;
import org.dissan.stateMachines.JobStateMachine;
import org.dissan.stateMachines.JobStates;

import java.util.Objects;

import static org.dissan.Macros.BACK_CALL;

//TODO MODIFY THE BEAN CLASS
public class PublishShiftStateCli extends JobStateMachine {

    private final String page;

    protected PublishShiftStateCli(User user) throws InvalidDateException {
        this.setAccountInfo(user);
        this.page = "Demise manager: " + this.getAccount();
    }

    @Override
    public void nextState(JobStates state) {
        if (Objects.requireNonNull(state) == JobStates.PUBLISH_SHIFT) {
            this.publish();
        }
    }



    private void publish(){
        ShiftPublisherGraphic controller = new ShiftPublisherGraphic();

        Output.pageMessage(this.page, "Write the information", true);

        String name = Input.getInfo(this.page, "insert job name");
        String place = Input.getInfo(this.page, "insert job place");
        String date = Input.getInfo(this.page, "insert job date yyyy-MM-dd");
        String time = Input.getInfo(this.page, "insert job time");
        String description = Input.getInfo(this.page, "insert job description");

        try {
            String dateTime = controller.setDateTime(date, time);
            if (controller.isGood(name)
                    && controller.isGood(place)
                    && controller.isGood(date)
                    && controller.isGood(time)
            ) {
                if (description.equals("")){
                    description = "Job appliance";
                }
                controller.publishShift(GuiStarter.getUser().getUsername(), name, place, dateTime, description);
                GuiManager.popUp("Shift published");
            }
        } catch (Exception e) {
            Output.exception(e);
        }
    }
}
