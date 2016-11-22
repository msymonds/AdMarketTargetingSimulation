import java.util.ArrayList;
import java.util.Iterator;

public class MyAlgorithm {
	static double[] B;
	static double[] f; //objective function for each U(St)
	static double deltaB;
	static User[] sTPopArray;
	static USI mySt;
	static USI si; // selected Label set to add to allocate budget
	static USI[] mySet;
	static int index = 0; // index to corresponding Si

	public static double[] myApproxAlg(USI sT, double bo, USI[] permutations){
		mySet = permutations;
		mySt = sT;
		B = new double[mySet.length];
		for(int i = 0; i < B.length; i++){
			B[i] = 0.0;
		}
		f = new double[mySt.getPopSize()]; 
		sTPopArray = new User[sT.getPopSize()];
		Iterator stPopIter = mySt.getPop().iterator();
		for(int i = 0; i < f.length; i++){
			sTPopArray[i] = (User)stPopIter.next();
			f[i] = 0.0;
		}
		int deltaI = 0;
		int counter = 1;
		do {
			si = getSmallestLabelCost();
			//System.out.println("Si selected: " + si.getTraitsString());
			si.selectionOrder = counter;
			counter++;
			double siCost = (si.getLabelCost()*si.getPopSize());
			deltaI = (int)(siCost * 100);
			siCost = (deltaI / 100.0);
			deltaB = Math.min(bo, siCost);
			B[index] += deltaB;
			bo -= deltaB;
			deltaI = (int)(bo * 100);
			bo = (deltaI / 100.0);
			//System.out.println("DelatB: " + deltaB + "\tremaining bo: " + bo);
			updateFx(si, deltaB);
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
	
	public static USI getSmallestLabelCost(){
		//System.out.println("Searching for si");
		USI result = null;
		index = 0;
		for(int i = index; i < mySet.length; i++){
			if(!(B[i] > 0.0)){
				result = mySet[i];
				index = i;
				break;
			}
		}
		
		for(int i = index+1; i < mySet.length; i++){
			if(!(B[i] > 0.0) && mySet[i].getTotalSetCost() < result.getTotalSetCost()){
				result = mySet[i];
				index = i;
			}
		}
		
		return result;
	}
	
	public static void updateFx(USI sI, double deltaB){
		int counter = 0;
		Iterator iter = sI.sharedWithST.iterator();
		// for each user in U(St)
		while(iter.hasNext()){
			User u = (User)iter.next();
			// update objective function for every user in St
			u.objectFunction += (deltaB /(sI.getLabelCost() * sI.getPopSize())); 
			// fill in f with those f(x) calculated
			for(int i = 0; i < f.length; i++){
				f[i] = sTPopArray[i].getUserObjectiveFunction();
			}
		}
	} // updateFx()
}
