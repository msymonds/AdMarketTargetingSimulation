import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public class Driver {
	
	static final Label[] masterLabelSet = LabelBuilder.getLabelSet();
	static final User[] masterPopulation = UserBuilder.createPopulation(1000000, masterLabelSet);
	static long startTime, elapsedTime;
	static String time;
	
	// for St
	static USI targetPop; // target Label set and users associated with set
	static User[] targetUsers;
	static double sTCost = 0.0; // (P(S) * |U(St)|)
	static final double BUDGET_ADJUSTMENT = 0.05; // adjust here for single run
	
	// For OG Alg
	static USI[] workingPermutations; // for OG Alg argument
	static double budget = 0.0; // ((P(S) * |U(St)|) * BUDGET_ADJUSTMENT)
	static Collection[] ogMatching;
	
	// for My Alg
	static USI[] myWorkingPermutations;
	static double myBudget = 0.0;
	static Collection[] myMatching;
	
	public static void main(String[] args){
		
		System.out.println("Master Pop size: " + masterPopulation.length);
		Characteristic[] sT;
		do{
			sT = createRandomTargetLabelSet(4);
			targetPop = new USI(sT);
		} while(targetPop.getPopSize() < 1000 || targetPop.getLabelCost() < 3.0);
		
		targetUsers = new User[targetPop.getPopSize()];
		Iterator uIter = targetPop.pop.iterator();
		int c = 0;
		while(uIter.hasNext()){
			User u = (User)uIter.next();
			targetUsers[c] = u;
			c++;
		}
		//printPopulation("StPop_Start.txt");
		
		ogMatching = new Collection[targetPop.getPopSize()];
		myMatching = new Collection[targetPop.getPopSize()];
		for(int i = 0; i < targetPop.getPopSize(); i++){
			ogMatching[i] = new ArrayList<USI>();
			myMatching[i] = new ArrayList<USI>();
		}
		
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
		
		//System.out.println("Info on targetPop: " + targetPop.getTraitsString());
		Iterator tIter = targetPop.getPop().iterator();
		while (tIter.hasNext()){
			User u = (User)tIter.next();
			//System.out.println("\nUser: " + u.toString());
		}
		int counter = 0;
		
		
		//prep and run OG Alg
		startTime = System.currentTimeMillis();
		ArrayList<USI> permutations = OGAlgPrep.getQualifiedPermutations(targetPop);
		elapsedTime = (System.currentTimeMillis() - startTime);
		time = getTimeString(elapsedTime);
		System.out.println("\nNumber of qualified permutations S(1...n): " + permutations.size());		
		System.out.println("Elapsed time: " + time);
		workingPermutations = new USI[permutations.size()];
		Iterator permIter = permutations.iterator();
		while(permIter.hasNext()){
			workingPermutations[counter] = (USI)permIter.next();
			counter++;
		}
		runOnce(workingPermutations, 0);
		runSeries(workingPermutations, 0);
		//printPopulation("StPop_PostOG.txt");
		
		
		// prep and run My Alg
		startTime = System.currentTimeMillis();
		ArrayList<USI> myPermutations = MyAlgPrep.getQualifiedPermutations(targetPop);
		elapsedTime = (System.currentTimeMillis() - startTime);
		time = getTimeString(elapsedTime);
		outputLabelSet("myS_LabelSet.txt", myPermutations);
		System.out.println("\nNumber of my qualified permutations S(1...n): " + myPermutations.size());		
		System.out.println("Elapsed time: " + time);
		myWorkingPermutations = new USI[myPermutations.size()];
		Iterator myPermIter = myPermutations.iterator();
		counter = 0;
		while(myPermIter.hasNext()){
			myWorkingPermutations[counter] = (USI)myPermIter.next();
			counter++;
		}
		printPopulation("StPop_postMy.txt");
		runOnce(myWorkingPermutations, 1);
		runSeries(myWorkingPermutations, 1);
		
		printComparativeResults();
		printPopulation("StPop_postMy.txt");
	}
	
	public static String getTimeString(long elapsedTime){
		String s = "";
		int seconds = (int) ((elapsedTime / 1000) % 60);
		int minutes = (int) (((elapsedTime / 1000) / 60) % 60);
		int hours = (int) ((((elapsedTime / 1000) / 60) / 60) % 60);
		s += hours + "h, ";
		s += minutes + "m, ";
		s += seconds + "s, ";
		s += (elapsedTime % 1000) + "ms";
		return s;
	}
	
	public static void printPopulation(String s){
		try(  PrintWriter out = new PrintWriter( s )  ){
			out.println("Target Population for label set: " + targetPop.getTraitsString());
			out.println("");
			int count = 0;
			Iterator pIter = targetPop.pop.iterator();
			while(pIter.hasNext()){
				User u = (User)pIter.next();
				out.println("From targetPop:\t\t" + u.toString());
				out.println("From targetUser[]:\t" + targetUsers[count].toString());
				out.println("");
				count++;
			}
		    
		    
		} catch(Exception e){
			System.out.println("Error writing to StPop.txt" + e);
		}
		System.out.println(s + " file written");
	}
	
	public static void outputLabelSet(String s, ArrayList<USI> myPermutations){
		try(  PrintWriter out = new PrintWriter( s )  ){
			out.println("Label set for my Permutations: ");
			out.println("");
			int counter = 1;
			Iterator pIter = myPermutations.iterator();
			while(pIter.hasNext()){
				USI u = (USI)pIter.next();
				out.println(counter + ". P(S): " + u.getLabelCost() +
						"\t|U(Si)|: " + u.getPopSize() +
						"\tTraits: " + u.getTraitsString());
				out.println("");
				counter++;
			}
		    
		    
		} catch(Exception e){
			System.out.println("Error writing to StPop.txt" + e);
		}
		System.out.println(s + " file written");
	
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
			startTime = System.currentTimeMillis();
			b1 = OgAlgorithm.approxAlg(targetPop, budget, mySi);
			elapsedTime = (System.currentTimeMillis() - startTime);
			
		} else {
			System.out.println("\nCalling My Approximation Algorithm(St, bo, S(1...n)) once");
			startTime = System.currentTimeMillis();
			b1 = MyAlgorithm.myApproxAlg(targetPop, budget, mySi);
			elapsedTime = (System.currentTimeMillis() - startTime);
		}
		System.out.println("Done!");
		time = getTimeString(elapsedTime);
		reportSingleRun(b1, (""+(BUDGET_ADJUSTMENT * 100)), mySi, time, choice);
		
	}
	
	public static void runSeries(USI[] mySi, int choice){
		if(choice == 0){
			System.out.println("\nCalling original Approximation Algorithm(St, bo, S(1...n)) in series...");
		}
		else{
			System.out.println("\nCalling My Approximation Algorithm(St, bo, S(1...n)) in series...");
		}
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
			
			budget = (sTCost * (i/10.0));
			double[] b1;
			if(choice == 0){
				startTime = System.currentTimeMillis();
				b1 = OgAlgorithm.approxAlg(targetPop, budget, mySi);
				elapsedTime = (System.currentTimeMillis() - startTime);
			}
			else{
				startTime = System.currentTimeMillis();
				b1 = MyAlgorithm.myApproxAlg(targetPop, budget, mySi);
				elapsedTime = (System.currentTimeMillis() - startTime);
			}
			time = getTimeString(elapsedTime);
			reportSingleRun(b1, (""+(i * 10)), mySi, time, choice);
			reportSeriesRun(b1, (i * 10), budget, mySi, time, choice);
			
		}
	}
	
	public static void reportSingleRun(double[] b1, String percent, USI[] mySi, String time, int choice){
		String s = "Single_" + percent + "_" + (choice == 0 ? "OG":"My") + ".txt";
		try(  PrintWriter out = new PrintWriter( s )  ){
			int totalAlgUsers = 0;
			out.println("Run Time: " + time);
			out.println("Budget allocation result:");
			double bTotal = 0.0;
			ArrayList<User> coveredCount = new ArrayList<User>();
			for(int i = 0; i < b1.length; i++){
				if(b1[i] > 0.0){
					out.print("\nSelection order: " + mySi[i].selectionOrder +
							"\tSi[" + i + "]: Budget allocated: $" + b1[i]);
					bTotal += b1[i];
					out.println("\t|U(S)|: " + mySi[i].getPopSize() +
								"\tP(S): $" + mySi[i].getLabelCost() +
								"\t(P(S) * |U(S)|: $)" + mySi[i].getTotalSetCost() + 
								"\nSi label set: " + mySi[i].getTraitsString() +
								"\n" + mySi[i].getNumSharedUsers() + " user" +
								(mySi[i].getNumSharedUsers() != 1 ? "s":"") + " in Si also in St:");
						
					ArrayList<User> pop = mySi[i].sharedWithST;
					Iterator popIter = pop.iterator();
					while(popIter.hasNext()){
						boolean unique = true;
						User u = (User)popIter.next();
						int index = findStUser(u);
						if(index > -1){
							if(choice == 0)
								ogMatching[index].add(mySi[i]);
							else
								myMatching[index].add(mySi[i]);
						} else {
							out.println("Intersecting user not found in U(St)");
						}
						Iterator cIter = coveredCount.iterator();
						while(cIter.hasNext()){
							User u2 = (User)cIter.next();
							if(u.equals(u2)){
								unique = false;
							} 
						}
						if(unique){
							coveredCount.add(u);
							out.println(u.toString());
						}
					}
				} // has budget allocated
			}
			out.println("\nTotal Budget allocated: $" + bTotal);
			totalAlgUsers = coveredCount.size();
			int sTUsersCovered = (int)(budget/targetPop.getLabelCost());
			double percentDif = ((double)(totalAlgUsers - sTUsersCovered)/(double)sTUsersCovered);
			int adj = (int)(percentDif * 10000);
			percentDif = adj/100.0;
			out.println("Total number of users covered if allocating only to St: " + sTUsersCovered);
			out.println("Total number of users covered using algorithm: " + totalAlgUsers);
			out.println("Diference: " + (totalAlgUsers - sTUsersCovered) +
					"\tPercentage improvement: " + (percentDif) + "%");
			System.out.println("Writing to " + s + " complete");
		} catch(Exception e){
			System.out.println("Error writing to file" + e);
		}
		
	}
	
	public static void reportSeriesRun(double[] b1, double adjustment, double currentBudget, USI[] mySi, String time, int choice){
		String s = "SeriesReport" + (choice == 0 ? "OG":"My") + ".txt";
		try(  PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(s, true)))  ){
			ArrayList<User> coveredCount = new ArrayList<User>();
			int adj = 0;
			int totalAlgUsers = 0;
			int repeats = 0;
			double bTotal = 0.0;
			for(int k = 0; k < b1.length; k++){
				if(b1[k] > 0.0){
					bTotal += b1[k];
					Iterator iter = mySi[k].sharedWithST.iterator();
					while(iter.hasNext()){
						boolean unique = true;
						User u = (User)iter.next();
						Iterator cIter = coveredCount.iterator();
						while(cIter.hasNext()){
							User u2 = (User)cIter.next();
							if (u2.equals(u))
								unique = false;
						}
						if(unique)
							coveredCount.add(u);
					}
				} // has budget allocated
			}
			out.println("Run Time: " + time);
			out.println("\nAt " + adjustment + "% of total St cost of $" + 
					sTCost + ": ($" + bTotal + ")");
			totalAlgUsers = coveredCount.size();
			int sTUsersCovered = (int)(currentBudget/targetPop.getLabelCost());
			double percentDif = ((double)(totalAlgUsers - sTUsersCovered)/(double)sTUsersCovered);
			adj = (int)(percentDif * 10000);
			percentDif = adj/100.0;
			out.println("Total number of users covered if allocating only to St: " + sTUsersCovered);
			out.println("Total number of users covered using algorithm: " + totalAlgUsers);
			out.println("Diference: " + (totalAlgUsers - sTUsersCovered) +
					"\tPercentage improvement: " + (percentDif) + "%\n");
			System.out.println("Writing to " + s + " complete");
		}catch(Exception e){
			System.out.println("Error writing to file" + e);
		}
		
	}
	
	public static int findStUser(User u){
		int result = -1;
		for(int i = 0; i < targetUsers.length; i++){
			if(targetUsers[i].equals(u))
				result = i;
		}
		return result;
	}
	
	public static void printComparativeResults(){
		
		try(  PrintWriter out = new PrintWriter( "comparativeOutput.txt" )  ){
			
			out.println("TargetPop Label Set: " + targetPop.getTraitsString());
			out.println("PopSize: " + targetPop.getPopSize() +
					"\tP(S): " + targetPop.getLabelCost());
			out.println("");
			for(int i = 0; i < targetUsers.length; i++){
				out.println("");
				 out.print("\n\nTargetPop user # " + (i+1) + ": ");
				 out.println(targetUsers[i].toString());
				 
				 out.println("\nFrom ogAlg:\n");
				 //ArrayList<USI> ogP = (ArrayList<USI>) ogMatching[i];
				 Iterator oIter = ((ArrayList<USI>) ogMatching[i]).iterator();
				 while(oIter.hasNext()){
					 USI ogUSI = (USI)oIter.next();
					 out.println("Si label set: " + ogUSI.getTraitsString() +
							 "\tP(S): " + ogUSI.getLabelCost() + 
							 "\t|U(S)|: " + ogUSI.getPopSize());
					 out.println("Users in labelSet: ");
					 Iterator ogUIter = ogUSI.sharedWithST.iterator();
					 while(ogUIter.hasNext()){
						 User u = (User)ogUIter.next();
						 out.println(u.toString());
					 }
				 }
				 
				 out.println("\nFrom myAlg:\n");
				 //ArrayList<USI> myP = (ArrayList<USI>) myMatching[i];
				 Iterator myIter = (myMatching[i]).iterator();
				 while(myIter.hasNext()){
					 USI myUSI = (USI)myIter.next();
					 out.println("Si label set: " + myUSI.getTraitsString() +
							 "\tP(S): " + myUSI.getLabelCost() + 
							 "\t|U(S)|: " + myUSI.getPopSize());
					 out.println("Users in labelSet: ");
					 Iterator myUIter = myUSI.sharedWithST.iterator();
					 while(myUIter.hasNext()){
						 User u = (User)myUIter.next();
						 out.println(u.toString());
					 }
				 }
			}
		   
		    
		    
		} catch(Exception e){
			System.out.println("Error writing to file" + e);
		}
		System.out.println("comparativeOutput.txt file written");
	}
}
