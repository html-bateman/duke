
/**
 * 在这里给出对类 MinutesFilter 的描述。
 * 
 * @作者（你的名字）
 * @版本（一个版本号或者一个日期）
 */
public class MinutesFilter implements Filter {
    private int myminMinutes;
    private int mymaxMinutes;
    public MinutesFilter(int minMinutes,int maxMinutes){
        myminMinutes = minMinutes;
        mymaxMinutes = maxMinutes;
    }
    @Override
    public boolean satisfies(String id){
        return MovieDatabase.getMinutes(id)<=mymaxMinutes&&
                MovieDatabase.getMinutes(id)>=myminMinutes;
    }
}
