import java.util.*;
import edu.duke.*;

public class EarthQuakeClient2 {
    public EarthQuakeClient2() {
        // TODO Auto-generated constructor stub
    }

    public ArrayList<QuakeEntry> filter(ArrayList<QuakeEntry> quakeData, Filter f) { 
        ArrayList<QuakeEntry> answer = new ArrayList<QuakeEntry>();
        for(QuakeEntry qe : quakeData) { 
            if (f.satisfies(qe)) { 
                answer.add(qe); 
            } 
        } 
        
        return answer;
    } 
    public ArrayList<QuakeEntry> filter(ArrayList<QuakeEntry> quakeData, CopyOfFilter f) { 
        ArrayList<QuakeEntry> answer = new ArrayList<QuakeEntry>();
        for(QuakeEntry qe : quakeData) { 
            if (f.satisfies(qe)) { 
                answer.add(qe); 
            } 
        } 
        
        return answer;
    } 

    public void quakesWithFilterOLD() { 
        EarthQuakeParser parser = new EarthQuakeParser(); 
        //String source = "http://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_week.atom";
        String source = "data/nov20quakedatasmall.atom";
        ArrayList<QuakeEntry> list  = parser.read(source);         
        System.out.println("read data for "+list.size()+" quakes");

        CopyOfFilter f = new MinMagFilter(4.0); 
        ArrayList<QuakeEntry> m7  = filter(list, f); 
        for (QuakeEntry qe: m7) { 
            System.out.println(qe);
        } 
    }

    public void createCSV() {
        EarthQuakeParser parser = new EarthQuakeParser();
        //String source = "../data/nov20quakedata.atom";
        String source = "data/nov20quakedatasmall.atom";
        //String source = "http://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_week.atom";
        ArrayList<QuakeEntry> list  = parser.read(source);
        dumpCSV(list);
        System.out.println("# quakes read: "+list.size());
    }

    public void dumpCSV(ArrayList<QuakeEntry> list) {
        System.out.println("Latitude,Longitude,Magnitude,Info");
        for(QuakeEntry qe : list){
            System.out.printf("%4.2f,%4.2f,%4.2f,%s\n",
                qe.getLocation().getLatitude(),
                qe.getLocation().getLongitude(),
                qe.getMagnitude(),
                qe.getInfo());
        }
    }
    
    public void quakesWithFilter1() {
        EarthQuakeParser parser = new EarthQuakeParser();
        String source = "data/nov20quakedatasmall.atom";
        ArrayList<QuakeEntry> quakeData = parser.read(source);
    
        // First filter: Magnitude between 4.0 and 5.0
        double minMag = 4.0;
        double maxMag = 5.0;
        CopyOfMagnitudeFilter magFilter = new CopyOfMagnitudeFilter(minMag, maxMag);
        ArrayList<QuakeEntry> magFilteredQuakes = filter(quakeData, magFilter);
    
        // Second filter: Depth between -35000.0 and -12000.0
        double minDepth = -35000.0;
        double maxDepth = -12000.0;
        CopyOfDepthFilter depthFilter = new CopyOfDepthFilter(minDepth, maxDepth);
        ArrayList<QuakeEntry> resultQuakes = filter(magFilteredQuakes, depthFilter);
    
        for (QuakeEntry quake : resultQuakes) {
            System.out.println(quake);
        }
    
        System.out.println("Number of earthquakes found: " + resultQuakes.size());
    }
    
    public void quakesWithFilter() {
        EarthQuakeParser parser = new EarthQuakeParser();
        String source = "data/nov20quakedatasmall.atom";
        ArrayList<QuakeEntry> quakeData = parser.read(source);
    
        // First filter: Location within 10,000 km from Tokyo, Japan
        Location tokyoLocation = new Location(35.42, 139.43);
        double maxDistanceTokyo = 10000000.0; // 10,000 km
        CopyOfDistanceFilter distanceFilter = new CopyOfDistanceFilter(tokyoLocation, maxDistanceTokyo);
        ArrayList<QuakeEntry> distanceFilteredQuakes = filter(quakeData, distanceFilter);
    
        // Second filter: Title ends with "Japan"
        String japanSuffix = "Japan";
        CopyOfPhraseFilter japanFilter = new CopyOfPhraseFilter("end", japanSuffix);
        ArrayList<QuakeEntry> resultQuakes = filter(distanceFilteredQuakes, japanFilter);
    
        for (QuakeEntry quake : resultQuakes) {
            System.out.println(quake);
        }
    
        System.out.println("Number of earthquakes found: " + resultQuakes.size());
    }

    public void testMatchAllFilter() {
        EarthQuakeParser parser = new EarthQuakeParser();
        String source = "data/nov20quakedatasmall.atom";
        ArrayList<QuakeEntry> quakeData = parser.read(source);
    
        // Print all earthquakes and count
        System.out.println("All earthquakes:");
        for (QuakeEntry quake : quakeData) {
            System.out.println(quake);
        }
        System.out.println("Total number of earthquakes: " + quakeData.size());
    
        // Comment out the printing of all earthquakes, but continue to print the total count
        // System.out.println("Total number of earthquakes: " + quakeData.size());
    
        // Create a MatchAllFilter and add three filters
        CopyOfMatchAllFilter maf = new CopyOfMatchAllFilter();
        maf.addFilter(new CopyOfMagnitudeFilter(0.0, 2.0));
        maf.addFilter(new CopyOfDepthFilter(-100000.0, -10000.0));
        maf.addFilter(new CopyOfPhraseFilter("any", "a"));
    
        // Use filter(list, maf) to apply all three filters
        ArrayList<QuakeEntry> filteredQuakes = filter(quakeData, maf);
    
        // Print the resulting list of earthquakes
        System.out.println("Filtered earthquakes:");
        for (QuakeEntry quake : filteredQuakes) {
            System.out.println(quake);
        }
        System.out.println("Number of earthquakes meeting the criteria: " + filteredQuakes.size());
    }
    
    public void testMatchAllFilter2() {
        EarthQuakeParser parser = new EarthQuakeParser();
        String source = "data/nov20quakedatasmall.atom";
        ArrayList<QuakeEntry> quakeData = parser.read(source);
    
        // Print all earthquakes and count
        System.out.println("All earthquakes:");
        for (QuakeEntry quake : quakeData) {
            System.out.println(quake);
        }
        System.out.println("Total number of earthquakes: " + quakeData.size());
    
        // Create a MatchAllFilter and add three different filters
        CopyOfMatchAllFilter maf = new CopyOfMatchAllFilter();
        maf.addFilter(new CopyOfMagnitudeFilter(0.0, 3.0));
    
        // Location of Tulsa, Oklahoma (36.1314, -95.9372)
        Location tulsaLocation = new Location(36.1314, -95.9372);
        maf.addFilter(new CopyOfDistanceFilter(tulsaLocation, 10000000.0)); // 10000 km
    
        maf.addFilter(new CopyOfPhraseFilter("any", "Ca"));
    
        // Use filter(list, maf) to apply all three filters
        ArrayList<QuakeEntry> filteredQuakes = filter(quakeData, maf);
    
        // Print the resulting list of earthquakes
        System.out.println("Filtered earthquakes:");
        for (QuakeEntry quake : filteredQuakes) {
            System.out.println(quake);
        }
        System.out.println("Number of earthquakes meeting the criteria: " + filteredQuakes.size());
    }
    
    
    public void testMatchAllFilterV2() {
        EarthQuakeParser parser = new EarthQuakeParser();
        String source = "data/nov20quakedatasmall.atom";
        ArrayList<QuakeEntry> quakeData = parser.read(source);
    
        // Print all earthquakes and count
        System.out.println("All earthquakes:");
        for (QuakeEntry quake : quakeData) {
            System.out.println(quake);
        }
        System.out.println("Total number of earthquakes: " + quakeData.size());
    
        // Create a MatchAllFilter and add three filters
        MatchAllFilter maf = new MatchAllFilter("MyFilter");
        maf.addFilter(new MagnitudeFilter(0.0, 2.0, "Magnitude"));
        maf.addFilter(new DepthFilter(-100000.0, -10000.0, "Depth"));
        maf.addFilter(new PhraseFilter("any", "a", "Phrase"));
    
        // Use filter(list, maf) to apply all three filters
        ArrayList<QuakeEntry> filteredQuakes = filter(quakeData, maf);
    
        // Print the resulting list of earthquakes
        System.out.println("Filtered earthquakes:");
        for (QuakeEntry quake : filteredQuakes) {
            System.out.println(quake);
        }
        System.out.println("Number of earthquakes meeting the criteria: " + filteredQuakes.size());
    
        // Print the names of all filters used in the MatchAllFilter
        System.out.println("Filters used are: " + maf.getName());
    }


}
