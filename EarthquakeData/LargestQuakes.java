import java.util.ArrayList;

public class LargestQuakes {

    public void findLargestQuakes() {
        // Read earthquake data from a source (e.g., file nov20quakedatasmall.atom)
        EarthQuakeParser parser = new EarthQuakeParser();
        String source = "data/nov20quakedata.atom";
        ArrayList<QuakeEntry> quakeData = parser.read(source);

        // Print all earthquakes and the total number
        System.out.println("All earthquakes:");
        for (QuakeEntry quake : quakeData) {
            System.out.println(quake);
        }
        System.out.println("Total number of earthquakes: " + quakeData.size());

        // Find and print the index of the largest magnitude earthquake
        int indexOfLargest = indexOfLargest(quakeData);
        System.out.println("Index of the largest magnitude earthquake: " + indexOfLargest);
        System.out.println("Largest magnitude earthquake: " + quakeData.get(indexOfLargest));

        // Print the top 5 largest magnitude earthquakes
        System.out.println("\nTop 5 largest magnitude earthquakes:");
        ArrayList<QuakeEntry> largestQuakes = getLargest(quakeData, 5);
        for (QuakeEntry quake : largestQuakes) {
            System.out.println(quake);
        }
    }

    private int indexOfLargest(ArrayList<QuakeEntry> data) {
        int indexOfLargest = 0;
        for (int i = 1; i < data.size(); i++) {
            if (data.get(i).getMagnitude() > data.get(indexOfLargest).getMagnitude()) {
                indexOfLargest = i;
            }
        }
        return indexOfLargest;
    }

    private ArrayList<QuakeEntry> getLargest(ArrayList<QuakeEntry> quakeData, int howMany) {
        ArrayList<QuakeEntry> largestQuakes = new ArrayList<>();
        int numToReturn = Math.min(howMany, quakeData.size());

        for (int i = 0; i < numToReturn; i++) {
            int indexOfLargest = indexOfLargest(quakeData);
            largestQuakes.add(quakeData.remove(indexOfLargest));
        }

        return largestQuakes;
    }

    public static void main(String[] args) {
        LargestQuakes largestQuakes = new LargestQuakes();
        largestQuakes.findLargestQuakes();
    }
}

