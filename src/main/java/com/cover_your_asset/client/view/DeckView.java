package com.cover_your_asset.client.view;

import com.cover_your_asset.client.helper.Util;

import javafx.scene.Group;

public class DeckView extends Group {
	
	public DeckView(int deckSize) {
		create(deckSize);
	}
	
	private void create(int deckSize) {
		for (int i = 0; i < deckSize; i++) {
			// create a dummy card, important in 3-D to show how tall it is
			CardView cardView = new CardView(0);
			//cardView.setPrefWidth(Util.CARD_WIDTH);
			//cardView.setPrefHeight(Util.CARD_HEIGHT);
			cardView.setTranslateX(Util.TRANSLATE_X + 20);
			cardView.setTranslateY(Util.TRANSLATE_Y - (Util.CARD_HEIGHT/ 2));
			cardView.setTranslateZ(-i * 2);
			getChildren().add(cardView);
		}
	}
	
	public void finalize() {
		System.out.println("deckview memory deallocated.");
	}
	
}
