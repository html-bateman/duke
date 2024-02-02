public class PhraseFilter implements Filter {
    private String whereToSearch;
    private String phrase;
    private String filterName;

    public PhraseFilter(String where, String word,String name) {
        whereToSearch = where;
        phrase = word;
        filterName = name;
    }

    @Override
    public boolean satisfies(QuakeEntry qe) {
        String title = qe.getInfo();

        switch (whereToSearch.toLowerCase()) {
            case "start":
                return title.startsWith(phrase);
            case "end":
                return title.endsWith(phrase);
            case "any":
                return title.contains(phrase);
            default:
                return false; // Invalid whereToSearch value
        }
    }
    
    @Override
    public String getName(){
        return filterName;
    }
}

