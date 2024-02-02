import edu.duke.*;
import java.util.*;
import org.apache.commons.csv.*;
/**
 * 在这里给出对类 FristRatings 的描述。
 * 
 * @作者（你的名字）
 * @版本（一个版本号或者一个日期）
 */
public class FirstRatings {
    private String movieDataFile;
    private String raterDataFile;
    private ArrayList<Rater> raterList;
    
    public FirstRatings(){
        movieDataFile = "data/ratedmoviesfull.csv";
        raterDataFile = "data/ratings.csv";
        // movieDataFile = "data/ratedmovies_short.csv";
        // raterDataFile = "data/ratings_short.csv";
        raterList = loadRaters(raterDataFile);
    }
    
    
    public ArrayList<Movie> loadMovies(String filename){
        FileResource fr = new FileResource(filename);
        CSVParser parser = fr.getCSVParser();
        ArrayList<Movie> movieList = new ArrayList<>();
        for(CSVRecord record:parser){
            movieList.add(new Movie(record.get("id"),
            record.get("title"),
            record.get("year"),
            record.get("genre"),
            record.get("director"),
            record.get("country"),
            record.get("poster"),
            Integer.parseInt(record.get("minutes"))
            ));
        }
        return movieList;
    }
    
    public ArrayList<Rater> loadRaters(String filename){
        FileResource fr = new FileResource(filename);
        CSVParser parser = fr.getCSVParser();
        ArrayList<Rater> raterList = new ArrayList<>();
        boolean exsitedId = false;
        for(CSVRecord  record:parser){
            if(raterList.size()==0){
                Rater firstRater = new Rater(record.get("rater_id"));  
                firstRater.addRating(record.get("movie_id"),
                    Double.parseDouble(record.get("rating")));
                raterList.add(firstRater);
            }else{
                for(Rater r: raterList){
                    //System.out.print("id in the csv:"+record.get("rater_id"));
                    //System.out.print(" id in the list:"+r.getID());
                    int idInCSV = Integer.parseInt(record.get("rater_id"));
                    int idInList = Integer.parseInt(r.getID());
                    if(idInCSV==idInList) exsitedId= true;
                    //System.out.println(" "+exsitedId);
                }
                if(exsitedId==false){
                    Rater rater = new Rater(record.get("rater_id"));
                    raterList.add(rater);
                }
                int index = Integer.parseInt(record.get("rater_id"))-1;
                raterList.get(index).addRating(record.get("movie_id"),
                    Double.parseDouble(record.get("rating")));
            }
            exsitedId=false;
        }
        return raterList;
    }   
    
    public void testLoadMovies(){
        ArrayList<Movie> movieList = new ArrayList<>();
        movieList = loadMovies(movieDataFile);
        System.out.println("there are "+movieList.size()+" movies in the file");
        int comedyCount = 0;
        int greaterThan150m = 0;
        HashMap<String,Integer> directorMovies = new HashMap<>();
        for(Movie movie:movieList){
            //System.out.println(movie);
            if(movie.getGenres().contains("Comedy")) comedyCount+=1;
            if(movie.getMinutes()>150) greaterThan150m +=1;
            if(!directorMovies.containsKey(movie.getDirector())){
                directorMovies.put(movie.getDirector(),1);
            }else{
                directorMovies.put(movie.getDirector(),directorMovies.get(movie.getDirector())+1);
            }
        }
        System.out.println("there are "+comedyCount+" comedy movies.");
        System.out.println("there are "+greaterThan150m+" run longer than 150m.");
        String directorWithMostMovies = findKeyWithMaxValue(directorMovies);
        System.out.println("the director with maximum movies is "+directorWithMostMovies + ". With "+
            directorMovies.get(directorWithMostMovies)+" movies");
    }
    
    private static String findKeyWithMaxValue(Map<String,Integer> map){
        String maxKey = "";
        int maxValue = 0;
        for(Map.Entry<String,Integer> entry : map.entrySet()){
            String currentKey = entry.getKey();
            int currentValue = entry.getValue();
            if(currentValue>maxValue){
                maxValue = currentValue;
                maxKey = currentKey;
            }
        }
        return maxKey;
    
    }
    
    public void testLoadRaters(){
        ArrayList<Rater> raterList = new ArrayList<>();
        raterList = loadRaters(raterDataFile);
        int numberOfRaters  = raterList.size();
        System.out.println("Total number of raters:"+numberOfRaters);
        for(Rater r:raterList){
            String id = r.getID();
            int num = r.numRatings();
            System.out.println("the rater:"+id+" has "+num+" ratings");
            ArrayList<String> ratingList = new ArrayList<>();
            ratingList = r.getItemsRated();
            for(String item:ratingList){
                double rating = r.getRating(item);
                System.out.println("MovieId:"+item+" Rating:"+rating);
            }  
        }
        
        //get the number of rating by rater id
        int raterId = 193;
        System.out.println("The rater:"+raterId+" has "+
            findNumOfRating(raterId)+" ratings");
        
        //determine who have the max number of ratings
        ArrayList<Rater> maxNumRatingRaterList = new ArrayList<>();
        maxNumRatingRaterList = findMaxNumberRatingOfRater();
        System.out.println("there are "+maxNumRatingRaterList.size()
            +" raters have maximum number of ratings.");
        int num = maxNumRatingRaterList.get(0).numRatings();
        System.out.println("the max number is "+num);
        System.out.println("these raters are:");
        for(Rater r:maxNumRatingRaterList){ 
            System.out.print(r.getID()+" ");
        }
        System.out.println();
        
        //get the number of ratings a particular movie has
        String movieId = "1798709";
        int numOfRating = findNumOfRatingsByMovieId(movieId);
        System.out.println("movieId:"+movieId+" was rated by "+numOfRating+" raters.");
    
        //determine how many different movies has been rated
        int numOfRatedMovies = findNumOfMoviesRated();
        System.out.println("there were "+numOfRatedMovies+" movies rated.");
    }
    
    public int findNumOfRating(int raterId){
        ArrayList<Rater> raterList = new ArrayList<>();
        raterList = loadRaters(raterDataFile);
        int num=0;
        for(Rater r : raterList){
            if(Integer.parseInt(r.getID())==raterId){
                num = r.numRatings();
            }
        }
        return num;
    }
    
    public ArrayList<Rater> findMaxNumberRatingOfRater(){
        ArrayList<Rater> raterList = new ArrayList<>();
        raterList = loadRaters(raterDataFile);
        int maxNumRatings = 0;
        ArrayList<Rater> maxNumRatingRaterList = new ArrayList<>();
        for(Rater r: raterList){
            if(r.numRatings()>maxNumRatings){
                maxNumRatings = r.numRatings();
            }
        } 
        for(Rater r: raterList){
            if(r.numRatings()==maxNumRatings){
                maxNumRatingRaterList.add(r);
            }
        }
        return maxNumRatingRaterList;
    }
    
    public int findNumOfRatingsByMovieId(String movieId){
        Map<String,Integer> movieRatedByNum = new HashMap<>();
        for(Rater r:raterList){
            ArrayList<String> ratingList = new ArrayList<>();
            ratingList = r.getItemsRated();
            for(String item:ratingList){
                //double rating = r.getRating(item);
                //System.out.println("MovieId:"+item+" Rating:"+rating);
                if(!movieRatedByNum.containsKey(item)){
                    movieRatedByNum.put(item,1);
                }else{
                    movieRatedByNum.put(item,movieRatedByNum.get(item)+1);
                }
            }  
        }
        //System.out.println(movieRatedByNum);
        int numberOfRatings = 0;
        try{
            numberOfRatings = movieRatedByNum.get(movieId);
        }catch(NullPointerException exception){
            System.out.println("MovieId:["+movieId+"] does not exist.");
        }
        return numberOfRatings;
    }
    
    public HashMap<String,Integer> generateMovieToRatenumMap(){
        ArrayList<Rater> raterList = new ArrayList<>();
        raterList = loadRaters(raterDataFile);
        HashMap<String,Integer> movieRatedByNum = new HashMap<>();
        for(Rater r:raterList){
            ArrayList<String> ratingList = new ArrayList<>();
            ratingList = r.getItemsRated();
            for(String item:ratingList){
                //double rating = r.getRating(item);
                //System.out.println("MovieId:"+item+" Rating:"+rating);
                if(!movieRatedByNum.containsKey(item)){
                    movieRatedByNum.put(item,1);
                }else{
                    movieRatedByNum.put(item,movieRatedByNum.get(item)+1);
                }
            }  
        }
        return movieRatedByNum;
    }
    
    public int findNumOfMoviesRated(){
        ArrayList<Rater> raterList = new ArrayList<>();
        raterList = loadRaters(raterDataFile);
        Map<String,Integer> movieRatedByNum = new HashMap<>();
        for(Rater r:raterList){
            ArrayList<String> ratingList = new ArrayList<>();
            ratingList = r.getItemsRated();
            for(String item:ratingList){
                //double rating = r.getRating(item);
                //System.out.println("MovieId:"+item+" Rating:"+rating);
                if(!movieRatedByNum.containsKey(item)){
                    movieRatedByNum.put(item,1);
                }else{
                    movieRatedByNum.put(item,movieRatedByNum.get(item)+1);
                }
            }  
        }
        int numberOfMovies = movieRatedByNum.size();
        return numberOfMovies;
    }
    
    //generate movieId list from the raterList by movieId
    public ArrayList<String> generateMovieIdList(){
        ArrayList<Rater> raterList = new ArrayList<>();
        raterList = loadRaters(raterDataFile);
        ArrayList<String> movieIdList = new ArrayList<>();
        for(Rater r:raterList){
            ArrayList<String> ratingListOfRater = new ArrayList<>();
            ratingListOfRater = r.getItemsRated();
            for(String item:ratingListOfRater){
                //double rating = r.getRating(item);
                //System.out.println("MovieId:"+item+" Rating:"+rating);
                movieIdList.add(item);
            }
        }
        return movieIdList;
    }
    
    public ArrayList<Double> generateRatingList(){
        ArrayList<Rater> raterList = new ArrayList<>();
        raterList = loadRaters(raterDataFile);
        ArrayList<Double> ratingList = new ArrayList<>();
        for(Rater r:raterList){
            ArrayList<String> ratingListOfRater = new ArrayList<>();
            ratingListOfRater = r.getItemsRated();
            for(String item:ratingListOfRater){
                double rating = r.getRating(item);
                //System.out.println("MovieId:"+item+" Rating:"+rating);
                ratingList.add(rating);
            }
        }
        return ratingList;
    }
    
    
    
    
    
}
