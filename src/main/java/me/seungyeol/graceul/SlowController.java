package me.seungyeol.graceul;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PreDestroy;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import static java.lang.Thread.sleep;

@RestController
class SlowController {


    ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(4);

    @Autowired
    HealthService healthService;

    @RestController
    class UserController {

        @GetMapping("/hello")
        public String hello() throws InterruptedException {
            Thread.sleep(10000L);
            executor.execute(this::task);
            System.out.println("Main Finished!");
            return "Main finished";
        }

        private void task() {
            try {
                System.out.println("Working Thread = " + Thread.currentThread());
                sleep(10000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Completed 10 seconds...");
        }
    }

    @GetMapping("/unhealthy")
    public ResponseEntity unhealth() {
        return healthService.unhealthy(executor);
    }

    @PreDestroy
    public void destroy() {
        System.out.println("Triggered PreDestroy");

        //Verify if the threads have completed their tasks and then proceed with shutdown
        while (executor.getActiveCount() > 0) {
            try {
                System.out.println("Destory Thread = " + Thread.currentThread());
                sleep(10000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Completed all active threads");
    }
}