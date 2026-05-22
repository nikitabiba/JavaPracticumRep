package fitprogwork;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(scanBasePackages = {"fitprogwork", "ru.omstu.figprogwork.db"})
@EnableScheduling
public class FitprogworkApplication {
    public static void main(String[] args) {
        SpringApplication.run(FitprogworkApplication.class, args);
    }
}