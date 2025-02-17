package edu.wgu.d387_sample_code.perfassess;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.Locale;
import java.util.Properties;

@Service
public class WelcomeService {
    public String getLocalizedMessage(String lang, String region) {
        Properties properties = new Properties();
        String filename = String.format("welcome_%s_%s.properties", lang, region);


        try {
            InputStream stream = new ClassPathResource(filename).getInputStream();
            properties.load(stream);
            return properties.getProperty("welcome", "Get default message");
        } catch (Exception e) {return "Error loading properties file: " + "welcome_en_US.properties";}


    }
}
