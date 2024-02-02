public class MagnitudeFilter implements Filter {
    private double minMagnitude;
    private double maxMagnitude;
    private String filterName;

    public MagnitudeFilter(double min, double max,String name) {
        minMagnitude = min;
        maxMagnitude = max;
        filterName = name;
    }

    @Override
    public boolean satisfies(QuakeEntry qe) {
        double magnitude = qe.getMagnitude();
        return magnitude >= minMagnitude && magnitude <= maxMagnitude;
    }
    
    @Override
    public String getName(){
        return filterName;
    }
    
}
