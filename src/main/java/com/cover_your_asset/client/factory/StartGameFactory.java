package com.cover_your_asset.client.factory;

import java.io.BufferedWriter;
import java.io.IOException;

import com.cover_your_asset.client.helper.Util;

public class StartGameFactory {
	
	private StartGameFactory() {}
	
	public static void startGame(final BufferedWriter requestWriter) { 
		
		Thread thread = new Thread(new Runnable() {
			public void run() {
				String requestString = RequestFactory.generateStartGameRequestString(Util.gameCode, Util.playerId);
				try {
					requestWriter.write(requestString);
					requestWriter.flush();
				} catch (IOException e) {
					System.out.println("Error during starting the game");
				}
			}
		});
		thread.setDaemon(true);
		thread.start();
	}
}
