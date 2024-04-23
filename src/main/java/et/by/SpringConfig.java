package et.by;

import et.by.models.user.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ComponentScan("et.by")
@PropertySource("classpath:user.properties")
public class SpringConfig {
    @Bean
    public User user(){
        return new User();
    }

}
