import java.util.ArrayList;
import java.util.Iterator;

public class Driver {
	
	static final Label[] masterLabelSet = LabelBuilder.getLabelSet();
	static final User[] masterPopulation = UserBuilder.createPopulation(100000, masterLabelSet);
	
	// for St
	static USI targetPop; // target Label set and users associated with set
	static double sTCost = 0.0; // (P(S) * |U(St)|)
	static final double BUDGET_ADJUSTMENT = 0.3; // adjust here for single run
	
	// For OG Alg
	static USI[] workingPermutations; // for OG Alg argument
	static double budget = 0.0; // ((P(S) * |U(St)|) * BUDGET_ADJUSTMENT)
	
	// for My Alg
	static USI[] myWorkingPermutations;
	static double myBudget = 0.0;
	
	public static void main(String[] args){
		
		System.out.println("Master Pop size: " + masterPopulation.length);
		Characteristic[] sT;
		do{
			sT = createRandomTargetLabelSet(4);
			targetPop = new USI(sT);
		} while(targetPop.getPopSize() > 20 || targetPop.getPopSize() < 10);
		
		// print results of target Label selection
		System.out.print("\ntargetPop (St): ");
		printLabelSet(sT);
		System.out.print("P(St): $" + targetPop.getLabelCost());
		System.out.print("\t|U(St)|: " + targetPop.getPopSize());
		sTCost = targetPop.getLabelCost()*targetPop.getPopSize();
		int adj = (int)(sTCost * 100);
		sTCost = adj/100.0;
		System.out.print("\tRequired budget ( |U(St)|*P(St) ): $" +
		(sTCost) + "\n");
		
		System.out.println("Info on targetPop: " + targetPop.getTraitsString());
		Iterator tIter = targetPop.getPop().iterator();
		while (tIter.hasNext()){
			User u = (User)tIter.next();
			System.out.println("\nUser: " + u.toString());
		}
		
		
		//prep and run OG Alg
		ArrayList<USI> permutations = OGAlgPrep.getQualifiedPermutations(targetPop);
		System.out.println("\nNumber of qualified permutations S(1...n): " + permutations.size());		
		workingPermutations = new USI[permutations.size()];
		Iterator permIter = permutations.iterator();
		int counter = 0;
		while(permIter.hasNext()){
			workingPermutations[counter] = (USI)permIter.next();
			counter++;
		}
		runOnce(workingPermutations, 0);
		runSeries(workingPermutations, 0);
		
		
		
		// prep and run My Alg
		ArrayList<USI> myPermutations = MyAlgPrep.getQualifiedPermutations(targetPop);
		System.out.println("\nNumber of my qualified permutations S(1...n): " + myPermutations.size());		
		myWorkingPermutations = new USI[myPermutations.size()];
		Iterator myPermIter = myPermutations.iterator();
		counter = 0;
		while(myPermIter.hasNext()){
			myWorkingPermutations[counter] = (USI)myPermIter.next();
			counter++;
		}
		runOnce(myWorkingPermutations, 1);
		runSeries(myWorkingPermutations, 1);
		
	}
	
	
	
	public static void printPopulation(){
		int countA = 1;
		int countB = 0;
		for(User u : masterPopulation){
			System.out.print("User " + countA + ":\t");
			countA++;
			System.out.print("Attributes: ");
			for(Characteristic c : u.traits){
				if(c != null)
					System.out.print(c.attributeS + ", ");
				else
					System.out.print("NULL, ");
			}
			System.out.print("\n");
		}
	}
	
	public static Characteristic[] createRandomTargetLabelSet(int n){
		Characteristic[] result = new Characteristic[masterLabelSet.length];
		for(int i = 0; i < n; i++){
			int pos = (int)(Math.random()*masterLabelSet.length);
			
			if(result[pos] == null){
				int chosen = (int)(Math.random()*masterLabelSet[pos].domain.size());
				result[pos] = masterLabelSet[pos].domain.get(chosen);
			} else {
				i--;
			}
		}
		return result;
	}
	
	
	public static void printLabelSet(Characteristic[] sI){
		System.out.print("{");
		for(Characteristic c : sI){
			if(c == null)
				System.out.print("NULL");
			else
				System.out.print(c.attributeS);
			System.out.print(", ");
		}
		System.out.println("}");
	}
	
	public static void runOnce(USI[] mySi, int choice){
		Iterator stIter = targetPop.getPop().iterator();
		while(stIter.hasNext()){
			User u = (User)stIter.next();
			u.objectFunction = 0.0;
		}
		for(int j = 0; j < mySi.length; j++){
			mySi[j].hSubI = 0.0;
			mySi[j].rSubI = 0.0;
		}
		
		int adj = 0;
		budget = (sTCost * BUDGET_ADJUSTMENT);
		adj = (int)(budget*100);
		budget = adj/100.0;
		System.out.println("Ad budget (bo) set to " + (BUDGET_ADJUSTMENT * 100) +
				"% of required budget: $" + budget);
		double[] b1;
		if(choice == 0){
			System.out.println("\nCalling original Approximation Algorithm(St, bo, S(1...n)) once");
			b1 = OgAlgorithm.approxAlg(targetPop, budget, mySi);
			
		} else {
			System.out.println("\nCalling My Approximation Algorithm(St, bo, S(1...n)) once");
			b1 = MyAlgorithm.myApproxAlg(targetPop, budget, mySi);
			
		}
		System.out.println("Done!");
		
		reportSingleRun(b1);
		
	}
	
	public static void runSeries(USI[] mySi, int choice){
		
		for(int i = 9; i > 0; i--){
			
			// clear/reset f(x) for all U(St), and Hi, Ri for all Si
			Iterator stIter = targetPop.getPop().iterator();
			while(stIter.hasNext()){
				User u = (User)stIter.next();
				u.objectFunction = 0.0;
			}
			for(int j = 0; j < mySi.length; j++){
				mySi[j].hSubI = 0.0;
				mySi[j].rSubI = 0.0;
			}
			
			double currentBudget = (sTCost * (i/10.0));
			double[] b1;
			if(choice == 0){
				System.out.println("\nCalling original Approximation Algorithm(St, bo, S(1...n)) in series...");
				b1 = OgAlgorithm.approxAlg(targetPop, currentBudget, mySi);
			}
			else{
				System.out.println("\nCalling My Approximation Algorithm(St, bo, S(1...n)) in series...");
				b1 = MyAlgorithm.myApproxAlg(targetPop, currentBudget, mySi);
			}
			reportSeriesRun(b1, (i * 10), currentBudget);
			
		}
	}
	
	public static void reportSingleRun(double[] b1){
		int totalAlgUsers = 0;
		System.out.println("Budget allocation result:");
		double bTotal = 0.0;
		for(int i = 0; i < b1.length; i++){
			if(b1[i] > 0.0){
				System.out.print("\nSi[" + i + "]: Budget allocated: $" + b1[i]);
				bTotal += b1[i];
				System.out.println("\t|U(S)|: " + workingPermutations[i].getPopSize() +
							"\tP(S): $" + workingPermutations[i].getLabelCost() +
							"\t(P(S) * |U(S)|: $)" + workingPermutations[i].getLabelCost() * workingPermutations[i].getPopSize() + 
							"\nSi label set: " + workingPermutations[i].getTraitsString() +
							"\n" + workingPermutations[i].getNumSharedUsers() + " user" +
							(workingPermutations[i].getNumSharedUsers() > 1 ? "s":"") + " in Si also in St:");
					
				ArrayList<User> pop = workingPermutations[i].getPop();
				Iterator popIter = pop.iterator();
				while(popIter.hasNext()){
					User u = (User)popIter.next();
					if(targetPop.hasUser(u)){
						totalAlgUsers++;
						System.out.println(u.toString());
					}
				}
			} // has budget allocated
		}
		System.out.println("\nTotal Budget allocated: $" + bTotal);
		
		int sTUsersCovered = (int)(budget/targetPop.getLabelCost());
		double percentDif = ((double)(totalAlgUsers - sTUsersCovered)/(double)sTUsersCovered);
		int adj = (int)(percentDif * 10000);
		percentDif = adj/100.0;
		System.out.println("Total number of users covered if allocating only to St: " + sTUsersCovered);
		System.out.println("Total number of users covered using algorithm: " + totalAlgUsers);
		System.out.println("Diference: " + (totalAlgUsers - sTUsersCovered) +
				"\tPercentage improvement: " + (percentDif) + "%");
	}
	
	public static void reportSeriesRun(double[] b1, double adjustment, double currentBudget){
		int adj = 0;
		int totalAlgUsers = 0;
		double bTotal = 0.0;
		for(int k = 0; k < b1.length; k++){
			if(b1[k] > 0.0){
				bTotal += b1[k];
				totalAlgUsers += workingPermutations[k].getNumSharedUsers();
			} // has budget allocated
		}
		System.out.println("\nAt " + adjustment + "% of total St cost of $" + 
				sTCost + ": ($" + bTotal + ")");
		
		int sTUsersCovered = (int)(currentBudget/targetPop.getLabelCost());
		double percentDif = ((double)(totalAlgUsers - sTUsersCovered)/(double)sTUsersCovered);
		adj = (int)(percentDif * 10000);
		percentDif = adj/100.0;
		System.out.println("Total number of users covered if allocating only to St: " + sTUsersCovered);
		System.out.println("Total number of users covered using algorithm: " + totalAlgUsers);
		System.out.println("Diference: " + (totalAlgUsers - sTUsersCovered) +
				"\tPercentage improvement: " + (percentDif) + "%\n");
	}
	
}
