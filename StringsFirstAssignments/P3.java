
/**
 * 在这里给出对类 P3 的描述。
 * 
 * @作者（你的名字）
 * @版本（一个版本号或者一个日期）
 */
public class P3 {
    Boolean twoOccurrences(String stringa, String stringb){
        int startIndex=stringb.indexOf(stringa);
        int endIndex=stringb.indexOf(stringa,startIndex+stringb.length());
        if(endIndex!=-1){
            return true;
        }
        return false;
    }
    
    void testing(){
        twoOccurrences("by","a storyby abby");
        twoOccurrences("by","asdfasdfsa");
        twoOccurrences("by","bybybybybyb");
        lastPart("an","banannana");
        lastPart("zoo","forest");
        
    }
    
    String lastPart(String stringa, String stringb){
        int startIndex=stringb.indexOf(stringa);
        if(startIndex==-1){
            return stringb;
        }
        return stringb.substring(startIndex+stringa.length(),stringb.length());
    }
}
