package com.cover_your_asset.client.view;

import com.cover_your_asset.client.helper.Util;
import com.cover_your_asset.client.model.Card;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import javafx.scene.Group;

public class DiscardPileView extends Group {
	
	private CardView topCardView;
	
	public DiscardPileView(JsonArray discardPileJson) {
		create(discardPileJson);
		setCache(true);
	}
	
	private void create(JsonArray discardPileJson) {
		for (int i = 0; i < discardPileJson.size(); i++) {
			JsonObject cardJson = discardPileJson.get(i).getAsJsonObject();
			int cardId = cardJson.get("id").getAsInt();
			CardView cardView = new CardView(cardId);
			//cardView.setPrefWidth(Util.CARD_WIDTH);
			//cardView.setPrefHeight(Util.CARD_HEIGHT);
			cardView.setTranslateX(Util.TRANSLATE_X - (20 + Util.CARD_WIDTH));
			cardView.setTranslateY(Util.TRANSLATE_Y - (Util.CARD_HEIGHT/ 2));
			getChildren().add(cardView);
			// update the top card
			if (i == discardPileJson.size() - 1) {
				topCardView = cardView;
			}
		}
	}
	
	public void toggleSelection() {
		topCardView.toggleSelected();
	}
	
	public Card getTopCard() {
		return topCardView.getCard();
	}
	
}
