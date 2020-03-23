//<--! package -->

//<--! import -->
import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import net.sourceforge.tess4j.util.LoadLibs;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.sax.BodyContentHandler;
import org.xml.sax.SAXException;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.charset.StandardCharsets;

public class Indexation {
    //<--! JAVAPACKAGE -->

    public static String parseMetadata(String filename) {
        AutoDetectParser parser = new AutoDetectParser();
        BodyContentHandler handler = new BodyContentHandler();
        Metadata metadata = new Metadata();
        String result = new String();
        try {
            InputStream stream = new BufferedInputStream(new FileInputStream(filename));
            parser.parse(stream, handler, metadata);
            result = metadata.toString();
            System.out.println(result);
        } catch (IOException | SAXException | TikaException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String parseTextFromImage(String filename) throws TesseractException {
        String result = "";
        File imageFile = new File(filename);
        System.out.println("Image name is :" + imageFile.toString());
        ITesseract instance = new Tesseract();
        instance.setDatapath(JAVAPACKAGE + "indexation/tessdata"); // sets tessData path
        result = instance.doOCR(imageFile);
        return result;
    }

    public static String imageAI(String pathImg) throws Exception {

        String script = JAVAPACKAGE + "indexation/imageAI/imageAI.py ";
        //<--! script
        String commandToExecute = "python3 " + script + pathImg;
        String res = "";
        int error = -1;

        try {
            System.out.println(commandToExecute);

            Process pr = Runtime.getRuntime().exec(commandToExecute);
            error = pr.waitFor();
            InputStream stdout = pr.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(stdout, StandardCharsets.UTF_8));
            String line;

            try {
                while ((line = reader.readLine()) != null) {
                    if(!line.contains("tracking")) {
                        res = res + line + " ";
                    }
                }
            } catch (IOException e) {
                System.out.println("Exception in reading output" + e.toString());
            }
            System.out.println(error);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(error != 0) {
            throw new Exception("Error during script execution.");
        }

        return res;
    }

    public static String createImagefromByteArray(byte[] data) throws IOException {
        ByteArrayInputStream bis = new ByteArrayInputStream(data);
        BufferedImage bImage2 = ImageIO.read(bis);
        File f = new File(JAVAPACKAGE + "indexation/tampon.png");
        if(!ImageIO.write(bImage2, "PNG",f)){
            throw new RuntimeException("Unexpected error writing image");
        }
        System.out.println("image created at png format.");
        return f.getPath();
    }
}
