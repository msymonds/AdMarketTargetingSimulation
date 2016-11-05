
public class Driver {
	
	static Label[] masterLabelSet;
	static User[] userPopulation;
	
	public static void main(String[] args){
		masterLabelSet = LabelBuilder.getLabelSet();
		userPopulation = UserBuilder.createPopulation(1000, masterLabelSet);
		
		
		int countA = 1;
		int countB = 0;
		for(User u : userPopulation){
			System.out.println("User " + countA + ":");
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
}
