/*******************************************************************************
 * This software is provided as a supplement to the authors' textbooks on digital
 *  image processing published by Springer-Verlag in various languages and editions.
 * Permission to use and distribute this software is granted under the BSD 2-Clause 
 * "Simplified" License (see http://opensource.org/licenses/BSD-2-Clause). 
 * Copyright (c) 2006-2016 Wilhelm Burger, Mark J. Burge. All rights reserved. 
 * Visit http://imagingbook.com for additional details.
 *******************************************************************************/
package Point_Operations;

import java.io.File;

import ij.IJ;
import ij.ImagePlus;
import ij.io.Opener;
import ij.plugin.filter.PlugInFilter;
import ij.process.ImageProcessor;

public class Raise_Contrast4All implements PlugInFilter {

	public int setup(String arg, ImagePlus img) {
		return DOES_ALL+DOES_STACKS;//F3查閱
	}
    
	public void run(ImageProcessor ip) {
		int w = ip.getWidth();
		int h = ip.getHeight();

		for (int v = 0; v < h; v++) {
			for (int u = 0; u < w; u++) {
				int a = ip.get(u, v);
				int b = (int) (a * 1.5 + 0.5);
				if (b > 255)
					b = 255; 	// clamp to maximum value
				ip.set(u, v, b);
			}
		}
	}
	
	public static void main(String[] args) {
		new ij.ImageJ();

		File file = new File("../../../ij152-win-java8/sample-images/samples/boats.gif");// leaf.jpg //boats.gif

		final ImagePlus imp = new Opener().openImage(file.getAbsolutePath());
		final ImagePlus imp4Original = new Opener().openImage(file.getAbsolutePath());

		imp4Original.show();
		imp.show();
		IJ.runPlugIn("Point_Operations.Raise_Contrast4All", "");
	}
	
}
