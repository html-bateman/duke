import java.util.ArrayList;
import java.util.HashMap;
import java.util.*;

/**
 * 在这里给出对类 MovieRunnerAverage 的描述。
 * 
 * @作者（你的名字）
 * @版本（一个版本号或者一个日期）
 */
public class MovieRunnerAverage {
    public void printAverageRatings(){
        SecondRatings sr = new SecondRatings();
        int numOfMovies = sr.getMovieSize();
        System.out.println("number of movies:"+numOfMovies);
        int numOfRaters = sr.getRaterSize();
        System.out.println("number of raters:"+numOfRaters);
        
        ArrayList<Rating> moviesAverage = new ArrayList<>();
        moviesAverage = sr.getAverageRatings(12);
        
        //for(Rating r:moviesAverage) System.out.println(r.getItem()+" "+r.getValue());
        
        ArrayList<Double> rate = new ArrayList<>();
        HashMap<Double,String> rateToName = new HashMap<>();
        for(Rating rating:moviesAverage){
            rate.add(rating.getValue());
            rateToName.put(rating.getValue(),sr.getTitle(rating.getItem()));
        }
        //Collections.sort(rate,Collections.reverseOrder());
        Collections.sort(rate);
        for(Double r:rate){
            System.out.print(r+" ");
            System.out.println(rateToName.get(r));
        }
    }  
    
    public void getAverageRatingOneMovie(){
        SecondRatings sr = new SecondRatings();
        String movieTitle = "Vacation";
        String movieId = sr.getID(movieTitle);
        double rate = sr.getAverageByID(movieId,3);
        System.out.println("The average rating of "+movieTitle+" is "+rate);
    }
}
