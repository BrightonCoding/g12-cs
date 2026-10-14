package ch8;

import java.text.DecimalFormat;

public class Population {

    
    public static void main(String[] args) {

        final double  GROWTH_RATE = 1.017;
        double populationMexico = 106.2;
        int year = 2005;
        boolean limit = true;

        DecimalFormat df = new DecimalFormat("0.0");

        while (limit) {
            populationMexico *= GROWTH_RATE;
            year += 1;

            
            if (populationMexico > 120) {
                limit = false;
            }
        }

        System.out.println("The population will reach " + df.format(populationMexico) + " million in " + year);
    }
}
