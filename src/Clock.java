import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

// Definición de clase Clock que usa arquitectura Singleton
// Enum se asegura de que solo se pueda crear una instancia en todo el código
public enum Clock {
    INSTANCE;

    private boolean playing = true;


    public void stopPlaying() {
        playing = false;
    }

    // Lista con todos los métodos suscritos al evento Tick();
    private final List<Runnable> tickListeners = new ArrayList<>();

    public void suscribe(Runnable listener) { // Método para suscribir y almacenar métodos al evento
        tickListeners.add(listener);
    }

    private void tick() {// Evento Tick
        tickListeners.forEach(Runnable::run);
    }

    private void Update() { // Función donde se almacenan todas las cosas que deben estar sucendiendo cada frame
        while (playing) {

        }

    }


    void Main() {

        Update();

    }

}












}
