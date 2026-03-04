public class Board {

    void probarTick() {
        System.out.println("La clase Board está reconociendo el Tick");
    }
    public void main(String[] args) {
    Clock.INSTANCE.suscribe(this::probarTick);
    Clock.INSTANCE.updateClock(1000);
    while (true) {
            assert true;
        }
    }

}
