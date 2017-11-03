package mozaik_process;

import java.awt.Image;
import utils.Persist;

public class MozaikGenerator {
	
	public static int GRAIN = 35;
	
	public static int generate(Image image, String originalFileName) {
		if(image == null)
			return Persist.ERROR_INPUT_STREAM_NULL;
		
		
		// The actual MozaikGenerator Algorithm is right here
		ImageFrame frame = new ImageFrame(image, originalFileName);
		return frame.compute(GRAIN);
	}


}
