import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

import java.net.URL;


/**
 * Creates a model for the representation of a car.
 */
public class Car extends Rectangle {

    /**
     * Components of a car. Contribute to time
     * Engine will impact top speed, tires will impact acceleration, and weight will affect both.
     * Boost will affect how much quicker you go when you activate it.
     */
    private int engine, tires, boost, weight;

    /** This is what all the components should add up to. */
    private int total;

//    /** TODO: Turbo boost??
//     * This was something we thought about implementing but didn't get a chance to. For the future, maybe.
//     */
//    private boolean isBoosted;

    /** The total time the car has driven for. Proportional to the distance traveled and components. */
    private double time;

    /** Reference to the turn order and identification. TODO: Associate a car to a users name ?? */
    private int id;

    private Color[] colors = { Color.RED, Color.BLUE, Color.BLACK, Color.YELLOW, Color.ORANGE };
    private Color color;

    /**
     * Creates a car.
     * @param x The horizontal location
     * @param y The vertical location
     * @param offset The sizing relative to locations sizing
     * @param id The identifier
     */
    public Car(double x, double y, double offset, int id) {
        super(x, y, offset, offset);
        primeStats(18);
        this.id = id;
        String[] names = { "bug", "blue", "black", "yellow","orange"};
        URL resource = getClass().getResource(names[id % names.length] + ".png");
        this.setFill(new ImagePattern(new Image(resource.toString())));
        color = colors[id%colors.length];
    }

    public Color getColor() { return color; }

    /**
     * Artem
     * Sets up the attributes of the car to begin with. Should only be used once.
     * @param statTotal the total you want the stats to add up to.
     */
    private void primeStats(int statTotal) {
        this.total = statTotal; //This number is fairly arbitrary, but if all the stats are 6 they'll add up to a little bit less than that which seems fair.
        int ran = (int)((Math.random()*10)+1); //1-10
        engine = ran; total -= ran;

        do{
            ran = (int)((Math.random()*10)+1);
        }while(ran>total);
        tires = ran; total -= ran;

        weight = total; //Whatever is left goes to weight
        total = 0;

    }

    /**
     * @return The current time elapsed.
     */
    public double getTime() { return time; }


    /**
     * Artem
     * Calculates time based on parameters.
     * Made it so that 5 is the midpoint for each parameter in the calculations.
     * @param distance takes variable of the distance that is covered in this turn
     */
    private void calculateTime(double distance){
        double addedTime = 0;
        if(distance >= 5) { //I conducted a series of playthroughs. Value of distance seems to be between 0 < d < 10, approx.
            addedTime += distance * (1/(0.5 + 0.1*engine)); //If distance is greater than half the avg., engine comes into play.
        }
        else if(distance < 5){
            addedTime += distance * (1/(0.5 + 0.1*tires)); //Tires == acceleration == distance < 5
        }

        addedTime = addedTime * (1/(0.9 + (0.02*weight))); //Weight makes a difference but on a smaller scale

//        if(isBoosted){
//            addedTime = addedTime * (1/(1 + 0.1*boost)); //Boost can never hurt you when it is structured this way. Even at 1, you're still reducing time.
//        }

        time += addedTime;
    }


    /**
     * @return The car's identifier
     */
    public int getIdentifier() {
        return id;
    }

    public int getEngine(){
        return engine;
    }

    public int getTires(){
        return tires;
    }

    public int getWeight(){
        return weight;
    }

    /*
     * Moves a car from one location to another. Updates the time.
     * @param location The location to move to.
     */
    public void newLocation(double x, double y, double distanceIn) {
        this.setX(x);
        this.setY(y);
        calculateTime(distanceIn);
        //System.out.println(x + "" + y + "" + distanceIn);
        //time += (distanceIn) * 0.32;
    }

    /**
     * @return The textual representation of a car.
     */
    @Override
    public String toString() {
        return (id + 1) + "\t\t" + String.format("%.1f", time);
    }

}
