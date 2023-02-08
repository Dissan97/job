package org.disagn.beans;

import org.disagn.interfaces.Refresh;
import org.disagn.models.users.Employer;

public class ShiftBean implements Refresh {

    private String name;
    private String description;
    private Employer employer;

    public ShiftBean() {

        this.name = null;
        this.description = null;
        this.employer = null;
    }

    public String getName() {
        return this.name;
    }






    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Employer getEmployer() {
        return this.employer;
    }

    public void setEmployer(Employer employer) {
        this.employer = employer;
    }

    public void refresh() {
        this.name = null;
        this.employer = null;
        this.description = null;
    }
}
