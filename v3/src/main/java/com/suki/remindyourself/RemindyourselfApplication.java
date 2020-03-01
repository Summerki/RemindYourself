package com.suki.remindyourself;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class RemindyourselfApplication {

    public static void main(String[] args) {
        SpringApplication.run(RemindyourselfApplication.class, args);
    }

}
