package de.chris.apps.bose.controller.entity;

public class Power {

    private final String powerState;

    public Power(boolean isOn) {
        this.powerState = isOn ? "on" : "off";
    }

    public String getPowerState() {
        return powerState;
    }
}
