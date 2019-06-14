package fr.iccorp.shana.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Writer;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.util.DefaultPropertiesPersister;


/**
 * Properties specific to Shana.
 * <p>
 * Properties are configured in the application.yml file. See
 * {@link io.github.jhipster.config.JHipsterProperties} for a good example.
 */

@Configuration
@ConfigurationProperties(prefix = "settings", ignoreUnknownFields = false)
public class SettingsConfiguration {

	private Properties prop;
	private File file;
	
	public SettingsConfiguration() {
		ClassLoader classLoader = getClass().getClassLoader();
		file = new File(classLoader.getResource("config/settings.properties").getFile());
	}

	public String getPhotoCouverture() {
		try (InputStream input = new FileInputStream(file)) {
			prop = new Properties();
			prop.load(input);
			return prop.getProperty("couverture");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}

	public void setPhotoCouverture(String photoCouverture) {
		try (Writer output = new FileWriter(file)){
			prop = new Properties();
			prop.setProperty("couverture", photoCouverture);
			prop.store(output, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
