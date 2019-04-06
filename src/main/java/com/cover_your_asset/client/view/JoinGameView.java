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

public class JoinGameView extends VBox {

	private TextField gameCodeTextField;
	private TextField usernameTextField;
	private Button joinGameButton;
	private Label titleLabel;
	private Label errorLabel;
	private VBox vbox;

	public JoinGameView() {
		initComponents();
	}

	private void initComponents() {
		vbox = new VBox();
		titleLabel = new Label("Join Game");
		errorLabel = new Label();
		gameCodeTextField = new TextField();
		usernameTextField = new TextField();
		joinGameButton = new Button("Join");
		vbox.getChildren().addAll(errorLabel, gameCodeTextField, usernameTextField, joinGameButton);
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

		// join button
		joinGameButton.setFont(new Font(20));
		
		Font textFieldFont = new Font(16);

		// username text field
		usernameTextField.setPromptText("Username");
		usernameTextField.setFont(textFieldFont);

		// game code text field
		gameCodeTextField.setPromptText("Game code");
		gameCodeTextField.setFont(textFieldFont); 

		// error label
		errorLabel.setVisible(false);
		errorLabel.setFont(new Font(10));
		errorLabel.setTextFill(Color.DARKRED);
	}

	public void toggleErrorMessage(boolean visibility, String message) {
		errorLabel.setVisible(visibility);
		errorLabel.setManaged(visibility); 
		errorLabel.setText(message);
	}

	public void setListeners(EventHandler<ActionEvent> joinButonEventHandler) {
		joinGameButton.setOnAction(joinButonEventHandler);
	}

	public String getUsername() {
		return usernameTextField.getText().trim();
	}

	public String getCode() {
		return gameCodeTextField.getText().trim();
	}
	
	public void finalize() {
		System.out.println("join game deallocated.");
	}

}
