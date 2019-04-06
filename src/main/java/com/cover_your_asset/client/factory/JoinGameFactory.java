package com.cover_your_asset.client.factory;

import java.io.BufferedWriter;
import java.io.IOException;

import com.cover_your_asset.client.helper.Util;
import com.cover_your_asset.client.view.ViewController;

public class JoinGameFactory {
	
	private JoinGameFactory() {}

	public static void joinGame(final BufferedWriter requestWriter, ViewController mainView) {
		Util.playerId = Util.generateRandomRequestId();
		final String gameCode = mainView.joinGameView.getCode();
		final String username = mainView.joinGameView.getUsername();
		mainView.joinGameView.toggleErrorMessage(false, ""); 
		String errorMessage = validate(gameCode, username);
		if (errorMessage != null) {
			mainView.joinGameView.toggleErrorMessage(true, errorMessage);
			return;
		}
		Thread thread = new Thread(new Runnable() {
			public void run() {
				String requestString = RequestFactory.generateJoinGameRequestString(gameCode, Util.playerId, username);
				try {
					requestWriter.write(requestString);
					requestWriter.flush();
				} catch (IOException e) {
					System.out.println("Error during joining the game");
				}
			}
		});
		thread.setDaemon(true);
		thread.start();
	}
	
	private static String validate(String gameCode, String username) {
		if (gameCode.length() != 4) {
			return "game code must have 4 characters";
		} else if (username.length() < 0 || username.length() > 10) {
			return "username must be between 1 and 10 characters";
		}
		return null;
	}

}
