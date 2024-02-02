public class CopyOfPhraseFilter implements CopyOfFilter {
    private String whereToSearch;
    private String phrase;

    public CopyOfPhraseFilter(String where, String word) {
        whereToSearch = where;
        phrase = word;
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
    
}

