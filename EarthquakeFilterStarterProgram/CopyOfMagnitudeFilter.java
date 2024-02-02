public class CopyOfMagnitudeFilter implements CopyOfFilter {
    private double minMagnitude;
    private double maxMagnitude;

    public CopyOfMagnitudeFilter(double min, double max) {
        minMagnitude = min;
        maxMagnitude = max;
    }

    @Override
    public boolean satisfies(QuakeEntry qe) {
        double magnitude = qe.getMagnitude();
        return magnitude >= minMagnitude && magnitude <= maxMagnitude;
    }
  
}
