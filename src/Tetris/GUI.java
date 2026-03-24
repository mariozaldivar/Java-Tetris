package Tetris;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.paint.Color;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCombination;
import javafx.scene.shape.Rectangle;
import javafx.scene.layout.BorderPane;
import java.util.concurrent.ThreadPoolExecutor;

public class GUI extends Application { // GUI quiere decir Graphical User Interface

    public static void main(String[] args){
        launch(args);
    } //Main temporal para correr la GUI

    Image Logo = new Image("LogoTetris.png"); //Es el logo que aparece en la ventana del juego

    //Variables de la Pantalla de inicio
    BorderPane PantallaInicio = new BorderPane();
    Scene scene = new Scene(PantallaInicio,Color.WHITE); //Scene es lo que va a aparecer en la pantalla es Scene(nodo,x,y,Colordefondo) xy se mide en pixeles
    VBox BotonesIncio = new VBox(20);//Vbox (espacio entre hijos en px)
    Button IniciarJuego = new Button();
    Button Configuracion = new Button();

    Text Titulo = new Text("Tetris");

    @Override
    public void start(Stage stage) throws Exception {
        //PantallaInicio
        BorderPane EscenaJuego = new BorderPane();
        BorderPane EscenaConfiguracion = new BorderPane();
        BorderPane Gameover = new BorderPane();
        Board Update = new Board();



        SetupMenuButtons(IniciarJuego,"Iniciar",scene,EscenaJuego);
        SetupMenuButtons(Configuracion,"Configuracion",scene,EscenaConfiguracion);


        //Juego

        GridPane Tablero = new GridPane();
        for (int Rows = 0; Rows < 20;Rows++) {
            for (int Colm = 0; Colm < 10; Colm++){
                Rectangle cell = new Rectangle(20,20);
                int updateCell = Update.board[Rows][Colm];
                switch (updateCell) {
                    case 0:
                        cell.setStroke(Color.BLUE);
                    case 1:
                        cell.setStroke(Color.YELLOW);
                    case 2:
                        cell.setStroke(Color.RED);
                    default:
                        cell.setStroke(Color.BLACK);
                }
                Tablero.add(cell,Colm,Rows);
            }
        }

        EscenaJuego.getChildren().add(Tablero);



        Titulo.setFont(new Font("Impact",70));

        PantallaInicio.setTop(Titulo);
        BotonesIncio.getChildren().addAll(Titulo,IniciarJuego,Configuracion);
        PantallaInicio.setCenter(BotonesIncio);
        BotonesIncio.setAlignment(Pos.CENTER);
        BorderPane.setAlignment(BotonesIncio,Pos.CENTER);





/**
 GridPane TableroTetris = new GridPane(); //Gridpane es para hacer el tablero de tetris
 TableroTetris.setAlignment(Pos.CENTER); //Hace que todos los nodo de Gripanes se dibujen centrados
 for (int rows = 0; rows < 20; rows++){ //Dos for para hacer que el tablero tenga 20 filas y 10 columnas
 for (int colums = 0; colums < 10; colums++){
 Rectangle Celda = new Rectangle(20,20); //Rectangle son los cuadrados de las celdas
 Celda.setOpacity(0.4);
 Celda.setStroke(Color.BLACK);
 TableroTetris.add(Celda, colums, rows); //Se anade la celda como un hijo de Tablerotetris en una posicion indicada por las colums y rows
 }
 }

 scene.setOnKeyPressed(event -> { //setOnKeyPressed permite detectar cuando se presiona una tecla
 if (event.getCode() == KeyCode.A){ //getCode checa que tecla fue presionado y si es igual A suce algo
 System.out.println("Se ha presionado la tecla A");
 } else if (event.getCode() == KeyCode.D){
 System.out.println(("Se ha presionado la tecla D"));
 }
 });

 scene.setOnKeyReleased(event -> { //setOnKeyReleased permite detectar cuando se suelta una tecla
 System.out.println("No hay teclas presionadas");
 });

 //PantallaInicio.setCenter(TableroTetris); //Coloca a el tablero en el centro de la pantalla
 **/

        //stage.setFullScreen(true); //Pone en patalla completa el juego
        stage.setTitle("Tetris");
        stage.getIcons().add(Logo);
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show(); //Muestras la ventana
    }


    private void SetupMenuButtons (Button button, String ButtonName, Scene scene, BorderPane changeroot){
        button.setPrefSize(400,75);
        button.setOnMouseEntered(event -> button.setStyle("-fx-background-color: #22bfa1;"));
        button.setOnMouseExited(event -> button.setStyle("-fx-background-color: #ffffff;"));

        button.setOnMouseClicked(event -> {
            System.out.println("Se ha presionado" + ButtonName);
            button.setStyle("-fx-background-color: #3ac129;");
            scene.setRoot(changeroot);

        });
        button.setOnMouseReleased(mouseEvent -> {
            System.out.println("Se ha presionado " + ButtonName);
            button.setStyle("-fx-background-color: #ffffff;");
        });
    }





}

