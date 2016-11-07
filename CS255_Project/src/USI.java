import java.util.ArrayList;
import java.util.Iterator;

public class USI {
	Characteristic[] traits = new Characteristic[10];
	ArrayList<User> pop = new ArrayList<User>();
	double rSubI = 0.0;
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
	
	public double calculateRsubI(USI usersRemaining){
		// need to implement
		return rSubI;
	}
	
	public double getRsubI(){
		return rSubI;
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
}
