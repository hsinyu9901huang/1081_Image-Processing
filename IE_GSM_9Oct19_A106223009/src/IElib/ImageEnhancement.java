package IElib;

import ij.process.ImageProcessor;

public class ImageEnhancement {

	public ImageEnhancement() {
		// TODO Auto-generated constructor stub
	}
	
	public void runEqualizeHistogram(ImageProcessor ip) {
		int M = ip.getWidth();
		int N = ip.getHeight();
		int K = 256; // number of intensity values

		// compute the cumulative histogram:
		int[] H = ip.getHistogram();
		for (int j = 1; j < H.length; j++) {
			H[j] = H[j - 1] + H[j];
		}

		// equalize the image:
		for (int v = 0; v < N; v++) {
			for (int u = 0; u < M; u++) {
				int a = ip.get(u, v);
				int b = H[a] * (K - 1) / (M * N);
				ip.set(u, v, b);
			}
		}
	}

	public void runGammaCorrection(ImageProcessor ip,double GAMMA) {
		
		// works for 8-bit images only 
	    int K = 256;
	    int aMax = K - 1;
//	    double GAMMA = 2.8;//0.3;//   
	
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

}
