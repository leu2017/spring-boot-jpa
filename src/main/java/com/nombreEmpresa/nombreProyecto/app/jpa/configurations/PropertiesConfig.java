package com.nombreEmpresa.nombreProyecto.app.jpa.configurations;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

@Configuration
@PropertySources({
        @PropertySource("classpath:vistas.properties"),
        @PropertySource("classpath:mensajes.properties"),
        @PropertySource("classpath:errores.properties")
})
public class PropertiesConfig {
    static final Log LOGGER = LogFactory.getLog(PropertiesConfig.class);
    
    public PropertiesConfig() {
        LOGGER.info("METHOD...PropertiesConfig ");
    }
}
