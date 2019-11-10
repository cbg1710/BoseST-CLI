package de.chris.apps.bose.restapi;

public class PostExecutionFailure extends RuntimeException {

	private static final long serialVersionUID = -5824599059741293801L;

	public PostExecutionFailure(String message) {
        super(message);
    }
}
