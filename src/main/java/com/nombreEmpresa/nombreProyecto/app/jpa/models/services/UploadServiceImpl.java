package com.nombreEmpresa.nombreProyecto.app.jpa.models.services;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

@Service("uploadServiceImpl")
public class UploadServiceImpl implements IUploadFileService {
	private static final Log LOGGER = LogFactory.getLog(UploadServiceImpl.class);
	private static final String UPLOADS_FOLDER = "uploads";

	@Override
	public Resource cargarImagen(String nombrefile) throws MalformedURLException {
		Path pathFoto = getPath(nombrefile);
		Resource resource = null;

		resource = new UrlResource(pathFoto.toUri());
		if (!resource.isReadable() || !resource.exists()) {
			/*
			 * Si en la carpeta UPLOADS_FOLDER no hay ninguna foto almacenada
			 * colocamos una imagen predeterminada
			 * */
			Path pathFotoDefecto=getPathPorDefecto();
			resource = new UrlResource(pathFotoDefecto.toUri());
			throw new RuntimeException("Error: No se puede cargar la imagen " + pathFoto.toString());
		}

		LOGGER.info("METHOD...UploadServiceImpl.cargarImagen(). " + "PARAM OUT: " + nombrefile.getClass().getName());
		return resource;
	}

	@Override
	public String copiar(MultipartFile file) throws IOException {
		String unicoFileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
		Path rootPath = getPath(unicoFileName);
		Files.copy(file.getInputStream(), rootPath);

		LOGGER.info("METHOD...UploadServiceImpl.copiar(). " + "PARAM OUT: " + file.getClass().getName());
		return unicoFileName;
	}

	@Override
	public boolean borrar(String nombrefile) {
		Path pathFoto = getPath(nombrefile);
		LOGGER.info("METHOD...UploadServiceImpl.borrar(). " + "nombrefile: [" + nombrefile+"]");
		LOGGER.info("METHOD...UploadServiceImpl.borrar(). " + "pathFoto: " + pathFoto);
		File archivo=pathFoto.toFile();
		if(archivo.canRead()&& archivo.exists()) {
			if(archivo.delete()) {
				return true;
			}
			
		}
		LOGGER.info("METHOD...UploadServiceImpl.borrar(). " + "PARAM OUT: " + nombrefile.getClass().getName());
		return false;
	}
	
	public Path getPathPorDefecto() {
		

		LOGGER.info("METHOD...UploadServiceImpl.getPathPorDefecto(). " + "PARAM OUT:Imagen por defecto: imagen_no_disponible_282x300.png");
		return Paths.get("src/main/resources/static/imgs").resolve("imagen_no_disponible_282x300.png").toAbsolutePath();
	}
	public Path getPath(String nombrefile) {
		

		LOGGER.info("METHOD...UploadServiceImpl.getPath(). " + "PARAM OUT: " + nombrefile.getClass().getName());
		return Paths.get(UPLOADS_FOLDER).resolve(nombrefile).toAbsolutePath();
	}

	@Override
	public void deleteAll() {
		FileSystemUtils.deleteRecursively(Paths.get(UPLOADS_FOLDER).toFile());
		LOGGER.info("METHOD...UploadServiceImpl.deleteAll(). " + "PARAM OUT: NO");
	}

	@Override
	public void init() throws IOException {
		Files.createDirectory(Paths.get(UPLOADS_FOLDER));
		LOGGER.info("METHOD...UploadServiceImpl.init(). " + "PARAM OUT: NO");
		
	}

}
