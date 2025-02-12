
public class AccountingIFApp {

	public static void main(String[] args) {
		
		double valueOfSupply = Double.parseDouble(args[0]);
		double vatRate = 0.1;
		double expenseRate= 0.3;
		double vat = valueOfSupply*vatRate;
		
		double total = valueOfSupply + vat;
		double expense = valueOfSupply * expenseRate;
		double income = valueOfSupply - expense;
		
		double dividend1;
		double dividend2;
		double dividend3;
		
		if(income >10000.0) {			
			 dividend1 = 0.5;
			 dividend2 = 0.3;
			 dividend3 = 0.2;
		}
		
		else {
			 dividend1 = 1;
			 dividend2 = 0;
			 dividend3 = 0;
		}
		
		System.out.println("Value of supply : " + valueOfSupply);
		System.out.println("VAT : " + vat );
		System.out.println("Total : " + total );
		
		System.out.println("Expense : " + expense );
		System.out.println("Income : " + income );
		
		System.out.println("Dividend 1 : " + income * dividend1 );
		System.out.println("Dividend 2 : " + income * dividend2 );
		System.out.println("Dividend 3 : " + income * dividend3 );
		
		
	}

}
