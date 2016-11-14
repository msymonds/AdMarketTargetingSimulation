import java.util.ArrayList;
import java.util.Iterator;

public class OgAlgorithm {
	static USI[] workingPermutations;
	static double[] B; //budget allocation by Si
	static double[] f; //objective function for each U(St)
	static ArrayList<User> targetPop; // Xs to cover
	static double deltaB = 0.0; // budget allocated to a selected Si
	static USI mySt;
	static USI si; // selected Label set to add to allocate budget
	static int index = 0; // index to corresponding Si
	
	public static double[] approxAlg(USI sT, double bo, USI[] permutations){
		workingPermutations = permutations;
		
		
		mySt = sT;
		B = new double[permutations.length]; 
		f = new double[sT.getPopSize()]; 
		for(int i = 0; i < B.length; i++){
			B[i] = 0.0;
		}
		for(int i = 0; i < f.length; i++){
			f[i] = 0.0;
		}
		targetPop = sT.getPop();
		int deltaI = 0;
		do{
			si = getLargestMarginalIncrement();
			System.out.println("Si chosen: P(S): " + si.getLabelCost() + "\tHi: " + si.getHsubI());
			double siCost = (si.getLabelCost()*si.getPopSize()*(1.0 - si.getHsubI()));
			deltaI = (int)(siCost * 100);
			siCost = (deltaI / 100.0);
			deltaB = Math.min(bo, siCost);
			deltaI = (int)(deltaB * 100);
			deltaB = (deltaI / 100.0);
			System.out.print("deltaB: $" + deltaB + " from index: " + index);
			B[index] += deltaB;
			bo -= deltaB;
			deltaI = (int)(bo * 100);
			bo = (deltaI / 100.0);
			System.out.println("\tRemaining bo: $" + bo);
			//updateFx();
			//System.out.println("(bo > 0.1)?: " + (bo > 0.1) + "\t(!allCovered(f))?: " + (!allCovered(f)));
		} while((bo > 0.1) && (!allCovered(f)));
		
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
		double currentRi = 0.0;
		double largestRi = 0.0;
		USI biggestRi = null;
		index = 0;
		
		int counter = 0;
		Iterator iter = targetPop.iterator();
		// for each user in U(St)
		while(iter.hasNext()){
			User u = (User)iter.next();
			// update objective function for every user in St
			if(u.getUserObjectiveFunction() < 1){
				u.calculateObjectiveFunction(workingPermutations, B);
			}
			// fill in f with those f(x) calculated
			f[counter] = u.getUserObjectiveFunction();
			counter++;
		}
		
		
		// for each Si
		for(int i = 0; i < workingPermutations.length; i++){
			currentRi = 0.0;
			// sum up every f(x) for every U(Si) also in U(St) with an f(x) < 1
			// divide sum by |U(Si)|*P(Si) = Ri
			// record Ri to Si
			currentRi = workingPermutations[i].calculateRsubI(mySt);
			if(currentRi >= largestRi && !(B[i] > 0.0) && workingPermutations[i].getLabelCost() > 0.01){
				largestRi = currentRi;
				biggestRi = workingPermutations[i];
				index = i;
			}
		}
		// return the Si with the largest Ri
		return biggestRi;
	}
	
	public static void updateFx(){
		
	}
}
