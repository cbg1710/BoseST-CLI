package de.chris.apps.bose.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import de.chris.apps.bose.commands.BoseActions;
import de.chris.apps.bose.controller.entity.Health;
import de.chris.apps.bose.controller.entity.Power;

@RestController
public class SoundTouchManager {
    
    @GetMapping("/health")
    public Health health() {
        return new Health();
    }

    @GetMapping("/bose/power")
    public Power GetPower() {
        return new Power(BoseActions.isBoseOn());
    }

    @PostMapping("/bose/power/{power}")
    public void SetPower(@PathVariable boolean power) {
        BoseActions.switchPower(power);
    }

    @PostMapping("/bose/play/preset/{preset}")
    public void SetPreset(@PathVariable int preset) {
        BoseActions.choosePreset(preset); 
    }

    @PostMapping("/bose/play/volume/{volume}")
    public void SetVolume(@PathVariable int volume) {
        BoseActions.postVolume(volume); 
    }


}
