package org.agnese_dissan.graphicControllers;

import org.agnese_dissan.beans.AccountBean;
import org.agnese_dissan.beans.JobApplierBean;
import org.agnese_dissan.controllers.ApplicationAcceptor;
import org.agnese_dissan.exceptions.ApplyNotExistException;
import org.agnese_dissan.models.job.ShiftApply;
import org.agnese_dissan.models.users.User;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ApplicationAcceptorGraphic {

    private final AccountBean accountBean;
    private JobApplierBean applierBean;
    private ApplicationAcceptor controller;

    public ApplicationAcceptorGraphic() {
        this.controller = new ApplicationAcceptor();
        this.accountBean = new AccountBean();
        this.applierBean = new JobApplierBean();
    }

    public AccountBean getAccountBean() {
        return this.accountBean;
    }

    public void getUserData(User employer) throws FileNotFoundException {
        JobApplierGraphic jobApplierController = new JobApplierGraphic();
        this.applierBean = jobApplierController.getBean();
        jobApplierController.pullAppliances(employer);

        LoginGraphic loginController = new LoginGraphic();
        loginController.pullEmployee(this.accountBean);

        List<User> userList = this.accountBean.getListEmployees();
        List<ShiftApply> applyList = applierBean.getShiftApplyList();
        List<User> filteredUserList = new ArrayList<>();

        if (applyList != null && userList != null){
            for (ShiftApply apply:
                 applyList) {
                for (User user:
                     userList) {
                    if (apply.getEmployee().equals(user.getUsername())){
                        filteredUserList.add(user);
                    }
                }
            }
            this.accountBean.setEmployeeList(filteredUserList);
        }
    }

    public JobApplierBean getApplierBean() {
        return this.applierBean;
    }

    public void manageCandidate(User employee, User employer, ShiftApply apply, boolean accept) throws IOException, ApplyNotExistException {
        if (employee != null){
            this.controller.manageCandidate(employee, employer, apply, accept);
        }
    }

}