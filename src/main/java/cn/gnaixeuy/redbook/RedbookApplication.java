package cn.gnaixeuy.redbook;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

//排除security安全验证
@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class RedbookApplication {

    public static void main(String[] args) {
        SpringApplication.run(RedbookApplication.class, args);
    }


}
