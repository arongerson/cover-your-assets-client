package com.cover_your_asset.client.view;

import com.cover_your_asset.client.helper.CardFactory;
import com.cover_your_asset.client.helper.Util;
import com.cover_your_asset.client.model.Card;

import javafx.beans.binding.DoubleBinding;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.effect.Effect;
import javafx.scene.effect.Light.Distant;
import javafx.scene.effect.Lighting;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.text.Text;

public class CardView extends Group {

	private Card card;
	private Label valueLabel;
	private Text infoText;
	private boolean selected = false;
	private Background cardBackground;
	private Background selectedBackground;
	private BorderPane topOfCard;
	private double depth = 2;

	public CardView(int cardId) {
		init(cardId);
	}

	private final void init(int cardId) {
		cardBackground = new Background(new BackgroundFill(Util.backgroundColor, new CornerRadii(0), new Insets(0)));
		selectedBackground = new Background(new BackgroundFill(Util.darkYelloGreen, new CornerRadii(0), new Insets(0)));
		card = CardFactory.getCard(cardId);
		createControls();
		setControlProperties();
	}

	private void createControls() {
		topOfCard = new BorderPane();
		valueLabel = new Label(Integer.toString(card.getValue())); 
		infoText = new Text(card.getText());
		StackPane stackPane = new StackPane(); 
		stackPane.getChildren().add(infoText);
		topOfCard.setTop(valueLabel);
		topOfCard.setCenter(stackPane);
		Box box = createBox();
		getChildren().addAll(box, topOfCard);
		setCache(true);
	}
	
	private Box createBox() {
		Box box = new Box(Util.CARD_WIDTH, Util.CARD_HEIGHT, depth);
		box.setMaterial(new PhongMaterial(Util.darkYelloGreen));
		box.setTranslateX(Util.CARD_WIDTH/2);
		box.setTranslateY(Util.CARD_HEIGHT/2);
		return box;
	}

	private void setControlProperties() {
		//setPrefHeight(200);
		setEffect(getLightingEffect());
		topOfCard.setPrefSize(Util.CARD_WIDTH, Util.CARD_HEIGHT);
		topOfCard.setBackground(cardBackground);
		topOfCard.setTranslateZ(-depth);
		
		// infoTextArea
		infoText.setWrappingWidth(Util.CARD_WIDTH - 20);
		
		// label 
		valueLabel.setTextFill(Util.darkYelloGreen);
		valueLabel.setAlignment(Pos.CENTER);
		valueLabel.setPrefWidth(Util.CARD_WIDTH);
		valueLabel.setBackground(new Background(new BackgroundFill(Util.darkYelloGreen, new CornerRadii(0, 0, 0, 0, false), new Insets(0)))); 
		valueLabel.setTextFill(Color.WHITE);
		valueLabel.setPadding(new Insets(10));
	}

	public Card getCard() {
		return card;
	}

//	private String getFilePath(String fileURI) {
//		File file = DownloadFactory.getFile(fileURI);
//		return "file:///" + file.getAbsolutePath();
//	}
	
	private Effect getLightingEffect() {
		Distant light = new Distant();
        light.setAzimuth(5.0f);
        Lighting l = new Lighting();
        l.setLight(light);
        l.setSurfaceScale(1.0f);
        return l;
	}
	
	public void toggleSelected() {
		selected = !selected;
		if (selected) {
			topOfCard.setBackground(selectedBackground);
		} else {
			topOfCard.setBackground(cardBackground);
		}
	}
	
	public boolean isSelected() {
		return selected;
	}
	
	public static class DimensionBinding {
		public DoubleBinding layoutX;
		public DoubleBinding layoutY;
		public DoubleBinding height;
		public DoubleBinding width;
		public DoubleBinding maxHeight;
		public DoubleBinding maxWidth;
	}
	
	public void finalize() {
		System.out.println(valueLabel + " memory deallocated.");
	}
}
