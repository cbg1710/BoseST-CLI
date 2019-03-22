package de.chris.apps.bose.restapi;

import de.chris.apps.bose.xml.Volume;

public class PostVolume extends Post<Volume> {

	private static final String REST_API_VOLUME = "volume";

	public PostVolume(int volume) {
		super(REST_API_VOLUME);
		Volume vol = new Volume();
		vol.setValue(volume);
		setValue(vol);
		preparePost();
	}
}
