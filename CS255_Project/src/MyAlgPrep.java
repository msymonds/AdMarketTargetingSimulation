import java.util.ArrayList;
import java.util.Iterator;

public class MyAlgPrep {

	
	public static ArrayList<USI> getQualifiedPermutations(USI targetPop){
		ArrayList<USI> result = new ArrayList<USI>();
		Iterator iter = targetPop.getPop().iterator();
		while(iter.hasNext()){
			User u = (User)iter.next();
			Characteristic[] ogC = u.traits;
			Characteristic[] newC = new Characteristic[ogC.length];
			for(int i = 0; i< newC.length; i++){
				if(ogC[i] != null){
					newC[i] = new Characteristic(ogC[i].attributeS);
					newC[i].setCost(ogC[i].cost);
				}
			}
			USI sI = new USI(newC);
			sI.findPopIntersection(targetPop);
			USI best = getCheapestSi(sI, targetPop);
			result.add(best);
		}
		return result;
	}
	
	private static USI getCheapestSi(USI candidate, USI target){
		USI result = candidate;
		Characteristic[] currentSet = candidate.getTraits();
		double candidateCost = result.getTotalSetCost();
		double targetCost = (target.getLabelCost() * candidate.getNumSharedUsers());
		boolean[] examined = new boolean[candidate.getTraits().length];
		double largestCurrentAttributePrice = 0.0;
		int attributeIndex = 0;
		
		for(int i = 0; i < examined.length; i++){
			if(currentSet[i] == null ) //|| target.getTraits()[i] != null
				examined[i] = true;
			else
				examined[i] = false;
		}
		
		while(candidateCost > targetCost && !allExamined(examined)){
			largestCurrentAttributePrice = 0.0;
			for(int i = 0; i < currentSet.length; i++){
				if(currentSet[i] != null && !examined[i] && currentSet[i].cost > largestCurrentAttributePrice){
					largestCurrentAttributePrice = currentSet[i].cost;
					attributeIndex = i;
				}
			}
			Characteristic temp = currentSet[attributeIndex];
			currentSet[attributeIndex] = null;
			examined[attributeIndex] = true;
			USI current = new USI(currentSet);
			current.findPopIntersection(target);
			if(current.getTotalSetCost() < candidateCost && current.sharedUsers > 0){
				result = current;
				candidateCost = result.getTotalSetCost();
				targetCost = (target.getLabelCost() * result.getNumSharedUsers());
			} else {
				currentSet[attributeIndex] = temp;
			}
		}
		return result;
	}
	
	
	private static boolean allExamined(boolean[] test){
		for(int i = 0; i < test.length; i++){
			if(test[i] == false)
				return false;
		}
		return true;
	}
	
}
