package org.disagn.states.commandline.employer;



import org.disagn.cli.io.Input;
import org.disagn.cli.io.Printer;
import org.disagn.decorator.PageContainer;
import org.disagn.graphics.ShiftPublisherGraphic;
import org.disagn.models.users.User;
import org.disagn.states.JobAbstractState;
import org.disagn.states.commandline.CliMachine;


public class PublishShiftStateCli extends JobAbstractState {

    private final String page;
    private final User user;
    public PublishShiftStateCli(User user) {
        this.user = user;
        PageContainer container = new PageContainer("DEMISE MANAGER", this.user);
        this.page = container.display();
    }


    @Override
    public void entry(CliMachine stateMachine){
        ShiftPublisherGraphic controller = new ShiftPublisherGraphic();

        Printer.pageMessage(this.page, "Write the information", true);

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
                Printer.pageMessage(page, "Shift published", true);
            }
        } catch (Exception e) {
            Printer.exception(e);
        }

        stateMachine.getBack();
    }
}
