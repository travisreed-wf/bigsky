package bigsky.gui;

/*
 * Code Modified from http://stackoverflow.com/questions/12475503/how-to-change-the-size-of-an-image
 */
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;

import bigsky.Global;

public class ImgUtils {

	public BufferedImage scaleImage(int WIDTH, int HEIGHT, String filename) {
	    BufferedImage bi = null;
	    try {
	    	ImageIcon ii;
	    	if (filename.equals(Global.blankContactImage)){
	    		ii = new ImageIcon(ImgUtils.class.getResource("android-logo2.jpg"));//path to image
	    	}
	    	else {
	    		ii = new ImageIcon(filename);//path to image
	    	}
	        bi = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
	        Graphics2D g2d = (Graphics2D) bi.createGraphics();
	        g2d.addRenderingHints(new RenderingHints(RenderingHints.KEY_RENDERING,RenderingHints.VALUE_RENDER_QUALITY));
	        g2d.drawImage(ii.getImage(), 0, 0, WIDTH, HEIGHT, null);
	    } catch (Exception e) {
	        e.printStackTrace();
	        return null;
	    }
	    return bi;
	}
}
