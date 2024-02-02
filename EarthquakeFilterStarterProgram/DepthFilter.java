public class DepthFilter implements Filter {
    private double minDepth;
    private double maxDepth;
    private String filterName;

    public DepthFilter(double min, double max,String name) {
        minDepth = min;
        maxDepth = max;
        filterName = name;
    }

    @Override
    public boolean satisfies(QuakeEntry qe) {
        double depth = qe.getDepth();
        return depth >= minDepth && depth <= maxDepth;
    }
    
    @Override
    public String getName(){
        return filterName;
    }
}
