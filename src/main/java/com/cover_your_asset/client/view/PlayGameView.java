package com.cover_your_asset.client.view;

import com.cover_your_asset.client.helper.Util;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Point3D;
import javafx.scene.AmbientLight;
import javafx.scene.DepthTest;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.PerspectiveCamera;
import javafx.scene.PointLight;
import javafx.scene.SceneAntialiasing;
import javafx.scene.SubScene;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class PlayGameView extends Pane {

	public static final double PADDING = 10;
	
	private Group cameraGroup = new Group();
	private SubScene subScene;
	
	private Label foulPlayLabel;
	public ActionsView actionsView;
	public AssetsView selectedAssetsView;
	public DiscardPileView discardPileView;
	public Group root3D = new Group();
	public PlayGameView() {
		initControls();
	}

	private void initControls() {
		createComponents();
		setControlProperties();
		init3D();
	}

	public void setListeners(EventHandler<ActionEvent> stealHandler, EventHandler<ActionEvent> yieldHandler,
			EventHandler<ActionEvent> coverHandler, EventHandler<ActionEvent> discardHandler,
			EventHandler<ActionEvent> drawHandler) {
		actionsView.setListeners(stealHandler, yieldHandler, coverHandler, discardHandler, drawHandler);
	}

	private void createComponents() {
		actionsView = new ActionsView();
		foulPlayLabel = new Label();
		getChildren().addAll(actionsView, foulPlayLabel);	
	}

	private void setControlProperties() {
		// actionsView
		actionsView.layoutXProperty().bind(Util.scene.widthProperty().subtract(actionsView.widthProperty()).subtract(10));
		actionsView.layoutYProperty().bind(Util.scene.heightProperty().subtract(actionsView.heightProperty()).subtract(10));
		
		foulPlayLabel.setBackground(new Background(new BackgroundFill(Color.WHITE, new CornerRadii(3, 3, 3, 3, false), Insets.EMPTY)));
		foulPlayLabel.setTextFill(Color.RED); 
		foulPlayLabel.setPadding(new Insets(3));
		foulPlayLabel.setTranslateX(10);
		foulPlayLabel.setTranslateY(10);
		foulPlayLabel.setTranslateZ(-1);
		foulPlayLabel.setVisible(false);
		//foulPlayLabel.setPrefWidth(300);
		
		setBackground(Util.getBackground());
	}

	public void init3D() {
		subScene = new SubScene(root3D, 800, 600, true, SceneAntialiasing.BALANCED);
		subScene.widthProperty().bind(Util.scene.widthProperty());
		subScene.heightProperty().bind(Util.scene.heightProperty());
		getChildren().add(subScene);
		PerspectiveCamera camera = new PerspectiveCamera(true);
		subScene.setCamera(camera); 
	    cameraGroup.getChildren().add(camera);
	    root3D.getChildren().add(cameraGroup);
	    camera.setFarClip(10000);
	    cameraGroup.setRotationAxis(new Point3D(10, 0, 0));
	    double theta = 20;
	    double height = 700;
	    cameraGroup.setRotate(theta);
	    cameraGroup.setTranslateX(-20);
	    cameraGroup.setTranslateY(100 + height*Math.tan(Math.toRadians(theta)));
	    cameraGroup.setTranslateZ(-height);
	    cameraGroup.setCache(true);
	    cameraGroup.setDepthTest(DepthTest.ENABLE);
	    camera.setFieldOfView(40); 
	}

	public void update() {
		root3D = null;
		System.gc();
		root3D = new Group();
		root3D.getChildren().add(cameraGroup);
		subScene.setRoot(root3D); 
	}

	public void addNode(Node node) {
		root3D.getChildren().add(node);
	}

	public void updateLastActionMessage(String message) {
		actionsView.updateLastActionMessage(message);
	}
	
	public void addLight() {
		PointLight light = new PointLight();
	    light.setColor(Color.RED);
	    light.setTranslateX(-100);
	    light.setTranslateY(400);
	    light.setTranslateZ(-100);
	    
	    // create a ambient light 
        AmbientLight ambientLight = new AmbientLight(Util.lightYelloGreen); 
	    root3D.getChildren().addAll(light, ambientLight);
	}
	
	public void updateError(String message) {
		foulPlayLabel.setText(message); 
		foulPlayLabel.setVisible(true); 
		Thread th = new Thread(new Runnable() {
			public void run() {
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					
				}
				foulPlayLabel.setVisible(false);
			}
		});
		th.setDaemon(true);
		th.start();
	}

}
