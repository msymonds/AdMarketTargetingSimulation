import java.util.ArrayList;

public class OgAlgorithm {
	static double[] B; //budget allocation
	static double[] f; //objective function for each U(St)
	static ArrayList<User> targetPop; // Xs to cover
	static User[] workingPop; // go between for targetPop/objective function
	static double deltaB = 0.0; // budget allocated to a selected Si
	static USI si; // selected Label set to add to allocate budget
	static int index = 0; // index to corresponding Si
	
	public static double[] approxAlg(USI sT, double bo, ArrayList<USI> permutations){
		B = new double[permutations.size()]; 
		f = new double[sT.getPopSize()]; 
		for(int i = 0; i < B.length; i++){
			B[i] = 0.0;
		}
		for(int i = 0; i < f.length; i++){
			f[i] = 0.0;
		}
		targetPop = sT.getPop();
		workingPop = new User[targetPop.size()];
		for(int i = 0; i < workingPop.length; i++){
			workingPop[i] = targetPop.get(i);
		}
		
		do{
			si = getLargestMarginalIncrement();
			deltaB = Math.min(bo, (si.getLabelCost()*si.getPopSize()*calculateHi()));
			B[index] = deltaB;
			bo -= deltaB;
			updateFx();
			
		} while(bo > 0 || allCovered(f));
		
		return B;
	}
	
	public static boolean allCovered(double[] f){
		for(int i = 0; i < f.length; i++){
			if(f[i] < 1.0)
				return false;
		}
		return true;
	}
	
	public static USI getLargestMarginalIncrement(){
		USI result = null;
		
		return result;
	}
	
	public static double calculateHi(){
		double result = 0.0;
				
		return result;
	}
	
	public static void updateFx(){
		
	}
}
