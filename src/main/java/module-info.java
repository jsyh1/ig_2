module co.edu.poli.sw2 {
    requires javafx.controls;
    requires javafx.fxml;
	requires java.sql;

    opens co.edu.poli.vista to javafx.fxml;

	//Nuevas
	opens co.edu.poli.sw2.controlador to javafx.fxml;
	opens co.edu.poli.sw2.modelo to javafx.base;
	
    exports co.edu.poli.vista;
}
