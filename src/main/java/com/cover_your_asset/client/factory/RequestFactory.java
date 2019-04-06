package com.cover_your_asset.client.factory;

import com.cover_your_asset.client.helper.RequestCode;

public class RequestFactory {

	private static final String DELIMITER = ":;";
	private static final String NEW_LINE = "\n";

	private RequestFactory() {
	}

	public static String generateDownloadFileRequestString(String filename) {
		// format: REQUEST_CODE :; filename
		return generateRequestString(Integer.toString(RequestCode.DWNLD.getNumber()), filename);
	}

	public static String generateCreateGameRequestString(String gameName, String username, String numberOfCards) {
		// format: REQUEST_CODE :; gameName :; username :; numberOfCards
		return generateRequestString(Integer.toString(RequestCode.CRTGM.getNumber()), gameName, username,
				numberOfCards);
	}

	public static String generateJoinGameRequestString(String code, String randomRequestId, String username) {
		// format: REQUEST_CODE :; gameCode :; requestId :; username
		return generateRequestString(Integer.toString(RequestCode.JNGM.getNumber()), code, randomRequestId, username);
	}

	public static String generateStartGameRequestString(String code, String playerId) {
		// format: REQUEST_CODE :; gameCode :; playerId
		return generateRequestString(Integer.toString(RequestCode.STRT.getNumber()), code, playerId);
	}

	public static String generateDiscardRequestString(String code, String playerId, String cardId) {
		// format: REQUEST_CODE :; gameCode :; playerId :; cardId
		return generateRequestString(Integer.toString(RequestCode.DSCRD.getNumber()), code, playerId, cardId);
	}

	public static String generateCoverWithOwnCardsRequestString(String gameCode, String playerId, String cardId1,
			String cardId2) {
		// format: REQUEST_CODE :; gameCode :; playerId :; card1 :; card2
		return generateRequestString(Integer.toString(RequestCode.CVRWN.getNumber()), gameCode, playerId, cardId1,
				cardId2);
	}

	public static String generateCoverFromDiscardPileRequestString(String gameCode, String playerId, String cardId) {
		// format: REQUEST_CODE :; gameCode :; playerId :; card1
		return generateRequestString(Integer.toString(RequestCode.CVRDSCRD.getNumber()), gameCode, playerId, cardId);
	}

	public static String generateStealRequestString(String gameCode, String attackerId, String attackedId,
			String cardId) {
		// format: REQUEST_CODE :; gameCode :; attackerId :; attackedId :; cardId
		return generateRequestString(Integer.toString(RequestCode.STL.getNumber()), gameCode, attackerId, attackedId, cardId);
	}
	
	public static String generateYieldRequestString(String gameCode, String yieldingPlayerId, String yieldedToPlayerId) {
		// format: REQUEST_CODE :; gameCode :; yieldingPlayerId :; yieldedToPlayerId
		return generateRequestString(Integer.toString(RequestCode.YLD.getNumber()), gameCode, yieldingPlayerId, yieldedToPlayerId);
	}
	
	public static String generateDrawRequestString(String gameCode, String playerId) {
		// format: REQUEST_CODE :; gameCode :; playerId
		return generateRequestString(Integer.toString(RequestCode.DRW.getNumber()), gameCode, playerId);
	}

	private static String generateRequestString(String... tokens) {
		StringBuffer requestString = new StringBuffer();
		for (String token : tokens) {
			requestString.append(token).append(DELIMITER);
		}
		// remove the last delimiter, last two chars
		int lastIndex = requestString.length();
		requestString.delete(lastIndex - 2, lastIndex);
		// append new line
		requestString.append(NEW_LINE);
		return requestString.toString();
	}
}
