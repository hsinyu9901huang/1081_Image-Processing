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

import IElib.ImageEnhancement;
import ij.IJ;
import ij.ImagePlus;
import ij.io.Opener;
import ij.plugin.filter.PlugInFilter;
import ij.process.ImageProcessor;

public class Equalize_Histogram implements PlugInFilter {

	public int setup(String arg, ImagePlus img) {
		return DOES_ALL;
	}
    
	public void run(ImageProcessor ip) {
		ImageEnhancement ie = new ImageEnhancement();
		ie.runEqualizeHistogram(ip);
//		int M = ip.getWidth();
//		int N = ip.getHeight();
//		int K = 256; // number of intensity values
//
//		// compute the cumulative histogram:
//		int[] H = ip.getHistogram();
//		for (int j = 1; j < H.length; j++) {
//			H[j] = H[j - 1] + H[j];
//		}
//
//		// equalize the image:
//		for (int v = 0; v < N; v++) {
//			for (int u = 0; u < M; u++) {
//				int a = ip.get(u, v);
//				int b = H[a] * (K - 1) / (M * N);
//				ip.set(u, v, b);
//			}
//		}
	}
	
	public static void main(String[] args) {
		new ij.ImageJ();

		File file = new File("../../../ij152-win-java8/sample-images/samples/boats.gif");// leaf.jpg //boats.gif

		final ImagePlus imp = new Opener().openImage(file.getAbsolutePath());
		final ImagePlus imp4Original = new Opener().openImage(file.getAbsolutePath());

		imp4Original.show();
		imp.show();
		IJ.runPlugIn("Point_Operations.Equalize_Histogram", "");
	}
}
