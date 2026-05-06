module Tetris {
    requires javafx.graphics;
    requires javafx.controls;
    requires java.xml;

    opens Tetris to javafx.graphics;
}