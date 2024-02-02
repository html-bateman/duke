import java.util.Comparator;

/**
 * 在这里给出对类 TitleLastAndMagnitudeComparator 的描述。
 * 
 * @作者（你的名字）
 * @版本（一个版本号或者一个日期）
 */
public class TitleLastAndMagnitudeComparator implements Comparator<QuakeEntry>{
    public int compare(QuakeEntry q1,QuakeEntry q2){
        String[] title1 = q1.getInfo().trim().split("\\s+");
        String last1 = title1[title1.length-1];
        String[] title2 = q2.getInfo().trim().split("\\s+");
        String last2 = title2[title2.length-1];
        if(last1.compareTo(last2)!=0){
            return last1.compareTo(last2);
        }else{
            return Double.compare(q1.getMagnitude(),q2.getMagnitude());
        }
    }
}
