package com.agnese_dissan.dao;

import org.agnese_dissan.Macros;
import org.agnese_dissan.daos.FileSystem;
import org.agnese_dissan.models.users.User;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TestPutNewUser {

    private final static Logger LOGGER = LoggerFactory.getLogger(TestPutNewUser.class);
    /**
     * Test used to view a new user is inserted correctly
     */
    @Test
    public void testFileSystem(){
        String path = "src/main/resources/org/agnese_dissan/dao/users/";
        File file;

        FileSystem fileSystem = new FileSystem();
        User user = null;
        try {
            user = new User("test");
            String localPath = path + user.getUserType().name().toLowerCase() + "/" + user.getUsername();
            file = new File(localPath);
            if (file.exists()){
                String f = localPath + "/schedules.json";
                LOGGER.info(f);
                assertTrue(new File(f).delete());
                f = localPath + "/appliances.json";
                LOGGER.info(f);
                assertTrue(new File(f).delete());
                assertTrue(file.delete());
            }
            fileSystem.putUser(user);

            user.setUserType(Macros.EMPLOYER);
            localPath = path + user.getUserType().name().toLowerCase() + "/" + user.getUsername();
            file = new File(localPath);
            if (file.exists()){
                String f = localPath + "/schedules.json";
                LOGGER.info(f);
                assertTrue(new File(f).delete());
                f = localPath + "/shifts.json";
                LOGGER.info(f);
                assertTrue(new File(f).delete());
                f = localPath + "/candidates.json";
                LOGGER.info(f);
                assertTrue(new File(f).delete());
                assertTrue(file.delete());
            }else {
                LOGGER.info("File does not exist");
            }

            fileSystem.putUser(user);
        } catch (Exception e) {
            e.printStackTrace();
            assertNull(e);
        }


    }
}
