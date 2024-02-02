import java.util.Comparator;

/**
 * 在这里给出对类 DistanceComparator 的描述。
 * 
 * @作者（你的名字）
 * @版本（一个版本号或者一个日期）
 */
public class DistanceComparator implements Comparator<QuakeEntry> {
    Location fromWhere;
    
    public DistanceComparator(Location where){
        fromWhere = where;
    }
    
    public int compare(QuakeEntry q1,QuakeEntry q2){
        double dist1 = q1.getLocation().distanceTo(fromWhere);
        double dist2 = q2.getLocation().distanceTo(fromWhere);
        return Double.compare(dist1,dist2);
    }
    
}
