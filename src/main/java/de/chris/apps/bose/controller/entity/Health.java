package de.chris.apps.bose.controller.entity;

public class Health {

    private final String status;

    public Health() {
        this.status = "up";
    }

    public String getStatus() {
        return status;
    }
}
