package brewingcoffee;

/* A fancy coffee maker.  Makes coffee of varying strengths. */
public class CoffeeMachine {

    private String coffeeMachine = "number 1";
	
	// The current strength of the coffee.
    private String strength = "Killer Intense";
    
    // Set the strength for the coffee to s; affects the fineness of the
    // grind.
    // "Weak", "Regular", "Strong" are the usual options for s, but you can
    // try others.
    public void setStrength(String s) {
        strength = s;
    }
    
    // Grind the beans.
    public void grindBeans() {
        System.out.println("Grinding beans for " +
            strength + " coffee.");
    }
    
    // Brew the coffee into c.
    public void brew(CoffeeCup c) {
        System.out.println("Brewing coffee.");
        c.fill();
    }
    
    // Fill the reservoir with water.
    public void addWater() {
        System.out.println("Adding Water");
    }

    // Add beans.
    public void addBeans() {
        System.out.println("Adding Beans");
    }
    
}
