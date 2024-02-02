import java.util.*;

/**
 * 在这里给出对类 EarthQuakeClient 的描述。
 * 
 * @作者（你的名字）
 * @版本（一个版本号或者一个日期）
 */
public class EarthQuakeClient {
    public void createCSV(){
        EarthQuakeParser parser = new EarthQuakeParser();
        String source = "/data/nov20quakedata.atom";
        ArrayList<QuakeEntry> list = parser.read(source);
        dumpCSV(list);
        System.out.println("#quakes read:"+list.size());
    }
    
    public void bigQuakes() {
        EarthQuakeParser parser = new EarthQuakeParser();
        // Replace with the path to your earthquake data file
        String source = "data/nov20quakedatasmall.atom";
        ArrayList<QuakeEntry> quakeData = parser.read(source);
        double magMin = 5.0;
        ArrayList<QuakeEntry> bigQuakes = filterByMagnitude(quakeData, magMin);

        System.out.println("Print earthquakes with magnitude higher than " + magMin);
        for (QuakeEntry qe : bigQuakes) {
            System.out.println(qe);
        }
        System.out.println("Number of earthquakes with magnitude higher than " + magMin + ": " + bigQuakes.size());
    }
    
    public void filterByMagnitude() {
        EarthQuakeParser parser = new EarthQuakeParser();
        // Replace with the path to your earthquake data file
        String source = "data/nov20quakedatasmall.atom";
        ArrayList<QuakeEntry> quakeData = parser.read(source);
        double magMin = 4.0;
        ArrayList<QuakeEntry> filteredData = filterByMagnitude(quakeData, magMin);

        System.out.println("Print earthquakes with magnitude higher than " + magMin);
        for (QuakeEntry qe : filteredData) {
            System.out.println(qe);
        }
        System.out.println("Found " + filteredData.size() + " quakes that match criteria.");
    }

    public ArrayList<QuakeEntry> filterByMagnitude(ArrayList<QuakeEntry> quakeData, double magMin) {
        ArrayList<QuakeEntry> answer = new ArrayList<>();
        for (QuakeEntry quake : quakeData) {
            if (quake.getMagnitude() > magMin) {
                answer.add(quake);
            }
        }
        return answer;
    }
    
    public ArrayList<QuakeEntry> filterByDistanceFrom(ArrayList<QuakeEntry> quakeData, double distMax, Location from) {
        ArrayList<QuakeEntry> answer = new ArrayList<>();
        for (QuakeEntry quake : quakeData) {
            Location quakeLocation = quake.getLocation();
            double distance = from.distanceTo(quakeLocation);
            if (distance < distMax) {
                answer.add(quake);
            }
        }
        return answer;
    }
    
    public void closeToMe() {
        EarthQuakeParser parser = new EarthQuakeParser();
        String source = "https://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_week.atom";
        ArrayList<QuakeEntry> list = parser.read(source);

        double distMax = 1000000.0; // Replace with your desired distance
        Location durhamLocation = new Location(35.988, -78.907);
        Location bridgeportLocation = new Location(38.17, -118.82);

        System.out.println("Print earthquakes within " + distMax + " meters from Durham, NC");
        ArrayList<QuakeEntry> closeQuakesDurham = filterByDistanceFrom(list, distMax, durhamLocation);
        for (QuakeEntry qe : closeQuakesDurham) {
            double distance = durhamLocation.distanceTo(qe.getLocation());
            System.out.println(distance / 1000 + " km, " + qe);
        }
        System.out.println("Number of earthquakes within " + distMax + " meters from Durham, NC: " + closeQuakesDurham.size());

        System.out.println("\nPrint earthquakes within " + distMax + " meters from Bridgeport, CA");
        ArrayList<QuakeEntry> closeQuakesBridgeport = filterByDistanceFrom(list, distMax, bridgeportLocation);
        for (QuakeEntry qe : closeQuakesBridgeport) {
            double distance = bridgeportLocation.distanceTo(qe.getLocation());
            System.out.println(distance / 1000 + " km, " + qe);
        }
        System.out.println("Number of earthquakes within " + distMax + " meters from Bridgeport, CA: " + closeQuakesBridgeport.size());
    }
    
    
    public ArrayList<QuakeEntry> filterByDepth(ArrayList<QuakeEntry> quakeData, double minDepth, double maxDepth) {
        ArrayList<QuakeEntry> filteredQuakes = new ArrayList<>();
        for (QuakeEntry qe : quakeData) {
            double depth = qe.getDepth();
            if (depth > minDepth && depth < maxDepth) {
                filteredQuakes.add(qe);
            }
        }
        return filteredQuakes;
    }
    
    public void quakesOfDepth() {
        EarthQuakeParser parser = new EarthQuakeParser();
        String source = "data/nov20quakedata.atom";
        ArrayList<QuakeEntry> quakeData = parser.read(source);

        double minDepth = -10000.0;
        double maxDepth = -8000.0;

        ArrayList<QuakeEntry> filteredQuakes = filterByDepth(quakeData, minDepth, maxDepth);

        System.out.println("Quakes with depth between " + minDepth + " and " + maxDepth + ":");
        for (QuakeEntry qe : filteredQuakes) {
            System.out.println(qe);
        }

        System.out.println("Number of earthquakes found: " + filteredQuakes.size());
    }
    
    public ArrayList<QuakeEntry> filterByPhrase(ArrayList<QuakeEntry> quakeData, String where, String phrase) {
        ArrayList<QuakeEntry> filteredQuakes = new ArrayList<>();

        for (QuakeEntry qe : quakeData) {
            String title = qe.getInfo().toLowerCase(); // Convert to lowercase for case-insensitive comparison

            switch (where.toLowerCase()) {
                case "start":
                    if (title.startsWith(phrase.toLowerCase())) {
                        filteredQuakes.add(qe);
                    }
                    break;

                case "end":
                    if (title.endsWith(phrase.toLowerCase())) {
                        filteredQuakes.add(qe);
                    }
                    break;

                case "any":
                    if (title.contains(phrase.toLowerCase())) {
                        filteredQuakes.add(qe);
                    }
                    break;

                default:
                    // Handle invalid 'where' value
                    System.out.println("Invalid 'where' value. Use 'start', 'end', or 'any'.");
                    return new ArrayList<>(); // Return an empty list in case of an error
            }
        }

        return filteredQuakes;
    }
    
    public void quakesByPhrase() {
        EarthQuakeParser parser = new EarthQuakeParser();
        String source = "data/nov20quakedata.atom";
        ArrayList<QuakeEntry> quakeData = parser.read(source);

        // Define the phrase and where to search
        String phrase = "Explosion";
        String where = "start";

        // Use filterByPhrase to get the earthquakes based on the criteria
        ArrayList<QuakeEntry> filteredQuakes = filterByPhrase(quakeData, where, phrase);

        // Print the earthquakes and the number of earthquakes found
        System.out.println("Quakes with phrase \"" + phrase + "\" at the " + where + " of the title:");
        for (QuakeEntry qe : filteredQuakes) {
            System.out.println(qe);
        }
        System.out.println("Number of earthquakes found: " + filteredQuakes.size());
    }
    
    private void dumpCSV(ArrayList<QuakeEntry> list) {
        System.out.println("Latitude,Longitude,Magnitude,Title,Depth");
        for (QuakeEntry quake : list) {
            System.out.printf("%f,%f,%f,%s,%f\n",
                    quake.getLocation().getLatitude(),
                    quake.getLocation().getLongitude(),
                    quake.getMagnitude(),
                    quake.getInfo(),
                    quake.getDepth());
        }
    }
    
    
    
}
