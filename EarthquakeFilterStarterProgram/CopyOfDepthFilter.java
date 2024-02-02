public class CopyOfDepthFilter implements CopyOfFilter {
    private double minDepth;
    private double maxDepth;

    public CopyOfDepthFilter(double min, double max) {
        minDepth = min;
        maxDepth = max;
    }

    @Override
    public boolean satisfies(QuakeEntry qe) {
        double depth = qe.getDepth();
        return depth >= minDepth && depth <= maxDepth;
    }
}
