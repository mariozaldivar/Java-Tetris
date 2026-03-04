import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;


// Definición de clase Clock que usa arquitectura Singleton
// Enum se asegura de que solo se pueda crear una instancia en todo el código
public enum Clock {
    INSTANCE;

    private boolean playing = true;
    private boolean isPaused = false;
    private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
    // Esta clase crea un scheduler, que se encarga de revisar el paso del tiempo
    // El nombre es largo y parece confusa, pero es importante para la lógica del clock
    private ScheduledFuture<?> currentTickTask;
    private long speed = 16;

    public void pauseGame() {
        isPaused = true;
    }
    public void unpauseGame() {
        isPaused = false;
    }
    public void stopPlaying() {
        playing = false;
    }
    public void updateClock(long newSpeed) {
        if (currentTickTask != null && !currentTickTask.isCancelled())
        {
            currentTickTask.cancel(false);
        }
        currentTickTask = scheduler.scheduleAtFixedRate(this::tick, 0, newSpeed, TimeUnit.MILLISECONDS );
    }

    // Lista con todos los métodos suscritos al evento Tick();
    private final List<Runnable> tickListeners = new ArrayList<>();

    public void suscribe(Runnable listener) { // Método para suscribir y almacenar métodos al evento
        tickListeners.add(listener);
    }

    private void tick() {// Evento Tick
        if (isPaused) { return; }
        else {

            tickListeners.forEach(Runnable::run);
        }
    }


}












