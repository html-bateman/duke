import java.util.ArrayList;
import edu.duke.*;
import java.util.HashMap;
import java.util.Set;
import java.util.Map;

/**
 * 在这里给出对类 LogAnalyzer 的描述。
 * 
 * @作者（你的名字）
 * @版本（一个版本号或者一个日期）
 */
public class LogAnalyzer {
    private ArrayList<LogEntry> records;
    private WebLogParser parser;
    public LogAnalyzer(){
        records = new ArrayList<LogEntry>();
    }
    
    public void readFile(String filename){
        FileResource file = new FileResource(filename);
        for(String line:file.lines()){
            records.add(parser.parseEntry(line));
        }
    }
    
    public void printAll(){
        for(LogEntry le:records){
            System.out.println(le);
        }
    }
    
    public int countUniqueIPs(){
        ArrayList<String> uniqueIPs = new ArrayList<String>();
        for(LogEntry le:records){
            String ipAddr = le.getIpAddress();
            if(!uniqueIPs.contains(ipAddr)){
                uniqueIPs.add(ipAddr);
            }
        }
        return uniqueIPs.size();
    }
    
    public int countUniqueIPsMap(){
        HashMap<String,Integer> counts = countVisitsPerIP();
        return counts.size();
    }
    
    public void printAllHigherThanNum(int num){
        for(LogEntry le: records){
            if(le.getStatusCode()>num){
                System.out.println(le);
            }
        }
    }
    
    public ArrayList<String> uniqueIPVisitsOnDay(String someday){
        ArrayList<LogEntry> uniqueDay = new ArrayList<LogEntry>();
        for(LogEntry le:records){
            String date = le.getAccessTime().toString();
            int indexOfDate = date.indexOf(someday);
            if(indexOfDate!=-1){
                uniqueDay.add(le);
            }
        }
        ArrayList<String> uniqueIPsDay = new ArrayList<String>();
        for(LogEntry le:uniqueDay){
            String ipAddr = le.getIpAddress();
            if(!uniqueIPsDay.contains(ipAddr)){
                uniqueIPsDay.add(ipAddr);
            }
        }
        return uniqueIPsDay;
    }
    
    public int countUniqueIPsInRange(int low,int high){
        ArrayList<LogEntry> inRange = new ArrayList<LogEntry>();
        for(LogEntry le:records){
            int status = le.getStatusCode();
            if(status>=low&&status<=high){
                inRange.add(le);
            }
        }
        ArrayList<String> uniqueIPsRange = new ArrayList<String>();
        for(LogEntry le:inRange){
            String ipAddr = le.getIpAddress();
            if(!uniqueIPsRange.contains(ipAddr)){
                uniqueIPsRange.add(ipAddr);
            }
        }
        return uniqueIPsRange.size();
    }
    
    public HashMap<String,Integer> countVisitsPerIP(){
        HashMap<String,Integer> counts = new HashMap<>();
        for(LogEntry le:records){
            String ip = le.getIpAddress();
            if(!counts.containsKey(ip)){
                counts.put(ip,1);
            }else{
                counts.put(ip,counts.get(ip)+1);
            }
        }
        return counts;
    }
    
    public HashMap<String,Integer> countVisitsPerIPDay(String someday){
        HashMap<String,Integer> counts = new HashMap<>();
        for(LogEntry le:records){
            String date = le.getAccessTime().toString();
            if(date.indexOf(someday)!=-1){
                String ip = le.getIpAddress();
                if(!counts.containsKey(ip)){
                counts.put(ip,1);
                }else{
                    counts.put(ip,counts.get(ip)+1);
                }
            }
        }
        return counts;
    }
    
    public int mostNumberVisitsByIP(HashMap<String,Integer> counts){
        ArrayList<String> ipInMap = new ArrayList<>();
        ArrayList<Integer> countInMap = new ArrayList<>();
        for(String ip:counts.keySet()){
            ipInMap.add(ip);
            countInMap.add(counts.get(ip));
        }
        int max=0;
        int indexOfMostVisit = 0;
        for(int count : countInMap){
            if(count>max){
                max=count;
                indexOfMostVisit=countInMap.indexOf(max);
            }
        }
        System.out.println("ip:"+ipInMap.get(indexOfMostVisit)+"|Counts:"+max);
        
        return max;
    }
    
    public ArrayList<String> iPsMostVisits(HashMap<String,Integer> counts){
        ArrayList<String> ipInMap = new ArrayList<>();
        ArrayList<Integer> countInMap = new ArrayList<>();
        ArrayList<String> mostVistsIp = new ArrayList<>();
        for(String ip:counts.keySet()){
            ipInMap.add(ip);
            countInMap.add(counts.get(ip));
        }
        int max=0;
        int indexOfMostVisit = 0;
        for(int count : countInMap){
            if(count>=max){
                max=count;
                indexOfMostVisit=countInMap.indexOf(count);
                mostVistsIp.add(ipInMap.get(indexOfMostVisit));
            }
        }
        System.out.println("the most visits is "+max);
        System.out.println("this ip is "+mostVistsIp);
        return mostVistsIp;
    }
    
    public HashMap<String,ArrayList<String>> iPsForDays(){
        HashMap<String,ArrayList<String>> dayToIps = new HashMap<>();
        for(LogEntry entry: records){
            String date = entry.getAccessTime().toString();
            String dateLite = date.substring(4,10);
            if(!dayToIps.containsKey(date)){
                dayToIps.put(date,uniqueIPVisitsOnDay(dateLite));
            }
        }
        return dayToIps;
    }
    
    public String dayWithMostIPVisits(HashMap<String,ArrayList<String>> dayToIPs){
        ArrayList<String> dayInMap = new ArrayList<>();
        ArrayList<ArrayList<String>> ipsInMap = new ArrayList<>();
        ArrayList<String> mostVistsDay = new ArrayList<>();
        for(String day:dayToIPs.keySet()){
            dayInMap.add(day);
            ipsInMap.add(dayToIPs.get(day));
        }
        int max=0;
        int indexOfMostVisit = 0;
        for(ArrayList<String> ips : ipsInMap){
            if(ips.size()>=max){
                max=ips.size();
                indexOfMostVisit=ipsInMap.indexOf(ips);
                mostVistsDay.add(dayInMap.get(indexOfMostVisit));
            }
        }
        System.out.println("the most visits is "+max);
        System.out.println("this day is "+mostVistsDay.get(0));
        return mostVistsDay.get(0);
    }
    
    public ArrayList<String> iPsWithMostVisitsOnDay(HashMap<String,ArrayList<String>> dayToIPs,
        String someday){
 
        HashMap<String,Integer> ipToCounts = countVisitsPerIPDay(someday);
        return iPsMostVisits(ipToCounts);
    }
    
    
}
