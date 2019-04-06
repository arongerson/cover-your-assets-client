package com.cover_your_asset.client.factory;

import com.cover_your_asset.client.PlayGameController;
import com.cover_your_asset.client.helper.ResponseCode;
import com.cover_your_asset.client.helper.Util;
import com.cover_your_asset.client.view.PlayGameView;

import javafx.application.Platform;

public class ResponseFactory {

	public static void process(String response) {
		// if the code is not found the request is dropped
		System.out.println(response);
		String[] tokens = response.split(Util.DELIMITER);
		int requestCode = Integer.parseInt(tokens[0].trim());
		System.out.println("requestCode: " + requestCode);
		boolean status = (tokens[1].equals("1")) ? true : false;
		if (requestCode == ResponseCode.RCRTGM.getNumber()) {
			createGame(status, tokens);
		} else if (requestCode == ResponseCode.RJNGM.getNumber()) {
			joinGame(status, tokens);
		} else if (requestCode == ResponseCode.RRCNNCT.getNumber()) {
			reconnect(status, tokens);
		} else if (requestCode == ResponseCode.RSTL.getNumber()) {
			steal(status, tokens);
		} else if (requestCode == ResponseCode.RYLD.getNumber()) {
			yield(status, tokens);
		} else if (requestCode == ResponseCode.RDSCRD.getNumber()) {
			discard(status, tokens);
		} else if (requestCode == ResponseCode.RNSTRCTN.getNumber()) {
			instruction(status, tokens);
		} else if (requestCode == ResponseCode.RCVRDSCRD.getNumber()) {
			coverFromDiscard(status, tokens);
		} else if (requestCode == ResponseCode.RCVRWN.getNumber()) {
			coverFromOwn(status, tokens);
		} else if (requestCode == ResponseCode.RGTSCRS.getNumber()) {
			getScores(status, tokens);
		} else if (requestCode == ResponseCode.RGTWNSCR.getNumber()) {
			getOwnScores(status, tokens);
		} else if (requestCode == ResponseCode.RSTRT.getNumber()) {
			start(status, tokens);
		} else if (requestCode == ResponseCode.RDRW.getNumber()) {
			draw(status, tokens);
		}
	}

	private static void createGame(boolean status, String[] tokens) {
		String message = tokens[2];
		String playerId = tokens[3];
		String gameCode = tokens[4];
		String json = tokens[5];
		Util.playerId = playerId;
		Util.gameCode = gameCode;
		Util.viewController.createGameResponse(status, message, json);
	}

	private static void joinGame(boolean status, String[] tokens) {
		String originRequestId = tokens[2];
		String message = tokens[3];
		if (status) {
			// String playerName = tokens[4];
			String playerId = tokens[5];
			String gameCode = tokens[6];
			String json = tokens[7];
			Util.updatePlayerId(playerId, originRequestId);
			Util.gameCode = gameCode;
			Util.viewController.joinGameResponse(status, message, json);
		} else {
			System.out.println("failed to join");
			Util.viewController.joinGameResponse(status, message, null);
		}
	}

	private static void reconnect(boolean status, String[] tokens) {

	}

	private static void steal(boolean status, String[] tokens) {
		// String playerId = tokens[2];
		String message = tokens[3];
		// String attackerUsername = tokens[4];
		// String attackedUsername = tokens[5];
		String json = tokens[6];
		if (status) {
			Util.playGameController.updateView(json, message);
		}
	}

	private static void yield(boolean status, String[] tokens) {
		// String playerId = tokens[2];
		String message = tokens[3];
		// String yielderUsername = tokens[4];
		// String yieldedToUsername = tokens[5];
		String json = tokens[6];
		if (status) {
			Util.playGameController.updateView(json, message);
		}
	}

	private static void discard(boolean status, String[] tokens) {
		// String playerId = tokens[2];
		String message = tokens[3];
		// String cardText = tokens[4];
		String json = tokens[5];
		if (status) {
			Util.playGameController.updateView(json, message);
		}
	}
	
	private static void draw(boolean status, String[] tokens) {
		// String playerId = tokens[2];
		String message = tokens[3];
		// String playerUsername = tokens[4];
		// String counterpartUsername = tokens[5];
		String json = tokens[6];
		if (status) {
			Util.playGameController.updateView(json, message);
		}
	}

	private static void instruction(boolean status, String[] tokens) {

	}

	private static void coverFromDiscard(boolean status, String[] tokens) {
		// String playerId = tokens[2];
		String message = tokens[3];
		// String cardText = tokens[4];
		String json = tokens[5];
		if (status) {
			Util.playGameController.updateView(json, message);
		}
	}

	private static void coverFromOwn(boolean status, String[] tokens) {
		// String playerId = tokens[2];
		String message = tokens[3];
		// String cardText = tokens[4];
		String json = tokens[5];
		if (status) {
			Util.playGameController.updateView(json, message);
		}
	}

	private static void getScores(boolean status, String[] tokens) {

	}

	private static void getOwnScores(boolean status, String[] tokens) {

	}

	private static void start(boolean status, String[] tokens) {
		// String playerId = tokens[2];
		String message = tokens[3];
		String json = tokens[4];
		if (Util.viewController.playGameView == null) {
			Util.viewController.playGameView = new PlayGameView();
			Platform.runLater(new Runnable() {
				public void run() {
					Util.scene.setRoot(Util.viewController.playGameView);
				}
			});
		}
		Util.viewController.startGameResponse(status, message, json);
		Util.playGameController = new PlayGameController(Util.viewController.playGameView);
		Util.playGameController.updateView(json, message);
	}

}
