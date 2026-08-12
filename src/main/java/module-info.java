module co.edu.poli.sw2 {
    requires javafx.controls;
    requires javafx.fxml;
	requires java.sql;

    opens co.edu.poli.sw2 to javafx.fxml;
    exports co.edu.poli.sw2;
}
