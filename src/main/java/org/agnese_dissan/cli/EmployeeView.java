package org.agnese_dissan.cli;

import org.agnese_dissan.beans.AccountBean;
import org.agnese_dissan.beans.DemiseBean;
import org.agnese_dissan.cli.io.Input;
import org.agnese_dissan.cli.io.Output;
import org.agnese_dissan.exceptions.InvalidDateException;
import org.agnese_dissan.graphicControllers.DemiseGraphicController;
import org.agnese_dissan.interfaces.JobView;
import org.agnese_dissan.models.job.Shift;
import org.agnese_dissan.models.job.ShiftApply;
import org.agnese_dissan.models.users.Employee;
import org.agnese_dissan.models.users.User;

import java.util.ArrayList;
import java.util.List;

public class EmployeeView implements JobView {


    private Employee employee;
    private final AccountBean accountBean;
    private final List<String> commandList = new ArrayList<>();

    private DemiseGraphicController graphicController;
    private DemiseBean bean;
    private String pageMsg;

    public EmployeeView(User user) {
        try {
            employee = new Employee(user);
        } catch (InvalidDateException e) {
            e.printStackTrace();
        }

        accountBean = new AccountBean(user);
        this.pageMsg = "@EMPLOYEE{"+user.getUsername()+"}";
        commandList.add("ACCOUNT");
        commandList.add("APPLY_SHIFT");
        commandList.add("VIEW_APPLIES");
        commandList.add("DEMISE_SHIFT");
        commandList.add("HELP");
        commandList.add("EXIT");
    }

    @Override
    public void startUi() {
        String page = "HOME" + this.pageMsg;
        Output.pageMessage(page, "Type help to get list", false);
        while (true) {

            String line = Input.getCmd(this.commandList);

            switch (line) {
                case "ACCOUNT" ->
                    this.getAccountInfo();
                case "APPLY_SHIFT" ->
                    applyShift();
                case "VIEW_APPLIES" ->
                    viewApplies();
                case "DEMISE_SHIFT" ->
                    this.demiseShift();
                case "HELP" ->
                    Output.printList("HOME", this.commandList);
                case "EXIT" -> {
                    Output.pageMessage("HOME", employee.getUsername() + " Bye...", true);
                    System.exit(0);
                }
                case "" -> {}
                default -> {
                    Output.pageMessage("HOME", "PLEASE TYPE THIS COMMAND", true);
                    Output.printList("HOME", this.commandList);
                }
            }

        }

    }

    private void getAccountInfo(){
        String page = "HOME{" + accountBean.getUsername() + "}";
        Output.pageMessage(page, "NAME: " + accountBean.getName() , true);
        Output.pageMessage(page, "SURNAME: " + accountBean.getSurname(), true);
        Output.pageMessage(page, "DATE OF BIRTH: " + accountBean.getDateOfBirth(), true);
        Output.pageMessage(page, "CITY OF BIRTH: " + accountBean.getCityOfBirth(), true);
    }

    private void applyShift() {

    }
    private void viewApplies() {
    }

    private void demiseShift() {
        List<String> commandList = new ArrayList<>();
        this.graphicController = new DemiseGraphicController();
        this.bean = this.graphicController.getDemiseBean();
        Thread thread = new Thread(this::notifyDemise);
        thread.start();


        commandList.add("PRINT_CANDIDATES");
        commandList.add("CHOOSE_SHIFT");
        commandList.add("HELP");
        commandList.add("EXIT");

        String page = "DEMISE"+ this.pageMsg;

        Output.pageMessage(page, "choose a command type help to list commands", true);
        while (true) {

            Output.pageMessage(page,"",false);
            String line = Input.getCmd(commandList);

            switch (line) {
                case "PRINT_MYAPPLICATION" ->{
                    //TODO implements this
                    List<String> myApplication = getMyApplication();
                    Output.printList(page, myApplication);
                }
                case "CHOOSE_SHIFT" -> {
                    //TODO implements this
                    List<String> myApplication = getMyApplication();
                    line = Input.getCmd(myApplication);
                    line = this.graphicController.newDemise(line);
                    Output.pageMessage(page, line, true);
                }
                case "HELP" ->{
                    Output.printList(page, commandList);
                }
                case "EXIT" ->{
                    return;
                }
            }

        }
    }

    private List<String> getMyApplication(){
        //TODO this return null so need to be added
        List<ShiftApply> listApplication;
        listApplication = this.bean.getApplications();

        if (listApplication == null){
            this.graphicController.getApplication(this.employee);
            listApplication = this.bean.getApplications();
        }

        List<String> codes = new ArrayList<>();

        for (ShiftApply apply: listApplication
             ) {
            codes.add(apply.getApplicationCode());
        }

        return codes;
    }

    //is protected because need to be used in Tests

    //TODO Handle multi session with a class for this can be simulated by a configuration file that will charge some meta data.
    private void notifyDemise() {
        Output.println("hello world");
    }

}
