
public class Characteristic {
	public String attributeS = "";
	public long attributeN = -1;
	public double cost = 0;
	
	public Characteristic(String s){
		attributeS = s;
	}
	
	public Characteristic(long l){
		attributeN = l;
	}
	
	public void setCost(long n){
		if(n >= 0)
			this.cost = n;
	}
	
	
	
}
