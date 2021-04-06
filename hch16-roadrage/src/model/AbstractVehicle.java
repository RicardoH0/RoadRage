
package model;

// check resdVehicle for testing
// change MAP in readVehicle line #37
public abstract class AbstractVehicle implements Vehicle {

    /**
     * The point of X.
     */
    private int myX;
    /**
     * The point of Y.
     */
    private int myY;
    /**
     * The Direction.
     */
    private Direction myDir;
    /**
     * The Reset Direction.
     */
    private final Direction myResetDir;
    /**
     * The reset point of X.
     */
    private final int myResetX;
    /**
     * The reset point of Y.
     */
    private final int myResetY;

    /**
     * The number of poke.
     */
    private int myPokeCount;


    /**
     * The protected constructor.
     * @param theX
     * @param theY
     * @param theDir
     */
    protected AbstractVehicle(final int theX, final int theY, final Direction theDir) {

        myX = theX;
        myY = theY;
        myDir = theDir;
        myResetX = theX;
        myResetY = theY;
        myResetDir = theDir;


    }

    // A command that notifies this vehicle that
    // it has collided with the given other Vehicle object.
    public final void collide(final Vehicle theOther) {
        if (this.isAlive() && theOther.isAlive()) {
            if (theOther.getDeathTime() >= this.getDeathTime()) {
                theOther.poke();
            } else if (this.getDeathTime() >= theOther.getDeathTime()) {
                this.poke();
            }
        }
    }

    // A query that returns which the direction this vehicle is facing.
    public final Direction getDirection() {
        return myDir;
    }

    // A command that sets the movement direction of this vehicle.
    public final void setDirection(final Direction theDir) {
        myDir = theDir;
    }

    // A query that returns the name of the image file
    // that the GUI will use to draw this Vehicle object on the screen.
    public final String getImageFileName() {
        final String image;
        if (this.isAlive()) {
            image = getClass().getSimpleName().toLowerCase() + ".gif";
        } else {
            image = getClass().getSimpleName().toLowerCase() + "_dead.gif";
        }
        return image;
    }

    // Queries that return the x coordinates of this vehicle.
    public final int getX() {
        return myX;
    }

    // Queries that return the y coordinates of this vehicle.
    public final int getY() {
        return myY;
    }

    // A command called by the graphical user interface
    // once for each time the city animates one turn.
    public final void poke() {
        myPokeCount++;
        if (myPokeCount > getDeathTime()) {
            this.setDirection(Direction.random());
            myPokeCount = 0;
        }

    }

    // A command that instructs this Vehicle object to return to the initial
    // state
    // (including position, direction, and being alive) it had when it was
    // constructed.
    public final void reset() {
        setX(myResetX);
        setY(myResetY);
        setDirection(myResetDir);

    }

    // A query that returns whether this vehicle is alive.
    public final boolean isAlive() {
        boolean result = false;

        if (myPokeCount > 0) {
            result = false;
        } else {
            result = true;
        }

        return result;
    }

    // Commands that set the x coordinates of this Vehicle.
    public final void setX(final int theX) {
        myX = theX;
    }

    // Commands that set the y coordinates of this Vehicle.
    public final void setY(final int theY) {
        myY = theY;
    }

}
