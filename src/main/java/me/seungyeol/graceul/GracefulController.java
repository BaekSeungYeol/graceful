package me.seungyeol.graceul;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GracefulController {
    @Autowired
    HealthService healthService;

//    @GetMapping("/unhealthy")
//    public ResponseEntity unhealth() {
//        return healthService.unhealthy(executor);
//    }
}
