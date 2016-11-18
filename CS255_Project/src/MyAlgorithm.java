
public class MyAlgorithm {
	static double[] B;
	static double deltaB;

	public static double[] myApproxAlg(USI sT, double bo, USI[] permutations){
		B = new double[permutations.length];
		for(int i = 0; i < B.length; i++){
			B[i] = 0.0;
		}
		int counter = 0;
		while(counter < permutations.length && bo > 0.0){
			deltaB = Math.min(bo, permutations[counter].getLabelCost()*permutations[counter].getPopSize());
			B[counter] = deltaB;
			bo -= deltaB;
			counter++;
		}
		return B;
	}
}
