package com.nombreEmpresa.nombreProyecto.app.jpa.models.services;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nombreEmpresa.nombreProyecto.app.jpa.models.dao.IClienteDao;
import com.nombreEmpresa.nombreProyecto.app.jpa.models.entitys.ClienteEntity;
/**
 * Esta clase es la implementacion del servicio de Cliente.
 * Va a operar sobre la Tabla Cliente, pero va a devolver datos
 * de la clase ClienteModel, gracias al converter, que permite transformar
 * datos de la clase ClienteEntity a la clase ClienteModel.
 * Asi las operaciones no se hacen directamente hacia la tabla de la
 * base de datos teniendo como intermediario a la clase ClienteModel
 * */
@Service("clienteServiceImpl")
public class ClienteServiceImpl implements IClienteService {
	private static final Log LOGGER = LogFactory.getLog(ClienteServiceImpl.class);
	@Autowired
	@Qualifier("IClienteDao")
	IClienteDao clienteDao;
	
	
	@Override
	@Transactional(readOnly = true)
	public List<ClienteEntity> findAll() {
		LOGGER.info("METHOD...ClienteServiceImpl.findAll(). " +
                "PARAM OUT: "+ clienteDao.getClass().getName());
		
		
		return (List<ClienteEntity>) clienteDao.findAll();
	}

	@Override
	@Transactional
	public void save(ClienteEntity cliente) {
				
			clienteDao.save(cliente);
			LOGGER.info("METHOD...ClienteServiceImpl.save(). " +
		                "PARAM OUT: CREAMOS o ACTUALIZAMOS un Cliente");
			
	}

	@Override
	@Transactional(readOnly = true)
	public ClienteEntity findOne(Long id) {
		
		/**Busca un ClienteEntity por Id*/
		
		LOGGER.info("METHOD...ClienteServiceImpl.findOne(). " +
                "PARAM OUT: "+ clienteDao.findById(id).get().getClass().getName());
		return clienteDao.findById(id).get();
	}

	@Override
	@Transactional
	public void delete(Long id) {
		/**
		 * Este metodo no realiza la conversion de ClienteEntity a ClienteModel, borra directamente de la
		 * BD
		 * */
		clienteDao.deleteById(id);
		LOGGER.info("METHOD...ClienteServiceImpl.delete(). " +
                "PARAM OUT: "+ clienteDao.getClass().getName());
	}
	
	@Override
	@Transactional(readOnly = true)
	public Page<ClienteEntity> findAll(Pageable pageable) {
		
		LOGGER.info("METHOD...ClienteServiceImpl.findAll(Pageable pageable). " +
                "PARAM OUT: "+ clienteDao.getClass().getName());
		/**
		 * Este metodo no realiza la conversion de ClienteEntity a ClienteModel, lee directamente de la
		 * BD
		 * */
		
		return clienteDao.findAll(pageable);
	}
	@Override
	public String mensajeLog() {
		// Metodo personalizado que no tiene reflejo en el CRUD Repositorio
		return null;
	}

	
	
}
