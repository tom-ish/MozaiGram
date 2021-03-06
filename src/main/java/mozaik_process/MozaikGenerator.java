package mozaik_process;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.List;

import utils.Persist;

public class MozaikGenerator {
	
	public static int GRAIN = 35;
	
	public static int generate(List<BufferedImage> savedImages, Image image, String originalFileName) {
		if(image == null)
			return Persist.ERROR_INPUT_STREAM_NULL;
		
		
		// The actual MozaikGenerator Algorithm is right here
		ImageFrame frame = new ImageFrame(savedImages, image, originalFileName);
		return frame.generateMozaik(GRAIN);
//		return frame.compute(GRAIN);
	}


}
