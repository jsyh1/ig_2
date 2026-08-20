package co.edu.poli.servicios;

import co.edu.poli.sw2.modelo.Agricultura;
import co.edu.poli.sw2.modelo.Dron;

public class crearDronAgricultura implements factoriaDrones{

	@Override
	public Dron crearDrone() {

		return new Agricultura();
	}

}
