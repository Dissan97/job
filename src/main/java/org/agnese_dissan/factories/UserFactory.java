package org.agnese_dissan.factories;


import org.agnese_dissan.exceptions.InvalidDateException;
import org.agnese_dissan.models.users.Employee;
import org.agnese_dissan.models.users.Employer;
import org.agnese_dissan.models.users.User;

public class UserFactory {

    private static Employer employer = null;
    static Employee employee = null;

    private UserFactory() {
    }


    /**
     * Building user instance
     * @param whichUser we want to get
     * @return the user instance
     */
    public static User getUser(int whichUser) throws InvalidDateException {
        if (whichUser == 0){
            if (employer == null){
                employer = new Employer();
            }
            return employer;
        }
        else if (whichUser == 1){
            if(employee == null){
                employee = new Employee();
            }
            return employee;
        }
        return null;
    }




}
