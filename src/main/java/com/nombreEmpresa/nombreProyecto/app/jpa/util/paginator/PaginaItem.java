package com.nombreEmpresa.nombreProyecto.app.jpa.util.paginator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class PaginaItem {
	private static final Log LOGGER = LogFactory.getLog(PaginaItem.class);
	private int numero;
	private boolean actual;
	public PaginaItem(int numero, boolean actual) {
		
		this.numero = numero;
		this.actual = actual;
		LOGGER.info("METHOD...PaginaItem.PaginaItem(). " +
                "PARAM OUT: Constructor de clase");
	}
	public int getNumero() {
		LOGGER.info("METHOD...PaginaItem.getNumero(). " +
                "PARAM OUT: "+this.numero);
		return this.numero;
	}
	public boolean isActual() {
		LOGGER.info("METHOD...PaginaItem.isActual(). " +
                "PARAM OUT: "+this.actual);
		return this.actual;
	}
	
	
}
