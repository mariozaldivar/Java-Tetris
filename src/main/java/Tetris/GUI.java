package Tetris;

import javafx.animation.*;
import javafx.application.Application;
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
//Poner el layout de la ountuacion y el hold -
//Hacer el menu de pause
//Hacer el retry y gameover en la gui
//Reset keybinds -
//hacer que las letras salten en los menus  y que el texto de la tecla ya usada escrollee en el boton -
//Hacer que se muestren las siguientes piesas
//

public class GUI extends Application {

  Game updateGame = new Game();

  private static final int cellWidht = 30;
  private static final int cellHeight = 30;
  GridPane Tablero = new GridPane();
  Node[][] Cellmap = new Node[BOARD_HEIGHT][BOARD_WIDTH];

  // Configuracion del tablero
  private void CrearTablero() {
    Tablero.setAlignment(Pos.CENTER);
    Tablero.setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
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

  private static Color setColor(int numeroCelda) {
    return switch (numeroCelda) {
      case 0 -> Color.WHITE;
      case 1 -> Color.RED;
      case 2 -> Color.PALETURQUOISE;
      case 3 -> Color.BLUE;
      case 4 -> Color.GREEN;
      case 5 -> Color.YELLOW;
      case 6 -> Color.ANTIQUEWHITE;
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

  private void setupMenuButton(Button button, Scene tetris, BorderPane cambiarEscena, String nombre, Font font,
      Parent escenaAnterior) {
    button.setFont(font);
    button.setText(nombre);
    hoverOverButton(button);
    button.setOnMouseClicked(event -> {
      button.setStyle("-fx-background-color: #3ac129;");
      if (nombre.equals("BACK")) {
        tetris.setRoot(escenaAnterior);
      } else {
        tetris.setRoot(cambiarEscena);
      }

      if (nombre.equals("INICIAR")) {
        System.out.println("Se ha presionado " + nombre);
        updateTableroTetris();
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

  private void showHoldPiece(GridPane HoldPiece) {
    HoldPiece.getChildren().clear();
    int[][] holdPieceMatrix = updateGame.holdPiece.shape;
    int holdPieceSize = updateGame.holdPiece.size;

    int offset = (4 - holdPieceSize) / 2;

    for (int rows = 0; rows < holdPieceSize; rows++) {
      for (int colums = 0; colums < holdPieceSize; colums++) {
        int currentRectangle = holdPieceMatrix[rows][colums];
        if (currentRectangle > 0) {
          Rectangle rect = new Rectangle(cellWidht, cellHeight);
          rect.setFill(setColor(currentRectangle));

          HoldPiece.add(rect, colums + offset, rows + offset);
        }

      }
    }
  }

  private void resetKeybinds(VBox keybindsUI, Map<String, KeyCode> keybinds) {

  }

  private void pauseMenu() {
  }

  public void start(Stage currentScene) throws Exception {
    Font tetrisfontTitulo = Font.loadFont(getClass().getResource("/fonts/PressStart2P-Regular.ttf").toExternalForm(),
        80);
    Font textoBotones = Font.loadFont(getClass().getResource("/fonts/PressStart2P-Regular.ttf").toExternalForm(), 20);
    Font configuracionTexto = Font.loadFont(getClass().getResource("/fonts/PressStart2P-Regular.ttf").toExternalForm(),
        30);

    // Menu principal
    Image Background = new Image(getClass().getResourceAsStream("/images/Mainmenu.gif"));
    ImageView background = new ImageView(Background);

    StackPane rootcontainer = new StackPane();

    BorderPane menuPrincipal = new BorderPane();
    BorderPane menuJuego = new BorderPane();
    BorderPane menuConfiguracion = new BorderPane();
    ScrollPane scrollConfig = new ScrollPane();
    menuConfiguracion.setCenter(scrollConfig);
    Scene pantallaTetris = new Scene(rootcontainer, Color.WHITE);

    background.fitHeightProperty().bind(rootcontainer.widthProperty());
    background.fitHeightProperty().bind(rootcontainer.heightProperty());
    background.setPreserveRatio(true);
    background.setSmooth(true);

    rootcontainer.getChildren().addAll(background, menuPrincipal);

    VBox botonesInicio = new VBox();

    Button iniciarJuego = new Button();
    Button Configuracion = new Button();
    Text tituloMenuPrincipal = new Text("TETRIS");
    tituloMenuPrincipal.setFill(Color.WHITE);
    tituloMenuPrincipal.setFont(tetrisfontTitulo);

    setupMenuButton(iniciarJuego, pantallaTetris, menuJuego, "INICIAR", textoBotones, rootcontainer);
    setupMenuButton(Configuracion, pantallaTetris, menuConfiguracion, "CONFIG", textoBotones, rootcontainer);

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
    keybinds.put("Soft drop", KeyCode.DOWN);
    keybinds.put("Hard drop", KeyCode.SPACE);
    keybinds.put("Rotate", KeyCode.X);
    keybinds.put("Rotate Counterclockwise", KeyCode.Z);
    keybinds.put("rotate 180", KeyCode.A);
    keybinds.put("Hold", KeyCode.C);

    setupConfigButtons(ConfigUI, keybinds, configuracionTexto);
    ConfigUI.getChildren().addFirst(tituloConfiguracion);
    setupMenuButton(back, pantallaTetris, menuPrincipal, "BACK", textoBotones, rootcontainer);
    ConfigUI.getChildren().add(back);

    menuConfiguracion.setBottom(back);
    menuConfiguracion.setCenter(scrollConfig);

    scrollConfig.setContent(ConfigUI);
    scrollConfig.setFitToWidth(true);
    scrollConfig.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER); // Evita el scroll horizontal
    scrollConfig.setStyle("-fx-background-color:transparent; -fx-background: transparent;");
    smoothScroll(scrollConfig);

    // Juego
    VBox tableroDerecho = new VBox();
    VBox tableroIzquierdo = new VBox();
    GridPane HoldPiece = new GridPane();

    BorderPane posicionHold = new BorderPane();

    for (int i = 0; i < 4; i++) {
      HoldPiece.getColumnConstraints().add(new ColumnConstraints(cellWidht));
      HoldPiece.getRowConstraints().add(new RowConstraints(cellHeight));
    }
    HoldPiece.setAlignment(Pos.CENTER_RIGHT);
    HoldPiece.setPrefSize((cellWidht * 4), (cellHeight * 4));

    tableroIzquierdo.setAlignment(Pos.CENTER);
    tableroIzquierdo.setPadding(new Insets(20));
    tableroIzquierdo.setPrefWidth(0);

    Label holdLabel = new Label("HOLD");
    holdLabel.setFont(textoBotones);
    tableroIzquierdo.getChildren().addAll(holdLabel, HoldPiece);
    posicionHold.setRight(tableroIzquierdo);
    menuJuego.setLeft(posicionHold);

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
      for (Map.Entry<String, KeyCode> keybind : keybinds.entrySet()) { // Obtienes el nombre de la tecla presionada
        if (keybind.getValue() == currentKey) {
          curkey = keybind.getKey();
          break;
        }
      }

      switch (curkey) {
        case "Left" -> updateGame.movePiece(0, -1);
        case "Right" -> updateGame.movePiece(0, 1);
        case "Hard drop" -> updateGame.hardDrop();
        case "Rotate" -> updateGame.pieceRotate();
        case "Hold" -> {
          updateGame.holdPiece();
          showHoldPiece(HoldPiece);
        }
        default -> System.out.println("Se ha presionado la tecla" + currentKey);
      }
    });
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
      private final long velocidadDeCaida = 500_000; // 500ms

      @Override
      public void handle(long now) {
        if (now - lastUpdate >= velocidadDeCaida) {
          updateTablero();
          lastUpdate = now;
        }
      }

    }.start();
  }

  public static void main(String[] args) {
    launch(args);
  }

}
