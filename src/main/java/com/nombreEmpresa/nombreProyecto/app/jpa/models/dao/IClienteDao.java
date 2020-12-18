package com.nombreEmpresa.nombreProyecto.app.jpa.models.dao;


import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.nombreEmpresa.nombreProyecto.app.jpa.models.entitys.ClienteEntity;
/**
 * Repositorio Crud
 * */
@Repository("IClienteDao")
public interface IClienteDao extends PagingAndSortingRepository<ClienteEntity, Long>{
	
/**
 * Se podrian tener metodos con CONSULTAS personalizadas
 * Para metodos con otras funcionalidades se implementa en IClienteService
 * */
}
