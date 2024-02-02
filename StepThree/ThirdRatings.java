
/**

/**
 * Write a description of SecondRatings here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

import java.util.*;

public class ThirdRatings {
    private ArrayList<Rater> myRaters;
    private ArrayList<String> movieIdList;
    private ArrayList<Double> ratingList;
    private HashMap<String,Integer> movieidToNumofrating;
    private FirstRatings fr;
    
    public ThirdRatings() {
        // default constructor
        this("data/ratings.csv");
        //this("data/ratings_short.csv");
    }
    
    public ThirdRatings(String ratingsfile){
        fr = new FirstRatings();
        myRaters = fr.loadRaters(ratingsfile);
        
        // movieidToNumofrating = fr.generateMovieToRatenumMap();
        movieIdList = fr.generateMovieIdList();
        // //for(String s:movieIdList) System.out.println(s);
        ratingList = fr.generateRatingList();
        // //for(Double d:ratingList) System.out.println(d);
    }
    
    
    public int getRaterSize(){
        return myRaters.size();
    }
    
    public double getAverageByID(String movieId,int minimalRaters){
        double total = 0.0;
        int numOfRatings = fr.findNumOfRatingsByMovieId(movieId);
        System.out.println("movieid:"+movieId+" ["+
           MovieDatabase.getTitle(movieId)+"] has "+numOfRatings +" ratings");
        if(numOfRatings>=minimalRaters){
            for(int k=0;k<movieIdList.size();k++){
                if(movieIdList.get(k).equals(movieId)){
                    total+=ratingList.get(k);
                }
            }
            System.out.println("the total ratings is:"+total);
            double average = total/numOfRatings;
            System.out.println("the average rating is:"+average);
            System.out.println("______________________________");
            return average;
        }
        return 0.0;
    }
    
    public ArrayList<Rating> getAverageRatings(int minimalRaters){
        ArrayList<Rating> moviesAverageRate = new ArrayList<>();
        ArrayList<Rating> moviesAverageClean = new ArrayList<>();
        ArrayList<String> movies = MovieDatabase.filterBy(new TrueFilter());
        int count=0;
        for(String movieId:movies){
            moviesAverageRate.add(new Rating(movieId,
                getAverageByID(movieId,minimalRaters)));
            count=count+1;
        }
        for(Rating r: moviesAverageRate){
            if(r.getValue()!=0.0) moviesAverageClean.add(r);
        }
        System.out.println(count+" movies have been processed.");
        return moviesAverageClean;
    }
    
    public ArrayList<Rating> getAverageRatingsByFilter(int minimalRaters,Filter filterCriteria){
        ArrayList<Rating> moviesAverageRate = new ArrayList<>();
        ArrayList<Rating> moviesAverageClean = new ArrayList<>();
        ArrayList<String> movies = MovieDatabase.filterBy(filterCriteria);
        movies.forEach((movieId)->
            moviesAverageRate.add(new Rating(movieId,
                getAverageByID(movieId,minimalRaters))));
        for(Rating r: moviesAverageRate){
            if(r.getValue()!=0.0) moviesAverageClean.add(r);
        }
        return moviesAverageClean;
    }

    
    
    
}