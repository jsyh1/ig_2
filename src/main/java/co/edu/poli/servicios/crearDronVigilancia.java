package co.edu.poli.servicios;

import co.edu.poli.sw2.modelo.Dron;
import co.edu.poli.sw2.modelo.Vigilancia;

public class crearDronVigilancia implements factoriaDrones{
	
	@Override
	public Dron crearDrone() {

		return new Vigilancia();
	}

}
