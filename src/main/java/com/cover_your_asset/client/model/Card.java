package com.cover_your_asset.client.model;

import java.io.Serializable;

public class Card implements Serializable {

	private static final long serialVersionUID = 1L;
	private int id;
	private String text;
	private String photoPath;
	private boolean wilCard;
	private int value;
	private int type;
	
	public Card() {}
	
	public Card(int id, int type, int value, String text, String photoPath, boolean wildCard) {
		this.id = id;
		this.type = type;
		this.value = value;
		this.text = text;
		this.photoPath = photoPath;
		this.wilCard = wildCard;
	}

	public int getId() {
		return id;
	}

	public String getText() {
		return text;
	}

	public String getPhotoPath() {
		return photoPath;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}
	
	public boolean isWildCard() {
		return wilCard;
	}
	
	public int getType() {
		return type;
	}
	
	public void finalize() {
		System.out.println(text + " card memory deallocated.");
	}

}
