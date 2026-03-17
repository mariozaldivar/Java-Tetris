package Tetris;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.paint.Color;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.layout.BorderPane;

public class GUI extends Application { // GUI quiere decir Graphical User Interface

    public static void main(String[] args){
        launch(args);
    } //Main temporal para correr la GUI
    @Override
    public void start(Stage stage) throws Exception {
        //PantallaInicio
        BorderPane PantallaInicio = new BorderPane();
        Scene scene = new Scene(PantallaInicio,Color.WHITE); //Scene es lo que va a aparecer en la pantalla es Scene(nodo,x,y,Colordefondo) xy se mide en pixeles
        Image Logo = new Image("LogoTetris.png"); //Es el logo que aparece en la ventana del juego

        VBox BotonesIncio = new VBox(20);//Vbox (espacio entre hijos en px)

        Button IniciarJuego = new Button();
        Button Configuracion = new Button();
        Button Salir = new Button();

        IniciarJuego.setPrefSize(400,75); //(Largo, alto)
        Configuracion.setPrefSize(400,75);

        IniciarJuego.setOnMouseEntered(event -> { //Suscede algoc cuando el mouse oasa por enciam del boton
            IniciarJuego.setStyle("-fx-background-color: #22bfa1;");
        } );
        IniciarJuego.setOnMouseExited(event -> { //sucede algo cuando el
            IniciarJuego.setStyle("-fx-background-color: #ffffff;");
        });
        IniciarJuego.setOnMouseClicked(event -> {
            System.out.println("Iniciar clicked");
            IniciarJuego.setStyle("-fx-background-color: #51b624;");

        });
        IniciarJuego.setOnMouseReleased(event ->{
            System.out.println("Iniciar Released");
            IniciarJuego.setStyle("-fx-background-color: #ffffff;");
        });


        Configuracion.setOnMouseEntered(event -> {//Sucede algo cuando el mouse pasa por encima del boton
            Configuracion.setStyle("-fx-background-color: #22bfa1;");
        });
        Configuracion.setOnMouseExited(event -> {//Sucede algo cuando el mouse deja de pasar por encima del boton
            Configuracion.setStyle("-fx-background-color: #ffffff;");
        });
        Configuracion.setOnMouseClicked(event -> {//Sucede algo cuando presionas el mouse
            System.out.println("Configuracion clicked");
            Configuracion.setStyle("-fx-background-color: #3ac129;");
        });
        Configuracion.setOnMouseReleased(event -> {//Sucede algo cuando dejas de presionar el mouse
            System.out.println("Configuracion released");
            Configuracion.setStyle("-fx-background-color: #ffffff;");
        });

        BotonesIncio.getChildren().addAll(IniciarJuego,Configuracion);
        PantallaInicio.setCenter(BotonesIncio);
        BotonesIncio.setAlignment(Pos.CENTER);
        BorderPane.setAlignment(BotonesIncio,Pos.CENTER);







        //Pantalla de inicio



        //Escena del Juego




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
}
