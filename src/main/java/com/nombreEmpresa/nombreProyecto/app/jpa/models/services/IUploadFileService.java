package com.nombreEmpresa.nombreProyecto.app.jpa.models.services;

import java.io.IOException;
import java.net.MalformedURLException;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface IUploadFileService {
	public Resource cargarImagen(String nombrefile) throws MalformedURLException;
	public String copiar(MultipartFile file) throws IOException;
	public boolean borrar(String nombrefile);
	public void deleteAll();
	public void init() throws IOException;
	
}
