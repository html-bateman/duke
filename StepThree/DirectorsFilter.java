import java.util.ArrayList;

/**
 * 在这里给出对类 DirectorsFilter 的描述。
 * 
 * @作者（你的名字）
 * @版本（一个版本号或者一个日期）
 */
public class DirectorsFilter implements Filter{
    private String myDirectors;
    public DirectorsFilter(String directors) {
      myDirectors = directors;  
    }

    @Override
    public boolean satisfies(String id){
        String[] ds = MovieDatabase.getDirector(id).split(",");
        for(String director:ds){
            if(myDirectors.contains(director)) return true;
        }
        return false;
    }
}
