package com.cover_your_asset.client.model;

import com.cover_your_asset.client.helper.CardFactory;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class Score implements Comparable<Score>{
	private String username;
	private int score;
	
	public Score(JsonObject player) {
		init(player);
	}
	
	private final void init(JsonObject player) {
		username = player.get("username").getAsString();
		JsonArray assets = player.getAsJsonArray("assets");
		calculateScore(assets);
	}
	
	private void calculateScore(JsonArray assets) {
		score = 0;
		for (int i = 0; i < assets.size(); i++) {
			JsonObject assetStackJson = assets.get(i).getAsJsonObject();
			int stackScore = calculateStackScore(assetStackJson);
			score += stackScore;
		}
	}

	private int calculateStackScore(JsonObject assetStackJson) {
		JsonArray cardsJson = assetStackJson.get("cards").getAsJsonArray();
		int stackScore = 0;
		for (int i = 0; i < cardsJson.size(); i++) {
			JsonObject cardJson = cardsJson.get(i).getAsJsonObject();
			int cardId = cardJson.get("id").getAsInt();
			Card card = CardFactory.getCard(cardId);
			stackScore += card.getValue();
		}
		return stackScore;
	}

	public String getUsername() {
		return username;
	}
	
	public int getScore() {
		return score;
	}

	public int compareTo(Score o) {
		return o.score -score;
	}
}
