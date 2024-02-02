import java.util.Date;
import java.util.HashMap;

/**
 * 在这里给出对类 Test 的描述。
 * 
 * @作者（你的名字）
 * @版本（一个版本号或者一个日期）
 */
public class Test {
    public void testLogEntry(){
        LogEntry le = new LogEntry("1.2.3.4", new Date(),
            "example request",200,500);
        System.out.println(le);
        LogEntry le2 = new LogEntry("1.2.100.4",new Date(),
            "example request 2",300,400);
        System.out.println(le2);
    }
    
    public void testLogAnalyzer(){
        LogAnalyzer analyzer = new LogAnalyzer();
        analyzer.readFile("short-test_log");
        analyzer.printAll();  
    }
    
    public void testUniqIP(){
        LogAnalyzer la = new LogAnalyzer();
        la.readFile("weblog2_log.txt");
        int uniqueIPs = la.countUniqueIPs();
        System.out.println("There are "+uniqueIPs);
    }
    
    public void testprintAllHigherThanNum(){
        LogAnalyzer analyzer = new LogAnalyzer();
        analyzer.readFile("weblog1_log.txt");
        analyzer.printAllHigherThanNum(400);
    }
    
    public void testuniqueIPVisitsOnDay(){
        LogAnalyzer analyzer = new LogAnalyzer();
        analyzer.readFile("weblog2_log.txt");
        System.out.println(analyzer.uniqueIPVisitsOnDay("Sep 24").size());
    }
    
    public void testcountUniqueIPsInRange(){
        LogAnalyzer analyzer = new LogAnalyzer();
        analyzer.readFile("weblog2_log.txt");
        System.out.println(analyzer.countUniqueIPsInRange(200,299));
    }
    
    public void testcountVisitsPerIP(){
        LogAnalyzer analyzer = new LogAnalyzer();
        analyzer.readFile("weblog1_log.txt");
        HashMap<String,Integer> counts = analyzer.countVisitsPerIP();
        System.out.println(counts);
    }
    
    public void testiPsMostVisits(){
        LogAnalyzer analyzer = new LogAnalyzer();
        analyzer.readFile("weblog2_log.txt");
        analyzer.iPsMostVisits(analyzer.countVisitsPerIP());
    }
    
    public void testmostNumberVisitsByIP(){
        LogAnalyzer analyzer = new LogAnalyzer();
        analyzer.readFile("weblog2_log.txt");
        analyzer.mostNumberVisitsByIP(analyzer.countVisitsPerIP());
    }
    
    public void testdayWithMostIPVisits(){
        LogAnalyzer analyzer = new LogAnalyzer();
        analyzer.readFile("weblog2_log.txt");
        analyzer.dayWithMostIPVisits(analyzer.iPsForDays());
    }
    
    public void testiPsWithMostVisitsOnDay(){
        LogAnalyzer analyzer = new LogAnalyzer();
        analyzer.readFile("weblog2_log.txt");
        System.out.println(analyzer.iPsWithMostVisitsOnDay(analyzer.iPsForDays(),"Sep 29"));
    }
}
