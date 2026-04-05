DOCUMENTACIÓN:

<h1><b style="font-size: 20px;">Clock.java</b></h1>

Contiene clase Clock con arquitectura Singleton (solo existe una instancia en todo el programa)

Atributos: 
- +Clock INSTANCE (Única instancia de la clase, accesible desde cualquier script con Clock.INSTANCE)
- +bool isPlaying (Representa si el juego está corriendo)
- +bool isPaused (Representa si el juego está pausado)
- -long speed (Es la velocidad con la que se llama al evento Tick, que controla cuán rápido bajan las piezas. Se va a modificar dinámicamente)
- -ScheduledExecutorService scheduler (Objeto que se encarga de crear un proceso que se ejecute cada cierto tiempo, encargado de llamar al evento Tick cada vez que pase x cantidad de tiempo)
- -ScheduledFuture<?> currentTickTask (Objeto que representa el proceso que se creó en el cual se ejecuta el evento Tick, de manera que se pueda acceder a este fácilmente para cancelarlo o modificarlo)
- -List<Runnable> tickListeners (Lista de funciones [todas las funciones en Java tienen un atributo Runnable], en el que se almacenan todas las funciones que se ejecutan cada vez que se llama a la función Tick)

Métodos: 
- +suscribe(Runnable listener) (Añade una función a la lista de eventos que se ejecutan con Tick())
- +unsuscribe(Runnable listener) (Elimina una función de la lista que se ejecuta con Tick())
- +pauseGame, unpauseGame, stopPlaying (Setters y getters para los atributos)
- -tick() (Evento que se encarga de pasar por cada atributo de la lista tickListeners, y los ejecuta uno a uno)
- +updateClockSpeed(long newSpeed) (Función que se encarga de cambiar la velocidad con la que el scheduler ejecuta el tick. Se tiene que hacer una función, pues para que funcione correctamente se tiene que eliminar el proceso anterior antes de empezar uno nuevo con la velocidad deseada)
- +startGame() (Inicia el juego dando el primer updateClockSpeed [crea el primer proceso], y deja las funcinoes playing y isPaused como true y false respectivamente)

Helper Functions (funciones de ayuda general)
- copyIntMatrix(int[][] matrix) (Función que regresa una matriz copia de la que introduzcas. Es necesaria para copiar matrices, porque si haces matriz_copia = matriz_original, al modificar la matriz copia también se modificará la original.)


<h1><b>Board.java</b></h1>
Atributos:
- +int[][] board (Matriz 10x20 que representa el estado actual del tablero. Todas las piezas son representadas con números del 1 al 7, y el 0 representa falta ausencia de bloques)
- +Piece currentPiece (La pieza actual que está manejando el jugador. Es un objeto de la clase pieza)

MétodosThe GridPane automatically triggers a layout pass (requestLayout()) when children are added, removed, or resized, rendering changes efficiently without manual :
- Board() (Constructor, automáticamente genera la primera currentPiece, y suscribe la función lowerPiece al Tick)
- +void lowerPiece() (Función que se encarga de que las piezas desciendan. Se ejecuta cada Tick. Revisa con la función CheckMoveDown si la pieza puede bajar, y si si, copia las lineas a la línea inferior de una en una, cambiando la línea original por ceros)
- +boolean checkMoveDown() (Revisa si la pieza puede seguir bajando. Utiliza el atributo offset de la currentPiece para ignorar las líneas de 0s que la forma de la pieza pueda tener.)
- +boolean checkExactRotation() (Función aún en desarrollo)
- +void printBoard() (Función que se encarga de imprimir la matriz board)
- +void main() (Se encarga de correr la lógica del juego. Por ahora utiliza un while(true) para dejar el programa corriendo)
