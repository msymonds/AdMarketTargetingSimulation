import java.util.ArrayList;

public class LabelBuilder {
	
	
	public static Label[] getLabelSet(){
		System.out.print("Building Master Label set...");
		Label[] mySet = new Label[10];
		
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
		 */
		double[] ageCost = {0.74, 0.98, 0.87, 0.84, 0.72, 0.67, 0.63};
		Label age = new Label();
		age.domain.add(new Characteristic("12-17"));
		age.domain.get(0).cost = ageCost[0];
		age.domain.add(new Characteristic("18-24"));
		age.domain.get(1).cost = ageCost[1];
		age.domain.add(new Characteristic("25-34"));
		age.domain.get(2).cost = ageCost[2];
		age.domain.add(new Characteristic("35-44"));
		age.domain.get(3).cost = ageCost[3];
		age.domain.add(new Characteristic("45-54"));
		age.domain.get(4).cost = ageCost[4];
		age.domain.add(new Characteristic("55-64"));
		age.domain.get(5).cost = ageCost[5];
		age.domain.add(new Characteristic("65+"));
		age.domain.get(6).cost = ageCost[6];
		mySet[0] = age;
		
		//sex
		Label sex = new Label();
		sex.domain.add(new Characteristic("male"));
		sex.domain.get(0).cost = 0.65;
		sex.domain.add(new Characteristic("female"));
		sex.domain.get(1).cost = 0.72;
		mySet[1] = sex;
		
		// location
		Label location = new Label();
		String[] states = {"AL", "AK", "AZ", "AR", "CA", "CO", "CT", "DC", "DE", "FL", "GA", 
				"HI", "ID", "IL", "IN", "IA", "KS", "KY", "LA", "ME", "MD", "MA", "MI",
				"MN", "MS", "MO", "MT", "NE", "NV", "NH", "NJ", "NM", "NY", "NC", "ND", 
				"OH", "OK", "OR", "PA", "RI", "SC", "SD", "TN", "TX", "UT", "VT", "VA",
				"WA", "WV", "WI", "WY"};
		
		// cost based on cost of living index
		double[] stateCost = {0.45, 0.66, 0.49, 0.44, 0.71, 0.5, 0.66, 0.51,
				0.74, 0.49, 0.46, 0.84, 0.44, 0.47, 0.44, 0.46, 0.45, 0.45,
				0.47, 0.56, 0.63, 0.63, 0.45, 0.5, 0.42, 0.45, 0.5, 0.45, 
				0.52, 0.6, 0.6, 0.48, 0.67, 0.47, 0.49, 0.46, 0.44, 0.57, 
				0.52, 0.61, 0.5, 0.49, 0.45, 0.45, 0.46, 0.61, 0.51, 0.53,
				0.48, 0.48, 0.45};
		for(int i = 0; i < 51; i++){
			location.domain.add(new Characteristic(states[i]));
			location.domain.get(i).cost = stateCost[i];
		}
		mySet[2] = location;
		
		// education level - modeled from Facebook
		Label educationLevel = new Label();
		educationLevel.domain.add(new Characteristic("Associates Degree"));
		educationLevel.domain.get(0).cost = 0.01;
		educationLevel.domain.add(new Characteristic("College Grad"));
		educationLevel.domain.get(1).cost = 0.56;
		educationLevel.domain.add(new Characteristic("Doctorate Degree"));
		educationLevel.domain.get(2).cost = 0.01;
		educationLevel.domain.add(new Characteristic("High School Grad"));
		educationLevel.domain.get(3).cost = 0.24;
		educationLevel.domain.add(new Characteristic("In College"));
		educationLevel.domain.get(4).cost = 0.04;
		educationLevel.domain.add(new Characteristic("In Grad School"));
		educationLevel.domain.get(5).cost = 0.01;
		educationLevel.domain.add(new Characteristic("In High School"));
		educationLevel.domain.get(6).cost = 0.02;
		educationLevel.domain.add(new Characteristic("Master's Degree"));
		educationLevel.domain.get(7).cost = 0.05;
		educationLevel.domain.add(new Characteristic("Professional Degree"));
		educationLevel.domain.get(8).cost = 0.01;
		educationLevel.domain.add(new Characteristic("Some College"));
		educationLevel.domain.get(9).cost = 0.5;
		educationLevel.domain.add(new Characteristic("Some Grad School"));
		educationLevel.domain.get(10).cost = 0.01;
		educationLevel.domain.add(new Characteristic("Some High School"));
		educationLevel.domain.get(11).cost = 0.3;
		educationLevel.domain.add(new Characteristic("Unspecified"));
		educationLevel.domain.get(12).cost = 0.01;
		mySet[3] = educationLevel;
		
		// marital status
		Label maritalStatus = new Label();
		maritalStatus.domain.add(new Characteristic("single"));
		maritalStatus.domain.get(0).cost = 0.76;
		maritalStatus.domain.add(new Characteristic("married"));
		maritalStatus.domain.get(1).cost = 0.62;
		maritalStatus.domain.add(new Characteristic("divorced"));
		maritalStatus.domain.get(2).cost = 0.34;
		maritalStatus.domain.add(new Characteristic("widowed"));
		maritalStatus.domain.get(3).cost = 0.54;
		mySet[4] = maritalStatus;
		
		Label incomeLevel = new Label();
		incomeLevel.domain.add(new Characteristic("Less than $25,000"));
		incomeLevel.domain.get(0).cost = 0.35;
		incomeLevel.domain.add(new Characteristic("$25,000 - $39,999"));
		incomeLevel.domain.get(1).cost = 0.45;
		incomeLevel.domain.add(new Characteristic("$40,000 - $59,999"));
		incomeLevel.domain.get(2).cost = 0.55;
		incomeLevel.domain.add(new Characteristic("$60,000 - $74,999"));
		incomeLevel.domain.get(3).cost = 0.65;
		incomeLevel.domain.add(new Characteristic("$75,000 - $99,999"));
		incomeLevel.domain.get(4).cost = 0.75;
		incomeLevel.domain.add(new Characteristic("$100,000 or more"));
		incomeLevel.domain.get(5).cost = 0.85;
		mySet[5] = incomeLevel;
		
		Label IndustryEmployed = new Label();
		IndustryEmployed.domain.add(new Characteristic("Administrative"));
		IndustryEmployed.domain.get(0).cost = 0.5;
		IndustryEmployed.domain.add(new Characteristic("Engineering"));
		IndustryEmployed.domain.get(1).cost = 0.67;
		IndustryEmployed.domain.add(new Characteristic("Arts"));
		IndustryEmployed.domain.get(2).cost = 0.59;
		IndustryEmployed.domain.add(new Characteristic("Business"));
		IndustryEmployed.domain.get(3).cost = 0.47;
		IndustryEmployed.domain.add(new Characteristic("Maintenence"));
		IndustryEmployed.domain.get(4).cost = 0.44;
		IndustryEmployed.domain.add(new Characteristic("Social Services"));
		IndustryEmployed.domain.get(5).cost = 0.39;
		IndustryEmployed.domain.add(new Characteristic("Comp and Math"));
		IndustryEmployed.domain.get(6).cost = 0.72;
		IndustryEmployed.domain.add(new Characteristic("Construction"));
		IndustryEmployed.domain.get(7).cost = 0.61;
		IndustryEmployed.domain.add(new Characteristic("Education"));
		IndustryEmployed.domain.get(8).cost = 0.53;
		IndustryEmployed.domain.add(new Characteristic("Food/Service"));
		IndustryEmployed.domain.get(9).cost = 0.38;
		IndustryEmployed.domain.add(new Characteristic("Government"));
		IndustryEmployed.domain.get(10).cost = 0.26;
		IndustryEmployed.domain.add(new Characteristic("Medical"));
		IndustryEmployed.domain.get(11).cost = 0.73;
		IndustryEmployed.domain.add(new Characteristic("IT/Technical"));
		IndustryEmployed.domain.get(12).cost = 0.51;
		IndustryEmployed.domain.add(new Characteristic("Install/Repair"));
		IndustryEmployed.domain.get(13).cost = 0.5;
		IndustryEmployed.domain.add(new Characteristic("Legal"));
		IndustryEmployed.domain.get(14).cost = 0.41;
		IndustryEmployed.domain.add(new Characteristic("Science"));
		IndustryEmployed.domain.get(15).cost = 0.56;
		IndustryEmployed.domain.add(new Characteristic("Management"));
		IndustryEmployed.domain.get(16).cost = 0.61;
		IndustryEmployed.domain.add(new Characteristic("Military"));
		IndustryEmployed.domain.get(17).cost = 0.5;
		IndustryEmployed.domain.add(new Characteristic("Nurses"));
		IndustryEmployed.domain.get(18).cost = 0.57;
		IndustryEmployed.domain.add(new Characteristic("Personal Care"));
		IndustryEmployed.domain.get(19).cost = 0.42;
		IndustryEmployed.domain.add(new Characteristic("Production"));
		IndustryEmployed.domain.get(20).cost = 0.58;
		IndustryEmployed.domain.add(new Characteristic("Protective Service"));
		IndustryEmployed.domain.get(21).cost = 0.46;
		IndustryEmployed.domain.add(new Characteristic("Retail"));
		IndustryEmployed.domain.get(22).cost = 0.69;
		IndustryEmployed.domain.add(new Characteristic("Sales"));
		IndustryEmployed.domain.get(23).cost = 0.67;
		IndustryEmployed.domain.add(new Characteristic("Temp/Seasonal"));
		IndustryEmployed.domain.get(24).cost = 0.32;
		IndustryEmployed.domain.add(new Characteristic("Transportation"));
		IndustryEmployed.domain.get(25).cost = 0.48;
		IndustryEmployed.domain.add(new Characteristic("Veterans"));
		IndustryEmployed.domain.get(26).cost = 0.5;
		mySet[6] = IndustryEmployed;
		
		
		// optional Labels
		int domainCount = 5;
		
		Label junkA = new Label();
		for(int i = 0; i < domainCount; i++){
			junkA.domain.add(new Characteristic("" + i));
			junkA.domain.get(i).cost = ((Math.random()*.8) + 0.2);
		}
		mySet[7] = junkA;
		
		Label junkB = new Label();
		for(int i = 0; i < domainCount; i++){
			junkB.domain.add(new Characteristic("" + i));
			junkB.domain.get(i).cost = ((Math.random()*.8) + 0.2);
		}
		mySet[8] = junkB;
		
		Label junkC = new Label();
		for(int i = 0; i < domainCount; i++){
			junkC.domain.add(new Characteristic("" + i));
			junkC.domain.get(i).cost = ((Math.random()*.8) + 0.2);
		}
		mySet[9] = junkC;
		
		
		System.out.print("\tdone!\n");
		return mySet;
	}
	
}
