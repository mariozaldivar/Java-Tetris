package Tetris;

import javafx.animation.*;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.HashMap;
import java.util.Map;

import static Tetris.Game.BOARD_HEIGHT;
import static Tetris.Game.BOARD_WIDTH;

public class GUI extends Application {
  Game updateGame = new Game();


  private static final int cellWidht = 30;
  private static final int cellHeight = 30;
  GridPane Tablero = new GridPane();
  Node[][] Cellmap = new Node[BOARD_HEIGHT][BOARD_WIDTH];


  //Configuracion del tablero
  private void CrearTablero(){
    Tablero.setAlignment(Pos.CENTER);
    for (int rows = 0; rows < BOARD_HEIGHT; rows++){
      for (int colums = 0; colums < BOARD_WIDTH; colums++){
        Rectangle Cell = new Rectangle(cellWidht,cellHeight);
        if (rows < 2){
          Cell.setOpacity(.20);
          Cell.setStroke(Color.BLACK);
        }
        Tablero.add(Cell,colums,rows);
        Cellmap[rows][colums] = Cell;
        Cell.setStroke(Color.LIGHTBLUE);
      }
    }
  }

  private void updateTablero(){
    int[][] tableroActual = updateGame.getBoardState();
    for(int rows = 0; rows < BOARD_HEIGHT; rows++){
      for (int colums = 0; colums < BOARD_WIDTH; colums++){
        Rectangle currentCell = (Rectangle) Cellmap[rows][colums];
        int numeroCelda = tableroActual[rows][colums];
        currentCell.setFill(setColor(numeroCelda));

      }
    }
  }

  private static  Color setColor(int numeroCelda) {
    return switch (numeroCelda) {
      case 1 -> Color.RED;
      case 2 -> Color.PALETURQUOISE;
      case 3 -> Color.BLUE;
      case 4 -> Color.GREEN;
      case 5 -> Color.YELLOW;
      case 6 -> Color.ANTIQUEWHITE;
      case 7 -> Color.SALMON;
      default -> Color.BLACK;
    };
  }

  //Botones
  private void hoverOverButton(Button button){
    button.setPrefSize(400,75);
    button.setOnMouseEntered(event -> button.setStyle("-fx-background-color: #22bfa1;"));
    button.setOnMouseExited(event -> button.setStyle("-fx-background-color: #ffffff;"));
  }

  private void setupMenuButton(Button button, Scene tetris, BorderPane cambiarEscena, String nombre){
    button.setText(nombre);
    hoverOverButton(button);
    button.setOnMouseClicked(event -> {
      button.setStyle("-fx-background-color: #3ac129;");
      tetris.setRoot(cambiarEscena);
      if (nombre.equals("INICIAR")){
        System.out.println("Se ha presionado " + nombre);
        updateTableroTetris();
        Clock.INSTANCE.startGame();
      } else if (nombre.equals("CONFIG")) {
        System.out.println("Config");
        //
      }
    });
    button.setOnMouseReleased(event -> button.setStyle("-fx-background-color: #ffffff;"));
  }

  private void setupConfigButtons(VBox keybindsUI, Map<String, KeyCode> keybinds){
    keybindsUI.getChildren().clear();
    keybindsUI.setPadding(new Insets(50, 0 ,50, 0));
    GridPane configLayout = new GridPane();
    configLayout.setHgap(30);
    configLayout.setVgap(15);
    configLayout.setAlignment(Pos.CENTER);

    int row = 0;
    for (String keyName : keybinds.keySet()){
      Label label = new Label(keyName);
      label.setStyle("-fx-font-size: 50px; -fx-text-fill: black; -fx-font-family:Sans Serif");

      Button button = new Button(keybinds.get(keyName).toString());
      button.setPrefSize(400,50);
      button.setOnMouseClicked(event -> {
        button.requestFocus();
        button.setText("...");

        button.focusedProperty().addListener((ObservableValue,OldValue,NewValue) ->{
          if (!NewValue){
            button.setText(keybinds.get(keyName).toString());
            button.setOnKeyPressed(null);
          }
        });

        button.setOnKeyPressed(e -> {
          KeyCode currentkey = e.getCode();
          if (currentkey == KeyCode.ESCAPE){
            button.setText(keybinds.get(keyName).toString());
            button.setOnKeyPressed(null);
          }
          if (keybinds.containsValue(currentkey)){
            button.setText("Key already in use");
            PauseTransition wait = new PauseTransition(Duration.seconds(1));
            wait.setOnFinished(Actionevent -> button.setText(keybinds.get(keyName).toString()));
            wait.play();
          } else {
            keybinds.put(keyName, currentkey);
            button.setText(currentkey.toString());
            button.setOnKeyPressed(null);
          }
          e.consume();
        });
      });
      configLayout.add(label,0,row);
      configLayout.add(button,1,row);
      row++;
    }
    keybindsUI.getChildren().add(configLayout);
    keybindsUI.setAlignment(Pos.CENTER);
  }

  private void resetKeybinds(VBox keybindsUI, Map<String, KeyCode> keybinds){

  }

  public void start(Stage currentScene) throws Exception {
    //Menu principal


    BorderPane menuPrincipal = new BorderPane();
    BorderPane menuJuego = new BorderPane();
    BorderPane menuConfiguracion = new BorderPane();
    ScrollPane scrollConfig = new ScrollPane();
    menuConfiguracion.setCenter(scrollConfig);


    Scene pantallaTetris = new Scene(menuPrincipal, Color.WHITE);

    VBox botonesInicio = new VBox();

    Button iniciarJuego = new Button();
    Button Configuracion = new Button();
    Text tituloMenuPrincipal = new Text("TETRIS");

    tituloMenuPrincipal.setFont(new Font("Impact", 100));

    setupMenuButton(iniciarJuego, pantallaTetris, menuJuego, "INICIAR");
    setupMenuButton(Configuracion, pantallaTetris, menuConfiguracion, "CONFIG");

    botonesInicio.getChildren().addAll(tituloMenuPrincipal,iniciarJuego,Configuracion);

    menuPrincipal.setCenter(botonesInicio);
    botonesInicio.setAlignment(Pos.CENTER);

    //Configuracion
    VBox ConfigUI = new VBox();
    ConfigUI.setAlignment(Pos.CENTER);
    ConfigUI.setSpacing(20);
    Button back = new Button();

    Text tituloConfiguracion = new Text("Configuración");
    tituloConfiguracion.setFont(new Font("Impact",100));

    Map<String, KeyCode> keybinds = new HashMap<>();
    keybinds.put("Left",KeyCode.LEFT);
    keybinds.put("Right",KeyCode.RIGHT);
    keybinds.put("Soft drop", KeyCode.DOWN);
    keybinds.put("Hard drop", KeyCode.SPACE);
    keybinds.put("Rotate clockwise", KeyCode.X);
    keybinds.put("Rotate Counterclockwise", KeyCode.Z);
    keybinds.put("rotate 180",KeyCode.A);
    keybinds.put("Hold",KeyCode.C);

    setupConfigButtons(ConfigUI, keybinds);
    ConfigUI.getChildren().addFirst(tituloConfiguracion);
    setupMenuButton(back, pantallaTetris, menuPrincipal , "BACK");
    ConfigUI.getChildren().add(back);

    menuConfiguracion.setBottom(back);
    menuConfiguracion.setCenter(scrollConfig);

    scrollConfig.setContent(ConfigUI);
    scrollConfig.setFitToWidth(true);
    scrollConfig.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
    scrollConfig.setStyle("-fx-background-color:transparent; -fx-background: transparent;");
    smoothScroll(scrollConfig);

    //Juego

    VBox tableroDerecho = new VBox();
    VBox tableroIzquierdo = new VBox();




    CrearTablero();
    menuJuego.setCenter(Tablero);
    BorderPane.setAlignment(Tablero, Pos.CENTER);

    currentScene.setTitle("Tetris");
    currentScene.setScene(pantallaTetris);
    currentScene.setMaximized(true);
    currentScene.show();

    pantallaTetris.setOnKeyPressed(event -> {

      KeyCode currentKey = event.getCode();
      System.out.println(event.getCharacter());
      String curkey = "";
      for (Map.Entry<String, KeyCode> keybind : keybinds.entrySet()){
        if(keybind.getValue() == currentKey){
          curkey = keybind.getKey();
          break;
        }
      }

      switch (curkey){
        case "Left" -> updateGame.movePiece(0,-1);
        case "Right" -> updateGame.movePiece(0,1);
        case "Hard drop" -> updateGame.hardDrop();
        default -> System.out.println("Se ha presionado la tecla" + currentKey);
      }
    });
  }

  private void smoothScroll(ScrollPane scrollPane){
    double scrollSpeed = 0.1; //Duracion del scroll en s

    scrollPane.getContent().setOnScroll(event -> {
      double deltaY = event.getDeltaY() * scrollSpeed;
      double target = scrollPane.getVvalue() - deltaY;

      Timeline timeline = new Timeline();
      KeyValue keyValue = new KeyValue( scrollPane.vvalueProperty(), Math.max(0,Math.min(1,target)), Interpolator.EASE_OUT);
      KeyFrame kf = new KeyFrame(Duration.millis(200), keyValue);
      timeline.getKeyFrames().add(kf);
      timeline.play();
      event.consume();
    });
  }

  private void updateTableroTetris () {
    new AnimationTimer() {
      private long lastUpdate = 0;
      private final long velocidadDeCaida = 500_000; //500ms

      @Override
      public void handle(long now){
        if (now - lastUpdate >= velocidadDeCaida){
          updateTablero();
          lastUpdate = now;
        }
      }

    }.start();
  }

}
