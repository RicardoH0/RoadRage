
package model;

import java.util.Map;

public final class Truck extends AbstractVehicle {
    /**
     * The default dead time.
     */
    private static final int DEFAULT_DEADTIME = 0;

    public Truck(final int theX, final int theY, final Direction theDir) {
        super(theX, theY, theDir);
    }

    /**
     * A query that returns whether this vehicle can pass through 
     * the given type of terrain, when the street lights are in the given state.
     */
    @Override
    public boolean canPass(final Terrain theTerrain, final Light theLight) {
        boolean result = false;

        if (theTerrain == Terrain.STREET || theTerrain == Terrain.LIGHT) {
            result = true;
        } else if (theTerrain == Terrain.CROSSWALK) {
            if (theLight != Light.RED) {
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
       // Direction dir = Direction.random();
        Direction dir = Direction.random();
        // checks if street is two way, left and right
        if ((theNeighbors.get(getDirection().left()) == Terrain.STREET)
            && (theNeighbors.get(getDirection().right()) == Terrain.STREET)) {
            // checks if it's a three way street, sees if straight is avaliable
            if (theNeighbors.get(getDirection()) == Terrain.STREET) {
                dir = changeDirection(getDirection().reverse(), dir);
                // else finds a new or uses old direction excluding the straight
                // direction.
            } else {
                dir = changeDirection(getDirection(), dir);
            }

        } else if ((theNeighbors.get(getDirection().left()) == Terrain.STREET)
                 && (theNeighbors.get(getDirection()) == Terrain.STREET)) {
            dir = changeDirection(getDirection().right(), dir);
        } else if ((theNeighbors.get(getDirection().right()) == Terrain.STREET)
                 && (theNeighbors.get(getDirection()) == Terrain.STREET)) {

            dir = changeDirection(getDirection().left(), dir);
        } else {
            dir = lastChoose(theNeighbors, dir);
        }

        return dir;
    }

    private Direction lastChoose(final Map<Direction, Terrain> theNeighbors,
                                final Direction theInitialDirection) {
        Direction lC = theInitialDirection;

        if ((theNeighbors.get(getDirection()) == Terrain.STREET)
            || (theNeighbors.get(getDirection()) == Terrain.LIGHT)
            || (theNeighbors.get(getDirection()) == Terrain.CROSSWALK)) {
            lC = getDirection();
        } else if ((theNeighbors.get(getDirection().left()) == Terrain.STREET)
                 || (theNeighbors.get(getDirection().left()) == Terrain.LIGHT)
                 || (theNeighbors.get(getDirection().left()) == Terrain.CROSSWALK)) {
            lC = getDirection().left();
        } else if ((theNeighbors.get(getDirection().right()) == Terrain.STREET)
                 || (theNeighbors.get(getDirection().right()) == Terrain.LIGHT)
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

 

    @Override
    public int getDeathTime() {
        // TODO Auto-generated method stub
        return DEFAULT_DEADTIME;
    }

}
