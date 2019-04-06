package com.cover_your_asset.client.view;

import com.cover_your_asset.client.model.Card;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import javafx.scene.Group;
import javafx.scene.Node;

public class AssetStackView extends Group {
	
	private boolean stolable;
	private CardView topCard;
	private double height = 0;
	
	public AssetStackView(JsonObject assetsJson) {
		stolable = assetsJson.get("stolable").getAsBoolean();
		JsonArray cardsJson = assetsJson.get("cards").getAsJsonArray();
		create(cardsJson);
		height = 2 * cardsJson.size();
	}
	
	private void create(JsonArray assetsJson) {
		for (int i = 0; i < assetsJson.size(); i++) {
			JsonObject cardJson = assetsJson.get(i).getAsJsonObject();
			int cardId = cardJson.get("id").getAsInt();
			CardView cardView = new CardView(cardId);
			cardView.setTranslateZ(-i * 2);
			cardView.setLayoutX(0);
			cardView.setLayoutY(0);
			getChildren().add(cardView);
			if (i == assetsJson.size() - 1) {
				topCard = cardView;
			}
		}
	}

	public void toggleSelection() {
		for (Node node : getChildren()) {
			CardView cardView = (CardView) node;
			cardView.toggleSelected();
		}
	}
	
	public boolean isStolable() {
		return stolable;
	}

	public Card getTopCard() {
		return topCard.getCard();
	}

	public double getHeight() {
		return height;
	}
}
