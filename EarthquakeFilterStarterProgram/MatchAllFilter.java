import java.util.ArrayList;

public class MatchAllFilter implements Filter {
    private ArrayList<Filter> filters;
    private String filterName;

    public MatchAllFilter(String name) {
        filters = new ArrayList<>();
        filterName = name;
    }

    public void addFilter(Filter filter) {
        filters.add(filter);
    }

    @Override
    public boolean satisfies(QuakeEntry qe) {
        for (Filter filter : filters) {
            if (!filter.satisfies(qe)) {
                return false; // If any filter condition is not satisfied, return false
            }
        }
        return true; // All filter conditions are satisfied
    }
    
    @Override
    public String getName() {
        StringBuilder names = new StringBuilder();
        for (Filter filter : filters) {
            names.append(filter.getName()).append(", ");
        }
        // Remove the trailing comma and space
        return names.length() > 2 ? names.substring(0, names.length() - 2) : names.toString();
    }
}
