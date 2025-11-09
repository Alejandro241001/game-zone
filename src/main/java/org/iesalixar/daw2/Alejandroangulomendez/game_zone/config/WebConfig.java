package org.iesalixar.daw2.Alejandroangulomendez.game_zone.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Configuración para servir archivos estáticos (imágenes, recursos) del backend.
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    private static final Logger logger = LoggerFactory.getLogger(WebConfig.class);

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        // --- 1️⃣ UPLOAD_PATH externo (para producción o entorno personalizado) ---
        String uploadPath = System.getProperty("UPLOAD_PATH");
        if (uploadPath != null && !uploadPath.isEmpty()) {
            logger.info("✅ UPLOAD_PATH configurado: {}", uploadPath);
            registry.addResourceHandler("/uploads/**")
                    .addResourceLocations("file:" + uploadPath + "/");
        } else {
            logger.warn("⚠️ UPLOAD_PATH no configurado. Se usará configuración local.");
        }

        // --- 2️⃣ Carpeta estática principal (src/main/resources/static/img) ---
        Path staticImgPath = Paths.get("src/main/resources/static/img");
        String staticImgAbsolutePath = staticImgPath.toFile().getAbsolutePath();

        registry.addResourceHandler("/img/**")
                .addResourceLocations("file:" + staticImgAbsolutePath + "/")
                .setCachePeriod(3600);

        // --- 3️⃣ Carpeta raíz /img (por compatibilidad con imágenes antiguas) ---
        Path rootImgPath = Paths.get("img");
        String rootImgAbsolutePath = rootImgPath.toFile().getAbsolutePath();

        registry.addResourceHandler("/img/**")
                .addResourceLocations("file:" + rootImgAbsolutePath + "/")
                .setCachePeriod(3600);

        logger.info("✅ Serviendo imágenes desde:");
        logger.info("   → {}", staticImgAbsolutePath);
        logger.info("   → {}", rootImgAbsolutePath);
    }
}
