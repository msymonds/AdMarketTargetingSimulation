import java.util.ArrayList;
import java.util.Iterator;

public class Driver {
	
	static final Label[] masterLabelSet = LabelBuilder.getLabelSet();
	static final User[] masterPopulation = UserBuilder.createPopulation(100000, masterLabelSet);
	static USI targetPop; 
	static double budget = 0.0;
	
	public static void main(String[] args){
		
		//printPopulation();
		System.out.println("Master Pop size: " + masterPopulation.length);
		Characteristic[] sT;
		do{
			sT = createRandomTargetLabelSet(4);
			targetPop = new USI(sT);
		} while(targetPop.getPopSize() < 100);
		
		System.out.print("\ntargetPop (St): ");
		printLabelSet(sT);
		System.out.print("P(St): $" + targetPop.getLabelCost());
		System.out.print("\t|U(St)|: " + targetPop.getPopSize());
		double br = targetPop.getLabelCost()*targetPop.getPopSize();
		int bri = (int)(br*100);
		br = bri/100.0;
		System.out.print("\tRequired budget ( |U(St)|*P(St) ): $" +
		(br) + "\n\n");
		
		ArrayList<USI> permutations = getQualifiedPermutations(targetPop);
		System.out.println("Number of qualified permutations S(1...n): " + permutations.size());
		
		budget = ((targetPop.getLabelCost()/2)*targetPop.getPopSize());
		int adj = (int)(budget*100);
		budget = adj/100.0;
		System.out.println("\nAd budget (bo) set to 50% of required budget: $" + budget);
		
		System.out.println("\nCalling original Approximation Algorithm(St, bo, S(1...n))");
		//double[] b1 = OgAlgorithm.approxAlg(targetPop, budget, permutations);
		//System.out.println("Done!");
		
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
	
	public static ArrayList<USI> getQualifiedPermutations(USI sT){
		System.out.println("Generating Qualified Permutations of St = S(1...n)...");
		ArrayList<USI> result = new ArrayList<USI>();
		Label[] pool = getLabelPool(sT.pop);
		long exp = 1;
		int sum = 0;
		System.out.print("Total number of traits pooled from St: ");
		for(int i = 0; i < pool.length; i++){
			int count = 0;
			ArrayList<Characteristic> c = pool[i].domain;
			Iterator iter = c.iterator();
			while(iter.hasNext()){
				iter.next();
				count++;
			}
			System.out.print("" + count + " + ");
			sum += count;
			exp *= (count + 1);
		}
		System.out.print("= " + sum + "\n");
		System.out.println("Expected number of raw permutations: " + exp);
		long masterCount = 0;
		ArrayList<Characteristic[]> rawPermutations = new ArrayList<Characteristic[]>();
		if(exp < Integer.MAX_VALUE){
			System.out.print("Examining raw permutations...");
			for(int i = 0; i < (pool[0].domain.size()+1); i++){
				for(int j = 0; j < (pool[1].domain.size()+1); j++){
					for(int k = 0; k < (pool[2].domain.size()+1); k++){
						for(int m = 0; m < (pool[3].domain.size()+1); m++){
							for(int n = 0; n < (pool[4].domain.size()+1); n++){
								for(int p = 0; p < (pool[5].domain.size()+1); p++){
									for(int q = 0; q < (pool[6].domain.size()+1); q++){
										for(int r = 0; r < (pool[7].domain.size()+1); r++){
											for(int s = 0; s < (pool[8].domain.size()+1); s++){
												for(int t = 0; t < (pool[9].domain.size()+1); t++){
													Characteristic[] a = {
															(i < pool[0].domain.size() ? pool[0].domain.get(i) : null),
															(j < pool[1].domain.size() ? pool[1].domain.get(j) : null),
															(k < pool[2].domain.size() ? pool[2].domain.get(k) : null),
															(m < pool[3].domain.size() ? pool[3].domain.get(m) : null),
															(n < pool[4].domain.size() ? pool[4].domain.get(n) : null),
															(p < pool[5].domain.size() ? pool[5].domain.get(p) : null),
															(q < pool[6].domain.size() ? pool[6].domain.get(q) : null),
															(r < pool[7].domain.size() ? pool[7].domain.get(r) : null),
															(s < pool[8].domain.size() ? pool[8].domain.get(s) : null),
															(t < pool[9].domain.size() ? pool[9].domain.get(t) : null)
													};
													masterCount++;
													double rawCost = 0;
													for(int v = 0; v < 10; v++){
														if(a[v] != null)
															rawCost += a[v].cost;
													}
													if(rawCost < sT.getLabelCost()){
														if(hasAtLeastOne(a)){
															USI candidate = new USI(a);
															
															if(getIntersectingUsers(candidate) > 0){
																result.add(candidate);
															}
														}
													}
													if(masterCount % 1000000 == 0)
														System.out.print(".");
												}
											}
										}
									}
								}
							}
						}
					}
				}
			}
		} else {
			System.out.println("Value too large to create raw permutations");
		}
		System.out.print("...done!!\n");
		Characteristic[] cut = new Characteristic[10];
		USI u = USI.getUSI(cut, result);
		if(u != null)
			System.out.print("\nNull USI object removed: " + result.remove(u) + "\n");
		
		return result;
	}
	
	private static Label[] getLabelPool(ArrayList<User> pop){
		
		Label[] pool = new Label[10];
		for(int i = 0; i < 10; i++)
			pool[i] = new Label();
		Iterator popIter = pop.iterator();
		while(popIter.hasNext()){
			User u = (User)popIter.next();
			Characteristic[] c = u.traits;
			for(int i = 0; i < 10; i++){
				if(c[i] != null){
					//String s = c[i].attributeS;
					boolean isPresent = false;
					ArrayList<Characteristic> list = pool[i].domain;
					Iterator cIter = list.iterator();
					while(cIter.hasNext()){
						Characteristic cL = ((Characteristic)cIter.next());
						if(c[i].equals(cL))
							isPresent = true;
					}
					if(!isPresent){
						pool[i].domain.add(c[i]);
					}
				}	
			}
		}		
		return pool;
	}
	
	public static int getIntersectingUsers(USI next){
		int result = 0;
		ArrayList<User> candidate = next.pop;
		ArrayList<User> master = targetPop.pop;
		int counter = 0;
		
		
		int sum1 = candidate.size();
		int sum2 = master.size();
		Iterator citer = candidate.iterator();
		while(citer.hasNext()){
			Iterator miter = master.iterator();
			User c = (User)citer.next();
			while(miter.hasNext()){
				User m = (User)miter.next();
				if(c.equals(m))
					result++;
				counter++;
			}
		}
		
		return result;
	}
	
	
	
	public static boolean hasAtLeastOne(Characteristic[] candidate){
		ArrayList<User> master = targetPop.pop;
		Iterator iter = master.iterator();
		while(iter.hasNext()){
			User mU = (User)iter.next();
			Characteristic[] m = mU.traits;
			boolean trial = true;
			for(int i = 0; i < candidate.length; i++){
				if(candidate[i] != null){
					if(m[i] == null || (!(candidate[i].equals(m[i]))))
						trial = false;
				}
			}
			if(trial)
				return true;
		}
		return false;
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
	
	
}
