package com.cover_your_asset.client.view;

import com.cover_your_asset.client.helper.Util;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class CreateGameView extends VBox {
	private TextField gameNameTextField;
	private TextField usernameTextField;
	private TextField numberOfCardsTextField;
	private Button createGameButton;
	private Label titleLabel;
	private Label errorLabel;
	private VBox vbox;

	public CreateGameView() {
		initComponents();
	}

	private void initComponents() {
		vbox = new VBox();
		titleLabel = new Label("Create Game");
		errorLabel = new Label();
		gameNameTextField = new TextField("Name");
		usernameTextField = new TextField("Jake");
		numberOfCardsTextField = new TextField("4");
		createGameButton = new Button("Create");
		vbox.getChildren().addAll(errorLabel, gameNameTextField, usernameTextField, numberOfCardsTextField,
				createGameButton);
		getChildren().addAll(titleLabel, vbox);
		setControlProperties();
	}

	private void setControlProperties() {
		vbox.setPadding(new Insets(10d));
		vbox.setSpacing(20);
		vbox.setBackground(new Background(new BackgroundFill(Color.WHITE, new CornerRadii(3, 3, 0, 0, false), Insets.EMPTY)));
		setPrefWidth(300);
		
		setBackground(new Background(new BackgroundFill(Color.WHITE, new CornerRadii(3), Insets.EMPTY)));

		// titleLabel
		titleLabel.setFont(new Font(25));
		titleLabel.setBackground(new Background(new BackgroundFill(Util.darkYelloGreen, new CornerRadii(3, 3, 0, 0, false), Insets.EMPTY)));
		titleLabel.prefWidthProperty().bind(widthProperty());
		titleLabel.setTextFill(Color.WHITE); 
		titleLabel.setPadding(new Insets(10));

		// create game button
		createGameButton.setFont(new Font(20));
		
		Font textFieldFont = new Font(16);

		// username text field
		usernameTextField.setPromptText("Username");
		usernameTextField.setFont(textFieldFont);

		// game code text field
		gameNameTextField.setPromptText("Game name");
		gameNameTextField.setFont(textFieldFont);
		
		// number of cards text field
		numberOfCardsTextField.setPromptText("Number of Cards");
		numberOfCardsTextField.setFont(textFieldFont);

		// error label
		errorLabel.setVisible(false);
		errorLabel.setFont(new Font(10));
		errorLabel.setTextFill(Color.DARKRED);
	}

	public void toggleErrorMessage(boolean visibility, String message) {
		errorLabel.setVisible(visibility);
		setManaged(visibility); 
		errorLabel.setText(message);
	}

	public void setListeners(EventHandler<ActionEvent> joinButonEventHandler) {
		createGameButton.setOnAction(joinButonEventHandler);
	}

	public String getUsername() {
		return usernameTextField.getText().trim();
	}

	public String getNumberOfCards() {
		return numberOfCardsTextField.getText().trim();
	}

	public String getGameName() {
		return gameNameTextField.getText().trim();
	}
}
