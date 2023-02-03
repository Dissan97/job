package org.dissan.models.job;

public class Demise {

    private final String application;
    private final String employee;
    private String motivation;
    private boolean sent = false;
    private boolean accepted = false;
    public Demise(ShiftApply apply, String motivation) {
        this.employee = apply.getEmployee();
        this.motivation = motivation;
        this.application = apply.toString();
    }


    public String getApplication() {
        return application;
    }

    public String getEmployee() {
        return employee;
    }

    public String getMotivation() {
        return motivation;
    }


    public boolean isSent() {
        return sent;
    }

    public void sent() {
        this.sent = true;
    }

    public boolean isAccepted() {
        return accepted;
    }

    public void setAccepted(boolean accepted) {
        this.accepted = accepted;
    }

    public void setMotivation(String motivation) {
        this.motivation = motivation;
    }
}
