package com.cover_your_asset.client.model;

public class Legacy {
//	private void create(JsonArray assetsJson, DoubleBinding heightBinding) {
//	for (int i = 0; i < assetsJson.size(); i++) {
//		JsonObject cardJson = assetsJson.get(i).getAsJsonObject();
//		int cardId = cardJson.get("id").getAsInt();
//		CardView cardView = new CardView(cardId);
//		cardView.prefHeightProperty().bind(heightBinding); 
//		cardView.prefWidthProperty().bind(heightBinding.multiply(2.0/3));
//		getChildren().add(cardView);
//		if (i == assetsJson.size() - 1) {
//			topCard = cardView;
//		}
//	}
//}
	
//	public AssetsView.DimensionBinding createAssetBindings(double rotationAngle) {
//		DoubleBinding assetCenterX = canvasRadiusBinding.multiply(Math.sin(rotationAngle));
//		DoubleBinding assetCenterY = canvasRadiusBinding.multiply(Math.cos(rotationAngle));
//		DoubleBinding assetX = assetCenterX.subtract(assetHalfWidth.multiply(2.0 / 3));
//		DoubleBinding assetY = assetCenterY.subtract(assetHalfWidth);
//		DoubleBinding translatedX = assetX.add(canvasCenterX);
//		DoubleBinding translatedY = assetY.add(canvasCenterY);
//		DoubleBinding createOvalBinding = widthProperty().subtract(canvasHeightProperty).divide(2)
//				.multiply(Math.sin(rotationAngle));
//		DoubleBinding assetWidthBinding = assetHalfWidth.multiply(2);
//		DoubleBinding ovalXBinding = translatedX.add(createOvalBinding);
//		AssetsView.DimensionBinding dimensionBinding = new AssetsView.DimensionBinding(ovalXBinding, translatedY,
//				assetWidthBinding);
//		return null;
//	}
	
//	private void initBindings() {
//		canvasHeightProperty = heightProperty().multiply(3.0 / 4);
//		assetRadiusProperty = canvasHeightProperty.divide(8);
//		canvasRadiusBinding = assetRadiusProperty.multiply(3);
//		assetHalfWidth = assetRadiusProperty.subtract(10);
//		canvasCenterX = widthProperty().divide(2);
//		canvasCenterY = heightProperty().multiply(3.0 / 8);
//	}



//	public CardView.DimensionBinding createCardViewBindings(int count, int numberOfCards) {
//		CardView.DimensionBinding dimensionBinding = new CardView.DimensionBinding();
//		dimensionBinding.width = widthProperty().subtract(numberOfCards * PADDING + PADDING).divide(numberOfCards);
//		dimensionBinding.height = dimensionBinding.width.multiply(1.5);
//		dimensionBinding.maxHeight = heightProperty().divide(4);
//		dimensionBinding.maxWidth = dimensionBinding.maxHeight.divide(1.5);
//		dimensionBinding.layoutX = dimensionBinding.width.multiply(count).add((count + 1) * PADDING).add(widthProperty()
//				.subtract(dimensionBinding.width.multiply(numberOfCards).add(PADDING * (numberOfCards + 1))).divide(2));
//		dimensionBinding.layoutY = heightProperty().subtract(dimensionBinding.height).subtract(PADDING);
//		return dimensionBinding;
//	}
	
//	public CardView createAndAddCardView(int id, int count, int numberOfCards) {
//		final CardView cardView = new CardView(id);
//		cardView.prefWidthProperty()
//				.bind(widthProperty().subtract(numberOfCards * PADDING + PADDING).divide(numberOfCards));
//		cardView.layoutXProperty().bind(cardView.widthProperty().multiply(count).add((count + 1) * PADDING)
//				.add(widthProperty()
//						.subtract(cardView.widthProperty().multiply(numberOfCards).add(PADDING * (numberOfCards + 1)))
//						.divide(2)));
//		cardView.layoutYProperty().bind(heightProperty().subtract(cardView.heightProperty()).subtract(PADDING));
//		cardView.maxHeightProperty().bind(heightProperty().divide(4));
//		cardView.maxWidthProperty().bind(cardView.maxHeightProperty().divide(1.5));
//		cardView.prefHeightProperty().bind(cardView.prefWidthProperty().multiply(1.5));
//		getChildren().add(cardView);
//		return cardView;
//	}
	
//	private void updateDeckAndDiscardPile1(JsonObject gameJson) {
//		DoubleBinding canvasHeightBinding = playGameView.heightProperty().multiply(3.0 / 4);
//		DoubleBinding assetHeightBinding = canvasHeightBinding.divide(4);
//		DoubleBinding assetWidthBinding = assetHeightBinding.multiply(2.0 / 3);
//		DoubleBinding centerXBinding = playGameView.widthProperty().divide(2);
//		DoubleBinding centerYBinding = canvasHeightBinding.divide(2);
//
//		JsonArray discardPileJson = gameJson.get("discardPile").getAsJsonArray();
//		playGameView.discardPileView = new DiscardPileView(discardPileJson, assetHeightBinding);
//		playGameView.discardPileView.layoutXProperty().bind(centerXBinding.subtract(assetWidthBinding).subtract(10));
//		playGameView.discardPileView.layoutYProperty().bind(centerYBinding.subtract(assetHeightBinding.divide(2)));
//		if (playerInTurn == Integer.parseInt(Util.playerId) && canStillToId == -1) {
//			playGameView.discardPileView.setOnMouseClicked(discardPileHandler);
//		}
//		playGameView.getChildren().add(playGameView.discardPileView);
//
//		int deckSize = gameJson.get("deckSize").getAsInt();
//		DeckView deckView = new DeckView(deckSize, assetHeightBinding);
//		deckView.layoutXProperty().bind(centerXBinding.add(10));
//		deckView.layoutYProperty().bind(centerYBinding.subtract(assetHeightBinding.divide(2)));
//		playGameView.getChildren().add(deckView);
//	}
}
