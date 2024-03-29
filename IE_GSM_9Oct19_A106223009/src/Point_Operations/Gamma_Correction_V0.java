package Point_Operations;
/*******************************************************************************
 * This software is provided as a supplement to the authors' textbooks on digital
 *  image processing published by Springer-Verlag in various languages and editions.
 * Permission to use and distribute this software is granted under the BSD 2-Clause 
 * "Simplified" License (see http://opensource.org/licenses/BSD-2-Clause). 
 * Copyright (c) 2006-2016 Wilhelm Burger, Mark J. Burge. All rights reserved. 
 * Visit http://imagingbook.com for additional details.
 *******************************************************************************/


import java.io.File;

import ij.IJ;
import ij.ImagePlus;
import ij.io.Opener;
import ij.plugin.filter.PlugInFilter;
import ij.process.ImageProcessor;

public class Gamma_Correction_V0 implements PlugInFilter {

	public int setup(String arg, ImagePlus img) {
		return DOES_ALL;//DOES_8G
	}
    
	public void run(ImageProcessor ip) {
		// works for 8-bit images only 
	    int K = 256;
	    int aMax = K - 1;
	    double GAMMA = 2.8;//0.3;//   
	
	    // create and fill the lookup table:
	    int[] Fgc = new int[K];                
	
	    for (int a = 0; a < K; a++) {
	        double aa = (double) a / aMax;	// scale to [0,1]
	        double bb = Math.pow(aa,GAMMA);	// gamma function
	        // scale back to [0,255]
	        int b = (int) Math.round(bb * aMax); 
	        Fgc[a] = b;  
	    }
	    
	    ip.applyTable(Fgc);  // modify the image
	}	
	
	public static void main(String[] args) {
		new ij.ImageJ();
		
		File file = new File("../../../ij152-win-java8/sample-images/samples/leaf.jpg");//leaf.jpg //boats.gif
		
		final ImagePlus imp = new Opener().openImage(file.getAbsolutePath());
		final ImagePlus imp4Original = new Opener().openImage(file.getAbsolutePath());
		
		imp4Original.show();
		imp.show();
		IJ.runPlugIn("Point_Operations.Gamma_Correction_V0", "");
	}
}
