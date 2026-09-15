package brewingcoffee;


/* A coffee cup. */
public class CoffeeCup {
	
	private boolean isFull;  // Default value false.

    // Return true if I contain coffee, false otherwise.
    public boolean isFull() {
		return isFull;
    }
    
    // Fill me with coffee.
    public void fill() {
		isFull = true;
    }
    
    // If I am full, drink from me.  If I am empty, print
    // an error message.
    public void drink() {
		if (isFull) {
	    	System.out.println("You glug the coffee down.");
	    	isFull = false;
		} else {
	    	System.out.println("You sip furiously, but only suck air.");
		}
    }
}