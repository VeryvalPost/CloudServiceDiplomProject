package netology.cloudserverdiplom;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CloudServerDiplomApplication {
    private static final Logger logger = LoggerFactory.getLogger(CloudServerDiplomApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(CloudServerDiplomApplication.class, args);
        logger.info("Application started");
    }

}
