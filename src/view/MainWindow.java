package view;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;

import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.animation.PauseTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.NPC;
import model.TennisBall;
import model.TennisPlayer;
import players.mariaSharapova;
import players.andyMurray;
import players.serenaWilliams;
import players.gaelMonfils;
import players.novakDjokovic;
import players.peteSampras;
import players.rafaelNadal;
import players.rogerFederer;
import players.stanWawrinka;

public class MainWindow extends Application{
	
	//UI Instance variables
	private Stage mainStage;
	private Scene welcomeScene;
	private Scene characterScene;
	private Scene gameScene;
	private BorderPane p;
	private Pane drawingPane;
	private Pane welcomeScreenPane;
	private Pane characterScreenPane;
	private Button backButton;
	private Button playButton;
	private Button easyButton;
	private Button unbeatableButton;
	private Button createButton;
	private ComboBox playerSelectComboBox;
	private TextField nameTextField;
	private VBox createPlayerVBox;
	private VBox playerSelectVBox;
	
	//To keep track of currently loaded player
	private Object selectedObject;
	private TennisBall tennisBall;
	private TennisPlayer selectedPlayer;
	private ImageView currentPlayerIV;
	private ImageView enemyPlayerIV;
	
	//Collections
	private ArrayList<TennisPlayer> tennisPlayers = new ArrayList<>();
	
	//Keycode booleans
	private boolean isKeyLeft = false;
	private boolean isKeyRight = false;
	private boolean isKeyPressed = false;
	
	//Game instances
	private boolean startPlay = false;
	private StackPane scorePane;
	private Text umpireText;
	private int enemyScoreCount = 0;
	private int playerScoreCount = 0;
	private boolean isUnbeatable = false;
	private boolean isFirstTime = true;
	
	//Player image urls
	private String tennisCourtURL = "http://image.shutterstock.com/z/stock-vector-green-tennis-court-with-low-net-stretched-across-the-center-viewed-slightly-from-top-with-visible-329870003.jpg";
	private String wawrinkaURL = "http://www.iptlworld.com/img//images/clients/iptl/entities/players/big/72.png";
	private String samprasURL = "http://nl.babolat.com/statics/img/history/1990.png";
	private String murrayURL = "http://www.iptlworld.com/img//images/clients/iptl/entities/players/big/8.png";
	private String nadalURL = "http://www.premiertennis.com/wp-content/uploads/2013/12/player_slider_2_03.png";
	private String federerURL = "http://siltontennis.com/images/racquets/wilson/federer.gif";
	private String djokovicURL = "http://www.iptlworld.com/img//images/clients/iptl/entities/players/big/15.png";
	private String monfilsURL = "http://media-s3-us-east-1.ceros.com/tennis/images/2015/05/22/1850c45afb615f0021bbb2bed6b2e519/monfils.png";
	private String serenaURL = "http://www.officialpsds.com/images/thumbs/Serena-williams1-psd89664.png";
	private String sharapovaURL = "http://media-s3-us-east-1.ceros.com/tennis/images/2015/05/26/9ce1e05f44d964254a73d99daf75c52e/sharapovanew.png";
	private String pascalURL = "https://i.ytimg.com/vi/rf0Qg1FIris/hqdefault.jpg";
	private String transparentURL = "http://silhouettesfree.com/sports/tennis/female-tennis-player-silhouette-image-11.png";
	
	//Controller instance
	private GameController controller;
	
	//Sound instance
	private AudioClip soundEffect;
	
	@Override
	public void start(Stage stage) throws Exception {
		mainStage = stage;
		//Set welcome screen pane on scene
		welcomeScene = new Scene(setUpWelcomeScreen(), 1300, 1000);
		mainStage.setScene(welcomeScene);
		mainStage.setTitle("Tennis Pong");
		mainStage.setResizable(false);
		mainStage.show();
		
		//welcome listeners
		welcomeListeners();
	}
	
	private void initializeGame(){
		//Load sound effect
		final URL sound = getClass().getResource("/tennis.mp3");
		soundEffect = new AudioClip(sound.toString());
				
		//Start game controller
		controller = new GameController();
		controller.startController();
	}
	
	//Code to setup Welcome Screen
	private Pane setUpWelcomeScreen(){
		welcomeScreenPane = new Pane();
		welcomeScreenPane.setMaxHeight(1300);
		welcomeScreenPane.setMaxWidth(1000);
		ImageView background = new ImageView(new Image("/welcome_screen.jpg"));
		background.setFitWidth(1300);
		background.setFitHeight(1000);
		background.setX(0);
		background.setY(0);
		
		Label titleLabel = new Label("Tennis Pong");
		titleLabel.setFont(Font.font("Futura", FontWeight.BOLD, 72));
		titleLabel.setTextFill(Color.web("#A52A2A"));
		titleLabel.setLayoutX(250);
		titleLabel.setLayoutY(450);
		
		Label instructionsLabel = new Label("Enter key to start each point"
				+ "\nLeft and right arrow keys to move player"
				+ "\nWhoever gets to \"game\" first wins!"
				+ "\nScoring system: 15, 30, 40, and then \"game\"");
		instructionsLabel.setFont(Font.font("Futura", 28));
		instructionsLabel.setTextFill(Color.web("#A52A2A"));
		instructionsLabel.setLayoutX(220);
		instructionsLabel.setLayoutY(580);
		
		playButton = new Button("Play!");
		playButton.setFont(Font.font("Futura", 36));
		playButton.setTextFill(Color.web("#A52A2A"));
		playButton.setLayoutX(420);
		playButton.setLayoutY(800);
		
		welcomeScreenPane.getChildren().addAll(background, titleLabel, instructionsLabel, playButton);
	
		return welcomeScreenPane;
	}
	
	//Code to setup Character Screen
	private Pane setUpCharacterScreen(){
		
		characterScreenPane = new Pane();
		characterScreenPane.setMaxHeight(1300);
		characterScreenPane.setMaxWidth(1000);
		ImageView background = new ImageView(new Image("/welcome_screen.jpg"));
		background.setFitWidth(1300);
		background.setFitHeight(1000);
		background.setX(0);
		background.setY(0);
		
		easyButton = new Button("Easy");
		easyButton.setFont(Font.font("Futura", 36));
		easyButton.setTextFill(Color.web("#A52A2A"));
		easyButton.setLayoutX(300);
		easyButton.setLayoutY(800);
		unbeatableButton = new Button("Unbeatable");
		unbeatableButton.setFont(Font.font("Futura", 36));
		unbeatableButton.setTextFill(Color.web("#A52A2A"));
		unbeatableButton.setLayoutX(700);
		unbeatableButton.setLayoutY(800);
		
		playerSelectVBox = new VBox();
		Label selectPlayerLabel = new Label("Select a Player");
		selectPlayerLabel.setFont(Font.font("Futura", FontWeight.BOLD, 36));
		selectPlayerLabel.setTextFill(Color.web("#A52A2A"));
		playerSelectComboBox = new ComboBox();
		//no need to initialize after first time
		if(isFirstTime){
			initializePlayers();
			isFirstTime = false;
		}
		playerSelectComboBox.getItems().addAll(initializeSelectionOptions());
		//Setting margins
		playerSelectVBox.setMargin(selectPlayerLabel, new Insets(10, 0, 0, 50));
		playerSelectVBox.setMargin(playerSelectComboBox, new Insets(10, 0, 0, 50));
		playerSelectVBox.getChildren().addAll(selectPlayerLabel, playerSelectComboBox);
		
		createPlayerVBox = new VBox();
		Label setNameLabel = new Label("Name of player");
		setNameLabel.setFont(Font.font("Futura", FontWeight.BOLD, 36));
		setNameLabel.setTextFill(Color.web("#A52A2A"));
		nameTextField = new TextField();
		createButton = new Button("Create");
		createButton.setFont(Font.font("Futura", 18));
		createButton.setTextFill(Color.web("#A52A2A"));
		//Setting margins
		createPlayerVBox.setMargin(setNameLabel, new Insets(10, 0, 0, 900));
		createPlayerVBox.setMargin(nameTextField, new Insets(10, 0, 0, 900));
		createPlayerVBox.setMargin(createButton, new Insets(10, 0, 0, 1200));
		createPlayerVBox.getChildren().addAll(setNameLabel, nameTextField, createButton);
		
		characterScreenPane.getChildren().addAll(background, easyButton, unbeatableButton, playerSelectVBox, createPlayerVBox);
		createPlayerVBox.setVisible(false);
		
		return characterScreenPane;
	}
	
	//Code to setup UI
	private BorderPane setUpGameScene(){
		//Setting up layouts
		drawingPane = new Pane();
		drawingPane.setMaxWidth(1200);
		drawingPane.setMaxHeight(800);
		ImageView background = new ImageView(new Image(tennisCourtURL));
		background.setFitWidth(1200);
		background.setFitHeight(800);
		background.setX(0);
		background.setY(0);
		drawingPane.getChildren().addAll(background);
		p = new BorderPane();
		p.setMaxWidth(500);
		p.setMaxHeight(550);
		VBox vBox = new VBox();
		
		//Selection
		backButton = new Button("Back to Main Menu");
		backButton.setFont(Font.font("Futura", 20));
		backButton.setTextFill(Color.web("#A52A2A"));
		vBox.setMargin(backButton, new Insets(10, 0, 0, 50));
		//Adding to pane
		vBox.getChildren().addAll(backButton);
		
		createNPC();
		createPlayers();
		
		p.setTop(vBox);
		p.setCenter(drawingPane);
		
		initializeGame();
		
		return p;
	}
	
	private void createNPC(){
		NPC npcUmpire = new NPC("Pascal Maria", pascalURL);
		ImageView npcUmpireIV = new ImageView(new Image("/pascal_maria.png"));
		npcUmpireIV.setX(30);
		npcUmpireIV.setY(400-30);
		npcUmpireIV.setFitHeight(90);
		npcUmpireIV.setFitWidth(90);
		
		drawingPane.getChildren().add(npcUmpireIV);
	}
	
	private void createPlayers(){
		//Now that selected player is known, can start dialoge
		createDialog();
		startScoreDialog();
		
		TennisPlayer novakDjokovic = new novakDjokovic("Novak Djokovic", 9, 7, 10, 8, djokovicURL);
		enemyPlayerIV = new ImageView(new Image(novakDjokovic.getImageURL()));
		//Setting dimensions
		enemyPlayerIV.setFitHeight(120);
		enemyPlayerIV.setFitWidth(120);
		enemyPlayerIV.setX(600-120/2);
		enemyPlayerIV.setY(0);
		
		drawingPane.getChildren().addAll(enemyPlayerIV, currentPlayerIV);
	}
	
	private void createTennisBall(){
		tennisBall = new TennisBall(40, 40, 10, Color.GREENYELLOW);
		//Setting dimensions
		tennisBall.setCenterX(600-tennisBall.getRadius());
		tennisBall.setCenterY(400-tennisBall.getRadius());
		drawingPane.getChildren().add(tennisBall);
	}
	
	private void createDialog(){
		Rectangle scorebox = new Rectangle();
		scorebox.setWidth(180);
		scorebox.setHeight(50);
		scorebox.setArcHeight(10);
		scorebox.setArcWidth(10);
		scorebox.setFill(Color.WHITE);
		scorebox.setStroke(Color.BLACK);
		
		umpireText = new Text(selectedPlayer.getName() + " to serve");
		
		scorePane = new StackPane();
		scorePane.getChildren().addAll(scorebox, umpireText);
		scorePane.setLayoutX(15);
		scorePane.setLayoutY(300);
		
		drawingPane.getChildren().add(scorePane);
	}
	
	private void startScoreDialog(){
		PauseTransition pause = new PauseTransition(Duration.seconds(3));
		    pause.setOnFinished(event -> {
		    	updateDialog();
		    });
		    pause.play();
	}
	
	private void welcomeListeners(){
		playButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				characterScene = new Scene(setUpCharacterScreen(), 1300, 1000);
				mainStage.setScene(characterScene);
				characterListeners();
			}
		});
	}
	
	private void loadPlayer(){
		//Can choose only one player to load
		currentPlayerIV = new ImageView(new Image(selectedPlayer.getImageURL()));
		//Setting dimensions and scaling
		currentPlayerIV.setFitWidth(120);
		currentPlayerIV.setFitHeight(120);
		currentPlayerIV.setX(600-120/2);
		currentPlayerIV.setY(800-120);
	}
	
	private void characterListeners(){
		//Combo-box handling
		playerSelectComboBox.valueProperty().addListener(new ChangeListener<Object>() {
			@Override
			public void changed(ObservableValue<? extends Object> observable, Object oldValue,
					Object newValue) {
				selectedObject = newValue;
				
				//Chose to create new player
				if(selectedObject instanceof String){
					createPlayerVBox.setVisible(true);
					playerSelectComboBox.setItems(initializeSelectionOptions());
				}
				//Chose existing player
				else{
					selectedPlayer = (TennisPlayer)selectedObject;
				}
			}
		});
		createButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if(!nameTextField.getText().isEmpty()){
					TennisPlayer newPlayer = new TennisPlayer(nameTextField.getText(), 8, 8, 8, 8, transparentURL);
					tennisPlayers.add(newPlayer);
					selectedPlayer = newPlayer;
				}
			}
		});
		easyButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if(selectedPlayer != null){
					isUnbeatable = false;
					loadPlayer();
					gameScene = new Scene(setUpGameScene(), 1300, 1000);
					mainStage.setScene(gameScene);
					gameListeners();
				}
			}
		});
		unbeatableButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if(selectedPlayer != null){
					isUnbeatable = true;
					loadPlayer();
					gameScene = new Scene(setUpGameScene(), 1300, 1000);
					mainStage.setScene(gameScene);
					gameListeners();
				}
			}
		});
	}
	
	private void randomizeDirection(){
		Random random = new Random();
		int result = random.nextInt(2);
		
		if(result == 1){
			tennisBall.changeDirectionX();
		}
	}
	
	private void gameListeners(){
		gameScene.setOnKeyPressed(new EventHandler<KeyEvent>(){

			@Override
			public void handle(KeyEvent event) {
				isKeyPressed = true;
				if(event.getCode() == KeyCode.LEFT)
					isKeyLeft = true;
				if(event.getCode() == KeyCode.RIGHT)
					isKeyRight = true;
				if(event.getCode() == KeyCode.ENTER){
					//Checks so that you can't play when the game is over
					if(playerScoreCount < 4 && enemyScoreCount < 4){
						//Hide dialog message scorebox
						scorePane.setVisible(false);
					
						startPlay = true;
						createTennisBall();
						randomizeDirection();
					}
			}
		}
		
		});
		gameScene.setOnKeyReleased(new EventHandler<KeyEvent>(){

			@Override
			public void handle(KeyEvent event) {
				isKeyPressed = false;
				isKeyLeft = false;
				isKeyRight = false;
			}
		
		});
		backButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				//Reset
				playerScoreCount = 0;
				enemyScoreCount = 0;
				selectedPlayer = null;
				isUnbeatable = false;
				welcomeScene = new Scene(setUpWelcomeScreen(), 1300, 1000);
				mainStage.setScene(welcomeScene);
				welcomeListeners();
			}
		});
	}
	
	private void initializePlayers(){
		TennisPlayer rogerFederer = new rogerFederer("Roger Federer", 8, 9, 6, 10, federerURL);
		TennisPlayer rafaelNadal = new rafaelNadal("Rafel Nadal", 10, 6, 8, 7, nadalURL);
		TennisPlayer andyMurray = new andyMurray("Andy Murray", 9, 8, 9, 8, murrayURL);
		TennisPlayer stanWawrinka = new stanWawrinka("Stan Wawrinka", 7, 8, 10, 8, wawrinkaURL);
		TennisPlayer peteSampras = new peteSampras("Pete Sampras", 7, 8, 10, 8, samprasURL);
		TennisPlayer mariaSharapova = new mariaSharapova("Maria Sharapova", 8, 7, 8, 8, sharapovaURL);
		TennisPlayer serenaWilliams = new serenaWilliams("Serena Williams", 7, 9, 8, 8, serenaURL);
		TennisPlayer gaelMonfils = new gaelMonfils("Gael Monfils", 9, 7, 8, 8, monfilsURL);
		tennisPlayers.add(rogerFederer);
		tennisPlayers.add(rafaelNadal);
		tennisPlayers.add(andyMurray);
		tennisPlayers.add(stanWawrinka);
		tennisPlayers.add(peteSampras);
		tennisPlayers.add(mariaSharapova);
		tennisPlayers.add(serenaWilliams);
		tennisPlayers.add(gaelMonfils);
	}
	
	//Create strings for selection box
	private ObservableList<Object> initializeSelectionOptions(){
		ObservableList<Object> options= FXCollections.observableArrayList("Create new player");
		
		for(TennisPlayer tennisplayer : tennisPlayers){
			options.add(tennisplayer);
		}
		
		return options;
	}
	
	private void resetGame(){
		startPlay = false;
		drawingPane.getChildren().remove(tennisBall);
		tennisBall = null;
		currentPlayerIV.setX(600-120/2);
		enemyPlayerIV.setX(600-120/2);
		
		updateDialog();
		
	}
	
	private void updateDialog(){
		scorePane.setVisible(true);
		
		String playerScore = "";
		String enemyScore = "";
		
		switch(playerScoreCount){
			case 0: playerScore = "0";
					break;
			case 1: playerScore = "15";
				break;
			case 2: playerScore = "30";
				break;
			case 3: playerScore = "40";
				break;
			case 4: playerScore = "Game " + selectedPlayer.getName();
				break;
		}
		
		switch(enemyScoreCount){
			case 0: enemyScore = "0";
				break;
			case 1: enemyScore = "15";
				break;
			case 2: enemyScore = "30";
				break;
			case 3: enemyScore = "40";
				break;
			case 4: enemyScore = "Game " + "Djokovic";
				break;
		}
		
		//Player wins
		if(playerScoreCount == 4){
			//Load sound effect
			final URL sound = getClass().getResource("/cheer.mp3");
			soundEffect = new AudioClip(sound.toString());
			soundEffect.play();
			
			umpireText.setText(playerScore);
		}
		//AI wins
		else if(enemyScoreCount == 4){
			//Load sound effect
			final URL sound = getClass().getResource("/boo.mp3");
			soundEffect = new AudioClip(sound.toString());
			soundEffect.play();
			
			umpireText.setText(enemyScore);
		}
		else 
			umpireText.setText(playerScore + " - " + enemyScore);
	}
	
	public static void main(String[] args){
		launch(args);
	}
	
	class GameController {
		
		public GameController(){}
		
		public void startController(){
			doTennisBallAnimation();
			doPlayerAnimation();
		}
		
		private void doTennisBallAnimation() {
			AnimationTimer timer = new AnimationTimer(){
				@Override
				public void handle(long now) {
					if(tennisBall != null && startPlay){
						double left = tennisBall.getCenterX() - 20;
						double right = tennisBall.getCenterX() + 20;
						double top = tennisBall.getCenterY() - 20;
						double bottom = tennisBall.getCenterY() + 20;
						
						//Check Boundaries, change direction if needed
						//see if ball every collides with enemy or player
						boolean isIntersectingEnemy = tennisBall.
								intersects(enemyPlayerIV.getX(), enemyPlayerIV.getY(), 
								enemyPlayerIV.getFitWidth(), enemyPlayerIV.getFitHeight());
						boolean isIntersectingPlayer = tennisBall.
								intersects(currentPlayerIV.getX(), currentPlayerIV.getY(), 
								currentPlayerIV.getFitWidth(), currentPlayerIV.getFitHeight());
						
						//Reset game
						if(bottom >= drawingPane.getHeight()){
							enemyScoreCount++;
							resetGame();
						}
						else if(top <= 0){
							playerScoreCount++;
							resetGame();
						}
						
						if(right >= drawingPane.getWidth() || left < 0){
							tennisBall.changeDirectionX();
						}
						
						if (isIntersectingEnemy || isIntersectingPlayer ) {
							tennisBall.changeDirectionY();
							randomizeDirection();
							soundEffect.play();
						}
						
						if(tennisBall != null)
							tennisBall.update();
					}
				}
			};

			timer.start();
		}
		
		private void doPlayerAnimation() {
			AnimationTimer timer = new AnimationTimer(){
				@Override
				public void handle(long now) {
					if(enemyPlayerIV != null && startPlay){
						double left = enemyPlayerIV.getX();
						double right = enemyPlayerIV.getX() + 120;
						
						if(tennisBall != null){
							if(!tennisBall.isMovingRight()){
								if(left > 0){
									if(isUnbeatable)
										enemyPlayerIV.setX(enemyPlayerIV.getX() - 1*1.5);
									else
										enemyPlayerIV.setX(enemyPlayerIV.getX() - 1*1.1);
								}
							}
							
							if(tennisBall.isMovingRight()){
								if(right < drawingPane.getWidth()){
									if(isUnbeatable)
										enemyPlayerIV.setX(enemyPlayerIV.getX() + 1*1.5);
									else
										enemyPlayerIV.setX(enemyPlayerIV.getX() + 1);
								}
							}
						}
					}
					
					if(currentPlayerIV != null){
						double left = currentPlayerIV.getX();
						double right = currentPlayerIV.getX() + 120;
						
						if(isKeyLeft){
							if(left > 0){
								currentPlayerIV.setX(currentPlayerIV.getX() - 1*2);
							}
						}
						
						if(isKeyRight){
							if(right < drawingPane.getWidth()){
								currentPlayerIV.setX(currentPlayerIV.getX() + 1*2);
							}
						}
					}
					
				}
			};

			timer.start();

		}
		
	}
	
}
