package de.chris.apps.bose.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import de.chris.apps.bose.controller.entity.Health;

@RestController
public class SoundTouchManager {
    @GetMapping("/health")
    public Health health() {
        return new Health();
    }
}
