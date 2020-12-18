package com.nombreEmpresa.nombreProyecto.app.jpa.models.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


import com.nombreEmpresa.nombreProyecto.app.jpa.models.entitys.ClienteEntity;


public interface IClienteService {
/**Se nombran como los mismos metodos que contiene el Repositorio Crud. 
 * Se considera falcade.
 * Se podria poner metodos personalizados 
 * */
	public List<ClienteEntity> findAll();
	public Page<ClienteEntity> findAll(Pageable pageable);
	public void save(ClienteEntity cliente);
	public ClienteEntity findOne(Long id);
	public void delete(Long id);
	public String mensajeLog();
}
