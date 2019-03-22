package de.chris.apps.bose.restapi;

public class PostExecutionFailure extends RuntimeException {

    public PostExecutionFailure(String message) {
        super(message);
    }
}
