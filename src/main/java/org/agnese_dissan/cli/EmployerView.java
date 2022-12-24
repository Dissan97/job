package org.agnese_dissan.cli;

import org.agnese_dissan.cli.io.Input;
import org.agnese_dissan.cli.io.Output;
import org.agnese_dissan.interfaces.JobView;
import org.agnese_dissan.models.Employee;
import org.agnese_dissan.models.User;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.DataFormatException;

public class EmployerView implements JobView {

    private final List<String> commandList = new ArrayList<>();
    private Employee employee;
    public EmployerView(User user) {
        try {
            employee = new Employee(user);
        } catch (DataFormatException e) {
            e.printStackTrace();
        }
        commandList.add("ACCOUNT");
        commandList.add("BOOK_ROOM");
        commandList.add("VIEW_ROOMS");
        commandList.add("CHECK_IN");
        commandList.add("HELP");
        commandList.add("EXIT");
    }

    @Override
    public void startUi() {
        Output.println("[HOME{("+ employee.getUsername()+")}]");
        Output.pageMessage("[HOME]", "Type help to get list", true);
        while (true) {

            Output.print("HOME:-> ");
            String line = Input.getCmd();

            if (line.equals("ACCOUNT")){
                this.getAccountInfo();
            } else if (line.equals("BOOK_ROOM")) {

            } else if (line.equals("VIEW_ROOMS")) {

            } else if (line.equals("CHECK_IN")) {
                this.checkIn();
            } else if (line.equals("HELP")) {
                Output.getCommandList("HOME", this.commandList);
            } else if (line.equals("EXIT")) {
                Output.pageMessage("HOME", employee.getUsername() + " Bye...", true);
                System.exit(0);
            } else if (line.equals("")) {
                continue;
            } else {
                Output.pageMessage("HOME", "PLEASE TYPE THIS COMMAND", true);
                Output.getCommandList("HOME", this.commandList);
            }

        }

    }

    private void getAccountInfo(){
        String page = "HOME{" + employee.getUsername() + "}";
        Output.pageMessage(page, "NAME: " + employee.getName() , true);
        Output.pageMessage(page, "SURNAME: " + employee.getSurname(), true);
        Output.pageMessage(page, "DATE OF BIRTH: " + employee.getDateOfBirth(), true);
        Output.pageMessage(page, "CITY OF BIRTH: " + employee.getCityOfBirth(), true);
    }

    private void checkIn() {
    }


}
