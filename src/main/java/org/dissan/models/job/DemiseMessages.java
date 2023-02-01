package org.dissan.models.job;

/**
 * Class added to simulate the interaction between assistant and employee
 */

public class DemiseMessages {
    private final String sender;
    private final String receiver;
    private final Issues issues;
    private final String motivation;
    private final String shiftApplianceCode;

    public DemiseMessages(String sender, String receiver, Issues issues, String motivation, String shiftApplianceCode) {
        this.sender = sender;
        this.receiver = receiver;
        this.issues = issues;
        this.motivation = motivation;
        this.shiftApplianceCode = shiftApplianceCode;
    }

    public DemiseMessages(String sender, String receiver, String motivation, String shiftApplianceCode) {
        this(sender, receiver, Issues.DEMISE, motivation, shiftApplianceCode);
    }

    public String getSender() {
        return sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public Issues getIssues() {
        return issues;
    }

    public String getMotivation() {
        return motivation;
    }

    public String getShiftCode() {
        return this.shiftApplianceCode;
    }
}
