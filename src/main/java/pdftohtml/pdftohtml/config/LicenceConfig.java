package pdftohtml.pdftohtml.config;
import com.aspose.words.License;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.core.io.ClassPathResource;
public class LicenceConfig {
    private static final Logger LOGGER = LogManager.getLogger(LicenceConfig.class);
    static {
        License license = new License();
        ClassPathResource resource = new ClassPathResource("licenses/Aspose.Words.lic");
        try {
            license.setLicense(resource.getInputStream());
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (license != null) {
            LOGGER.info("LicenceConfig is Set!");
        }
    }
}
