import java.util.ArrayList;

/**
 * 在这里给出对类 QuakeSort 的描述。
 * 
 * @作者（你的名字）
 * @版本（一个版本号或者一个日期）
 */
public class QuakeSort {
    public int getSmallestMagnitude(ArrayList<QuakeEntry> quakes,int from){
        int minIdx = from;
        for(int i=from+1;i<quakes.size();i++){
            if(quakes.get(i).getMagnitude()<quakes.get(minIdx).getMagnitude()){
                minIdx = i;
            }
        }
        return minIdx;
    }
    
    public QuakeEntry getSmallestMagnitude(ArrayList<QuakeEntry> quakes){
        QuakeEntry min = quakes.get(0);
        for(QuakeEntry q:quakes){
            if(q.getMagnitude()<min.getMagnitude()){
                min = q;
            }
        }
        return min;
    }
    
    public int getLargestDepth(ArrayList<QuakeEntry> quakeData, int from){
        int midIdx = from;
        for(int i=from+1;i<quakeData.size();i++){
            if(quakeData.get(i).getDepth()>quakeData.get(midIdx).getDepth()){
                midIdx = i;
            }
        }
        return midIdx;
    }
    
    public void onePassBubbleSort(ArrayList<QuakeEntry> quakeData,int numSorted){
        for(int i=0;i<quakeData.size();i++){
            if(i+1==quakeData.size()) break;
            if(quakeData.get(i).getMagnitude()>quakeData.get(i+1).getMagnitude()){
                QuakeEntry current = quakeData.get(i);
                QuakeEntry next = quakeData.get(i+1);
                quakeData.set(i,next);
                quakeData.set(i+1,current);     
            }
        }
    }
    
    public void sortByMagnitudeWithBubbleSort(ArrayList<QuakeEntry> in){
        for(int k=0;k<in.size();k++){
            onePassBubbleSort(in,k);
            System.out.println("Printing quakes after pass "+(k+1));
            for(QuakeEntry qe:in){
                System.out.println(qe);
            }
        }
    }
    
    
    
    public void sortByLargestDepth(ArrayList<QuakeEntry> in){
        for(int k=0;k<in.size();k++){
            int maxIndex = getLargestDepth(in,k);
            QuakeEntry current = in.get(k);
            QuakeEntry max = in.get(maxIndex);
            in.set(k,max);
            in.set(maxIndex,current);
        }
    }
    
        
    public void sortByLargestDepth(ArrayList<QuakeEntry> in,int numOfSelection){
        for(int k=0;k<numOfSelection-1;k++){
            int maxIndex = getLargestDepth(in,k);
            QuakeEntry current = in.get(k);
            QuakeEntry max = in.get(maxIndex);
            in.set(k,max);
            in.set(maxIndex,current);
        }
    }
    
    public void sortByMagnitude(ArrayList<QuakeEntry> in){
        for(int i=0;i<in.size();i++){
            int minIdx = getSmallestMagnitude(in,i);
            QuakeEntry qi = in.get(i);
            QuakeEntry qmin = in.get(minIdx);
            in.set(i,qmin);
            in.set(minIdx,qi);
        }    
    }
    
    public void sortByMagnitudeWithCheck(ArrayList<QuakeEntry> in){
        for(int i=0;i<in.size();i++){
            int minIdx = getSmallestMagnitude(in,i);
            QuakeEntry qi = in.get(i);
            QuakeEntry qmin = in.get(minIdx);
            in.set(i,qmin);
            in.set(minIdx,qi);
            if(checkInSortedOrder(in)){
                System.out.println((i+1)+" passes were needed to sort.");
                break;
            }
        }    
    }
    
    public ArrayList<QuakeEntry> sortByMagnitude1(ArrayList<QuakeEntry> in){
        ArrayList<QuakeEntry> out = new ArrayList<QuakeEntry>();
        while(!in.isEmpty()){
            QuakeEntry minElement = getSmallestMagnitude(in);
            in.remove(minElement);
            out.add(minElement);
        }
        return out;
    }
    
    
    public boolean checkInSortedOrder(ArrayList<QuakeEntry> quakeData){
        for(int i=0;i<quakeData.size()-1;i++){
            if(quakeData.get(i).getMagnitude()>quakeData.get(i+1).getMagnitude()){
                return false;   
            }
        }
        return true;
    }
    
    public void sortByMagnitudeWithBubbleSortWithCheck(ArrayList<QuakeEntry> in){
        for(int k=0;k<in.size();k++){
            onePassBubbleSort(in,k);
            if(checkInSortedOrder(in)==true){
                break;
            }
            System.out.println("Printing quakes after pass "+(k+1));
            // for(QuakeEntry qe:in){
                // System.out.println(qe);
            // }
        }
    }
    
    public void testSort(){
        EarthQuakeParser parser = new EarthQuakeParser();
        String source = "data/nov20quakedata.atom";
        ArrayList<QuakeEntry> list = parser.read(source);
        sortByMagnitude(list);
        for(QuakeEntry qe:list){
            System.out.println(qe);
        }
    }
    
    public void testSort1(){
        EarthQuakeParser parser = new EarthQuakeParser();
        String source = "data/nov20quakedata.atom";
        ArrayList<QuakeEntry> list = parser.read(source);
        list = sortByMagnitude1(list);
        for(QuakeEntry qe:list){
            System.out.println(qe);
        } 
    }
    
    public void testSortByLargestDepth(){
        EarthQuakeParser parser = new EarthQuakeParser();
        String source = "data/earthQuakeDataDec6sample2.atom";
        ArrayList<QuakeEntry> list = parser.read(source);
        sortByLargestDepth(list,50);
        for(QuakeEntry qe:list){
            System.out.println(qe);
        }   
    }
    
    public void testsortByMagnitudeWithBubbleSort(){
        EarthQuakeParser parser = new EarthQuakeParser();
        String source = "data/earthquakeDataSampleSix2.atom";
        ArrayList<QuakeEntry> list = parser.read(source);
        sortByMagnitudeWithBubbleSort(list);
        System.out.println("Earthquakes in sorted order:");
        for(QuakeEntry qe:list){
            System.out.println(qe);
        }  
    }
    
    public void testsortByMagnitudeWithBubbleSortWithCheck(){
        EarthQuakeParser parser = new EarthQuakeParser();
        String source = "data/earthQuakeDataWeekDec6sample2.atom";
        ArrayList<QuakeEntry> list = parser.read(source);
        sortByMagnitudeWithBubbleSortWithCheck(list);
        // System.out.println("Earthquakes in sorted order:");
        // for(QuakeEntry qe:list){
            // System.out.println(qe);
        // }  
    }
    
    public void testsortByMagnitudeWithCheck(){
        EarthQuakeParser parser = new EarthQuakeParser();
        String source = "data/earthQuakeDataWeekDec6sample2.atom";
        ArrayList<QuakeEntry> list = parser.read(source);
        sortByMagnitudeWithCheck(list);
        //System.out.println("Earthquakes in sorted order:");
        // for(QuakeEntry qe:list){
            // System.out.println(qe);
        // }  
    }
    
    
    
}
