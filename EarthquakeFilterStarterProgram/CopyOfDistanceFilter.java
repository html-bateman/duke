public class CopyOfDistanceFilter implements CopyOfFilter {
    private Location location;
    private double maxDistance;

    public CopyOfDistanceFilter(Location loc, double maxDist) {
        location = loc;
        maxDistance = maxDist;
    }

    @Override
    public boolean satisfies(QuakeEntry qe) {
        Location quakeLocation = qe.getLocation();
        double distance = location.distanceTo(quakeLocation);
        return distance < maxDistance;
    }
}

