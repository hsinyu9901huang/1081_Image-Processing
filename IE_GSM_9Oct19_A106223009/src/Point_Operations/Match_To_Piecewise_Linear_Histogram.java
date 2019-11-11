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

import javax.swing.JOptionPane;

import filestream.FileStream;
import filestream.OpenFileFilter;
import ij.IJ;
import ij.ImagePlus;
import ij.io.Opener;
import ij.plugin.filter.PlugInFilter;
import ij.process.ImageProcessor;
import imagingbook.pub.histogram.HistogramMatcher;
import imagingbook.pub.histogram.HistogramPlot;
import imagingbook.pub.histogram.PiecewiseLinearCdf;
import imagingbook.pub.histogram.Util;

public class Match_To_Piecewise_Linear_Histogram implements PlugInFilter { 
	String fileDir = "../../ij152-win-java8/samples_images/";
	static boolean debug4TryCatch=true;
	
	public int setup(String arg0, ImagePlus im) {
		return DOES_ALL;//DOES_8G;
	}
	
	public void run(ImageProcessor ipA) {
		// get histogram of original image
		int[] hA = ipA.getHistogram();
		
		(new HistogramPlot(hA, "Histogram A")).show();
		(new HistogramPlot(Util.Cdf(hA), "Cumulative Histogram A")).show();
		
		// -------------------------
		//int[] ik = {28, 75, 150, 210};
		int[] ik = {10, 120, 150, 210};
		double[] Pk = {.05, .25, .75, .95};
		PiecewiseLinearCdf pLCdf = new PiecewiseLinearCdf(256, ik, Pk);
		// -------------------------
		
		double[] nhB = pLCdf.getPdf();
		nhB = Util.normalizeHistogram(nhB);
		(new HistogramPlot(nhB, "Piecewise Linear")).show();
		(new HistogramPlot(pLCdf, "Piecewise Linear Cumulative")).show();
		
		HistogramMatcher m = new HistogramMatcher();
		int[] F = m.matchHistograms(hA, pLCdf);
		
//		for (int i = 0; i < F.length; i++) {
//			IJ.log(i + " -> " + F[i]);
//		}
		
		ipA.applyTable(F);
		int[] hAm = ipA.getHistogram();
		(new HistogramPlot(hAm, "Histogram A (mod)")).show();
		(new HistogramPlot(Util.Cdf(hAm), "Cumulative Histogram A (mod)")).show();
	}

	
	public static void main(String[] args) {
		new ij.ImageJ();// with or without this line, GUI of ImageJ will be shown or not shown...(demo
						// to students)
		// define filter file type in FileDialog
		OpenFileFilter openFileFilter[] = { new OpenFileFilter("jpeg", "Photo in JPEG format(jpeg)"),
				new OpenFileFilter("jpg", "Photo in JPEG format(jpg)"), new OpenFileFilter("png", "PNG image"),
				new OpenFileFilter("tif", "TIFF image"), new OpenFileFilter("bmp", "BMP image") };

		// define the file to open
//	 	File file = new File( "../../samples_images/MountainFog8Bit.tif" );//Cat.png;darkwoman.tif;MountainFog.jpg

		String filePath = "../../samples_images/";
		String titleOnDialogBox = "Open an Image";
		
		/**
		 * below 2 lines can be swapped ...
		 */
	FileStream fileStream = new FileStream();//OK; use this one for students
//			FileChoosing4WriteOrRead_v4 fileStream= new FileChoosing4WriteOrRead_v4();//OK; for me maybe use this one...
		
		
		boolean refresh = false;
		String message = "Continue for process?";
		do {
			
			try {
				File file = fileStream.chooseFile(filePath, titleOnDialogBox, openFileFilter);

				// open a file with ImageJ
				final ImagePlus imp = new Opener().openImage(file.getAbsolutePath());
				final ImagePlus imp4Original = new Opener().openImage(file.getAbsolutePath());

				// display it via ImageJ
				imp4Original.show();// this is just for showing original image
				imp.show();// after running, original image will be shown, and then processed via below
							// plugin.
				              //fileName
				IJ.runPlugIn("Point_Operations.Match_To_Piecewise_Linear_Histogram", "");
				
				refresh=true;

			} catch (Exception e) {
				// TODO Auto-generated catch block
				if (debug4TryCatch) e.printStackTrace();//you can comment out this line if needed...
				JOptionPane.showMessageDialog(null,
						"Maybe: \nYou just canceled your input.\n OR \nYou had no input at all.", "Exception Happened: "+e.getMessage(),
						JOptionPane.INFORMATION_MESSAGE);
				int resultInt = JOptionPane.showConfirmDialog(null, message, "Continue Process",
						JOptionPane.OK_CANCEL_OPTION);
				if (resultInt == 0)
					refresh = true;
				else
					refresh = false;
			}
		} while (refresh);
	}// end main
	
	
}

