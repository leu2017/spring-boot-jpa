package com.nombreEmpresa.nombreProyecto.app.jpa.util.paginator;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.data.domain.Page;

import com.nombreEmpresa.nombreProyecto.app.jpa.controllers.ClienteController;

public class PageRender<T> {
	private static final Log LOGGER = LogFactory.getLog(PageRender.class);
	private String url;
	private Page<T> page;
	private int totalPaginas;
	private int numeroElemntosPorPagina;
	private int paginaActual;
	private List<PaginaItem> paginas;
	
	public PageRender(String url, Page<T> page) {
		this.url = url;
		this.page = page;
		//Inicilizamos la lista
		this.paginas=new ArrayList<PaginaItem>();
		
		this.numeroElemntosPorPagina=page.getSize();
		this.totalPaginas=page.getTotalPages();
		//La primera pagina es 0
		this.paginaActual=page.getNumber()+1;
		
		
		int desde,hasta;
		if(totalPaginas<=numeroElemntosPorPagina) {
			desde=1;
			hasta=totalPaginas;
		}else {
			if(paginaActual<=numeroElemntosPorPagina/2) {
				desde=1;
				hasta=numeroElemntosPorPagina;
			}else if(paginaActual>=totalPaginas-numeroElemntosPorPagina/2) {
				desde=totalPaginas-numeroElemntosPorPagina+1;
				hasta=numeroElemntosPorPagina;
			}else {
				desde=paginaActual-numeroElemntosPorPagina/2;
				hasta=numeroElemntosPorPagina;
			}
		}//fin del if
		
		/**Rellenamos la lista*/
		for(int i=0; i<hasta;i++) {
			//paginaActual==desde+1-----> es un boolean
			this.paginas.add(new PaginaItem(desde+i, this.paginaActual==desde+i));
		}//fin del for
		
		LOGGER.info("METHOD...PageRender.PageRender(). " +
                "PARAM OUT: Contructor de la clase");
	}

	public String getUrl() {
		LOGGER.info("METHOD...PageRender.getUrl(). " +
                "PARAM OUT: "+this.url);
		return this.url;
	}

	public int getTotalPaginas() {
		LOGGER.info("METHOD...PageRender.getTotalPaginas(). " +
                "PARAM OUT: "+this.totalPaginas);
		return this.totalPaginas;
	}

	public int getPaginaActual() {
		LOGGER.info("METHOD...PageRender.getPaginaActual(). " +
                "PARAM OUT: "+this.paginaActual);
		return this.paginaActual;
	}

	public List<PaginaItem> getPaginas() {
		LOGGER.info("METHOD...PageRender.getPaginas(). " +
                "PARAM OUT: "+this.paginas.size());
		return this.paginas;
	}
	
	public boolean isFirst() {
		LOGGER.info("METHOD...PageRender.isFirst(). " +
                "PARAM OUT: "+page.isFirst());
		return page.isFirst();
	}
	public boolean isLast() {
		LOGGER.info("METHOD...PageRender.isLast(). " +
                "PARAM OUT: "+page.isLast());
		return page.isLast();
	}
	public boolean isEmpty() {
		LOGGER.info("METHOD...PageRender.isEmpty(). " +
                "PARAM OUT: "+page.isEmpty());
		return page.isEmpty();
	}
	public boolean hasNext() {
		LOGGER.info("METHOD...PageRender.hasNext(). " +
                "PARAM OUT: "+page.hasNext());
		return page.hasNext();
	}
	public boolean hasPrevious() {
		LOGGER.info("METHOD...PageRender.hasPrevious(). " +
                "PARAM OUT: "+page.hasPrevious());
		return page.hasPrevious();
	}
}
