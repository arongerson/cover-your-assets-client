package com.cover_your_asset.client;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import com.cover_your_asset.client.factory.CreateGameFactory;
import com.cover_your_asset.client.factory.JoinGameFactory;
import com.cover_your_asset.client.factory.ResponseFactory;
import com.cover_your_asset.client.factory.StartGameFactory;
import com.cover_your_asset.client.helper.CardFactory;
import com.cover_your_asset.client.helper.Util;
import com.cover_your_asset.client.view.ViewController;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.stage.Stage;
 
public class Main extends Application {
	
	private static BufferedWriter requestWriter;
	private static BufferedReader responseReader;
	private ViewController mainView;
	private Scene scene;
	private Socket socket;

	public static void main(String[] args) {
        launch(args);
    }
    
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Cover Your Assets");
        mainView = new ViewController();
        Util.viewController = mainView;
        scene = new Scene(mainView, 800, 700, true);
        Util.scene = scene;
        primaryStage.setScene(scene);
        primaryStage.show();
        initClient();
    }
    
    private void initClient() {
    	downloadCardFiles();
    	addListeners();
    	connect();
	}
    
    private void addListeners() {
		mainView.setMainViewListeners(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				switchGameSetupView();
			}
		});
		mainView.createGameView.setListeners(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				createGame();
			}
		}); 
		mainView.joinGameView.setListeners(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				joinGame();
			}
		});
		mainView.waitGameView.setListeners(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				startGame();
			}
		});
		mainView.scoresView.setListeners(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				closeScores();
			}
		});
	}
    
    private void switchGameSetupView() {
		mainView.switchSetupView();
	}
    
    private void createGame() {
		CreateGameFactory.createGame(requestWriter, mainView);
	}
    
    private void joinGame() {
    	// the random id is used during joining the game,
		// this is used when the response comes because to identify the source of the request since
		// before joining the game, the player still doesn't have a unique identifier to uniquely identify him
		// in the game
    	JoinGameFactory.joinGame(requestWriter, mainView);
	}
    
    private void startGame() {
    	StartGameFactory.startGame(requestWriter);
	}
    
    private void closeScores() {
    	mainView.backToHome();
    }

	private void downloadCardFiles() {
		CardFactory.downloadCards();
	}
	
	private void connect() {
		Thread th = new Thread(new Runnable() {
			public void run() {
				try {
					updateIOStreams(Util.getSocket());
				} catch (final UnknownHostException e) {
					
				} catch (final IOException e) {
					
				}
			}
		});
		th.setDaemon(true);
		th.start();
	}
	
	private void updateIOStreams(Socket newSocket) throws IOException { 
		if (socket != null && !socket.isClosed()) {
			socket.close();
		}
		this.socket = newSocket;
		requestWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
		Util.requestWriter = requestWriter;
		responseReader = new BufferedReader(new InputStreamReader(socket.getInputStream()), 4*1024);
		readResponse();
	}

	private void readResponse() throws IOException { 
		String response;
		while ((response = responseReader.readLine()) != null) {
			try {
				ResponseFactory.process(response);
			} catch(Exception exception) {
				System.out.println("error processing response: " + exception.toString());
				exception.printStackTrace();
			}
		}
	}
    
}