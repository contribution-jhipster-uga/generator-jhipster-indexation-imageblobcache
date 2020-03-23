//<--! package -->

import java.io.ByteArrayInputStream;
import java.io.IOException;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;

public class MetadataUtil {
	public static String extract(byte[] image) throws ImageProcessingException, IOException {

		StringBuffer result = new StringBuffer();
		ByteArrayInputStream bais = new ByteArrayInputStream(image);
		try {
			Metadata metadata = ImageMetadataReader.readMetadata(bais);
			for (Directory directory : metadata.getDirectories()) {

				result.append("DIR:").append(directory.getName()).append('\n');
				if(directory.getName().equals("GPS")) {
					System.out.println("The image contains a GPS tag");
				}
				for (Tag tag : directory.getTags()) {
					result.append("TAG:").append(tag).append('\n');
				}
				for (String error : directory.getErrors()) {
					result.append("ERROR:").append(error).append('\n');
				}
			}
			return result.toString();
		} finally {
			bais.close();
		}
	}
}
