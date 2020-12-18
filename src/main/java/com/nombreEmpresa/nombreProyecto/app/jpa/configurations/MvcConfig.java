package com.nombreEmpresa.nombreProyecto.app.jpa.configurations;

import java.nio.file.Paths;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
/**
 * NO ACTIVO
 * */
@Configuration("mvcConfig")
public class MvcConfig implements WebMvcConfigurer{
	//private static final Log LOGGER = LogFactory.getLog(MvcConfig.class);
	//Se crea el recurso de la carpeta uploads
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		// TODO Auto-generated method stub
		WebMvcConfigurer.super.addResourceHandlers(registry);
		/*String resourcePath=Paths.get("uploads").toAbsolutePath().toUri().toString();
		registry.addResourceHandler("/uploads/**").addResourceLocations(resourcePath);
		LOGGER.info("METHOD...MvcConfig.addResourceHandlers(). " + "PARAM OUT: " +
		 registry.getClass().getName());*/
	}
	

}
