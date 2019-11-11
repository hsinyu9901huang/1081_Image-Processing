
/*******************************************************************************
 * This software is provided as a supplement to the authors' textbooks on digital
 *  image processing published by Springer-Verlag in various languages and editions.
 * Permission to use and distribute this software is granted under the BSD 2-Clause 
 * "Simplified" License (see http://opensource.org/licenses/BSD-2-Clause). 
 * Copyright (c) 2006-2016 Wilhelm Burger, Mark J. Burge. All rights reserved. 
 * Visit http://imagingbook.com for additional details.
 *******************************************************************************/

import java.io.File;

import IElib.ImageEnhancement;
import ij.IJ;
import ij.ImagePlus;
import ij.gui.GenericDialog;
import ij.io.Opener;
import ij.plugin.filter.PlugInFilter;
import ij.process.ImageProcessor;

public class Gamma_Correction_V1 implements PlugInFilter {

	private final int CANCEL = 1;
	private final int ERROR = 2;
	private final int OK = 3;
	double GAMMA = 2.8;
	int r = 0;

	public int setup(String arg, ImagePlus img) {
		return DOES_ALL;// DOES_8G
	}

	public void run(ImageProcessor ip) {
		ImageEnhancement ie = new ImageEnhancement();
		/**
		 * show dialog, then get parameters needed
		 */
		do {
			r = GUI();
			if (r == CANCEL)
				return;
		} while (r == ERROR);

		ie.runGammaCorrection(ip, GAMMA);
		/*
		 * // works for 8-bit images only int K = 256; int aMax = K - 1; double GAMMA =
		 * 2.8;//0.3;//
		 * 
		 * // create and fill the lookup table: int[] Fgc = new int[K];
		 * 
		 * for (int a = 0; a < K; a++) { double aa = (double) a / aMax; // scale to
		 * [0,1] double bb = Math.pow(aa,GAMMA); // gamma function // scale back to
		 * [0,255] int b = (int) Math.round(bb * aMax); Fgc[a] = b; }
		 * 
		 * ip.applyTable(Fgc); // modify the image
		 */
	}

	/**
	 * GUI used for users to enter data needed
	 * 
	 * @return
	 */
	private int GUI() {
		GenericDialog gd = new GenericDialog("GAMMA_correction (setup parameters)", IJ.getInstance()); // Title for GUI
		gd.addNumericField("GAMMA", GAMMA, 1); // default value
		gd.showDialog(); // show GUI window
		if (gd.wasCanceled())
			return CANCEL; // If cancel, then exit and back to GUI

		GAMMA = gd.getNextNumber();
		// validate input data
		if (gd.invalidNumber()) {
			IJ.error("GAMMA value (needs to be numeric data)");
			GAMMA = 2.2;
			return ERROR;
		}

		// Display = gd.getNextBoolean();
		return OK; // if OK, then execute program
	}

	public static void main(String[] args) {
		new ij.ImageJ();

		File file = new File("../../../ij152-win-java8/sample-images/samples/leaf.jpg");// leaf.jpg //boats.gif

		final ImagePlus imp = new Opener().openImage(file.getAbsolutePath());
		// final ImagePlus imp4Original = new
		// Opener().openImage(file.getAbsolutePath());

		// imp4Original.show();
		imp.show();
		IJ.runPlugIn("Gamma_Correction_V1", "");
	}
}
