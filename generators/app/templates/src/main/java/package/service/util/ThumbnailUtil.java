//<--! package -->

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import net.coobird.thumbnailator.Thumbnails;

public class ThumbnailUtil {


	public static byte[] scale(byte[] buf, int max, String formatName) throws IOException {
		ByteArrayInputStream bais = null;
		java.io.ByteArrayOutputStream baos = null;
		try {
			bais = new ByteArrayInputStream(buf);
			BufferedImage sourceImage = ImageIO.read(bais);
			int width = sourceImage.getWidth();
			int height = sourceImage.getHeight();
			int w, h;
			if (width > height) {
				w = max;
				h = (int) ((height * max) / width);
			} else {
				h = max;
				w = (int) ((width * max) / height);
			}

			BufferedImage thumbnail = Thumbnails.of(sourceImage)
			        .size(w, h)
			        .asBufferedImage();

			baos = new ByteArrayOutputStream();
			ImageIO.write(thumbnail, formatName, baos);
			byte[] res = baos.toByteArray();
			return res;
		} finally {
			if (bais != null) {
				bais.close();
			}
			if (baos != null) {
				baos.close();
			}
		}
	}

}
