package org.agnese_dissan.cli.cliMachine;

import org.agnese_dissan.beans.ShiftBean;
import org.agnese_dissan.cli.io.Input;
import org.agnese_dissan.cli.io.Output;
import org.agnese_dissan.exceptions.InvalidDateException;
import org.agnese_dissan.graphicControllers.ShiftPublisherGraphic;
import org.agnese_dissan.models.users.Employer;
import org.agnese_dissan.models.users.User;

import java.util.Objects;

import static org.agnese_dissan.Macros.BACK_CALL;

public class PublishShiftState extends CliStateMachine {

    private final Employer employer;

    protected PublishShiftState(User user) throws InvalidDateException {
        this.employer = new Employer(user.getUsername(), user.getPassword(), user.getName(), user.getSurname(), user.getDateOfBirth(), user.getCityOfBirth());
    }

    @Override
    public void nextState(CliStates state) {
        if (Objects.requireNonNull(state) == CliStates.PUBLISH_SHIFT) {
            this.publishShift();
        }
    }

    /**
     * Function used by employers to publish shift application
     * @return INTEGER not sure about it
     */
    private int publishShift() {
        ShiftPublisherGraphic shiftPublisherGraphic = new ShiftPublisherGraphic();
        ShiftBean bean = shiftPublisherGraphic.getBean();
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

        if (bean.getDateTime() == null){
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
                bean.setDateTime(dateTime);
            } catch (InvalidDateException e) {
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

        shiftPublisherGraphic.publishShift();

        return 0;
    }
}
