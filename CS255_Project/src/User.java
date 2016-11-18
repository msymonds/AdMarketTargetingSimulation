import java.util.ArrayList;
import java.util.Iterator;

public class User {
	Characteristic[] traits = new Characteristic[10];
	double objectFunction = 0.0;
	
	public User(){
		
	}
	
	public double getUserObjectiveFunction(){
		return objectFunction;
	}
	
	public void calculateObjectiveFunction(USI[] family, double[] B){
		double result = 0.0;
		for(int i = 0; i < family.length; i++){
			if(family[i].hasUser(this)){
				if(family[i].getLabelCost() > 0.01)
					result += (B[i]/(family[i].getLabelCost() * family[i].getPopSize()));
			}
		}
		objectFunction = result;
	}
	
	public String toString(){
		String s = "User f(x): " + getUserObjectiveFunction() + "\tLabels:{";
		for(int i = 0; i < traits.length; i++){
			if(traits[i] == null)
				s += "NULL";
			else
				s += traits[i].attributeS;
			if(i < (traits.length-1))
				s += ", ";
		}
		s += "}";
		return s;
	}
}
