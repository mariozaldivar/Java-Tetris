package Tetris;

import javafx.animation.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.HashMap;
import java.util.Map;

import static Tetris.Game.BOARD_HEIGHT;
import static Tetris.Game.BOARD_WIDTH;
/*
* En javafx todos los elementos que se muestran en la pantalla son nodos y panes
* Nodos: Son los elementos de la pantalla como un boton o texto
* Pane: Son contenedores que determinan la posicion de los elementos de la GUI, hay multiples tipos de Panes,
* pero lo unico que las diferencia es la forma en la que los objetos son ordenados.
* Es posible poner un pane dentro de otro pane para poder crear layouts más complejos.
*/

//Anaddir fondo a el juego y menu -
//Hacer el retry y gameover en la gui
//Reset keybinds -
//hacer que las letras salten en los menus  y que el texto de la tecla ya usada escrollee en el boton -
//

public class GUI extends Application {

  Game updateGame = new Game();

  private boolean isGameOver = false;
  private static final int cellWidht = 30;
  private static final int cellHeight = 30;
  GridPane Tablero = new GridPane();
  Node[][] Cellmap = new Node[BOARD_HEIGHT][BOARD_WIDTH];
  private Label scoreLabel = new Label("0");
  private Label levelLabel = new Label("1");

  VBox pauseWindow;
  VBox tableroDerecho;
  Rectangle fondoPausa;
  Button continueButton;
  Button restartButton;
  Button exitButton;
  GridPane HoldPiece;
  Scene pantallaTetris;
  StackPane rootcontainer;
  Font textoBotones;
  Label textoPausa;

  // Configuracion del tablero
  private void CrearTablero() {
    Tablero.getChildren().clear();
    Tablero.setAlignment(Pos.CENTER);
    Tablero.setMaxSize(BOARD_WIDTH, BOARD_HEIGHT);
    for (int rows = 0; rows < BOARD_HEIGHT; rows++) {
      for (int colums = 0; colums < BOARD_WIDTH; colums++) {
        Rectangle Cell = new Rectangle(cellWidht, cellHeight);
        if (rows < 2) {
          Cell.setOpacity(.20);
          Cell.setStroke(Color.BLACK);
          Cell.setStrokeWidth(1);
        }
        Tablero.add(Cell, colums, rows);
        Cellmap[rows][colums] = Cell;
        Cell.setStroke(Color.BLACK);
        Cell.setStrokeWidth(1);
      }
    }
    Tablero.setStyle("-fx-border-color: #808080; -fx-border-width: 5px; -fx-border-style: solid;");
  }

  private void updateTablero() {
    int[][] tableroActual = updateGame.getBoardState();
    for (int rows = 0; rows < BOARD_HEIGHT; rows++) {
      for (int colums = 0; colums < BOARD_WIDTH; colums++) {
        Rectangle currentCell = (Rectangle) Cellmap[rows][colums];
        int numeroCelda = tableroActual[rows][colums];
        currentCell.setFill(setColor(numeroCelda));

      }
    }
  }

  private void resetTablero() {
    Tablero.getChildren().clear();
    CrearTablero();
  }

  private static Color setColor(int numeroCelda) {
    return switch (numeroCelda) {
      case 0 -> Color.TRANSPARENT;
      case 1 -> Color.RED;
      case 2 -> Color.PALETURQUOISE;
      case 3 -> Color.BLUE;
      case 4 -> Color.GREEN;
      case 5 -> Color.YELLOW;
      case 6 -> Color.ORANGE;
      case 7 -> Color.SALMON;
      default -> Color.GREY;
    };
  }

  // Botones
  private void hoverOverButton(Button button) {
    button.setPrefSize(400, 75);
    button.setOnMouseEntered(event -> button.setStyle("-fx-background-color: #22bfa1;"));
    button.setOnMouseExited(event -> button.setStyle("-fx-background-color: #ffffff;"));
  }

  private void setupMenuButton(Button button, StackPane cambiarEscena, String nombre, Font font) {
    button.setFont(font);
    button.setText(nombre);
    hoverOverButton(button);
    button.setOnMouseClicked(event -> {
      button.setStyle("-fx-background-color: #3ac129;");
      this.pantallaTetris.setRoot(cambiarEscena);

      if (nombre.equals("INICIAR")) {
        Clock.INSTANCE.unsubscribe(updateGame.pieceFallListener);
        updateGame.startGame();
        Clock.INSTANCE.startGame();
      } else if (nombre.equals("CONFIG")) {
        System.out.println("Config");

      }
    });
    button.setOnMouseReleased(event -> button.setStyle("-fx-background-color: #ffffff;"));
  }

  private void setupConfigButtons(VBox keybindsUI, Map<String, KeyCode> keybinds, Font font) {
    keybindsUI.getChildren().clear();
    keybindsUI.setPadding(new Insets(50, 0, 50, 0));
    GridPane configLayout = new GridPane();
    configLayout.setHgap(30);
    configLayout.setVgap(15);
    configLayout.setAlignment(Pos.CENTER);

    int row = 0;
    for (String keyName : keybinds.keySet()) { // Añade cada boton de la configuracion
      Label label = new Label(keyName);
      label.setStyle("-fx-text-fill: black;");
      label.setFont(font);

      Button button = new Button(keybinds.get(keyName).toString());
      button.setPrefSize(400, 50);
      button.setFont(font);
      hoverOverButton(button);
      button.setOnMouseClicked(event -> { // El texto dentro del button cambia a ...
        button.requestFocus();
        button.setText("...");

        button.focusedProperty().addListener((ObservableValue, OldValue, NewValue) -> { // Cuando seleccionas un nuevo
                                                                                        // boton o haces click en otro
                                                                                        // lugar de la pantalla el boton
                                                                                        // regresa a su estado original
          if (!NewValue) {
            button.setText(keybinds.get(keyName).toString());
            button.setOnKeyPressed(null);
          }
        });

        button.setOnKeyPressed(e -> { // Coloca la nueva tecla a la accion deseada
          KeyCode currentkey = e.getCode();
          if (currentkey == KeyCode.ESCAPE) {
            button.setText(keybinds.get(keyName).toString());
            button.setOnKeyPressed(null);
          }
          if (keybinds.containsValue(currentkey)) {
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
      configLayout.add(label, 0, row);
      configLayout.add(button, 1, row);
      row++;
    }
    keybindsUI.getChildren().add(configLayout);
    keybindsUI.setAlignment(Pos.CENTER);
  }

  private void showHoldPiece() {
    this.HoldPiece.getChildren().clear();
    if (updateGame.holdPiece == null) {return;}
    int[][] holdPieceMatrix = updateGame.holdPiece.shape;
    int holdPieceSize = updateGame.holdPiece.size;

    int offset = (4 - holdPieceSize) / 2;

    for (int rows = 0; rows < holdPieceSize; rows++) {
      for (int colums = 0; colums < holdPieceSize; colums++) {
        int currentRectangle = holdPieceMatrix[rows][colums];
        if (currentRectangle > 0) {
          Rectangle rect = new Rectangle(cellWidht, cellHeight);
          rect.setFill(setColor(currentRectangle));

          this.HoldPiece.add(rect, colums + offset, rows + offset);
        }

      }
    }
  }



  private void resetKeybinds(VBox keybindsUI, Map<String, KeyCode> keybinds) {

  }

  private void createPauseMenu(Font font) {
    this.pauseWindow = new VBox();

    this.textoPausa = new Label("Pause");
    this.textoPausa.setFont(font);
    this.continueButton = new Button("Continue");
    this.restartButton = new Button("Restart");
    this.exitButton = new Button("Exit");

    hoverOverButton(this.continueButton);
    hoverOverButton(this.restartButton);
    hoverOverButton(this.exitButton);

    this.pauseWindow.getChildren().addAll(this.textoPausa, this.continueButton, this.restartButton, this.exitButton);
    this.pauseWindow.setSpacing(20);
    this.pauseWindow.setAlignment(Pos.CENTER);
    this.pauseWindow.setVisible(false);
    this.pauseWindow.setManaged(false);
  }


  private void toggleVisibility() {
      this.pauseWindow.setVisible(false);
      this.pauseWindow.setManaged(false);
      this.fondoPausa.setVisible(false);
      this.fondoPausa.setManaged(false);
  }

  private void togglePause() {

    if (Clock.INSTANCE.isPaused) {
      this.pauseWindow.setVisible(true); this.pauseWindow.setManaged(true);
      this.fondoPausa.setVisible(true); this.fondoPausa.setManaged(false);

      this.continueButton.setOnMouseClicked(event -> {
        this.continueButton.setStyle("-fx-background-color: #3ac129;");
        Clock.INSTANCE.unpauseGame();
        toggleVisibility();
      });

      this.restartButton.setOnMouseClicked(event -> {
        toggleVisibility();
        Clock.INSTANCE.unpauseGame();
        Clock.INSTANCE.unsubscribe(updateGame.pieceFallListener);
        updateGame.startGame();
        resetTablero();
        showHoldPiece();
        showQueue();
      });

      this.exitButton.setOnMouseClicked(event -> {
        Clock.INSTANCE.gameOver();
        Clock.INSTANCE.unsubscribe(updateGame.pieceFallListener);

        updateGame.startGame();
        resetTablero();
        this.HoldPiece.getChildren().clear();
        showQueue();
        toggleVisibility();
        this.pantallaTetris.setRoot(this.rootcontainer);
        Clock.INSTANCE.unpauseGame();
      });

    } else {
      if (this.pauseWindow.isVisible()) {
        toggleVisibility();
      }

    }
  }


  private void showQueue() {
    this.tableroDerecho.getChildren().clear();
    Label textoQueue = new Label("Next");
    textoQueue.setFont(this.textoBotones);
    textoQueue.setAlignment(Pos.CENTER);
    this.tableroDerecho.getChildren().add(textoQueue);

    for (Piece queuePiece : updateGame.pieceQueue) {
      GridPane verPieza = new GridPane();
      for (int i = 0; i < 4; i++) {
        verPieza.getColumnConstraints().add(new ColumnConstraints(cellWidht));
        verPieza.getRowConstraints().add(new RowConstraints(cellHeight));
      }
      int[][] pieceQueueMatrix =  queuePiece.shape;
      for (int rows = 0; rows < queuePiece.size ; rows++) {
        for (int colums = 0; colums < queuePiece.size ; colums++) {
          int currentRectangle = pieceQueueMatrix[rows][colums];
          Rectangle Cell = new Rectangle(cellWidht, cellHeight);
          Cell.setFill(setColor(currentRectangle));
          verPieza.add(Cell, colums, rows);
        }
      }
      this.tableroDerecho.getChildren().add(verPieza);
    }
  }

  private void GameOver() {
    if (isGameOver) {return;}
    isGameOver = true;
    this.textoPausa.setText("GAME OVER");
    this.fondoPausa.setVisible(true);
    this.pauseWindow.setVisible(true);
    this.pauseWindow.setManaged(true);
    this.continueButton.setManaged(false);
    this.continueButton.setVisible(false);

    this.restartButton.setOnMouseClicked(event -> {
      isGameOver = false;
      Clock.INSTANCE.unsubscribe(updateGame.pieceFallListener);
      updateGame.startGame();
      Clock.INSTANCE.startGame();
      toggleVisibility();
      resetTablero();
      showHoldPiece();
      this.HoldPiece.getChildren().clear();
      showQueue();
      this.continueButton.setVisible(true);
      this.continueButton.setManaged(true);
      this.textoPausa.setText("PAUSE");
    });

    this.exitButton.setOnMouseClicked(event -> {
      isGameOver = false;
      Clock.INSTANCE.gameOver();
      Clock.INSTANCE.playing = true;
      toggleVisibility();
      resetTablero();
      showHoldPiece();
      this.HoldPiece.getChildren().clear();
      showQueue();
      this.textoPausa.setText("PAUSE");
      this.continueButton.setVisible(true);
      this.continueButton.setManaged(true);
      this.pantallaTetris.setRoot(this.rootcontainer);
    });
    System.out.println("GAME OVER");
  }


  public void start(Stage currentScene) throws Exception {
    Font tetrisfontTitulo = Font.loadFont(getClass().getResource("/fonts/PressStart2P-Regular.ttf").toExternalForm(),
        80);
    this.textoBotones = Font.loadFont(getClass().getResource("/fonts/PressStart2P-Regular.ttf").toExternalForm(), 20);
    Font configuracionTexto = Font.loadFont(getClass().getResource("/fonts/PressStart2P-Regular.ttf").toExternalForm(),
        30);

    // Menu principal
    Image Background = new Image(getClass().getResourceAsStream("/images/Mainmenu.gif"));
    ImageView background = new ImageView(Background);

    this.rootcontainer = new StackPane();
    StackPane layoutMenujuego = new StackPane();
    StackPane layoutConfig = new StackPane();
    BorderPane menuPrincipal = new BorderPane();
    BorderPane menuJuego = new BorderPane();
    BorderPane menuConfiguracion = new BorderPane();
    ScrollPane scrollConfig = new ScrollPane();
    menuConfiguracion.setCenter(scrollConfig);
    this.pantallaTetris = new Scene(this.rootcontainer, Color.WHITE);

    background.fitHeightProperty().bind(this.rootcontainer.widthProperty());
    background.fitHeightProperty().bind(this.rootcontainer.heightProperty());
    background.setPreserveRatio(true);
    background.setSmooth(true);

    this.rootcontainer.getChildren().addAll(background, menuPrincipal);

    VBox botonesInicio = new VBox();

    Button iniciarJuego = new Button();
    Button Configuracion = new Button();
    Text tituloMenuPrincipal = new Text("TETRIS");
    tituloMenuPrincipal.setFill(Color.WHITE);
    tituloMenuPrincipal.setFont(tetrisfontTitulo);

    setupMenuButton(iniciarJuego,  layoutMenujuego, "INICIAR", this.textoBotones);
    setupMenuButton(Configuracion,  layoutConfig, "CONFIG", this.textoBotones);

    botonesInicio.getChildren().addAll(tituloMenuPrincipal, iniciarJuego, Configuracion);
    botonesInicio.setSpacing(20);

    menuPrincipal.setCenter(botonesInicio);
    botonesInicio.setAlignment(Pos.CENTER);

    // Configuracion
    VBox ConfigUI = new VBox();
    ConfigUI.setAlignment(Pos.CENTER);
    ConfigUI.setSpacing(20);
    Button back = new Button();

    Text tituloConfiguracion = new Text("Configuración");
    tituloConfiguracion.setFont(tetrisfontTitulo);

    Map<String, KeyCode> keybinds = new HashMap<>();
    keybinds.put("Left", KeyCode.LEFT);
    keybinds.put("Right", KeyCode.RIGHT);
    //keybinds.put("Soft drop", KeyCode.DOWN);
    keybinds.put("Hard drop", KeyCode.SPACE);
    keybinds.put("Rotate", KeyCode.X);
    keybinds.put("Hold", KeyCode.C);
    keybinds.put("Pause", KeyCode.ESCAPE);

    setupConfigButtons(ConfigUI, keybinds, configuracionTexto);
    ConfigUI.getChildren().addFirst(tituloConfiguracion);
    setupMenuButton(back, this.rootcontainer, "BACK", this.textoBotones);
    ConfigUI.getChildren().add(back);

    menuConfiguracion.setBottom(back);
    menuConfiguracion.setCenter(scrollConfig);

    layoutConfig.getChildren().add(menuConfiguracion );

    scrollConfig.setContent(ConfigUI);
    scrollConfig.setFitToWidth(true);
    scrollConfig.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER); // Evita el scroll horizontal
    scrollConfig.setStyle("-fx-background-color:transparent; -fx-background: transparent;");
    smoothScroll(scrollConfig);

    // Juego
    VBox tableroIzquierdo = new VBox();
    this.HoldPiece = new GridPane();

    for (int i = 0; i < 4; i++) {
      this.HoldPiece.getColumnConstraints().add(new ColumnConstraints(cellWidht));
      this.HoldPiece.getRowConstraints().add(new RowConstraints(cellHeight));
    }

    Label holdLabel = new Label("HOLD");


    tableroIzquierdo.setAlignment(Pos.CENTER_RIGHT);
    tableroIzquierdo.setSpacing(10);

    holdLabel.setFont(this.textoBotones);
    holdLabel.setAlignment(Pos.CENTER_RIGHT);
    holdLabel.setMaxWidth(Double.MAX_VALUE);

    this.HoldPiece.setAlignment(Pos.CENTER_RIGHT);
    this.HoldPiece.setPrefSize((cellWidht * 4), (cellHeight * 4));

    tableroIzquierdo.getChildren().addAll(holdLabel, this.HoldPiece);
    tableroIzquierdo.setStyle("-fx-border-color: #808080; -fx-border-width: 5px; -fx-border-style: solid;");
    tableroIzquierdo.setMaxSize(Region.USE_PREF_SIZE  , Region.USE_PREF_SIZE);


    //Tablero derecho
    this.tableroDerecho = new VBox();
    this.tableroDerecho.setAlignment(Pos.CENTER_LEFT);
    this.tableroDerecho.setSpacing(5);
    this.tableroDerecho.setStyle("-fx-border-color: #808080; -fx-border-width: 5px; -fx-border-style: solid;");
    this.tableroDerecho.setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE );


    VBox panelPuntosDerecha = new VBox(10);
    VBox panelPuntosIzquierda = new VBox(10);

    Text txtPuntos = new Text("PUNTUACIÓN:");
    txtPuntos.setFont(textoBotones);
    scoreLabel.setFont(textoBotones);
    scoreLabel.setTextFill(Color.BLACK);

    Text txtLevel = new Text("NIVEL:");
    txtLevel.setFont(textoBotones);
    levelLabel.setFont(textoBotones);

    panelPuntosDerecha.getChildren().addAll(txtPuntos, scoreLabel);
    panelPuntosDerecha.setAlignment(Pos.TOP_RIGHT);
    panelPuntosDerecha.setPadding(new Insets(50, 20, 0, 20));

    panelPuntosIzquierda.getChildren().addAll(txtLevel, levelLabel);
    panelPuntosIzquierda.setAlignment(Pos.TOP_LEFT);
    panelPuntosIzquierda.setPadding(new Insets(50, 20, 0, 20));

    HBox layoutMenuJuego = new HBox();
    layoutMenuJuego.setAlignment(Pos.CENTER);
    layoutMenuJuego.setSpacing(10);

    layoutMenuJuego.getChildren().addAll(panelPuntosIzquierda,tableroIzquierdo, this.Tablero, this.tableroDerecho,panelPuntosDerecha);

    showQueue();
    CrearTablero();

    menuJuego.setCenter(layoutMenuJuego);

    fondoPausa = new Rectangle();
    this.fondoPausa.widthProperty().bind(layoutMenujuego.widthProperty());
    this.fondoPausa.heightProperty().bind(layoutMenujuego.heightProperty());
    this.fondoPausa.setFill(Color.GREY);
    this.fondoPausa.setOpacity(0.9);
    this.fondoPausa.setVisible(false);
    this.fondoPausa.setManaged(false);

    createPauseMenu(tetrisfontTitulo);

    layoutMenujuego.getChildren().addAll(menuJuego, this.fondoPausa,this.pauseWindow);


    currentScene.setTitle("Tetris");
    currentScene.setScene(this.pantallaTetris);
    currentScene.setMaximized(true);
    currentScene.show();




    this.pantallaTetris.setOnKeyPressed(event -> {

      KeyCode currentKey = event.getCode();
      System.out.println(event.getCharacter());
      String curkey = "";
      for (Map.Entry<String, KeyCode> keybind : keybinds.entrySet()) { // Obtienes el nombre de la tecla presionada
        if (keybind.getValue() == currentKey) {
          curkey = keybind.getKey();
          break;
        }
      }

      if (Clock.INSTANCE.playing && !Clock.INSTANCE.isPaused) {
        switch (curkey) {
          case "Left" -> updateGame.movePiece(0, -1);
          case "Right" -> updateGame.movePiece(0, 1);
          case "Soft drop" -> updateGame.movePiece(1, 0);
          case "Hard drop" -> updateGame.hardDrop();
          case "Rotate" -> updateGame.pieceRotate();
          case "Hold" -> {
            updateGame.holdPiece();
            showHoldPiece();
          }
          case "Pause" -> {
            Clock.INSTANCE.pauseGame();
            togglePause();
          }
          default -> System.out.println("Se ha presionado la tecla" + currentKey);
        }
      } else if (Clock.INSTANCE.isPaused)  {
        if (curkey.equals("Pause")) {
          Clock.INSTANCE.unpauseGame();
          togglePause();
        }
      }
    });

    currentScene.setOnCloseRequest(event -> {
      Platform.exit();
      System.exit(0);
    });
    updateTableroTetris();

  }

  private void smoothScroll(ScrollPane scrollPane) { // Hace que el scroll sea más suave
    double scrollSpeed = 0.1; // Duracion del scroll en s

    scrollPane.getContent().setOnScroll(event -> {
      double deltaY = event.getDeltaY() * scrollSpeed;
      double target = scrollPane.getVvalue() - deltaY;

      Timeline timeline = new Timeline();
      KeyValue keyValue = new KeyValue(scrollPane.vvalueProperty(), Math.max(0, Math.min(1, target)),
          Interpolator.EASE_OUT);
      KeyFrame kf = new KeyFrame(Duration.millis(200), keyValue);
      timeline.getKeyFrames().add(kf);
      timeline.play();
      event.consume();
    });
  }

  private void updateTableroTetris() {
    new AnimationTimer() {
      private long lastUpdate = 0;
      @Override
      public void handle(long now) {
        updateTablero();
        if (updateGame.queueChanged) {
          showQueue();
          updateGame.queueChanged = false;
        }
          scoreLabel.setText(String.valueOf(updateGame.getScore()));
          levelLabel.setText(String.valueOf(updateGame.getLevel()));
          lastUpdate = now;
          if (!Clock.INSTANCE.playing && !Clock.INSTANCE.isPaused) {
            GameOver();
          }

      }

    }.start();
  }

  public static void main(String[] args) {
    launch(args);
  }

}
