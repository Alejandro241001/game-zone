package org.iesalixar.daw2.Alejandroangulomendez.game_zone.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


/**
 * Clase de configuración para habilitar la gestión de recursos estáticos en Spring MVC.
 * Permite servir archivos desde directorios externos (UPLOAD_PATH) y desde la carpeta
 * 'img' de la raíz del proyecto.
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    // Logger para registrar eventos importantes
    private static final Logger logger = LoggerFactory.getLogger(WebConfig.class);


    /**
     * Configura los manejadores de recursos estáticos.
     * * @param registry el registro de manejadores de recursos
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        // --- 1. CONFIGURACIÓN EXISTENTE PARA UPLOAD_PATH (/uploads/**) ---

        // Obtener la variable UPLOAD_PATH desde las propiedades del sistema
        String uploadPath = System.getProperty("UPLOAD_PATH");

        // Verificar si la variable UPLOAD_PATH está configurada
        if (uploadPath != null && !uploadPath.isEmpty()) {
            logger.info("UPLOAD_PATH configurado correctamente: {}", uploadPath);

            // Configurar Spring para servir archivos desde la ruta obtenida
            registry.addResourceHandler("/uploads/**")
                    .addResourceLocations("file:" + uploadPath + "/");
        } else {
            logger.error("La variable de entorno UPLOAD_PATH no está configurada o está vacía.");
        }


        // --- 2. NUEVA CONFIGURACIÓN PARA LA CARPETA 'img' DEL PROYECTO (/img/**) ---
        // Esto permite que el frontend acceda a las imágenes de portada usando URLs como /img/Zelda.jpg
        registry.addResourceHandler("/img/**")
                .addResourceLocations("file:./img/");

        logger.info("ResourceHandler para /img/** configurado apuntando a la carpeta local 'img/'.");
    }

}
