package de.chris.apps.bose.restapi;

import de.chris.apps.bose.xml.ContentItem;

public class PostSelect extends Post<ContentItem> {

	private static final String REST_API_SELECT = "select";
	private ContentItem contentItem = new ContentItem();

	public PostSelect(String source) {
		super(REST_API_SELECT);
		contentItem.setSource(source);
		setValue(contentItem);
		preparePost();
	}

	public PostSelect(String source, String sourceAccount) {
		super(REST_API_SELECT);
		contentItem.setSource(source);
		contentItem.setSourceAccount(sourceAccount);
		setValue(contentItem);
		preparePost();
	}
}
