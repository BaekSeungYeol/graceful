package me.seungyeol.graceul;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.concurrent.ThreadPoolExecutor;

import static java.lang.Thread.sleep;

@Service
public class HealthService {
    private boolean healthy = true;

    public boolean isHealthy() { return healthy;}
    public void setHealthy(boolean healthy) {
        this.healthy = healthy;
    }


    public ResponseEntity unhealthy(ThreadPoolExecutor executor) {
        setHealthy(false);

        while (executor.getActiveCount() > 0) {
            try {
                System.out.println(Thread.currentThread());
                Thread.sleep(10000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("Completed all active threads");

        return ResponseEntity.badRequest().build();
    }
}
