
package model;

import java.util.Map;

public class Bicycle extends AbstractVehicle {

    /**
     * The default dead time.
     */
    private static final int DEFAULT_DEADTIME = 35;

    public Bicycle(final int theX, final int theY, final Direction theDir) {
        super(theX, theY, theDir);
    }

    /**
     * A query that returns whether this vehicle can pass through 
     * the given type of terrain, when the street lights are in the given state.
     */
    @Override
    public boolean canPass(final Terrain theTerrain, final Light theLight) {
        boolean result = false;

        if (theTerrain == Terrain.STREET || theTerrain == Terrain.TRAIL) {
            result = true;
        } else if (theTerrain == Terrain.LIGHT || theTerrain == Terrain.CROSSWALK) {
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
        final Direction dir = getDirection();
        Direction testDir = null;
        testDir = checkTrail(theNeighbors, testDir);

        if (testDir == null) {

            if ((theNeighbors.get(dir) == Terrain.STREET)
                || (theNeighbors.get(dir) == Terrain.LIGHT)
                || (theNeighbors.get(dir) == Terrain.TRAIL)
                || (theNeighbors.get(dir) == Terrain.CROSSWALK)) {
                testDir = dir;
            } else if ((theNeighbors.get(getDirection().left()) == Terrain.STREET)
                     || (theNeighbors.get(getDirection().left()) == Terrain.LIGHT)
                     || (theNeighbors.get(getDirection().left()) == Terrain.CROSSWALK)) {
                testDir = getDirection().left();
            } else if ((theNeighbors.get(getDirection().right()) == Terrain.STREET)
                     || (theNeighbors.get(getDirection().right()) == Terrain.LIGHT)
                     || (theNeighbors.get(getDirection().right()) == Terrain.CROSSWALK)) {
                testDir = getDirection().right();
            } else {
                testDir = getDirection().reverse();
            }
        }

        return testDir;
    }

    
    private Direction checkTrail(final Map<Direction, Terrain> theNeighbors,
                                 final Direction theInitialDirection) {
        Direction cT = theInitialDirection;
        if ((theNeighbors.get(getDirection().left()) == Terrain.TRAIL
             && theNeighbors.get(getDirection().reverse()) == Terrain.STREET)
            || (theNeighbors.get(getDirection().right()) == Terrain.TRAIL
                && theNeighbors.get(getDirection().reverse()) == Terrain.STREET)) {

            if (theNeighbors.get(getDirection().left()) == Terrain.TRAIL) {
                cT = getDirection().left();
            } else {
                cT = getDirection().right();
            }
        }
        return cT;
    }

    @Override
    public int getDeathTime() {
        return DEFAULT_DEADTIME;
    }

}
