import java.util.ArrayList;
import java.util.Iterator;

public class MyAlgPrep {

	
	public static ArrayList<USI> getQualifiedPermutations(USI targetPop){
		ArrayList<USI> result = new ArrayList<USI>();
		Iterator iter = targetPop.getPop().iterator();
		while(iter.hasNext()){
			User u = (User)iter.next();
			result.add(new USI(u.traits));
		}
		return result;
	}
	
}
