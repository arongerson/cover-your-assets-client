package com.cover_your_asset.client.view;

import com.cover_your_asset.client.helper.Util;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class WaitGameView extends VBox {

	private Label nameLabel;
	private Label codeLabel;
	private Label createdLabel;
	private Label errorLabel;
	private ListView<String> playersListView;
	private Button startButton;
	private VBox vbox;

	public WaitGameView() {
		initControls();
	}

	private void initControls() {
		createControls();
		setControlProperties();
	}

	private void setControlProperties() {
		vbox.setPadding(new Insets(10d));
		vbox.setSpacing(20d);
		vbox.setBackground(new Background(new BackgroundFill(Color.WHITE, new CornerRadii(3), Insets.EMPTY)));
		
		setBackground(new Background(new BackgroundFill(Color.WHITE, new CornerRadii(3), Insets.EMPTY)));
		setPrefWidth(300);
		
		// titleLabel
		nameLabel.setFont(new Font(25));
		nameLabel.setBackground(new Background(new BackgroundFill(Util.darkYelloGreen, new CornerRadii(3, 3, 0, 0, false), Insets.EMPTY)));
		nameLabel.prefWidthProperty().bind(widthProperty());
		nameLabel.setTextFill(Color.WHITE); 
		nameLabel.setPadding(new Insets(10));

		// start button
		startButton.setVisible(false);

		// error label
		errorLabel.setVisible(false);
		errorLabel.setFont(new Font(10));
		errorLabel.setTextFill(Color.DARKRED);
		
		// code label
		codeLabel.setTextFill(Util.darkYelloGreen);
		codeLabel.setFont(new Font(16));
		
		// created label 
		createdLabel.setTextFill(Util.darkYelloGreen);
		createdLabel.setFont(new Font(16));
		
	}

	private void createControls() {
		vbox = new VBox();
		nameLabel = new Label();
		errorLabel = new Label();
		codeLabel = new Label();
		createdLabel = new Label();
		playersListView = new ListView<String>();
		startButton = new Button("Start");
		vbox.getChildren().addAll(errorLabel, codeLabel, createdLabel, playersListView, startButton);
		getChildren().addAll(nameLabel, vbox);
	}

	private JsonObject findJsonObject(String json) {
		JsonParser jsonParser = new JsonParser();
		JsonElement element = jsonParser.parse(json);
		JsonObject gameJson = element.getAsJsonObject();
		return gameJson;
	}

	private void updateDetails(JsonObject gameJson) {
		String gameName = gameJson.getAsJsonPrimitive("name").getAsString();
		String gameCode = gameJson.getAsJsonPrimitive("code").getAsString();
		String gameCreated = gameJson.getAsJsonPrimitive("created").getAsString();
		nameLabel.setText(gameName);
		codeLabel.setText("Code: " + gameCode);
		createdLabel.setText("Created: " + gameCreated);
	}

	private void updatePlayers(JsonObject gameJson) {
		JsonArray playersJson = gameJson.getAsJsonArray("players");
		playersListView.getItems().clear();
		for (int i = 0; i < playersJson.size(); i++) {
			JsonObject playerJson = playersJson.get(i).getAsJsonObject();
			String username = playerJson.getAsJsonPrimitive("username").getAsString();
			playersListView.getItems().add(username);
		}
	}

	public void updateValues(boolean showStartButton, String json) {
		if (showStartButton) {
			startButton.setVisible(showStartButton);
		}
		JsonObject gameJson = findJsonObject(json);
		updateDetails(gameJson);
		updatePlayers(gameJson);
	}

	public void addPlayer(String name) {
		playersListView.getItems().add(name);
	}

	public void setListeners(EventHandler<ActionEvent> startButonEventHandler) {
		startButton.setOnAction(startButonEventHandler);
	}

	public void toggleErrorMessage(boolean visibility, String message) {
		errorLabel.setVisible(visibility);
		errorLabel.setText(message);
	}

}
