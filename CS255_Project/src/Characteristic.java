
public class Characteristic {
	public String attributeS = "";
	public double cost = 0;
	
	public Characteristic(String s){
		attributeS = s;
	}
	
	public void setCost(double n){
		if(n >= 0)
			this.cost = n;
	}
	
	
	
}
