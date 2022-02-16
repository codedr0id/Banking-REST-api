package paytm.assignment;

import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		Logger logger = org.apache.logging.log4j.LogManager.getLogger(Application.class);
		logger.info("Application started");
		SpringApplication.run(Application.class, args);
	}

}
