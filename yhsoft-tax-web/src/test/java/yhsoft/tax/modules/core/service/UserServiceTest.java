package yhsoft.tax.modules.core.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import yhsoft.tax.TaxApplication;
import yhsoft.tax.modules.core.model.UserExt;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = TaxApplication.class)
public class UserServiceTest {

    @Autowired
    UserService userService;

    @Test
    public void add() {


    }

    @Test
    public void get() {
        UserExt userExt = userService.get("7049e23d-8b7e-4271-a234-3e69d03d2fdd");
        System.out.println(userExt);
    }

}