package pdftohtml.pdftohtml;
import com.aspose.pdf.HtmlSaveOptions;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.aspose.pdf.*;
import com.aspose.pdf.SaveFormat;
import pdftohtml.pdftohtml.config.LicenceConfig;

import java.io.File;

//import com.aspose.pdf.Encoding;
@SpringBootApplication
public class PdftohtmlApplication {
	LicenceConfig licenceConfig = new LicenceConfig();


	public static void main(String[] args) {
		SpringApplication.run(PdftohtmlApplication.class, args);
		PdftohtmlApplication newObj = new PdftohtmlApplication();
		newObj.saveHtml();
	}

	Document pdfDocument = new Document("src/test/resource/image.pdf");

	// Create HtmlSaveOptions object
	HtmlSaveOptions htmlSaveOptions = new HtmlSaveOptions();

	// Split multi HTML pages
//        htmlSaveOptions.setSplitIntoPages(true);

	// Set Font saving mode
//	HtmlSaveOptions.FontSavingModes =HtmlSaveOptions.FontSavingModes.AlwaysSaveAsTTF;

	// Managing SVG folders content
//	htmlSaveOptions.SpecialFolderForSvgImages = "\\SvgSavePath";

	// Managing Images folder inside PDF
//	htmlSaveOptions.SpecialFolderForAllImages = "\\ImageSavePath";

// Save the output in HTML format
//HtmlSaveOptions saveOptions = new HtmlSaveOptions();
   //  pdfDocument.save+('Generated_out_Java.html',SaveFormat.)

	public void saveHtml(){
		this.pdfDocument.save("generated_out_java.html", SaveFormat.Html);
	}

}
