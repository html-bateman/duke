
/**

/**
 * Write a description of SecondRatings here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

import java.util.*;

public class SecondRatings {
    private ArrayList<Movie> myMovies;
    private ArrayList<Rater> myRaters;
    private ArrayList<String> movieIdList;
    private ArrayList<Double> ratingList;
    private HashMap<String,Integer> movieidToNumofrating;
    private FirstRatings fr;
    
    public SecondRatings() {
        // default constructor
        this("data/ratedmoviesfull.csv", "data/ratings.csv");
        //this("data/ratedmovies_short.csv", "data/ratings_short.csv");
    }
    
    public SecondRatings(String moviefile,String ratingsfile){
        fr = new FirstRatings();
        myMovies = fr.loadMovies(moviefile);
        myRaters = fr.loadRaters(ratingsfile);
        
        movieidToNumofrating = fr.generateMovieToRatenumMap();
        movieIdList = fr.generateMovieIdList();
        //for(String s:movieIdList) System.out.println(s);
        ratingList = fr.generateRatingList();
        //for(Double d:ratingList) System.out.println(d);
    }
    
    public int getMovieSize(){
        return myMovies.size();
    }
    
    public int getRaterSize(){
        return myRaters.size();
    }
    
    public double getAverageByID(String movieId,int minimalRaters){
        double total = 0.0;
        int numOfRatings = fr.findNumOfRatingsByMovieId(movieId);
        System.out.println("movieid:"+movieId+" has "+numOfRatings +" ratings");
        if(numOfRatings>=minimalRaters){
            for(int k=0;k<movieIdList.size();k++){
                if(movieIdList.get(k).equals(movieId)){
                    total+=ratingList.get(k);
                }
            }
            System.out.println("the total ratings is:"+total);
            double average = total/numOfRatings;
            System.out.println("the average rating is:"+average);
            return average;
        }
        return 0.0;
    }
    
    public ArrayList<Rating> getAverageRatings(int minimalRaters){
        ArrayList<Rating> moviesAverageRate = new ArrayList<>();
        ArrayList<Rating> moviesAverageClean = new ArrayList<>();
        movieidToNumofrating.forEach( (key,value)->
            moviesAverageRate.add(new Rating(key,
                getAverageByID(key,minimalRaters)))
        );
        for(Rating r: moviesAverageRate){
            if(r.getValue()!=0.0) moviesAverageClean.add(r);
        }
        return moviesAverageClean;
    }
    
    public String getTitle(String id){
        for(Movie movie:myMovies){
            if(movie.getID().equals(id)) return movie.getTitle();
        }
        return "The movie:"+id+" was not found.";
    }
    
    public String getID(String title){
        for(Movie movie:myMovies){
            if(movie.getTitle().equals(title)) return movie.getID();
        }
        return "The movie:"+title+" NO SUCH TITLE.";
    }
    
    
    
}