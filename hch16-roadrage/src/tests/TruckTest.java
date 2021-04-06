package tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.Direction;
import model.Truck;
import model.Light;
import model.Terrain;
import org.junit.Test;

public class TruckTest {
    /**
     * The number of times to repeat a test to have a high probability that all
     * random possibilities have been explored.
     */
    private static final int TRIES_FOR_RANDOMNESS = 50;
    
    /** Test method for Truck constructor. */
    @Test
    public void testTruckConstructor() {
        final Truck t = new Truck(10, 11, Direction.NORTH);
        
        assertEquals("Truck x coordinate not initialized correctly!", 10, t.getX());
        assertEquals("Truck y coordinate not initialized correctly!", 11, t.getY());
        assertEquals("Truck direction not initialized correctly!",
                     Direction.NORTH, t.getDirection());
        assertEquals("Truck death time not initialized correctly!", 0, t.getDeathTime());
        assertTrue("Truck isAlive() fails initially!", t.isAlive());
    }
    
    /** Test method for Truck setters. */
    @Test
    public void testTruckSetters() {
        final Truck t = new Truck(10, 11, Direction.NORTH);
        
        t.setX(12);
        assertEquals("Truck setX failed!", 12, t.getX());
        t.setY(13);
        assertEquals("Truck setY failed!", 13, t.getY());
        t.setDirection(Direction.SOUTH);
        assertEquals("Truck setDirection failed!", Direction.SOUTH, t.getDirection());
    }

    /**
     * Test method for {@link Truck#canPass(Terrain, Light)}.
     */
    @Test
public void testCanPass() {
        
        // Truck can move to GRASS or to CROSSWALKS
        // so we need to test both of those conditions
        
        // Truck should NOT choose to move to other terrain types
        // so we need to test that Truck never move to other terrain types
        
        // Truck should only reverse direction if no other option is available
        // so we need to be sure to test that requirement also
        
        final List<Terrain> validTerrain = new ArrayList<>();
        validTerrain.add(Terrain.STREET);
        validTerrain.add(Terrain.CROSSWALK);
                
        final Truck truck = new Truck(0, 0, Direction.NORTH);
        // test each terrain type as a destination
        for (final Terrain destinationTerrain : Terrain.values()) {
            // try the test under each light condition
            for (final Light currentLightCondition : Light.values()) {
                if (destinationTerrain == Terrain.LIGHT) {
                
                    // Truck can pass STREET under any light condition
                    assertTrue("Truck should be able to pass STREET"
                               + ", with light " + currentLightCondition,
                               truck.canPass(destinationTerrain, currentLightCondition));
                } else if (destinationTerrain == Terrain.CROSSWALK) {
                           // trucks can pass CROSSWALK
                           // if the light is RED

                    if (currentLightCondition == Light.RED) {
                        assertFalse("Truck should NOT be able to pass " + destinationTerrain
                            + ", with light " + currentLightCondition,
                            truck.canPass(destinationTerrain,
                                          currentLightCondition));
                    } else { // light is red
                        assertTrue("Truck should be able to pass " + destinationTerrain
                            + ", with light " + currentLightCondition,
                            truck.canPass(destinationTerrain,
                                          currentLightCondition));
                    }
                } else if (!validTerrain.contains(destinationTerrain)) {
 
                    assertFalse("Truck should NOT be able to pass " + destinationTerrain
                        + ", with light " + currentLightCondition,
                        truck.canPass(destinationTerrain, currentLightCondition));
                }
            } 
        }
    }

    /**
     * Test method for {@link Truck#chooseDirection(java.util.Map)}.
     */
    @Test
    public void testChooseDirectionSurroundedByStreet() {
        final Map<Direction, Terrain> neighbors = new HashMap<Direction, Terrain>();
        neighbors.put(Direction.WEST, Terrain.STREET);
        neighbors.put(Direction.NORTH, Terrain.STREET);
        neighbors.put(Direction.EAST, Terrain.STREET);
        neighbors.put(Direction.SOUTH, Terrain.STREET);
        
        boolean seenWest = false;
        boolean seenNorth = false;
        boolean seenEast = false;
        boolean seenSouth = false;
        
        final Truck truck = new Truck(0, 0, Direction.NORTH);
        
        for (int count = 0; count < TRIES_FOR_RANDOMNESS; count++) {
            final Direction d = truck.chooseDirection(neighbors);
            
            if (d == Direction.WEST) {
                seenWest = true;
            } else if (d == Direction.NORTH) {
                seenNorth = true;
            } else if (d == Direction.EAST) {
                seenEast = true;
            } else if (d == Direction.SOUTH) { // this should NOT be chosen
                seenSouth = true;
            }
        }
 
        assertTrue("Truck chooseDirection() fails to select randomly "
                   + "among all possible valid choices!",
                   seenWest && seenNorth && seenEast);
            
        assertFalse("Truck chooseDirection() reversed direction when not necessary!",
                    seenSouth);
    }
    
    
    /**
     * Test method for {@link Truck#chooseDirection(java.util.Map)}.
     */
    @Test
    public void testChooseDirectionOnWallMustReverse() {
        
        for (final Terrain t : Terrain.values()) {
            if (t != Terrain.STREET && t != Terrain.CROSSWALK && t != Terrain.LIGHT) {
                
                final Map<Direction, Terrain> neighbors = new HashMap<Direction, Terrain>();
                neighbors.put(Direction.WEST, t);
                neighbors.put(Direction.NORTH, t);
                neighbors.put(Direction.EAST, t);
                neighbors.put(Direction.SOUTH, Terrain.WALL);
                
                final Truck truck = new Truck(0, 0, Direction.NORTH);
                
                // the Truck must reverse and go SOUTH
                assertEquals("Truck chooseDirection() failed "
                                + "when reverse was the only valid choice!",
                             Direction.SOUTH, truck.chooseDirection(neighbors));
            }
                
        }
    }
}


