
package model;

import java.util.Map;

public final class Atv extends AbstractVehicle {

    /**
     * The default dead time.
     */
    private static final int DEFAULT_DEADTIME = 25;

    public Atv(final int theX, final int theY, final Direction theDir) {
        super(theX, theY, theDir);
    }

    /**
     * A query that returns whether this vehicle can pass through 
     * the given type of terrain, when the street lights are in the given state.
     */
    @Override
    public boolean canPass(final Terrain theTerrain, final Light theLight) {
        boolean result = true;

        if (theTerrain == Terrain.WALL) {
            result = false;
        }
        return result;
    }

    /**
     * A query that returns the direction in which this vehicle would liketo move,
     *  giventhe specified information.  Different vehicles have different 
     *  movement behaviors, as described previously.
     */
    @Override
    public Direction chooseDirection(final Map<Direction, Terrain> theNeighbors) {
        Direction dir = Direction.random();
        if (dir == getDirection().reverse()) {
            while (dir == getDirection().reverse()) {
                dir = Direction.random();
            }
        }
        return dir;
    }

    @Override
    public int getDeathTime() {
        return DEFAULT_DEADTIME;
    }

}
