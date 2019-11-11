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
import imagingbook.pub.histogram.HistogramPlot;
import imagingbook.pub.histogram.Util;

public class Show_Histogram implements PlugInFilter { 
	
	String title;
	
	public int setup(String arg0, ImagePlus im) {
		title = im.getTitle();
		return DOES_8G + NO_CHANGES;
	}
	
	public void run(ImageProcessor ip) {
		int[] h = ip.getHistogram();
		(new HistogramPlot(h, "Histogram of " + title)).show();
		(new HistogramPlot(Util.Cdf(h), "Cum. Histogram of " + title)).show();
	}
	
	public static void main(String[] args) {
		new ij.ImageJ();

		File file = new File("../../../ij152-win-java8/sample-images/samples/boats.gif");// leaf.jpg //boats.gif

		final ImagePlus imp = new Opener().openImage(file.getAbsolutePath());
		final ImagePlus imp4Original = new Opener().openImage(file.getAbsolutePath());

		imp4Original.show();
		imp.show();
		IJ.runPlugIn("Point_Operations.Show_Histogram", "");
	}
}

