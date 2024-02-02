import edu.duke.*;
import org.apache.commons.csv.*;
import java.io.*;
/**
 * 在这里给出对类 FirstCSVExample 的描述。
 * 
 * @作者（你的名字）
 * @版本（一个版本号或者一个日期）
 */
public class FirstCSVExample {
    public void readFood(){
        FileResource fr = new FileResource();
        CSVParser parser = fr.getCSVParser();
        for(CSVRecord record:parser){
            System.out.print(record.get("Name")+"");
            System.out.println(record.get("Favorite food"));
        }
    }
    
    public void listExporters(CSVParser parser, String exportOfInterest){
        for(CSVRecord record: parser){
            String export = record.get("Exports");
            if(export.contains(exportOfInterest)){
                String country = record.get("Country");
                System.out.println(country);
            }
        }
    }
    
    
    void countryInfo(CSVParser parser, String countryOfInterest){
        for(CSVRecord record: parser){
            String countries = record.get("Country");
            String exports = record.get("Exports");
            String values = record.get("Value (dollars)");
            if(countries.contains(countryOfInterest))
            {
                System.out.print(countries+":");
                System.out.print(exports+":");
                System.out.println(values);
            }
        }
    }
    
    void testcountryInfo(){
        FileResource file=new FileResource();
        CSVParser parser = file.getCSVParser();
        countryInfo(parser,"Nauru");
    }
    
    
    void listExportersTwoProducts(CSVParser parser,String exportItem1,String exportItem2){
        for(CSVRecord record: parser){
            String exports = record.get("Exports");
            if(exports.contains(exportItem1)&&exports.contains(exportItem2)){
                String country = record.get("Country");
                System.out.println(country);
            }
        }    
    }
    
    void testTwoProducts(){
        FileResource file = new FileResource();
        CSVParser parser = file.getCSVParser();
        listExportersTwoProducts(parser,"cotton","flowers");
    }
    
    int numberOfExporters(CSVParser parser,String exportItem){
        int count = 0;
        for(CSVRecord row: parser){
            String export = row.get("Exports");
            if(export.contains(exportItem)){
                count=count+1;
            }
        }
        return count;
    }
    
    void testNumber(){
        FileResource file = new FileResource();
        CSVParser parser = file.getCSVParser();
        System.out.println(numberOfExporters(parser,"cocoa"));
    }
    
    void bigExporters(CSVParser parser,String valueCompare){
        for(CSVRecord row: parser){
            String value = row.get("Value (dollars)");
            String country = row.get("Country");
            if(value.length()>valueCompare.length()){
                System.out.print(country+"");
                System.out.println(value);
            }
        }
    }
    
    void testbigExporters(){
        FileResource file = new FileResource();
        CSVParser parser = file.getCSVParser();
        bigExporters(parser,"$999,999,999,999");
    }
    
    public void whoExportsCoffee(){
        FileResource fr = new FileResource();
        CSVParser parser = fr.getCSVParser();
        listExporters(parser,"coffee");
    }
    
    CSVRecord hottestHourInFile(CSVParser parser){
        CSVRecord largestSoFar = null;
        for(CSVRecord currentRow : parser){
            largestSoFar=getLargestOfTwo(currentRow,largestSoFar);
            // if(largestSoFar == null){
                // largestSoFar = currentRow;
            // }
            // else{
                // double currentTemp =Double.parseDouble
                        // (currentRow.get("TemperatureF"));
                // double largestTemp = Double.parseDouble(
                          // largestSoFar.get("TemperatureF"));
                // if(currentTemp>largestTemp){
                    // largestSoFar = currentRow;
                // }
            // }
        }
        return largestSoFar;
    }
    
    void testhottestHourInFile(){
        FileResource fr = new FileResource();
        CSVRecord largest = hottestHourInFile(fr.getCSVParser());
        System.out.println("The hottest temperature was"+
                            largest.get("TemperatureF")+
                            " at "+largest.get("TimeEST"));
    }
    
    CSVRecord hottestInManyDays(){
        CSVRecord largestT = null;
        DirectoryResource dr = new DirectoryResource();
        for(File f:dr.selectedFiles()){
            FileResource fr=new FileResource(f);
            CSVRecord currentFile = hottestHourInFile(fr.getCSVParser());
            if(largestT==null){
                largestT = currentFile;
            }else{
                double currentTemp =Double.parseDouble
                        (currentFile.get("TemperatureF"));
                double largestTemp = Double.parseDouble(
                          largestT.get("TemperatureF"));
                if(currentTemp>largestTemp){
                    largestT= currentFile;
                }
            }
        }
        return largestT;
    }
    
    void testhottestInManyDays(){
        CSVRecord largest = hottestInManyDays(); 
        System.out.println("The hottest temperature was"+
                            largest.get("TemperatureF")+
                            " at "+largest.get("DateUTC"));
    }
    
    public CSVRecord getLargestOfTwo(CSVRecord currentRow, CSVRecord largestSoFar){
        if(largestSoFar == null){
                largestSoFar = currentRow;
            }
            else{
                double currentTemp =Double.parseDouble
                        (currentRow.get("TemperatureF"));
                double largestTemp = Double.parseDouble(
                          largestSoFar.get("TemperatureF"));
                if(currentTemp>largestTemp){
                    largestSoFar = currentRow;
                }
        }
        return largestSoFar;
    }
    
    CSVRecord coldestHourInFile(CSVParser parser){
        CSVRecord lowestTempRow=null;
        for(CSVRecord currentRow:parser){
            if(lowestTempRow==null){
                lowestTempRow=currentRow;
            }else{
                double currentTemp=Double.parseDouble(currentRow.get("TemperatureF"));
                double lowestTemp =Double.parseDouble(lowestTempRow.get("TemperatureF"));
                if(currentTemp<lowestTemp){
                    lowestTempRow=currentRow;
                }
            }
        }
        return lowestTempRow;
    }
    
    void printDateNTemp(CSVParser parser){
        for(CSVRecord row:parser){
            System.out.println(row.get("DateUTC")+" "+row.get("TemperatureF"));
        }
    }
    
    void testcoldestHourInFile(){
        FileResource file = new FileResource();
        CSVParser parsedFile = file.getCSVParser();
        CSVRecord lowestTempRow =coldestHourInFile(parsedFile);
        String lowestTemp = lowestTempRow.get("TemperatureF");
        System.out.println("the lowest temperature is "+lowestTemp);
        
    }
    
    String fileWithColdestTemperature(){
        CSVRecord lowestTempRow=null;
        String filename="";
        DirectoryResource files = new DirectoryResource();
        for(File f:files.selectedFiles()){
            FileResource fr= new FileResource(f);
            CSVParser parsedFile = fr.getCSVParser();
            //get the coldest row in current file
            CSVRecord currentRow =coldestHourInFile(parsedFile);
            if(lowestTempRow==null){
                lowestTempRow=currentRow;
            }else{
                double currentTemp=Double.parseDouble(currentRow.get("TemperatureF"));
                double lowestTemp =Double.parseDouble(lowestTempRow.get("TemperatureF"));
                if(currentTemp<lowestTemp){
                    lowestTempRow=currentRow;
                    filename = f.getName();
                }
            }
        }
        return filename;
    }
    
    void testFileWithColdestTemperature(){
        String filename=fileWithColdestTemperature();
        System.out.println("coldest temp is in "+filename);
        //System.out.println(System.getProperty("user.dir"));
        String year = filename.substring(8,12);
        String dataDirectory = "/Users/skew/Downloads/nc_weather/"+year+"/";
        FileResource file = new FileResource(dataDirectory+filename);
        CSVRecord row = coldestHourInFile(file.getCSVParser());
        System.out.println(row.get("TemperatureF"));
        //printDateNTemp(file.getCSVParser());
    }
    
    CSVRecord lowestHumidityInFile(CSVParser parsedfile){
        CSVRecord lowestTempRow=null;
        for(CSVRecord currentRow:parsedfile){
            if(lowestTempRow==null){
                lowestTempRow=currentRow;
            }else{
                if(currentRow.get("Humidity").equals("N/A")){
                   continue; 
                }
                double currentTemp=Double.parseDouble(currentRow.get("Humidity"));
                double lowestTemp =Double.parseDouble(lowestTempRow.get("Humidity"));
                if(currentTemp<lowestTemp){
                    lowestTempRow=currentRow;
                }
            }
        }
        return lowestTempRow;
    }
    
    void testLowestHumidityInfile(){
        FileResource fr = new FileResource();
        CSVParser parser = fr.getCSVParser();
        CSVRecord csv = lowestHumidityInFile(parser);
        String date = csv.get("DateUTC");
        String humidity = csv.get("Humidity");
        System.out.println("Lowest Humidity was "+humidity+" at "+date);
    }
    
    CSVRecord lowestHumidityInManyFiles(){
        CSVRecord lowestTempRow=null;
        String filename="";
        DirectoryResource files = new DirectoryResource();
        for(File f:files.selectedFiles()){
            FileResource fr= new FileResource(f);
            CSVParser parsedFile = fr.getCSVParser();
            //get the coldest row in current file
            CSVRecord currentRow =coldestHourInFile(parsedFile);
            if(lowestTempRow==null){
                lowestTempRow=currentRow;
            }else{
                if(currentRow.get("Humidity").equals("N/A")) continue; 

                double currentTemp=Double.parseDouble(currentRow.get("Humidity"));
                double lowestTemp =Double.parseDouble(lowestTempRow.get("Humidity"));
                if(currentTemp<lowestTemp){
                    lowestTempRow=currentRow;
                }
            }
        }
        return lowestTempRow;
    }
    
    void testLowestHumidityInManyFiles(){
        CSVRecord row=lowestHumidityInManyFiles();
        String date = row.get("DateUTC");
        String date2 = row.get("TimeEST");
        String humidity = row.get("Humidity");
        System.out.println("Lowest Humidity was "+humidity+" at "+date+"/"+date2);
    }
    
    double averageTemperatureInFile(CSVParser parser){
        double totalTem=0;
        int count=0;
        for(CSVRecord row:parser){
            double currentTemp=Double.parseDouble(row.get("TemperatureF"));
            totalTem=totalTem+currentTemp;
            count=count+1;
        }
        return totalTem/count;
    }
    
    double averageTemperatureWithHighHumidityInFile(CSVParser parser,int value){
        double totalTem=0;
        int count=0;
        for(CSVRecord row:parser){
            double currentTemp=Double.parseDouble(row.get("TemperatureF"));
            int currentHumi=Integer.parseInt(row.get("Humidity"));
            if(currentHumi>=value){
                totalTem=totalTem+currentTemp;
                count=count+1;
            }
        }
        return totalTem/count;
    }
    
    void testaverageTemperatureInFile(){
        FileResource file = new FileResource();
        CSVParser parser = file.getCSVParser();
        double averageT = averageTemperatureInFile(parser);
        System.out.println(averageT);
    }
    
    void testaverageTemperatureWithHighHumidityInFile(){
        FileResource file = new FileResource();
        CSVParser parser = file.getCSVParser();
        double averageT = averageTemperatureWithHighHumidityInFile(parser,80);
        System.out.println(averageT);
    }
    
    
}
