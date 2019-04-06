package com.cover_your_asset.client;

import java.util.ArrayList;
import java.util.List;

import com.cover_your_asset.client.factory.PlayGameFactory;
import com.cover_your_asset.client.helper.CardFactory;
import com.cover_your_asset.client.helper.Util;
import com.cover_your_asset.client.model.Card;
import com.cover_your_asset.client.model.Score;
import com.cover_your_asset.client.view.AssetsView;
import com.cover_your_asset.client.view.CardView;
import com.cover_your_asset.client.view.DeckView;
import com.cover_your_asset.client.view.DiscardPileView;
import com.cover_your_asset.client.view.PlayGameView;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.input.MouseEvent;

public class PlayGameController {

	private PlayGameView playGameView;
	private List<Integer> selectedPileCardList = new ArrayList<Integer>();
	private boolean pileSelected = false;
	private int playerInTurn;
	private int canStillToId;
	public static final double PADDING = 10;
	
	EventHandler<MouseEvent> pileCardHandler = new EventHandler<MouseEvent>() {
		public void handle(MouseEvent event) {
			pileCardClicked(event);
		}
	};
	
	EventHandler<MouseEvent> playerAssetHandler = new EventHandler<MouseEvent>() {
		public void handle(MouseEvent event) {
			playerAssetClicked(event);
		}
	};
	
	EventHandler<MouseEvent> discardPileHandler = new EventHandler<MouseEvent>() {
		public void handle(MouseEvent event) {
			discardPileClicked();
		}
	};

	public PlayGameController(PlayGameView playGameView) {
		this.playGameView = playGameView;
		init();
	}

	private final void init() {
		setListeners();
	}

	private void setListeners() {
		playGameView.setListeners(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				steal();
			}
		}, new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				yield();
			}
		}, new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				cover();
			}
		}, new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				discard();
			}
		}, new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				draw();
			}
		});
	}

	protected void discardPileClicked() {
		// if the pile is selected, un-select it
		// check if not more than one card from the pile has been selected
		// reset the clicked asset if any
		if (pileSelected) {
			pileSelected = false;
			playGameView.discardPileView.toggleSelection();
			updatePlayButtons();
		} else if (selectedPileCardList.size() > 1) {
			playGameView.updateError("you have more than one card from the pile has been selected.");
		} else {
			// remove the asset selection
			deselectAsset();
			pileSelected = true;
			playGameView.discardPileView.toggleSelection();
			updatePlayButtons();
		}
	}

	protected void pileCardClicked(MouseEvent event) {
		CardView cardView = (CardView) event.getSource();
		Integer cardId = cardView.getCard().getId();
		if (cardIsSelected(cardId)) {
			// unselect the card
			selectedPileCardList.remove(cardId);
			cardView.toggleSelected();
			updatePlayButtons();
		} else if (twoCardsFromPileSelected()) {
			// show error
			playGameView.updateError("remove some of your selections first.");
		} else if (oneCardFromPileSelected()) {
			if (isDuringRedeeming()) {
				playGameView.updateError("remove some of your selections first.");
			} else {
				// unselect the asset and the pile
				// add the card selected
				// update buttons
				deselectAsset();
				deselectPile();
				selectedPileCardList.add(cardId);
				cardView.toggleSelected();
				updatePlayButtons();
			}
		} else {
			// no card selected
			selectedPileCardList.add(cardId);
			cardView.toggleSelected();
			updatePlayButtons();
		}
	}

	protected void playerAssetClicked(MouseEvent event) {
		AssetsView assetsView = (AssetsView) event.getSource();
		if (assetIsSelected(assetsView)) {
			playGameView.selectedAssetsView = null;
			assetsView.toggleSelection();
			updatePlayButtons();
		} else if (selectedPileCardList.size() > 1) {
			playGameView.updateError("you have more than one card from the pile has been selected.");
		} else {
			deselectPile();
			deselectAsset();
			playGameView.selectedAssetsView = assetsView;
			playGameView.selectedAssetsView.toggleSelection();
			updatePlayButtons();
		}
	}

	private boolean assetIsSelected(AssetsView assetsView) {
		return assetsView == playGameView.selectedAssetsView;
	}
	
	private void updatePlayButtons() {
		playGameView.actionsView.disableAllPlayButtons();
		if (twoCardsSelected()) {
			playGameView.actionsView.enableCoverButton();
		} else if (cardFromPileAndAssetSelected()) {
			playGameView.actionsView.enableStealButton();
		} else if (oneCardFromPileSelected()) {
			playGameView.actionsView.enableDiscardButton();
		}
		if (isDuringRedeeming()) {
			// yield button should be enabled
			playGameView.actionsView.enableYieldButton();
		}
	}

	private boolean oneCardFromPileSelected() {
		return selectedPileCardList.size() == 1;
	}

	private boolean cardFromPileAndAssetSelected() {
		return selectedPileCardList.size() == 1 && playGameView.selectedAssetsView != null;
	}

	private boolean twoCardsSelected() {
		if (selectedPileCardList.size() == 2) {
			return true;
		} else if (selectedPileCardList.size() == 1 && pileSelected) {
			return true;
		}
		return false;
	}

	private boolean isDuringRedeeming() {
		return canStillToId != -1;
	}

	private void deselectPile() {
		if (pileSelected) {
			playGameView.discardPileView.toggleSelection();
			pileSelected = false;
		}
	}

	private void deselectAsset() {
		if (playGameView.selectedAssetsView != null) {
			playGameView.selectedAssetsView.toggleSelection();
			playGameView.selectedAssetsView = null;
		}
	}

	private boolean twoCardsFromPileSelected() {
		return selectedPileCardList.size() == 2;
	}

	private boolean cardIsSelected(int cardId) {
		return selectedPileCardList.contains(cardId);
	}

	protected void discard() {
		int cardId = selectedPileCardList.get(0);
		PlayGameFactory.discard(cardId);
	}

	protected void draw() {
		PlayGameFactory.draw();
	}

	protected void cover() {
		if (pileSelected) {
			attemptCoverFromDiscardPile();
		} else {
			attemptCoverWithOwnCards();
		}
	}

	private void attemptCoverFromDiscardPile() {
		Card card1 = CardFactory.getCard(selectedPileCardList.get(0));
		Card topCard = playGameView.discardPileView.getTopCard();
		if (isValidMatch(card1, topCard)) {
			PlayGameFactory.coverFromDiscardPile(card1.getId());
		} else {
			playGameView.updateError("Sorry, the cards don't match.");
		}
	}

	private void attemptCoverWithOwnCards() {
		Card card1 = CardFactory.getCard(selectedPileCardList.get(0));
		Card card2 = CardFactory.getCard(selectedPileCardList.get(1));
		if (isValidMatch(card1, card2)) {
			PlayGameFactory.coverWithOwnCards(card1.getId(), card2.getId());
		} else {
			playGameView.updateError("Sorry, the cards don't match."); 
		}
	}

	private boolean isValidMatch(Card card1, Card card2) {
		// the two cards should either match or at least one should be a wild card
		return oneIsWildCard(card1, card2) || cardsMatch(card1, card2);
	}

	private boolean oneIsWildCard(Card card1, Card card2) {
		return card1.isWildCard() || card2.isWildCard();
	}

	private boolean cardsMatch(Card card1, Card card2) {
		return card1.getType() == card2.getType();
	}

	private boolean isValidSteal(Card playerCard, Card topCard) {
		return cardsMatch(playerCard, topCard) || isValidWildCard(playerCard, topCard);
	}

	private boolean isValidWildCard(Card card, Card topCard) {
		return card.isWildCard() && card.getValue() >= topCard.getValue();
	}

	protected void yield() {
		PlayGameFactory.yield(canStillToId);
	}

	private void steal() {
		Card playerCard = CardFactory.getCard(selectedPileCardList.get(0));
		Card topCard = playGameView.selectedAssetsView.getTopCard();
		if (isValidSteal(playerCard, topCard)) {
			PlayGameFactory.steal(playGameView.selectedAssetsView.getPlayerId(), playerCard.getId());
		} else {
			playGameView.updateError("Sorry, the cards don't match.");
		}
	}
	
	private JsonObject findJsonObject(String json) {
		JsonParser jsonParser = new JsonParser();
		JsonElement element = jsonParser.parse(json);
		JsonObject gameJson = element.getAsJsonObject();
		return gameJson;
	}

	public void updateView(final String json, final String message) {
		
		Platform.runLater(new Runnable() {
			public void run() {
				resetView();
				JsonObject gameJson = findJsonObject(json);
				boolean isOver = gameJson.get("isOver").getAsBoolean();
				if (!isOver) {
					updateGameProperties(gameJson);
					updatePile(gameJson);
					updateAssets(gameJson);
					updateDeckAndDiscardPile(gameJson);
					updateLastActionMessage(message);
					playGameView.addLight();
				} else {
					processGameOver(gameJson);
				}
			}
		});

	}

	protected void processGameOver(JsonObject gameJson) {
		JsonArray players = gameJson.get("players").getAsJsonArray();
		int numberOfPlayers = players.size();
		ObservableList<Score> scoresList = FXCollections.observableArrayList();
		for (int i = 0; i < numberOfPlayers; i++) {
			JsonObject player = players.get(i).getAsJsonObject();
			Score score = new Score(player);
			scoresList.add(score);
		}
		Util.viewController.showScoresView();
		Util.viewController.scoresView.updateScores(scoresList); 
	}

	private void resetView() {
		playGameView.selectedAssetsView = null;
		playGameView.discardPileView = null;
		selectedPileCardList.clear();
		playGameView.root3D.getChildren().clear();
		pileSelected = false;
	}

	private void updateGameProperties(JsonObject gameJson) {
		playerInTurn = gameJson.get("inTurn").getAsInt();
		canStillToId = gameJson.get("canStillToId").getAsInt();
	}
	
	private void updateAssets(JsonObject gameJson) {
		JsonArray players = gameJson.get("players").getAsJsonArray();
		int numberOfPlayers = players.size();
		double theta = 2 * Math.PI / numberOfPlayers;
		// we shift by the players order to make each player's assets appear at the bottom
		int shift = Integer.parseInt(Util.playerId);
		for (int i = 0; i < numberOfPlayers; i++) {
			double rotationAngle = i * theta;
			int index = (shift + i) % numberOfPlayers;
			JsonObject player = players.get(index).getAsJsonObject();
			String username = player.get("username").getAsString();
			JsonArray assets = player.get("assets").getAsJsonArray();
			int playerId = player.get("order").getAsInt();
			Point2D coords = calculateAssetsCoords(rotationAngle);
			AssetsView assetsView = new AssetsView(assets, playerId, username);
			assetsView.setTranslateX(coords.getX());
			assetsView.setTranslateY(coords.getY());
			playGameView.addNode(assetsView);
			updateActionsView(player);
			setAssetClickedListener(assets, assetsView);
		}
	}
	
	private Point2D calculateAssetsCoords(double rotationAngle) {
		double x = 150 * Math.sin(rotationAngle) - (Util.CARD_HEIGHT / 2) + Util.TRANSLATE_X + 150*Math.sin(rotationAngle); 
		double y = 150 * Math.cos(rotationAngle) - (Util.CARD_HEIGHT / 2) + Util.TRANSLATE_Y;
		return new Point2D(x, y);
	}

	private void setAssetClickedListener(JsonArray assets, AssetsView assetsView) {
		// only set listener if the player is in turn
		// if the player has more than one stack of assets 
		// enable the assets to be clicked, hence stolen
		if (isAssetStollable(assets, assetsView.getPlayerId())) {
			assetsView.setOnMouseClicked(playerAssetHandler);
		}
	}

	private boolean isAssetStollable(JsonArray assets, int playerId) {
		if (assets.size() > 1 && playerInTurn == Integer.parseInt(Util.playerId)) {
			return (canStillToId == -1 || canStillToId == playerId) && Integer.parseInt(Util.playerId) != playerId;
		}
		return false;
	}

	private void updateActionsView(JsonObject player) {
		boolean steal = player.get("steal").getAsBoolean();
		boolean yield = player.get("yield").getAsBoolean();
		boolean cover = player.get("cover").getAsBoolean();
		if (steal || yield || cover) {
			playGameView.actionsView.update(player);
		}
	}
	
	private void updateDeckAndDiscardPile(JsonObject gameJson) {

		JsonArray discardPileJson = gameJson.get("discardPile").getAsJsonArray();
		DiscardPileView discardPileView = new DiscardPileView(discardPileJson);
		if (playerInTurn == Integer.parseInt(Util.playerId) && canStillToId == -1) {
			discardPileView.setOnMouseClicked(discardPileHandler);
		}
		// playGameView.getChildren().add(playGameView.discardPileView);
		playGameView.addNode(discardPileView);
		playGameView.discardPileView = discardPileView;
		int deckSize = gameJson.get("deckSize").getAsInt();
		DeckView deckView = new DeckView(deckSize);
		// playGameView.getChildren().add(deckView);
		playGameView.addNode(deckView);
	}
	
	protected void updateLastActionMessage(String message) {
		playGameView.updateLastActionMessage(message);
	}

	private void updatePile(JsonObject gameJson) {
		JsonArray pileJson = gameJson.get("pile").getAsJsonArray();
		int numberOfCards = pileJson.size();
		double startLayoutX = -(numberOfCards * Util.CARD_WIDTH + (numberOfCards - 1) * PADDING) / 2;
		double layoutY = 220;
		for (int i = 0; i < pileJson.size(); i++) {
			int id = pileJson.get(i).getAsJsonObject().get("id").getAsInt();
			double layoutX = startLayoutX + (Util.CARD_WIDTH + PADDING) * i;
			CardView cardView = new CardView(id);
			//cardView.setPrefWidth(Util.CARD_WIDTH);
			//cardView.setPrefHeight(Util.CARD_HEIGHT);
			cardView.setTranslateX(Util.TRANSLATE_X + layoutX);
			cardView.setTranslateY(Util.TRANSLATE_Y + layoutY);
			playGameView.addNode(cardView);
			setPileCardListener(cardView); 
		}
	}

	private void setPileCardListener(final CardView cardView) {
		// should be updated.
		if (playerInTurn == Integer.parseInt(Util.playerId)) {
			cardView.setOnMousePressed(pileCardHandler);
		}
	}

}
