/**
 * @author Jessie Stieger
 *
 */
public class FeatureVector {

	/**
	 * FeatureVector is a class for storing the results of
	 * counting the occurrences of colors.
	 * <p>
	 * Unlike the hash table, where the information can be
	 * almost anyplace with the array(s) (due to hashing), in the FeatureVector,
	 * the colors are in their numerical order and each count
	 * is in the array position for its color.
	 * <p>
	 * Besides storing the information, the class provides methods
	 * for getting the information (getTheCounts) and for computing
	 * the similarity between two vectors (cosineSimilarity).
	 */
	long[] colorCounts;
	int bitsPerPixel;
	int keySpaceSize;

	/**
	 * Constructor of a FeatureVector.
	 * 
	 * This creates a FeatureVector instance with an array of the
	 * proper size to hold a count for every possible element in the key space.
	 * 
	 * @param bpp	Bits per pixel. This controls the size of the vector.
	 * 				The keySpace Size is 2^k where k is bpp.
	 * 
	 */
	public FeatureVector(int bpp) {
		bitsPerPixel = bpp;
		keySpaceSize = 1 << bpp;
		colorCounts = new long[keySpaceSize];
	}

	public void getTheCounts(ColorHash ch){
		ColorKey key = null;
		for(int i = 0; i < keySpaceSize; i++){
			try	{key = new ColorKey(i,bitsPerPixel);} catch(Exception e) {}
			colorCounts[i] = ch.getCount(key);
		}
	}
	
	private double dotProduct(FeatureVector other){
		double sum = 0;
		for(int i = 0; i < keySpaceSize; i++){
			sum += this.colorCounts[i] * other.colorCounts[i];
		}
		return sum;
	}
	
	private double magnitude(){
		double sum = 0;
		for(int i = 0; i < keySpaceSize; i++){
			sum += colorCounts[i] * colorCounts[i];
		}
		return Math.sqrt(sum);
	}
	
	public double cosineSimilarity(FeatureVector other) {
		return (this.dotProduct(other)) / (this.magnitude() * other.magnitude());
	}

	/**
	 * Optional main method for your own tests of these methods.
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
