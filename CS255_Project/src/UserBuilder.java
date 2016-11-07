
public class UserBuilder {
	private static Label[] masterSet;
	
	public static User[] createPopulation(int n, Label[] traitSet){
		System.out.print("Building Master Pop. set...");
		User[] pop = new User[n];
		for(int i = 0; i < n;  i++){
			User u = new User();
			u.traits = getTraits(traitSet);
			pop[i] = u;
		}
		System.out.print("\tdone!\n");
		return pop;
	}
	
	
	public static Characteristic[] getTraits(Label[] traitSet){
		
		masterSet = traitSet;
		
		Characteristic[] myTraits = new Characteristic[traitSet.length];
		Characteristic current;
		
		// First 7 traits are mandatory for all Users
		
		
		/*
		 * 1. AGE
		 * Group:	Pop Dist.:	Cost:
		 * 			
		 * 12-17:	6%			.74
		 * 18-24:	13%			.98
		 * 25-34:	18%			.87
		 * 35-44:	17%			.84
		 * 45-54:	15%			.72
		 * 55-64:	14%			.67
		 * 65+		19%			.63
		 * 
		 * http://www.pewinternet.org/data-trend/social-media/social-media-use-by-age-group/
		 */
		
		int age = (int) ((Math.random()* 99) +1);
		if(age < 7){
			myTraits[0] = masterSet[0].domain.get(0);
			myTraits[0].cost = masterSet[0].domain.get(0).cost;
					
		} else if(age < 20){
			myTraits[0] = masterSet[0].domain.get(1);
			myTraits[0].cost = masterSet[0].domain.get(1).cost;
		} else if(age < 38){
			myTraits[0] = masterSet[0].domain.get(2);
			myTraits[0].cost = masterSet[0].domain.get(2).cost;
		} else if(age < 55){
			myTraits[0] = masterSet[0].domain.get(3);
			myTraits[0].cost = masterSet[0].domain.get(3).cost;
		} else if(age < 70){
			myTraits[0] = masterSet[0].domain.get(4);
			myTraits[0].cost = masterSet[0].domain.get(4).cost;
		} else if(age < 84){
			myTraits[0] = masterSet[0].domain.get(5);
			myTraits[0].cost = masterSet[0].domain.get(5).cost;
		} else {
			myTraits[0] = masterSet[0].domain.get(6);
			myTraits[0].cost = masterSet[0].domain.get(6).cost;
		}
		
		/*
		 * 2. Sex		Dist.	Cost
		 * 0 = male		45%		.65
		 * 1 = female	55%		.75
		 */
		
		int sex = (int) ((Math.random() * 100));
		if(sex < 46){
			myTraits[1] = masterSet[1].domain.get(0);
			myTraits[1].cost = masterSet[1].domain.get(0).cost;
		} else {
			myTraits[1] = masterSet[1].domain.get(1);
			myTraits[1].cost = masterSet[1].domain.get(1).cost;
		}
		
		/*
		 * 3. Location
		 * equal probability to live in any given state
		 * see LabelBuilder for cost info
		 */
		int state = (int)((Math.random()*51));
		myTraits[2] = masterSet[2].domain.get(state);
		myTraits[2].cost = masterSet[2].domain.get(state).cost;
		
		/*
		 * 4. EducationLevel
		 * 
		 * 
		 */
		int edu = (int)((Math.random()*99) +1);
		if(edu < 57){
			myTraits[3] = masterSet[3].domain.get(1);
			myTraits[3].cost = masterSet[3].domain.get(1).cost;
		} else if(edu < 81){
			myTraits[3] = masterSet[3].domain.get(3);
			myTraits[3].cost = masterSet[3].domain.get(3).cost;
		} else if(edu < 85){
			myTraits[3] = masterSet[3].domain.get(4);
			myTraits[3].cost = masterSet[3].domain.get(4).cost;
		} else if(edu < 87){
			myTraits[3] = masterSet[3].domain.get(6);
			myTraits[3].cost = masterSet[3].domain.get(6).cost;
		} else if (edu < 92){
			myTraits[3] = masterSet[3].domain.get(7);
			myTraits[3].cost = masterSet[3].domain.get(7).cost;
		} else if(edu < 97){
			myTraits[3] = masterSet[3].domain.get(9);
			myTraits[3].cost = masterSet[3].domain.get(9).cost;
		} else if(edu < 100){
			myTraits[3] = masterSet[3].domain.get(11);
			myTraits[3].cost = masterSet[3].domain.get(11).cost;
		} else {
			int[] left = {0, 2, 5, 8, 10, 12};
			int choice = (int)((Math.random()*5));
			myTraits[3] = masterSet[3].domain.get(choice);
			myTraits[3].cost = masterSet[3].domain.get(choice).cost;
		}
		
		/*
		 * 5. Maritalstatus
		 *  Married - 52%
		 *  Single - 35%
		 *  divorced - 9%
		 *  widowed - 4%
		 */
		int married = (int)(((Math.random() * 99) + 1));
		if(married < 53){
			myTraits[4] = masterSet[4].domain.get(0);
			myTraits[4].cost = masterSet[4].domain.get(0).cost;
		} else if(married < 88){
			myTraits[4] = masterSet[4].domain.get(1);
			myTraits[4].cost = masterSet[4].domain.get(1).cost;
		} else if(married < 97){
			myTraits[4] = masterSet[4].domain.get(2);
			myTraits[4].cost = masterSet[4].domain.get(2).cost;
		} else {
			myTraits[4] = masterSet[4].domain.get(3);
			myTraits[4].cost = masterSet[4].domain.get(3).cost;
		}
		
		/*
		 * 6. IncomeLevel		Dist.	Cost
		 * Less than $25,000	18%		.35
		 * $25,000 - $39,999	21%		.45
		 * $40,000 - $59,99		24%		.55
		 * $60,000 - $74,999	17%		.65
		 * $75,000 - $99,999	11%		.75
		 * $100,000 or more		9%		.85
		 */
		int income = (int)(((Math.random() * 99) + 1));
		if(income < 19){
			myTraits[5] = masterSet[5].domain.get(0);
			myTraits[5].cost = masterSet[5].domain.get(0).cost;
		} else if(income < 40){
			myTraits[5] = masterSet[5].domain.get(1);
			myTraits[5].cost = masterSet[5].domain.get(1).cost;
		} else if(income < 64){
			myTraits[5] = masterSet[5].domain.get(2);
			myTraits[5].cost = masterSet[5].domain.get(2).cost;
		} else if(income < 81){
			myTraits[5] = masterSet[5].domain.get(3);
			myTraits[5].cost = masterSet[5].domain.get(3).cost;
		} else if(income < 92){
			myTraits[5] = masterSet[5].domain.get(4);
			myTraits[5].cost = masterSet[5].domain.get(4).cost;
		} else {
			myTraits[5] = masterSet[5].domain.get(5);
			myTraits[5].cost = masterSet[5].domain.get(5).cost;
		}
		
		/*
		 * 7. Industry Employed
		 */
		int ind = (int) (((Math.random()*27)));
		myTraits[6] = masterSet[6].domain.get(ind);
		myTraits[6].cost = masterSet[6].domain.get(ind).cost;
		
		
		// next 3 are optional (random)
		int coin = 0;
		int factor = 0;
		int choice = 0;
		
		// 8. JunkA
		coin = (int)(Math.random()*10);
		if(coin > 5){
			factor = masterSet[7].domain.size();
			choice = (int)( (Math.random() * factor));
			myTraits[7] = masterSet[7].domain.get(choice);
			myTraits[7].cost = masterSet[7].domain.get(choice).cost;
		}
		
		// 9. JunkB
		coin = (int)(Math.random()*10);
		if(coin > 5){
			factor = masterSet[8].domain.size();
			choice = (int)( (Math.random() * factor));
			myTraits[8] = masterSet[8].domain.get(choice);
			myTraits[8].cost = masterSet[8].domain.get(choice).cost;
		}

		// 10. JunkC
		coin = (int)(Math.random()*10);
		if(coin > 5){
			factor = masterSet[9].domain.size();
			choice = (int)( (Math.random() * factor));
			myTraits[9] = masterSet[9].domain.get(choice);
			myTraits[9].cost = masterSet[9].domain.get(choice).cost;
		}

				
		return myTraits;
	}
}
