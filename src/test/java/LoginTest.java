import org.agnese_dissan.exceptions.UserLoginFailedException;
import org.agnese_dissan.graphicControllers.LoginGraphic;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LoginTest {

    @Test
    void testSignIn(){



        boolean success = true;
        boolean failed = false;
        LoginGraphic graphic = new LoginGraphic();


/*        try {
            graphic.signIn("dissan", "password");
        } catch (UserLoginFailedException e) {
            e.printStackTrace();
            success = false;
        }
*/

        try {
            graphic.signIn("dissan", "badPassword", false);
        } catch (UserLoginFailedException e) {
            e.printStackTrace();
            failed = true;
        }

        assertTrue(success);
        assertTrue(failed);


    }
}
