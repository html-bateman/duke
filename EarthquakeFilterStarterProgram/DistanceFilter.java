public class DistanceFilter implements Filter {
    private Location location;
    private double maxDistance;
    private String filterName;

    public DistanceFilter(Location loc, double maxDist,String name) {
        location = loc;
        maxDistance = maxDist;
        filterName = name;
    }

    @Override
    public boolean satisfies(QuakeEntry qe) {
        Location quakeLocation = qe.getLocation();
        double distance = location.distanceTo(quakeLocation);
        return distance < maxDistance;
    }
    
    @Override
    public String getName(){
        return filterName;
    }
}

