package com.cover_your_asset.client.view;

import com.cover_your_asset.client.helper.Util;
import com.google.gson.JsonObject;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.OverrunStyle;
import javafx.scene.control.Tooltip;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Effect;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class ActionsView extends VBox {
	
	private static final double ACTIONS_VIEW_WIDTH = 150;

	private Label playerInTurnLabel;
	private VBox actionsContent;
	private Button stealButton;
	private Button yieldButton;
	private Button coverButton;
	private Button discardButton;
	private Button drawButton;
	private Label lastActionLabel;
	private Tooltip lastActionTooltip;
	
	public ActionsView() {
		init();
	}

	private void init() {
		createControls();
		setControlProperties();
	}

	private void createControls() {
		playerInTurnLabel = new Label("Your turn");
		actionsContent = new VBox(10);
		stealButton = new Button("Steal");
		yieldButton = new Button("Yield");
		coverButton = new Button("Create Asset");
		drawButton = new Button("Draw");
		discardButton = new Button("Discard");
		lastActionLabel = new Label("Game Started");
		lastActionTooltip = new Tooltip();
		actionsContent.getChildren().addAll(stealButton, yieldButton, coverButton, discardButton, drawButton);
		getChildren().addAll(playerInTurnLabel, actionsContent, lastActionLabel);
	}

	private void setControlProperties() {
		setPrefWidth(ACTIONS_VIEW_WIDTH);
		setBackground(new Background(new BackgroundFill(Color.WHITE, new CornerRadii(3), Insets.EMPTY)));
		setEffect(getDropShadowEffect());
		setCache(true);
		
		actionsContent.setPadding(new Insets(10));
		
		playerInTurnLabel.setPadding(new Insets(10));
		playerInTurnLabel.setBackground(new Background(new BackgroundFill(Util.darkYelloGreen, new CornerRadii(3, 3, 0, 0, false), Insets.EMPTY)));
		playerInTurnLabel.setTextFill(Color.WHITE); 
		playerInTurnLabel.setPrefWidth(ACTIONS_VIEW_WIDTH); 
		
		lastActionLabel.setPadding(new Insets(10));
		lastActionLabel.setBackground(new Background(new BackgroundFill(Util.lightYelloGreen, new CornerRadii(0, 0, 3, 3, false), Insets.EMPTY)));
		lastActionLabel.setTextFill(Color.WHITE); 
		lastActionLabel.setPrefWidth(ACTIONS_VIEW_WIDTH); 
		lastActionLabel.setTooltip(lastActionTooltip); 
		lastActionLabel.setTextOverrun(OverrunStyle.CENTER_ELLIPSIS);
		
		stealButton.setPrefWidth(ACTIONS_VIEW_WIDTH - 20);
		yieldButton.setPrefWidth(ACTIONS_VIEW_WIDTH - 20);
		coverButton.setPrefWidth(ACTIONS_VIEW_WIDTH - 20);
		discardButton.setPrefWidth(ACTIONS_VIEW_WIDTH - 20);
		drawButton.setPrefWidth(ACTIONS_VIEW_WIDTH - 20);
	}
	
	private Effect getDropShadowEffect() {
		DropShadow dropShadow = new DropShadow();
		dropShadow.setOffsetY(2.0f);
		dropShadow.setOffsetX(2.0f);
		dropShadow.setColor(Color.LIGHTGRAY);
		return dropShadow;
	}
	
	public void update(JsonObject playerObject) {
		String id = playerObject.get("order").getAsString();
		updatePlayerInTurnText(playerObject, id);
		if (Util.playerId.equals(id)) {
			updatePlayerInTurn(playerObject);
		} else {
			toggleActionsContent(false);
		}
	}

	private void updatePlayerInTurnText(JsonObject playerObject, String id) {
		String label = "Your turn";
		if (!Util.playerId.equals(id)) {
			String username = playerObject.get("username").getAsString();
			if (username.endsWith("s")) {
				label = username + "' turn";
			} else {
				label = username + "'s turn";
			}
		}
		playerInTurnLabel.setText(label);
	}

	private void updatePlayerInTurn(JsonObject playerObject) {
		boolean yield = playerObject.get("yield").getAsBoolean();
		boolean steal = playerObject.get("steal").getAsBoolean();
		boolean cover = playerObject.get("cover").getAsBoolean();
		boolean discard = playerObject.get("discard").getAsBoolean();
		boolean draw = playerObject.get("draw").getAsBoolean();
		int canStealToId = playerObject.get("canStillToId").getAsInt();
		
		toggleActionsContent(true);
		disableAllPlayButtons();
		toggleCoverButton(cover);
		toggleYieldButton(yield);
		toggleDiscardButton(discard);
		toggleStealButton(steal);
		toggleDrawButton(draw);
		updateStealButtonText(canStealToId, draw);
	}

	private void updateStealButtonText(int canStealToId, boolean draw) {
		if (canStealToId == -1 || draw) {
			stealButton.setText("Steal");
		} else {
			stealButton.setText("Redeem");
		}
	}
	
	public void toggleStealButton(boolean show) {
		stealButton.setManaged(show);
		stealButton.setVisible(show);
	}
	
	public void toggleYieldButton(boolean show) {
		yieldButton.setManaged(show);
		yieldButton.setVisible(show);
	}
	
	public void toggleCoverButton(boolean show) {
		coverButton.setManaged(show);
		coverButton.setVisible(show);
	}
	
	public void toggleDiscardButton(boolean show) {
		discardButton.setManaged(show);
		discardButton.setVisible(show);
	}
	
	public void toggleDrawButton(boolean show) {
		drawButton.setManaged(show);
		drawButton.setVisible(show);
	}
	
	public void toggleActionsContent(boolean show) {
		actionsContent.setManaged(show);
		actionsContent.setVisible(show);
	}

	public void setListeners(EventHandler<ActionEvent> stealHandler, EventHandler<ActionEvent> yieldHandler,
			EventHandler<ActionEvent> coverHandler, EventHandler<ActionEvent> discardHandler, EventHandler<ActionEvent> drawHandler) {
		stealButton.setOnAction(stealHandler);
		yieldButton.setOnAction(yieldHandler);
		coverButton.setOnAction(coverHandler);
		discardButton.setOnAction(discardHandler);
		drawButton.setOnAction(drawHandler);
	}

	public void disableAllPlayButtons() {
		stealButton.setDisable(true);
		yieldButton.setDisable(false);
		coverButton.setDisable(true);
		discardButton.setDisable(true); 
		drawButton.setDisable(false);
	}

	public void enableCoverButton() {
		coverButton.setDisable(false); 
	}
	
	public void enableStealButton() {
		stealButton.setDisable(false); 
	}
	
	public void enableDiscardButton() {
		discardButton.setDisable(false); 
	}
	
	public void enableYieldButton() {
		yieldButton.setDisable(false); 
	}
	
	public void enableDrawButton() {
		drawButton.setDisable(false); 
	}

	public void updateLastActionMessage(String message) {
		lastActionLabel.setText(message); 
		lastActionTooltip.setText(message); 
	}
	
}
