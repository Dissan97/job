package com.agnese_dissan.models.job;

import org.AgneseDissan.exceptions.InvalidDateException;
import org.AgneseDissan.models.job.Shift;
import org.AgneseDissan.models.job.ShiftApply;
import org.AgneseDissan.models.job.ShiftScheduling;
import org.AgneseDissan.models.users.Employee;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TestShiftScheduling {

    private final static Logger LOGGER = LoggerFactory.getLogger(TestShiftScheduling.class);

    @Test
    public void testList(){
        //GOOD APPLIANCE SCHEDULING TESTING
        ShiftScheduling scheduling = new ShiftScheduling();
        List<ShiftApply> goodAppliance = new ArrayList<>();
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String jobDate = dateFormat.format(date);
        ShiftApply goodApply;
        Employee employee;
        try {
            employee = new Employee("test");
            goodApply = new ShiftApply("employee", new Shift("test", "test", "test", jobDate, "test"));
        } catch (InvalidDateException e) {
            throw new RuntimeException(e);
        }

        goodAppliance.add(goodApply);
        scheduling.addSchedule(goodAppliance);
        List<ShiftApply> applyList = scheduling.getAppliances();
        assertNotEquals(new ArrayList<>(), applyList);
        LOGGER.info("TEST PASSED BY APPLYING THIS:\n" + goodApply +"\nTEST DATE: " + dateFormat.format(date));

        //BAD APPLIANCE SCHEDULING TESTING
        List<ShiftApply> badAppliance = new ArrayList<>();
        ShiftApply badApply;
        String badDate = "1900-01-01";
        badApply = new ShiftApply("BadEmployee", new Shift("test", "test", "test", badDate, "test"));
        badAppliance.add(badApply);
        scheduling = new ShiftScheduling();
        scheduling.addSchedule(badAppliance);
        applyList = scheduling.getAppliances();
        assertEquals(new ArrayList<>(),applyList);
        LOGGER.info("TEST PASSED BY APPLYING THIS:\n" + badApply +"\nTEST DATE: " + badDate);

    }

}
