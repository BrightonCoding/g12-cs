package brewingcoffee;

 public class CoffeeKlatsch {

    public static void main(String[] args) {

        // Declare a reference to a CoffeeMachine.
        CoffeeMachine coffeemachine;
      
        // Create a new CoffeeMachine and make the variable refer to it.
        coffeemachine = new CoffeeMachine();
      
        // Add water and beans to the CoffeeMachine.
        coffeemachine.addWater();
        coffeemachine.grindBeans();

        // Get ready to grind some strong coffee in the CoffeeMachine.
        coffeemachine.setStrength("weak");
	
        // Grind the beans.
        coffeemachine.grindBeans();
       
        // Declare a reference to a CoffeeCup.
        CoffeeCup coffeecup;
	
        // Create a new CoffeeCup and make the variable refer to it.
       coffeecup = new CoffeeCup();
       
       // Have your CoffeeMachine brew coffee into the coffee cup.
        coffeemachine.brew(coffeecup);


        // Drink from the cup.
        coffeecup.drink();
   
        // Try to drink again --the cup is now empty.
       coffeecup.drink();


        // Declare ANOTHER coffee cup reference and initialize it.
	    CoffeeCup coffeecup2;
coffeecup2 = new CoffeeCup();


        // Brew coffee into the new cup.
        coffeemachine.brew(coffeecup2);


        // Try to drink from the first cup --it's still empty!
        coffeecup.drink();


        // Drink from the second cup.
      coffeecup2.drink();

        // Print a sigh of satisfaction.
        System.out.println("oooo i love a good morning coffee");
	
    }

    
}
