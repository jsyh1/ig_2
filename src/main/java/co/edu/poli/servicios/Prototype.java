package co.edu.poli.servicios;


import co.edu.poli.sw2.modelo.Agricultura;

public class Prototype {
	public Agricultura clonar(Agricultura original) {

        if (original == null) {
            throw new IllegalArgumentException("El dron original no puede ser null");
        }
        return new Agricultura(
        		original.getId(),
        		original.getSerial(),
        	    original.getModelo(),
        	    original.getPeso(),
        	    original.getCapacidadTanque());
	}
	
}
