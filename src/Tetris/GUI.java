package Tetris;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.layout.BorderPane;

import java.util.HashMap;
import java.util.Map;

public class GUI extends Application { // GUI quiere decir Graphical User Interface

  public static void main(String[] args) {
    launch(args);
  } // Main temporal para correr la GUI

  Image Logo = new Image("LogoTetris.png"); // Es el logo que aparece en la ventana del juego

  // Variables de la Pantalla de inicio
  BorderPane PantallaInicio = new BorderPane();
  Scene scene = new Scene(PantallaInicio, Color.WHITE); // Scene es lo que va a aparecer en la pantalla es
                                                        // Scene(nodo,x,y,Colordefondo) xy se mide en pixeles
  VBox BotonesIncio = new VBox(20);// Vbox (espacio entre hijos en px)
  Button IniciarJuego = new Button();
  Button Configuracion = new Button();
  GridPane Tablero = new GridPane();
  Node[][] Cellmap = new Node[20][10];

  Map<String, KeyCode> Keybinds = new HashMap<>();

  Board Update = new Board();
  UpdateTetris iniciar = new UpdateTetris();

  Text Titulo = new Text("Tetris");

  @Override
  public void start(Stage stage) throws Exception {
    // PantallaInicio
    BorderPane EscenaJuego = new BorderPane();
    BorderPane EscenaConfiguracion = new BorderPane();
    BorderPane Gameover = new BorderPane();

    SetupMenuButtons(IniciarJuego, "Iniciar", scene, EscenaJuego);
    SetupMenuButtons(Configuracion, "Configuracion", scene, EscenaConfiguracion);
    // Configuracion

    // Juego
    SetUpTablero();

    EscenaJuego.setCenter(Tablero);
    BorderPane.setAlignment(Tablero, Pos.CENTER);

    Titulo.setFont(new Font("Impact", 70));

    PantallaInicio.setTop(Titulo);
    BotonesIncio.getChildren().addAll(Titulo, IniciarJuego, Configuracion);
    PantallaInicio.setCenter(BotonesIncio);
    BotonesIncio.setAlignment(Pos.CENTER);
    BorderPane.setAlignment(BotonesIncio, Pos.CENTER);

    // stage.setFullScreen(true); //Pone en patalla completa el juego
    stage.setTitle("Tetris");
    stage.getIcons().add(Logo);
    stage.setScene(scene);
    stage.setMaximized(true);
    stage.show(); // Muestras la ventana
  }

  public class UpdateTetris extends AnimationTimer {
    private long lastUpdate = 0;
    private final long VelocidaddeCaida = 500_000_000; // 500ms

    @Override
    public void handle(long now) {
      if (now - lastUpdate >= VelocidaddeCaida) {
        Update.lowerPiece();
        UpdateTablero();
        lastUpdate = now;
      }
    }

  }

  private void SetUpTablero() {
    Tablero.setAlignment(Pos.CENTER);
    for (int rows = 0; rows < 20; rows++) {
      for (int colums = 0; colums < 10; colums++) {
        Rectangle cell = new Rectangle(30, 30);
        Tablero.add(cell, colums, rows);
        Cellmap[rows][colums] = cell;
      }
    }
  }

  private void UpdateTablero() {
    int[][] boardState = Update.getBoardState();
    for (int rows = 0; rows < 20; rows++) {
      for (int colum = 0; colum < 10; colum++) {
        Rectangle cell = (Rectangle) Cellmap[rows][colum];
        int numeropieza = boardState[rows][colum];
        cell.setFill(setColor(numeropieza));
        cell.setStroke(Color.LIGHTBLUE);
      }
    }
  }

  private static Color setColor(int currentcell) {
    return switch (currentcell) {
      case 1 -> Color.RED;
      case 2 -> Color.PALETURQUOISE;
      case 3 -> Color.BLUE;
      case 4 -> Color.GREEN;
      case 5 -> Color.YELLOW;
      case 6 -> Color.ANTIQUEWHITE;
      case 7 -> Color.SALMON;
      default -> Color.WHITE;
    };
  }

  private void SetupMenuButtons(Button button, String ButtonName, Scene scene, BorderPane changeroot) {
    button.setPrefSize(400, 75);
    button.setOnMouseEntered(event -> button.setStyle("-fx-background-color: #22bfa1;"));
    button.setOnMouseExited(event -> button.setStyle("-fx-background-color: #ffffff;"));

    button.setOnMouseClicked(event -> {
      System.out.println("Se ha presionado" + ButtonName);
      button.setStyle("-fx-background-color: #3ac129;");
      scene.setRoot(changeroot);
      if (ButtonName.equals("Iniciar")) {
        iniciar.start();
      }
    });
    button.setOnMouseReleased(mouseEvent -> {
      System.out.println("Se ha presionado " + ButtonName);
      button.setStyle("-fx-background-color: #ffffff;");
    });
  }

  private void ConfigButtons(Button button, String ButtonName) {
    button.setOnMouseEntered(event -> button.setStyle("-fx-border-color: #cdb923"));
    button.setOnMouseExited(event -> button.setStyle("-fx-border-color: #ffffffff"));
    button.setOnMouseClicked(e -> {
      button.setOnKeyPressed(event -> {

      });
    });
  }

}
