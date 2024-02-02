import java.util.Comparator;

/**
 * 在这里给出对类 TitleAndDepthComparator 的描述。
 * 
 * @作者（你的名字）
 * @版本（一个版本号或者一个日期）
 */
public class TitleAndDepthComparator implements Comparator<QuakeEntry> {
    public int compare(QuakeEntry q1,QuakeEntry q2){
        if(q1.getInfo().compareTo(q2.getInfo())==0){
            return Double.compare(q1.getDepth(),q2.getDepth());
        }
        return q1.getInfo().compareTo(q2.getInfo());
    }
}
