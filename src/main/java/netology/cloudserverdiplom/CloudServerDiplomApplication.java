package netology.cloudserverdiplom;

import netology.cloudserverdiplom.logger.LoggerClass;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CloudServerDiplomApplication {
    private static LoggerClass logger = new LoggerClass();

    public static void main(String[] args) {
        SpringApplication.run(CloudServerDiplomApplication.class, args);
        logger.writeLog("Application started");
    }

}
