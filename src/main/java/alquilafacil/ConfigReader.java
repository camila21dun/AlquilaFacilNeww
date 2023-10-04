package alquilafacil;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {

    public Properties cargarConfiguracion(String archivo) {
        Properties properties = new Properties();
        try (FileInputStream fis = new FileInputStream(archivo)) {
            properties.load(fis);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties;
    }

    public static void main(String[] args) {
        ConfigReader configReader = new ConfigReader();

        // Cargar y leer propiedades desde config.properties
        Properties configProps = configReader.cargarConfiguracion("src/main/resources/config/config.properties");

        String nombreProyecto = configProps.getProperty("nombreproyecto");
        String autor = configProps.getProperty("autor");
        String version = configProps.getProperty("version");

        System.out.println("Nombre del proyecto: " + nombreProyecto);
        System.out.println("Autor: " + autor);
        System.out.println("Versión: " + version);
    }
}
