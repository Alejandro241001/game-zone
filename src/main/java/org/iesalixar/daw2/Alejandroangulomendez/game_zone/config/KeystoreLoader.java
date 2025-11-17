package org.iesalixar.daw2.Alejandroangulomendez.game_zone.config;

import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;

@Configuration
public class KeystoreLoader {

    @PostConstruct
    public void initKeystore() throws IOException {

        String base64 = System.getenv("JWT_KEYSTORE_BASE64");
        String keystorePathEnv = System.getenv("JWT_KEYSTORE_PATH");

        if (base64 == null || keystorePathEnv == null) {
            System.out.println("⚠️ No se encontró JWT_KEYSTORE_BASE64 o JWT_KEYSTORE_PATH");
            return;
        }

        byte[] decoded = Base64.getDecoder().decode(base64);
        Path keystorePath = Paths.get(keystorePathEnv);

        // Crear carpeta si no existe
        if (keystorePath.getParent() != null && !Files.exists(keystorePath.getParent())) {
            Files.createDirectories(keystorePath.getParent());
        }

        Files.write(keystorePath, decoded);

        System.out.println("✅ Keystore generado en: " + keystorePath);
    }
}
