package com.company;

import java.util.HashMap;

public class AStarState
{
    /** This is a reference to the map that the A* algorithm is navigating. **/
    private Map2D map;
    public HashMap <Location, Waypoint> Opened = new HashMap <Location, Waypoint>();
    public HashMap <Location, Waypoint> Closed = new HashMap <Location, Waypoint>();

    /**
     * Initialize a new state object for the A* pathfinding algorithm to use.
     **/
    public AStarState(Map2D map)
    {
        if (map == null)
            throw new NullPointerException("map cannot be null");

        this.map = map;
    }

    /** Returns the map that the A* pathfinder is navigating. **/
    public Map2D getMap()
    {
        return map;
    }

    /**
     * This method scans through all open waypoints, and returns the waypoint
     * with the minimum total cost.  If there are no open waypoints, this method
     * returns <code>null</code>.
     **/
    public Waypoint getMinOpenWaypoint()
    {
        if (Opened.size() == 0) return null;

        Waypoint first = (Waypoint) Opened.values().toArray()[0];
        float min = first.getTotalCost();

        for(int i = 0; i < Opened.size();i++)
        {
            Waypoint wp1 = (Waypoint) Opened.values().toArray()[i];

            if(min >= wp1.getTotalCost())
            {
                min = wp1.getTotalCost();
                first = wp1;
            }
        }
        return first;
    }

    /**
     * This method adds a waypoint to (or potentially updates a waypoint already
     * in) the "open waypoints" collection.  If there is not already an open
     * waypoint at the new waypoint's location then the new waypoint is simply
     * added to the collection.  However, if there is already a waypoint at the
     * new waypoint's location, the new waypoint replaces the old one <em>only
     * if</em> the new waypoint's "previous cost" value is less than the current
     * waypoint's "previous cost" value.
     **/
    public boolean addOpenWaypoint(Waypoint newWP)
    {
        if(!Opened.containsKey(newWP.getLocation())) {
            Opened.put(newWP.getLocation(), newWP);
            return true;
        }
        else
        {
            if(Opened.get(newWP.getLocation()).getRemainingCost() > newWP.getRemainingCost())
            {
                Opened.put(newWP.getLocation(), newWP);
                return true;
            }
        }
        return false;
    }


    /** Returns the current number of open waypoints. **/
    public int numOpenWaypoints()
    {
        return Opened.size();
    }


    /**
     * This method moves the waypoint at the specified location from the
     * open list to the closed list.
     **/
    public void closeWaypoint(Location loc)
    {
        Waypoint wp = Opened.get(loc);
        Opened.remove(loc);
        Closed.put(loc, wp);
    }

    /**
     * Returns true if the collection of closed waypoints contains a waypoint
     * for the specified location.
     **/
    public boolean isLocationClosed(Location loc)
    {
        if (Closed.containsKey(loc)) return true;
        return false;
    }
}

