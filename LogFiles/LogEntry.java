import java.util.Date;

/**
 * 在这里给出对类 LogEntry 的描述。
 * 
 * @作者（你的名字）
 * @版本（一个版本号或者一个日期）
 */
public class LogEntry {
    private String ipAddress;
    private Date accessTime;
    private String request;
    private int statusCode;
    private int bytesReturned;
    
    public LogEntry(String ip,Date time,String req,int status,int bytes){
        ipAddress = ip;
        accessTime = time;
        request = req;
        statusCode = status;
        bytesReturned = bytes;
    }
    
    public String getIpAddress(){
        return ipAddress;
    }
    public Date getAccessTime(){
        return accessTime;
    }
    public String getRequest(){
        return request;
    }
    public int getStatusCode(){
        return statusCode;
    }
    public int getBytesRetured(){
        return bytesReturned;
    }
    
    public String toString(){
        return ipAddress+" "+accessTime+" "+request+" "+statusCode+" "+bytesReturned;
    }
    
    public String getLogInfo(){
        return ipAddress+" "+accessTime+" "+request+" "+statusCode+" "+bytesReturned;
    }
}
