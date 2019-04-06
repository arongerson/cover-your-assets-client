package com.cover_your_asset.client.view;

import com.cover_your_asset.client.helper.Util;
import com.cover_your_asset.client.model.Score;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class ScoresView extends AnchorPane {
	
	private Button startOverButton;
	private Label titleLabel;
	private TableView<Score> scoresTable;
	
	public ScoresView() {
		init();
	}

	private void init() {
		createControls();
		setControlProperties();
	}
	
	@SuppressWarnings("unchecked")
	private void createControls() {
		startOverButton = new Button("Start Over");
		titleLabel = new Label("The game is over (Scores)");
		scoresTable = new TableView<Score>();
		TableColumn<Score, String> usernameColumn = new TableColumn<Score, String>("Username");
        TableColumn<Score, Integer> scoreColumn = new TableColumn<Score, Integer>("Score");
        usernameColumn.setCellValueFactory(new PropertyValueFactory<Score, String>("username"));
        scoreColumn.setCellValueFactory(new PropertyValueFactory<Score, Integer>("score"));
        scoresTable.getColumns().addAll(usernameColumn, scoreColumn);
		VBox vbox = new VBox();
		vbox.getChildren().addAll(titleLabel, scoresTable);
		AnchorPane.setTopAnchor(vbox, 0d);
		AnchorPane.setBottomAnchor(vbox, 0d);
		AnchorPane.setLeftAnchor(vbox, 0d);
		AnchorPane.setRightAnchor(vbox, 0d);
		
		AnchorPane.setTopAnchor(startOverButton, 10d);
		AnchorPane.setRightAnchor(startOverButton, 10d);
		getChildren().addAll(vbox, startOverButton);
	}
	
	private void setControlProperties() {
		scoresTable.setEditable(false);
		
		titleLabel.setBackground(new Background(new BackgroundFill(Util.darkYelloGreen, CornerRadii.EMPTY, Insets.EMPTY)));
		titleLabel.setPadding(new Insets(30, 30, 30, 10));
		titleLabel.setFont(new Font(30));
		titleLabel.setTextFill(Color.WHITE);
		titleLabel.prefWidthProperty().bind(widthProperty());
	}
	
	public void updateScores(ObservableList<Score> data) {
		scoresTable.getItems().addAll(data);
	}

	public void setListeners(EventHandler<ActionEvent> closeButtonHandler) {
		startOverButton.setOnAction(closeButtonHandler); 
	}
	
}
