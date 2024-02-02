import java.util.ArrayList;

public class CopyOfMatchAllFilter implements CopyOfFilter {
    private ArrayList<CopyOfFilter> filters;

    public CopyOfMatchAllFilter() {
        filters = new ArrayList<>();
    }

    public void addFilter(CopyOfFilter filter) {
        filters.add(filter);
    }

    @Override
    public boolean satisfies(QuakeEntry qe) {
        for (CopyOfFilter filter : filters) {
            if (!filter.satisfies(qe)) {
                return false; // If any filter condition is not satisfied, return false
            }
        }
        return true; // All filter conditions are satisfied
    }
}
