package com.cover_your_asset.client.factory;

import java.io.IOException;

import com.cover_your_asset.client.helper.Util;

public class PlayGameFactory {
	
	private PlayGameFactory() {}
	
	public static void discard(final int cardId) {
		Thread thread = new Thread(new Runnable() {
			public void run() {
				String requestString = RequestFactory.generateDiscardRequestString(Util.gameCode, Util.playerId, Integer.toString(cardId));
				try {
					Util.requestWriter.write(requestString);
					Util.requestWriter.flush();
				} catch (IOException e) {
					System.out.println("Error during discarding card");
				}
			}
		});
		thread.setDaemon(true);
		thread.start();
	}

	public static void coverWithOwnCards(final int cardId1, final int cardId2) { 
		Thread thread = new Thread(new Runnable() {
			public void run() {
				String requestString = RequestFactory.generateCoverWithOwnCardsRequestString(Util.gameCode, Util.playerId, Integer.toString(cardId1), Integer.toString(cardId2));
				try {
					Util.requestWriter.write(requestString);
					Util.requestWriter.flush();
				} catch (IOException e) {
					System.out.println("Error during covering with own cards");
				}
			}
		});
		thread.setDaemon(true);
		thread.start();
	}

	public static void coverFromDiscardPile(final int cardId) {
		Thread thread = new Thread(new Runnable() {
			public void run() {
				String requestString = RequestFactory.generateCoverFromDiscardPileRequestString(Util.gameCode, Util.playerId, Integer.toString(cardId));
				try {
					Util.requestWriter.write(requestString);
					Util.requestWriter.flush();
				} catch (IOException e) {
					System.out.println("Error during covering with own cards");
				}
			}
		});
		thread.setDaemon(true);
		thread.start();
	}

	public static void steal(final int playerId, final int cardId) {
		Thread thread = new Thread(new Runnable() {
			public void run() {
				String requestString = RequestFactory.generateStealRequestString(Util.gameCode, Util.playerId, Integer.toString(playerId), Integer.toString(cardId));
				try {
					Util.requestWriter.write(requestString);
					Util.requestWriter.flush();
				} catch (IOException e) {
					System.out.println("Error during stealing");
				}
			}
		});
		thread.setDaemon(true);
		thread.start();
	}

	public static void yield(final int yieldedToPlayerId) {
		Thread thread = new Thread(new Runnable() {
			public void run() {
				String requestString = RequestFactory.generateYieldRequestString(Util.gameCode, Util.playerId, Integer.toString(yieldedToPlayerId));
				try {
					Util.requestWriter.write(requestString);
					Util.requestWriter.flush();
				} catch (IOException e) {
					System.out.println("Error during yielding");
				}
			}
		});
		thread.setDaemon(true);
		thread.start();
	}

	public static void draw() {
		Thread thread = new Thread(new Runnable() {
			public void run() {
				String requestString = RequestFactory.generateDrawRequestString(Util.gameCode, Util.playerId);
				try {
					Util.requestWriter.write(requestString);
					Util.requestWriter.flush();
				} catch (IOException e) {
					System.out.println("Error during yielding");
				}
			}
		});
		thread.setDaemon(true);
		thread.start();
	}

}
