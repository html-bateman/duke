import java.util.ArrayList;
import java.util.*;
/**
 * 在这里给出对类 DistanceSorter 的描述。
 * 
 * @作者（你的名字）
 * @版本（一个版本号或者一个日期）
 */
public class DistanceSorter {
    public void sortByDistance(){
        EarthQuakeParser parser = new EarthQuakeParser();
        String source = "data/nov20quakedata.atom";
        ArrayList<QuakeEntry> list = parser.read(source);
        Location where = new Location(35.9886,-78.9072);
        Collections.sort(list,new DistanceComparator(where));
        for(QuakeEntry qe:list){
            System.out.println(qe);
        }
    }
}
