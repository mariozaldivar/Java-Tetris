package Tetris;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/*
* Conceptos importantes para entender esta clase:
* Evento: Lista donde se almacenan (suscriben) funciones de otras clases, de manera que al invocar el evento, se
* ejecuten todas las funciones suscritas.
* Singleton: Arquitectura para una clase donde se busca que solo exista UNA INSTANCIA de la misma clase en todo el
* programa, haciendo que esa misma instancia sea universalmente accesible para todas las clases por medio de NombreDeLaClase.INSTANCE
* */



// Definición de clase Clock que usa arquitectura Singleton
// Enum es un tipo de clase especial que se asegura de que solo se pueda crear una instancia en todo el código
public enum Clock {
    INSTANCE;

    private boolean playing = true;
    private boolean isPaused = false;
    private long speed = 500; // Es la velocidad con la que se actualiza el tick
    private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
    // Esta clase crea un scheduler, que se encarga de ejecutar automáticamente una función pasado un tiempo
    // El nombre es largo y parece confusa, pero es importante para la lógica del clock
    private ScheduledFuture<?> currentTickTask;
    // Este atributo es para almacenar el "proceso" del scheduler
    // Es importante porque, si después queremos cambiar la velocidad de cada cuanto hay un tick
    // es necesario cancelar el proceso anterior, entonces con esta variable mantenemos el acceso
    private final List<Runnable> tickListeners = new ArrayList<>();
    // Lista con todos los métodos suscritos al evento Tick();

    public void suscribe(Runnable listener) { // Método para suscribir y almacenar métodos al evento
        tickListeners.add(listener);
    }
    public void unsubscribe(Runnable listener) { tickListeners.remove(listener);}

    public void pauseGame() { isPaused = true; }
    public void unpauseGame() { isPaused = false; }
    public void stopPlaying() { playing = false;}

    private void tick() {// Evento Tick
        if (isPaused) { return; }
        else {
            tickListeners.forEach(Runnable::run);
            // ForEach recorre toda la lista de funciones que hay en tickListeners
            // al ser una lista de objetos Runnable (ejecutables), por cada una, ejecuta su atributo run
        }
    }

    public void updateClockSpeed(long newSpeed) { // Sirve para cambiar la velocidad del reloj
        if (currentTickTask != null && !currentTickTask.isCancelled())
        {
            currentTickTask.cancel(false); // Revisa el anterior "proceso" del scheduler, y si existe todavía, lo cancela
        }
        currentTickTask = scheduler.scheduleAtFixedRate(this::tick, 0, newSpeed, TimeUnit.MILLISECONDS );
    }

    public void startGame() {// Esta función utiliza el método updateClockSpeed para crear el primer proceso

        updateClockSpeed(speed);
        playing = true;
        isPaused = false;
    }




    // Funciones que son útiles generalmente

    public void printIntMatrix(int[][] matrix){
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }
    }

    public int[][] copyIntMatrix(int[][] matrix) {
        int[][] buffer = new int[matrix.length][matrix[0].length];

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                buffer[i][j] = matrix[i][j];
            }
        }
        return buffer;
    }

}












