import java.util.ArrayList;
import java.util.Iterator;

public class USI {
	Characteristic[] traits = new Characteristic[10];
	ArrayList<User> pop = new ArrayList<User>();
	double rSubI = 0.0;
	double hSubI = 0.0;
	double labelCost = 0.0;
	
	
	public USI(){}
	
	public USI(Characteristic[] traits) {
		this.traits = traits;
		User[] masterPopulation = Driver.masterPopulation;
		
		for(User u : masterPopulation){
			boolean fits = true;
			int i = 0;
			while(fits && i < traits.length){
				if(traits[i] != null){
					if( (u.traits[i] == null) || (!(traits[i].equals(u.traits[i]))) )
						fits = false;
				}
				i++;
			}
			if(fits){
				pop.add(u);
			}
		}
	}
	
	public USI(Characteristic[] traits, User[] pop){
		this.traits = traits;
		
		for(User u : pop){
			this.pop.add(u);
		}
	}
	
	public double calculateRsubI(USI targetPop){
		// (sum of U(Si) also in U(St) whose f(x) < 1)) / (|U(Si)| * P(Si))
		rSubI = 0.0;
		hSubI = 0.0;
		double cumulativeRi = 0.0;
		Iterator pIter = pop.iterator();
		while(pIter.hasNext()){
			User u = (User)pIter.next();
			if(targetPop.hasUser(u) && (u.getUserObjectiveFunction() < 1.0)){
				cumulativeRi += u.getUserObjectiveFunction();
				if(u.getUserObjectiveFunction() < 0.9 && u.getUserObjectiveFunction() > hSubI)
					hSubI = u.getUserObjectiveFunction();
			}
		}
		rSubI = (cumulativeRi / (getLabelCost() * getPopSize()));
		return rSubI;
	}
	
	public double getRsubI(){
		return rSubI;
	}
	
	public double getHsubI(){
		return hSubI;
	}
	
	public Characteristic[] getTraits(){
		return traits;
	}
	
	public ArrayList<User> getPop(){
		return pop;
	}
	
	
	public int getPopSize(){
		return pop.size();
	}
	
	public void setLabelCost(){
		labelCost = 0.0;
		for(Characteristic c : traits){
			if(c != null)
				labelCost += c.cost;
		}
		int round = (int)(labelCost*100);
		labelCost = (round/100.0);
	}
	
	public double getLabelCost(){
		setLabelCost();
		return labelCost;
	}
	
	public static USI getUSI(Characteristic[] target, ArrayList<USI> sample){
		USI result = null;
		Iterator iter = sample.iterator();
		
		while(iter.hasNext()){
			boolean match = true;
			USI usii = (USI)iter.next();
			Characteristic[] userC = usii.traits;
			for(int j = 0; j < target.length; j++){
				if(target[j] != null){
					if( userC[j] == null || (!(userC[j].equals(target[j]))) ){
						match = false;
					}
				} else if(userC != null){
					match = false;
				}
			}
			if(match)
				return usii;
		}
		
		return result;
	}
	
	public boolean hasUser(User u){
		Iterator iter = pop.iterator();
		while(iter.hasNext()){
			User uC = (User)iter.next();
			if(u.equals(uC))
				return true;
		}
		return false;
	}
}