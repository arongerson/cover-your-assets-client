package com.cover_your_asset.client.helper;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import com.cover_your_asset.client.factory.DownloadFactory;
import com.cover_your_asset.client.model.Card;

public class CardFactory {
	
	private static List<Card> cards = new ArrayList<Card>();
	private static final String CARD_TEXT_FILE = "card_text";
	private static final String CARD_IMAGES_FOLDER = "images/";
	private static final String DELIMITER = ":;";
	
	private CardFactory() {}
	
	public static Card getCard(int cardId) {
		return cards.get(cardId);
	}
	
	public static void downloadCards() {
		Thread downloadThread = new Thread(new Runnable() {	
			public void run() {
				try {
					if (CardFactory.createCards()) {
						System.out.println("cards have been downloaded."); 
					}
				} catch(final Exception e) {
				}
			}
		});
		downloadThread.setDaemon(true);
		downloadThread.start();
	}
	
	private static boolean createCards() throws UnknownHostException, IOException {
		// download the card_text file
		// for every row in the file
		//	1. download the image if it does not exist
		//	2. create a card object and add it to the cardList
		// save the cardList in a file
		Socket socket = Util.getSocket();
		List<String> lines = DownloadFactory.getFileText(socket, CARD_TEXT_FILE);
		// if the cards/images folder does not exist create it
		DownloadFactory.createFolder(CARD_IMAGES_FOLDER);
		int id = 0;
		for (String line: lines) {
			String[] tokens = line.split(DELIMITER);
			String imgName = CARD_IMAGES_FOLDER + tokens[4].trim();
			socket = Util.getSocket();
			DownloadFactory.downloadFile(socket, imgName); 
			id = generateCards(id, tokens);
		}
		return true;
	}
	
	private static int generateCards(int id, String[] tokens) {
		int numberOfCards = Integer.parseInt(tokens[0].trim());
		for (int i = 0; i < numberOfCards; i++) {
			Card card = createCard(id, tokens);
			cards.add(card);
			id++;
		}
		return id;
	}
	
	private static Card createCard(int id, String[] tokens) {
		// number_of_cards :; value :; text :; image_name :; wild_card
		// for wild_card, 0 means not wild card, 1 means is wild card
		int type = Integer.parseInt(tokens[1].trim());
		int value = Integer.parseInt(tokens[2].trim());
		String text = tokens[3].trim();
		String imageName = tokens[4].trim();
		boolean wildCard = getWildCard(Integer.parseInt(tokens[5].trim()));
		Card card = new Card(id, type, value, text, CARD_IMAGES_FOLDER + imageName, wildCard);
		System.out.println(text);
		return card;
	}
	
	private static boolean getWildCard(int wildCardID) {
		return (wildCardID == 0) ? false : true;
	}
}
