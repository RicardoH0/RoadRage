
package model;

import java.util.Map;

public final class Car extends AbstractVehicle {

    /**
     * The default dead time.
     */
    private static final int DEFAULT_DEADTIME = 15;

    public Car(final int theX, final int theY, final Direction theDir) {
        super(theX, theY, theDir);
    }

    /**
     * A query that returns whether this vehicle can pass through 
     * the given type of terrain, when the street lights are in the given state.
     */
    @Override
    public boolean canPass(final Terrain theTerrain, final Light theLight) {
        boolean result = false;

        if (theTerrain == Terrain.STREET) {
            result = true;
        } else if (theTerrain == Terrain.LIGHT) {
            if (theLight == Light.GREEN || theLight == Light.YELLOW) {
                result = true;
            }
        } else if (theTerrain == Terrain.CROSSWALK) {
            if (theLight == Light.GREEN) {
                result = true;
            }
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
        final Direction dir;
        if ((theNeighbors.get(getDirection()) == Terrain.STREET)
            || (theNeighbors.get(getDirection()) == Terrain.LIGHT)
            || (theNeighbors.get(getDirection()) == Terrain.CROSSWALK)) {
            dir = getDirection();
        } else if ((theNeighbors.get(getDirection().left()) == Terrain.STREET)
                 || (theNeighbors.get(getDirection().left()) == Terrain.LIGHT)
                 || (theNeighbors.get(getDirection().left()) == Terrain.CROSSWALK)) {
            dir = getDirection().left();
        } else if ((theNeighbors.get(getDirection().right()) == Terrain.STREET)
                 || (theNeighbors.get(getDirection().right()) == Terrain.LIGHT)
                 || (theNeighbors.get(getDirection().right()) == Terrain.CROSSWALK)) {
            dir = getDirection().right();
        } else {
            dir = getDirection().reverse();
        }

        return dir;
    }

    @Override
    public int getDeathTime() {
        return DEFAULT_DEADTIME;
    }
}
