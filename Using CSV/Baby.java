import edu.duke.*;
import org.apache.commons.csv.*;
import java.io.*;
/**
 * 在这里给出对类 Baby 的描述。
 * 
 * @作者（你的名字）
 * @版本（一个版本号或者一个日期）
 */
public class Baby {
    public void printNames(){
        FileResource fr = new FileResource();
        for(CSVRecord rec: fr.getCSVParser(false)){
            int numBorn = Integer.parseInt(rec.get(2));
            if(numBorn<=100){
            System.out.println("Name "+rec.get(0)
                               +"Gender "+rec.get(1)
                            +"Num Born " + rec.get(2)); 
            }
        }
    }
    
    
    public void totalBirths(FileResource fr){
        int totalBirth = 0;
        int totalBoys = 0;
        int totalGirls = 0;
        int countGirlName = 0;
        int countBoyName=0;
        for(CSVRecord rec:fr.getCSVParser(false)){
            int numBorn = Integer.parseInt(rec.get(2));
            totalBirth+=numBorn;
            if(rec.get(1).equals("M")){
                totalBoys+=numBorn;
                countBoyName+=1;
            }else{
                totalGirls+=numBorn;
                countGirlName+=1;
            }
        }
        System.out.println("total births= "+totalBirth);
        System.out.println("total boys= "+totalBoys);
        System.out.println("total girls= "+totalGirls);
        System.out.println("total boy names= "+countBoyName);
        System.out.println("total girl names= "+countGirlName);
    }
    
    void testtotalBirths(){
        FileResource file = new FileResource();
        totalBirths(file);
    }
    
    
    int getRank(int year,String name,String gender){
        String filename="/Users/skew/Downloads/us_babynames/us_babynames_by_year/yob"
                        +Integer.toString(year)+".csv";
        FileResource file = new FileResource(filename);
        //default rank if name is not found.
        int defaultrank=-1;
        int currentRank=1;
        for(CSVRecord row:file.getCSVParser(false)){
            String currentName=row.get(0);
            String currentGender=row.get(1);
            if(currentGender.equals(gender)){
                if(currentName.equals(name)){
                    defaultrank=currentRank;
                    break;
                }
                //only count rank when the gender is matched.
                currentRank+=1;
            }
            
        }
        return defaultrank;
    }
    
    int getRankSelect(int year,String name,String gender,FileResource file){
        FileResource currentfile = file;
        //default rank if name is not found.
        int defaultrank=-1;
        int currentRank=1;
        for(CSVRecord row:file.getCSVParser(false)){
            String currentName=row.get(0);
            String currentGender=row.get(1);
            if(currentGender.equals(gender)){
                if(currentName.equals(name)){
                    defaultrank=currentRank;
                    break;
                }
                //only count rank when the gender is matched.
                currentRank+=1;
            }
            
        }
        return defaultrank;
    }
    
    void testgetRank(){
        int rank = getRank(1971,"Frank","M");
        System.out.println(rank);
    }
    
    String getName(int year,int rank,String gender){
        String filename="/Users/skew/Downloads/us_babynames/us_babynames_by_year/yob"
                        +Integer.toString(year)+".csv";
        FileResource file = new FileResource(filename);
        //default output if name is not found.
        String name="No name";
        int currentRank=1;
        for(CSVRecord row:file.getCSVParser(false)){
            String currentName=row.get(0);
            String currentGender=row.get(1);
            if(currentGender.equals(gender)){
                if(currentRank==rank){
                    name=currentName;
                    break;
                }
                //only count rank when the gender is matched.
                currentRank+=1;
            }
            
        }
        return name;
    }
    
    void testgetName(){
        String name=getName(1982,450,"M");
        System.out.println(name);
    }
    
    void whatIsNameInYear(String name,int year,int newYear,String gender){
         // Get the rank of the name in the birth year
        int rank = getRank(year, name, gender);

        // Get the name at the same rank in the new year
        String newName = getName(newYear, rank, gender);

        // Print the result
        System.out.println(name + " born in " + year + " would be " + newName + " if born in " + newYear + ".");
    }
    
    void testwhatIsNameInYear(){
        whatIsNameInYear("Susan",1972,2014,"F");
        whatIsNameInYear("Owen",1974,2014,"M");
    }
    
    int yearOfHighestRank(String name,String gender){
        DirectoryResource files = new DirectoryResource();
        int highestRank=Integer.MAX_VALUE;
        int year=-1;
        for(File f:files.selectedFiles()){
            FileResource file = new FileResource(f);
            //CSVParser currentparsedfile = file.getCSVParser();
            int currentyear= Integer.parseInt(f.getName().substring(3,7));
            int currentrank=getRankSelect(year,name,gender,file);
            //rank 1 is smaller than rank 2
            //but that is a higher rank
            if(currentrank!=-1&&currentrank<highestRank){
                highestRank=currentrank;
                year = currentyear;
            }
        }
        return year;
    }
    
    void testyearOfHighestRank(){
        int year=yearOfHighestRank("Genevieve","F");
        System.out.println(year);
        int year2=yearOfHighestRank("Mich","M");
        System.out.println(year2);
    }
    
    double getAverageRank(String name,String gender){
        DirectoryResource files = new DirectoryResource();
        int highestRank=Integer.MAX_VALUE;
        double totalrank=0;
        int count=0;
        for(File f:files.selectedFiles()){
            int year= Integer.parseInt(f.getName().substring(3,7));
            int currentrank=getRank(year,name,gender); 
            if(currentrank!=-1&&currentrank<highestRank){
                highestRank=currentrank;
            }
            totalrank+=currentrank;
            count+=1;
        }
        if(highestRank==-1) return -1.0;
        return totalrank/count;
    }
    
    void testgetAverageRank(){
        double rank=getAverageRank("Susan","F");
        System.out.println(rank);
        double rank1=getAverageRank("Robert","M");
        System.out.println(rank1);
        
    }
    
    int getTotalBirthsRankedHigher(int year,String name,String gender){
        String filename="/Users/skew/Downloads/us_babynames/us_babynames_by_year/yob"
                        +Integer.toString(year)+".csv";
        FileResource file = new FileResource(filename);
        int totalBirths=0;
        int currentRank=1;
        for(CSVRecord row:file.getCSVParser(false)){
            String currentName=row.get(0);
            String currentGender=row.get(1);
            int currentBirths=Integer.parseInt(row.get(2));
            if(currentGender.equals(gender)){
                if(currentName.equals(name)){
                    //found the name, get out of the loop
                    break;
                }
                //only count rank when the gender is matched.
                totalBirths+=currentBirths;
            }
            
        }
        return totalBirths;
    }
    
    void testgetTotalBirthsRankedHigher(){
        int num = getTotalBirthsRankedHigher(1990,"Emily","F");
        System.out.println(num);
        int num1 = getTotalBirthsRankedHigher(1990,"Drew","M");
        System.out.println(num1);
    }
    
    
}
