package me.seungyeol.graceul;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.availability.ApplicationAvailability;
import org.springframework.boot.availability.AvailabilityChangeEvent;
import org.springframework.boot.availability.LivenessState;
import org.springframework.boot.availability.ReadinessState;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/change")
public class ProbeController {
    @Autowired
    private ApplicationEventPublisher eventPublisher;

    @Autowired
    ApplicationAvailability availability;

    @GetMapping("/status")
    public String status() {
        return "Application is now " + availability.getLivenessState() + " " + availability.getReadinessState();
    }

    @GetMapping("/readiness/accepting")
    public String markReadinesAcceptingTraffic() {
        AvailabilityChangeEvent.publish(eventPublisher, this, ReadinessState.ACCEPTING_TRAFFIC);
        return "ACCEPTING_TRAFFIC";
    }

    @GetMapping("/readiness/refuse")
    public String markReadinesRefusingTraffic() {
        System.out.println("Refusing 되었습니다");
        AvailabilityChangeEvent.publish(eventPublisher, this, ReadinessState.REFUSING_TRAFFIC);
        return "REFUSING_TRAFFIC";
    }

    @GetMapping("/liveness/correct")
    public String markLivenessCorrect() {
        AvailabilityChangeEvent.publish(eventPublisher, this, LivenessState.CORRECT);
        return "CORRECT";
    }


    @GetMapping("/liveness/broken")
    public String markLivenessBroken() {
        AvailabilityChangeEvent.publish(eventPublisher, this, LivenessState.BROKEN);
        return "BROKEN";
    }
}
