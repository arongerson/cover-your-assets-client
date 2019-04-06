package com.cover_your_asset.client.view;


import com.cover_your_asset.client.helper.Util;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

public class ViewController extends AnchorPane {
	
	public JoinGameView joinGameView;
	public CreateGameView createGameView;
	public WaitGameView waitGameView;
	public AdditionalInfoView additionalInfoView;
	public PlayGameView playGameView;
	public ScoresView scoresView;
	private AnchorPane gameSetupAnchorPane;
	private Button switchViewButton;
	private boolean joinGame = true;
	private static final double CONTAINER_PADDING = 30;
	
	public ViewController() {
		init();
	}

	private final void init() {
		addAdditionInfoView();
		createGameSetupViews();
		addSwitchViewButton();
		setAnchorPaneProperties();
	}

	private void createGameSetupViews() {
		gameSetupAnchorPane = new AnchorPane();
		joinGameView = new JoinGameView();
		createGameView = new CreateGameView();
		waitGameView = new WaitGameView();
		scoresView = new ScoresView();
		AnchorPane.setTopAnchor(gameSetupAnchorPane, CONTAINER_PADDING);
		AnchorPane.setBottomAnchor(gameSetupAnchorPane, CONTAINER_PADDING);
		AnchorPane.setRightAnchor(gameSetupAnchorPane, CONTAINER_PADDING);
		
		AnchorPane.setTopAnchor(joinGameView, 0d);
		AnchorPane.setBottomAnchor(joinGameView, 0d);
		AnchorPane.setLeftAnchor(joinGameView, 0d);
		AnchorPane.setRightAnchor(joinGameView, 0d);
		getChildren().addAll(gameSetupAnchorPane);
		gameSetupAnchorPane.getChildren().add(joinGameView);
	}
	
	private void addSwitchViewButton() {
		switchViewButton = new Button("Create Game");
		AnchorPane.setBottomAnchor(switchViewButton, 10d);
		AnchorPane.setLeftAnchor(switchViewButton, 10d);
		gameSetupAnchorPane.getChildren().add(switchViewButton);
	}
	
	public void switchSetupView() {
		Pane pane;
		joinGame = !joinGame;
		if (joinGame) {
			switchViewButton.setText("Create Game");
			pane = joinGameView;
		} else {
			switchViewButton.setText("Join Game");
			pane = createGameView;
		}
		switchView(pane);
	}
	
	public void backToHome() {
		getChildren().clear();
		joinGame = true;
		getChildren().add(gameSetupAnchorPane);
		switchView(joinGameView);
	}

	private void switchView(Pane pane) {
		AnchorPane.setTopAnchor(pane, 0d);
		AnchorPane.setBottomAnchor(pane, 0d);
		AnchorPane.setLeftAnchor(pane, 0d);
		AnchorPane.setRightAnchor(pane, 0d);
		gameSetupAnchorPane.getChildren().set(0, pane);
	}
	
	public void switchWaitGameView(boolean showStartButton, String json) {
		switchViewButton.setVisible(false);
		switchViewButton.setManaged(false);
		waitGameView.updateValues(showStartButton, json);
		switchView(waitGameView);
	}
	
	private void clearExistingNodesAndAddNewNode(Node node) {
		getChildren().clear();
		AnchorPane.setTopAnchor(node, 0d);
		AnchorPane.setBottomAnchor(node, 0d);
		AnchorPane.setLeftAnchor(node, 0d);
		AnchorPane.setRightAnchor(node, 0d);
		getChildren().add(node); 
	}
	
	private void showPlayGameView(String json) {
//		if (playGameView == null) {
//			System.out.println("play game view created");
//			playGameView = new PlayGameView();
//		}
//		clearExistingNodesAndAddNewNode(playGameView);
	}
	
	public void showScoresView() {
		Util.scene.setRoot(this); 
		toggleSwitchViewButtonVisibility(true);
		clearExistingNodesAndAddNewNode(scoresView);
	}

	private void addAdditionInfoView() {
		additionalInfoView = new AdditionalInfoView();
	}

	private void setAnchorPaneProperties() {
		setBackground(Util.getBackground());
	}
	
	public void setMainViewListeners(EventHandler<ActionEvent> switchViewEventHandler) {
		switchViewButton.setOnAction(switchViewEventHandler);
	}
	
	public void toggleSwitchViewButtonVisibility(boolean visibility) {
		switchViewButton.setVisible(visibility);
	}

	private void processGameResponse(final boolean status, final boolean showStartButton, final String message, final String json) { 
		Platform.runLater(new Runnable() {
			public void run() {
				if (status) {
					switchWaitGameView(showStartButton, json);
				} else {
					if (showStartButton) {
						createGameView.toggleErrorMessage(true, message); 
					} else {
						joinGameView.toggleErrorMessage(true, message); 
					}
				}
			}
		});
	}
	
	public void createGameResponse(final boolean status, final String message, final String json) { 
		boolean showStartButton = true;
		processGameResponse(status, showStartButton, message, json);
	}

	public void joinGameResponse(boolean status, String message, String json) {
		boolean showStartButton = false;
		processGameResponse(status, showStartButton, message, json);
	}

	public void startGameResponse(final boolean status, final String message, final String json) { 
		Platform.runLater(new Runnable() {
			public void run() {
				if (status) {
					showPlayGameView(json);
				} else {
					createGameView.toggleErrorMessage(true, message); 
				}
			}
		});
	}
	
}
