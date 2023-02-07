package org.disagn.stateMachines.cliMachine.employer;



import org.disagn.cli.io.Input;
import org.disagn.cli.io.Output;
import org.disagn.graphicControllers.ShiftPublisherGraphic;
import org.disagn.models.users.User;
import org.disagn.stateMachines.JobAbstractState;
import org.disagn.stateMachines.cliMachine.CliMachine;


public class PublishShiftStateCli extends JobAbstractState {

    private final String page;
    private final User user;
    public PublishShiftStateCli(User user) {
        this.user = user;
        this.page = "Demise manager: " + user.getUsername();
    }


    @Override
    public void entry(CliMachine stateMachine){
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
                controller.publishShift(this.user.getUsername(), name, place, dateTime, description);
                Output.pageMessage(page, "Shift published", true);
            }
        } catch (Exception e) {
            Output.exception(e);
        }

        stateMachine.getBack();
    }
}
