import edu.duke.*;
/**
 * 在这里给出对类 P4 的描述。
 * 
 * @作者（你的名字）
 * @版本（一个版本号或者一个日期）
 */
public class P4 {
    String findYoutube(){
        String url="https://www.dukelearntoprogram.com/course2/data/manylinks.html";
        URLResource fr = new URLResource(url);
        String totalyoutube="";
        int num=0;
        for(String currword : fr.words()){ 
            int startIndex=currword.indexOf("\"");
            if(startIndex!=-1){
                int endIndex=currword.indexOf("\"",startIndex+1);
                if(endIndex!=-1){
                    String curryoutube=currword.substring(startIndex,endIndex+1);
                    int indexofYou=curryoutube.indexOf(".com/watch?v=");
                    if(indexofYou!=-1){
                        num+=1;
                        totalyoutube=totalyoutube+" "+num+":"+curryoutube+"\n";
                    }   
                }
            }  
        }
        return totalyoutube;
    }
    
    void test(){
        System.out.println(findYoutube());
    }
}
