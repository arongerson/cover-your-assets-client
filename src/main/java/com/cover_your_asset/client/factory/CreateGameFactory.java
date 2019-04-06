package com.cover_your_asset.client.factory;

import java.io.BufferedWriter;
import java.io.IOException;

import com.cover_your_asset.client.view.ViewController;

public class CreateGameFactory {

	public CreateGameFactory() {
	}
	
	public static void createGame(final BufferedWriter requestWriter, ViewController mainView) {
		final String gameName = mainView.createGameView.getGameName().trim();
		final String username = mainView.createGameView.getUsername().trim();
		final String numberOfCards = mainView.createGameView.getNumberOfCards().trim();
		mainView.createGameView.toggleErrorMessage(false, "");
		String errorMessage = validate(gameName, username, numberOfCards);
		if (errorMessage != null) {
			mainView.createGameView.toggleErrorMessage(true, errorMessage);
			return;
		}
		Thread thread = new Thread(new Runnable() {
			public void run() {
				String requestString = RequestFactory.generateCreateGameRequestString(gameName, username, numberOfCards);
				try {
					requestWriter.write(requestString);
					requestWriter.flush();
				} catch (IOException e) {
					System.out.println("Error during creating the game");
				}
			}
		});
		thread.setDaemon(true);
		thread.start();
	}
	
	private static String validate(String gameName, String username, String numberOfCards) {
		if (gameName.length() < 0 || gameName.length() > 10) {
			return "game name must be between 1 and 10 characters";
		} else if (username.length() < 0 || username.length() > 10) {
			return "username must be between 1 and 10 characters";
		} else {
			try {
				int num = Integer.parseInt(numberOfCards);
				if (num < 4 || num > 5) {
					return "number of cards must be 4 or 5";
				}
			} catch (NumberFormatException e) {
				return "Not a number";
			}
			return null;
		}
	}
}