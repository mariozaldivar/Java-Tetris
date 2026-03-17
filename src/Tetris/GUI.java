package Tetris;









import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
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
        BorderPane root = new BorderPane();
        Scene scene = new Scene(root,1000,1000,Color.WHITE); //Scene es lo que va a aparecer en la pantalla es Scene(nodo,x,y,Colordefondo) xy se mide en pixeles
        Image Logo = new Image("LogoTetris.png"); //Es el logo que aparece en la ventana del juego
        Text text = new Text();

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

        root.setCenter(TableroTetris); //Coloca a el tablero en el centro de la pantalla

        stage.setFullScreen(true); //Pone en patalla completa el juego
        stage.setTitle("Tetris");
        stage.getIcons().add(Logo);
        stage.setScene(scene);
        stage.show(); //Muestras la ventana

    }
}
