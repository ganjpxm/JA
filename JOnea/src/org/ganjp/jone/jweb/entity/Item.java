package org.ganjp.jone.jweb.entity;

public class Item {
	private String imagePath;
	private String itemUuid;
	private String title = "";
	private String summary = "";
	public Item () {}
	public Item (String title, String summary) {
        this.title = title;
        this.summary = summary;
    }
	
	public Item (String itemId, String title, String summary) {
		this.itemUuid = itemId;
        this.title = title;
        this.summary = summary;
    }
	
	public Item(String itemId, String imagePath, String title, String summary) {
		this.itemUuid = itemId;
        this.imagePath = imagePath;
        this.title = title;
        this.summary = summary;
    }

	public String getImagePath() {
		return imagePath;
	}

	public String getItemUuid() {
		return itemUuid;
	}

	public String getTitle() {
		return title;
	}

	public String getSummary() {
		return summary;
	}
}