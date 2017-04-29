/**
 * @author (Jessie Stieger)
 *
 */
public class ComparePaintings {

	public ComparePaintings(){}; // constructor.
	private int collisions = 0;
	
	// Load the image, construct the hash table, count the colors.
	ColorHash countColors(String filename, int bitsPerPixel) {
		ImageLoader iLoad = new ImageLoader(filename);
		ColorHash ch = new ColorHash(3,bitsPerPixel,"Linear Probing",.5);
		for(int i = 0; i < iLoad.getWidth();i++){
			for(int j = 0; j < iLoad.getHeight(); j++){
				collisions = ch.increment(iLoad.getColorKey(i, j, bitsPerPixel)).nCollisions;
			}
		}
		return ch;
	}
	
	ColorHash countColors(String filename, int bitsPerPixel,String probing) {
		collisions = 0;
		ImageLoader iLoad = new ImageLoader(filename);
		ColorHash ch = new ColorHash(3,bitsPerPixel,probing,.5);
		for(int i = 0; i < iLoad.getWidth();i++){
			for(int j = 0; j < iLoad.getHeight(); j++){
				collisions += ch.increment(iLoad.getColorKey(i, j, bitsPerPixel)).nCollisions;
			}
		}
		return ch;
	}
	
//	Starting with two hash tables of color counts, compute a measure of similarity based on the cosine distance of two vectors.
	double compare(ColorHash painting1, ColorHash painting2) {
		FeatureVector v1 = new FeatureVector(painting1.bitsPerPixel);
		FeatureVector v2 = new FeatureVector(painting2.bitsPerPixel);
		v1.getTheCounts(painting1);
		v2.getTheCounts(painting2);
		return v1.cosineSimilarity(v2);
	}

	//	A basic test for the compare method: S(x,x) should be 1.0, so you should 
	//  compute the similarity of an image with itself 
	//  and print out the answer. If it comes out to be 1.0, that is a good sign 
	//  for your implementation so far.
	void basicTest(String filename) {
		ColorHash ch = countColors(filename,3);
		System.out.println(compare(ch,ch));
	}

	//		Using the three given painting images and a variety of 
	//  bits-per-pixel values, compute and print out a table of collision counts
	//  in the following format:
	
	void fullSimilarityTests(){
		ColorHash mona = null;
		ColorHash cri = null;
		ColorHash star = null;
		System.out.format("%20s%20s%20s%20s", "Bits Per Pixel","S(Mona,Starry)","S(Mona,Christina)","S(Starry,Christina)");
		System.out.println();
		for(int i = 0; i < 8; i++){
			mona = countColors("MonaLisa.jpg",24 - 3 * i);
			cri = countColors("ChristinasWorld.jpg",24 - 3 * i);
			star = countColors("StarryNight.jpg",24 - 3 * i);
			System.out.format("%20d",24 - 3 * i);
			System.out.format("%20f",compare(mona,star));
			System.out.format("%20f",compare(mona,cri));
			System.out.format("%20f",compare(star,cri));
			System.out.println();
		}
	}
	
	void collisionTests() {
		ColorHash mona = null;
		ColorHash cri = null;
		ColorHash star = null;
		System.out.format("%16s%16s%19s%18s%21s%21s%24s","Bits Per Pixel","C(Mona,linear)","C(Mona,quadratic)","C(Starry,linear)","C(Starry,quadratic)","C(Christina,linear)", "C(Christina,quadratic)");
		System.out.println();
		for(int i = 0; i < 8; i++){
			System.out.format("%16d",24 - 3 * i);
			mona = countColors("MonaLisa.jpg",24 - 3 * i,"Linear Probing");
			System.out.format("%16d", collisions);
			mona = countColors("MonaLisa.jpg",24 - 3 * i,"Quadratic Probing");
			System.out.format("%19d", collisions);
			star = countColors("StarryNight.jpg",24 - 3 * i,"Linear Probing");
			System.out.format("%18d", collisions);
			star = countColors("StarryNight.jpg",24 - 3 * i,"Quadratic Probing");
			System.out.format("%21d", collisions);
			cri = countColors("ChristinasWorld.jpg",24 - 3 * i,"Linear Probing");
			System.out.format("%21d", collisions);
			cri = countColors("ChristinasWorld.jpg",24 - 3 * i,"Quadratic Probing");
			System.out.format("%24d", collisions);
			System.out.println();
		}
	}
		
// This simply checks that the images can be loaded, so you don't have 
// an issue with missing files or bad paths.
	void imageLoadingTest() {
		ImageLoader mona = new ImageLoader("MonaLisa.jpg");
		ImageLoader starry = new ImageLoader("StarryNight.jpg");
		ImageLoader christina = new ImageLoader("ChristinasWorld.jpg");
		System.out.println("It looks like we have successfully loaded all three test images.");

	}
	
	/**
	 * This is a basic testing function, and can be changed.
	 */
	public static void main(String[] args) {
		ComparePaintings cp = new ComparePaintings();
		cp.imageLoadingTest();
//		cp.basicTest("ChristinasWorld.jpg");
//		cp.basicTest("MonaLisa.jpg");
//		cp.basicTest("StarryNight.jpg");
		cp.fullSimilarityTests();
		cp.collisionTests();
		ColorHash temp = cp.countColors("MonaLisa.jpg",6);
		FeatureVector v = new FeatureVector(6);
		v.getTheCounts(temp);
		System.out.println(v.colorCounts[0]);
	}

}
