package org.dissan.stateMachines.cliMachine;

import org.dissan.beans.ShiftBean;
import org.dissan.cli.io.Input;
import org.dissan.cli.io.Output;
import org.dissan.exceptions.InvalidDateException;
import org.dissan.graphicControllers.ShiftPublisherGraphic;
import org.dissan.models.users.Employer;
import org.dissan.models.users.User;
import org.dissan.stateMachines.JobStateMachine;
import org.dissan.stateMachines.JobStates;

import java.util.Objects;

import static org.dissan.Macros.BACK_CALL;

//TODO MODIFY THE BEAN CLASS
public class PublishShiftStateCli extends JobStateMachine {

    private final Employer employer;

    protected PublishShiftStateCli(User user) throws InvalidDateException {
        this.employer = new Employer(user.getUsername(), user.getPassword(), user.getName(), user.getSurname(), user.getDateOfBirth(), user.getCityOfBirth());
    }

    @Override
    public void nextState(JobStates state) {
        if (Objects.requireNonNull(state) == JobStates.PUBLISH_SHIFT) {
            this.publishShift();
        }
    }

    /**
     * Function used by employers to publish shift application
     * @return INTEGER not sure about it
     */
    private int publishShift() {
        ShiftPublisherGraphic shiftPublisherGraphic = new ShiftPublisherGraphic();
        //TODO CHECK DATA IN THE VIEW
        ShiftBean bean = new ShiftBean();
        String line;
        String page = "PublishShif@Employer{" + this.employer.getUsername() + "}";

        Output.pageMessage(page, "PRESS #BACK TO #EXIT", true);

        // getting all the input foreach attribute controlling the correct data format
        if (bean.getName() == null) {
            Output.pageMessage(page, "Job name", false);
            line = Input.line();

            if (this.exit(line, bean)) {
                return BACK_CALL.ordinal();
            }

            if (bean.setName(line) == -1){
                Output.pageMessage(page, "Job name cannot be empty", true);
                return this.publishShift();
            }

        }

        bean.setEmployer(this.employer);

        if (bean.getJobPlace() == null){
            Output.pageMessage(page, "Job place insert address", false);
            line = Input.line();

            if (this.exit(line, bean)) {
                return BACK_CALL.ordinal();
            }
            if (bean.setJobPlace(line) == -1){
                Output.pageMessage(page, "Job place cannot be empty", true);
                return this.publishShift();
            }
        }

        if (bean.getJobDateTime() == null){
            Output.pageMessage(page, "Insert day", false);
            line = Input.line();

            if (this.exit(line, bean)) {
                return BACK_CALL.ordinal();
            }

            String dateTime = line;
            Output.pageMessage(page, "Insert Time", false);
            line = Input.line();

            if (this.exit(line, bean)){
                return BACK_CALL.ordinal();
            }

            dateTime += " " + line;
            try {
                //todo add time
                shiftPublisherGraphic.setDateTime(dateTime, "");
            } catch (Exception e) {
                Output.pageMessage(page, e.getMessage(), true);
                Output.pageMessage(page, "INSERTED {" + line + "}", true);
                return this.publishShift();
            }
        }

        if (bean.getDescription() == null){
            Output.pageMessage(page, "Insert description", false);
            line = Input.line();
            bean.setDescription(line);

            if (this.exit(line, bean)){
                return BACK_CALL.ordinal();
            }
        }
        //TODO UPDATE THIS BEAN
        //shiftPublisherGraphic.publishShift(GuiStarter.getUser().getUsername(), name, place, dateTime.toString(), description);

        return 0;
    }
}
