package pdftohtml.pdftohtml.utility;

import com.amazonaws.util.IOUtils;

import java.io.*;
import java.util.zip.GZIPInputStream;

public class UnzipUtility {
    public void unzip(String zipFilePath) throws IOException {
        FileInputStream fis = new FileInputStream(zipFilePath);
        Reader reader = new InputStreamReader(new GZIPInputStream(fis));   // this returns in gzip format
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        while (true) {
            int ch = reader.read();
            System.out.print((char)ch);
            if (ch==-1) {
                break;
            }
            out.write(ch);
        }
        InputStream is = new ByteArrayInputStream(out.toByteArray());
        File destPath = new File(zipFilePath);
        OutputStream outputStream = new FileOutputStream(destPath);   // replace zip file with unzipped one
        IOUtils.copy( is, outputStream);
        outputStream.close();
    }
}
