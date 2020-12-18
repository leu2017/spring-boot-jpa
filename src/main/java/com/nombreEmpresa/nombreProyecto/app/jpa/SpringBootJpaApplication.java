package com.nombreEmpresa.nombreProyecto.app.jpa;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.nombreEmpresa.nombreProyecto.app.jpa.controllers.ClienteController;
import com.nombreEmpresa.nombreProyecto.app.jpa.models.services.IUploadFileService;

@SpringBootApplication
public class SpringBootJpaApplication implements CommandLineRunner{
	private static final Log LOGGER = LogFactory.getLog(SpringBootJpaApplication.class);
	@Autowired
	@Qualifier("uploadServiceImpl")
	private IUploadFileService fileService;
	
	public static void main(String[] args) {
		SpringApplication.run(SpringBootJpaApplication.class, args);
		LOGGER.info("METHOD...SpringBootJpaApplication.main() " +
                "PARAM OUT : "+args.getClass().getName());
	}

	@Override
	public void run(String... args) throws Exception {
		fileService.deleteAll();
		fileService.init();
		
		LOGGER.info("METHOD...SpringBootJpaApplication.run() " +
                "PARAM OUT : "+args.getClass().getName());
		
	}

}
