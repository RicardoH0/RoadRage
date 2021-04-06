
package model;

import java.util.Map;

public class Human extends AbstractVehicle {

    /**
     * The default dead time.
     */
    private static final int DEFAULT_DEADTIME = 45;

    public Human(final int theX, final int theY, final Direction theDir) {

        super(theX, theY, theDir);

    }

    /**
     * A query that returns whether this vehicle can pass through 
     * the given type of terrain, when the street lights are in the given state.
     */
    @Override
    public boolean canPass(final Terrain theTerrain, final Light theLight) {
        boolean result = false;

        if (theTerrain == Terrain.GRASS) {
            result = true;
        } else if (theTerrain == Terrain.CROSSWALK) {
            if (theLight == Light.RED || theLight == Light.YELLOW) {
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
        final Direction dir = Direction.random();
        Direction testDir = null;
        testDir = checkCrosswalk(theNeighbors, testDir);

        if (testDir == null) {
            if ((theNeighbors.get(getDirection().left()) == Terrain.GRASS)
                && (theNeighbors.get(getDirection().right()) == Terrain.GRASS)) {
                // checks if it's a three way street, sees if straight is
                // avaliable
                if (theNeighbors.get(getDirection()) == Terrain.GRASS) {
                    testDir = changeDirection(getDirection().reverse(), dir);
                } else {
                    testDir = changeDirection(getDirection(), dir);
                }

            } else if ((theNeighbors.get(getDirection().left()) == Terrain.GRASS)
                     && (theNeighbors.get(getDirection()) == Terrain.GRASS)) {
                testDir = changeDirection(getDirection().right(), dir);
            } else if ((theNeighbors.get(getDirection().right()) == Terrain.GRASS)
                     && (theNeighbors.get(getDirection()) == Terrain.GRASS)) {

                testDir = changeDirection(getDirection().left(), dir);
            } else {
                testDir = lastChoose(theNeighbors, dir);
            }
        }

        return testDir;
    }

    private Direction lastChoose(final Map<Direction, Terrain> theNeighbors,
                                 final Direction theInitialDirection) {
        Direction lC = theInitialDirection;

        if ((theNeighbors.get(getDirection()) == Terrain.GRASS)
            || (theNeighbors.get(getDirection()) == Terrain.CROSSWALK)) {
            lC = getDirection();
        } else if ((theNeighbors.get(getDirection().left()) == Terrain.GRASS)
                 || (theNeighbors.get(getDirection().left()) == Terrain.CROSSWALK)) {
            lC = getDirection().left();
        } else if ((theNeighbors.get(getDirection().right()) == Terrain.GRASS)
                 || (theNeighbors.get(getDirection().right()) == Terrain.CROSSWALK)) {
            lC = getDirection().right();
        } else {
            lC = getDirection().reverse();
        }
        return lC;
    }

    private Direction changeDirection(final Direction theUnwantedDirection,
                                      final Direction theInitialDirection) {
        Direction cD = theInitialDirection;
        if ((cD == getDirection().reverse()) || (cD == theUnwantedDirection)) {
            while ((cD == getDirection().reverse()) || (cD == theUnwantedDirection)) {
                cD = Direction.random();
            }

        }
        return cD;
    }

    private Direction checkCrosswalk(final Map<Direction, Terrain> theNeighbors,
                                     final Direction theInitialDirection) {
        Direction cC = theInitialDirection;
        if ((theNeighbors.get(getDirection().left()) == Terrain.CROSSWALK
             && theNeighbors.get(getDirection().reverse()) == Terrain.GRASS)
            || (theNeighbors.get(getDirection().right()) == Terrain.CROSSWALK
                && theNeighbors.get(getDirection().reverse()) == Terrain.GRASS)
            || (theNeighbors.get(getDirection()) == Terrain.CROSSWALK
            && theNeighbors.get(getDirection().reverse()) == Terrain.GRASS)) {

            if (theNeighbors.get(getDirection().left()) == Terrain.CROSSWALK) {
                cC = getDirection().left();
            } else if (theNeighbors.get(getDirection().right()) == Terrain.CROSSWALK) {
                cC = getDirection().right();
            } else {
                cC = getDirection();
            }
        }

        return cC;
    }

    @Override
    public int getDeathTime() {
        // TODO Auto-generated method stub
        return DEFAULT_DEADTIME;
    }

}
