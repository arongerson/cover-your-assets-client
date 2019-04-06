package com.cover_your_asset.client.view;

import com.cover_your_asset.client.helper.Util;
import com.cover_your_asset.client.model.Card;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import javafx.beans.binding.DoubleBinding;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;

public class AssetsView extends Group {
	
	private int playerId;
	private boolean selected = false;
	private AssetStackView topAssets;
	
	public AssetsView(JsonArray assetsJson, int playerId, String username) {
		this.playerId = playerId;
		create(assetsJson);
		addUsernameLabel(username); 
	}

	private void create(JsonArray assetsJson) {
		double height = 0;
		for (int i = 0; i < assetsJson.size(); i++) {
			JsonObject assetStackJson = assetsJson.get(i).getAsJsonObject();
			AssetStackView assetStackView = new AssetStackView(assetStackJson);
			assetStackView.setTranslateZ(-height);
			System.out.println(height);
			height += assetStackView.getHeight();
			if (i % 2 == 0) {
				assetStackView.setRotate(90);
			}
			getChildren().add(assetStackView);
			if (i == assetsJson.size() - 1) {
				topAssets = assetStackView;
			}
		}
	}

	public int getPlayerId() {
		return playerId;
	}
	
	public boolean isSelected() {
		return selected;
	}

	public void toggleSelection() {
		topAssets.toggleSelection();
	}

	public Card getTopCard() {
		return topAssets.getTopCard();
	}
	
	private void addUsernameLabel(String username) {
		Label usernameLabel = new Label(username);
		usernameLabel.setBackground(new Background(new BackgroundFill(Util.darkYelloGreen, new CornerRadii(3, 3, 3, 3, false), Insets.EMPTY)));
		usernameLabel.setTextFill(Color.WHITE); 
		usernameLabel.setPadding(new Insets(3));
		usernameLabel.setPrefWidth(Util.CARD_WIDTH);
		usernameLabel.setTextAlignment(TextAlignment.CENTER);
		usernameLabel.setTranslateY(usernameLabel.getHeight() + Util.CARD_HEIGHT);
		usernameLabel.setTranslateZ(1);
		usernameLabel.setCache(true);
		usernameLabel.setAlignment(Pos.CENTER);
		getChildren().add(usernameLabel);
	}
	
	public static class DimensionBinding {
		public DoubleBinding layoutX;
		public DoubleBinding layoutY;
		public DoubleBinding breadth;
		
		public DimensionBinding(DoubleBinding layoutX, DoubleBinding layoutY, DoubleBinding breadth) {
			this.layoutX = layoutX;
			this.layoutY = layoutY;
			this.breadth = breadth;
		}
	}
}
