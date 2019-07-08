module JavaFXTest {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.jsoup;
    requires java.desktop;
    requires javafx.web;

    opens org.dimigo.gui.helloworld;
}