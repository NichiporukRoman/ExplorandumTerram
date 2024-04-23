package et.by;

import et.by.models.user.User;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author Neil Alishev
 */
public class TestSpring {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext config = new AnnotationConfigApplicationContext(SpringConfig.class);
        User user = config.getBean("user", User.class);
        System.out.println(user);
    }
}
