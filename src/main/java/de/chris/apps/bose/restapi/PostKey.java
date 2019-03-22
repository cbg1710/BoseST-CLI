package de.chris.apps.bose.restapi;

import de.chris.apps.bose.xml.Key;
import de.chris.apps.bose.xml.KeyState;
import de.chris.apps.bose.xml.KeyValues;

public class PostKey extends Post<Key> {

	private static final String REST_API_KEY = "key";

	private PostKey(KeyValues keyValues, KeyState keyState) {
		super(REST_API_KEY);
		Key keys = new Key();
		keys.setValue(keyValues);
		keys.setState(keyState);
		keys.setSender("Gabbo");
		setValue(keys);
		preparePost();
	}

	public static void postKey(KeyValues keyValues) {
		new PostKey(keyValues, KeyState.PRESS).postCommand();
		new PostKey(keyValues, KeyState.RELEASE).postCommand();
	}
}
