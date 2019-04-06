package com.cover_your_asset.client.helper;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Random;

import com.cover_your_asset.client.PlayGameController;
import com.cover_your_asset.client.view.ViewController;

import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.paint.Color;

public class Util {
	
	public static final int PORT = 3333;
	public static final String HOST = "10.225.102.134";
	public static final String DELIMITER = ":;";
	public static String playerId;
	public static String gameCode;
	public static ViewController viewController;
	public static PlayGameController playGameController;
	public static BufferedWriter requestWriter;
	public static Scene scene; 
	public static final Color backgroundColor = new Color(242.0/255, 242.0/255, 242.0/255, 1.0);
	public static final Color whiteColor = new Color(1, 1, 1, 1.0);
	public static final Color blackColor = new Color(0, 0, 0, 1.0);
	public static final Color darkYelloGreen = new Color(61.0/255, 66.0/255, 39.0/255, 1.0);
	public static final Color lightYelloGreen = new Color(200/255, 209/255, 161/255, 1.0);
	
	public static final double CARD_WIDTH = 60;
	public static final double CARD_HEIGHT = 90;
	public static final double TRANSLATE_X = 0;
	public static final double TRANSLATE_Y = 0;
	
	
	private Util() {}
	
	public static String generateRandomRequestId() {
		Random random = new Random();
		return random.nextInt(20000) + "" + System.currentTimeMillis();
	}
	
	public static Socket getSocket() throws UnknownHostException, IOException { 
		return new Socket(HOST, PORT);
	}
	
	public static void updatePlayerId(String currentPlayerId, String requestOriginId) {
		if (requestOriginId.equals(playerId)) {
			playerId = currentPlayerId;
		}
	}
	
	public static Background getBackground() {
		File file = new File("resources/cards/table_surface.jpg");
		String path = "file:///" + file.getAbsolutePath();
		Image image = new Image(path);
		return new Background(new BackgroundImage(image, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT));
	}
}
