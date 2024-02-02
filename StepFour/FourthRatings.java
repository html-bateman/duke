
/**

/** 
 * Write a description of SecondRatings here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

import java.util.*;
import java.util.logging.Logger;
import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.SimpleFormatter;


public class FourthRatings {
    private static final Logger logger = Logger.getLogger("");    
    private ArrayList<String> movieIdList;
    private ArrayList<Double> ratingList;
    private ArrayList<Rating> moviesAverageRate;
    private ArrayList<Rating> moviesAverageClean;

    
    public FourthRatings() {
        // default constructor
        this("ratings.csv");
        //this("ratings_short.csv");
    }
    
    public FourthRatings(String ratingsfile){
        RaterDatabase.initialize(ratingsfile);
        movieIdList = new ArrayList<>();
        ratingList = new ArrayList<>();
        moviesAverageRate = new ArrayList<>();
        moviesAverageClean = new ArrayList<>();
        generateLists();
    }
    
    
    
    public int getRaterSize(){
        return RaterDatabase.size();
    }
    
    public double getAverageByID(String movieId,int minimalRaters){
        double total = 0.0;
        int numOfRatings = findNumOfRatingsByMovieId(movieId);
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

        ArrayList<String> movies = MovieDatabase.filterBy(filterCriteria);
        movies.forEach((movieId)->
            moviesAverageRate.add(new Rating(movieId,
                getAverageByID(movieId,minimalRaters))));
        for(Rating r: moviesAverageRate){
            if(r.getValue()!=0.0) moviesAverageClean.add(r);
        }
        return moviesAverageClean;
    }
    
    private void generateLists(){
        for(Rater r:RaterDatabase.getRaters()){
            ArrayList<String> movieListOfRater = new ArrayList<>();
            movieListOfRater = r.getItemsRated();
            for(String item:movieListOfRater){
                double rating = r.getRating(item);
                //System.out.println("MovieId:"+item+" Rating:"+rating);
                movieIdList.add(item);
                ratingList.add(rating);
            }
        }
    }
    
    
    private int findNumOfRatingsByMovieId(String movieId){
        Map<String,Integer> movieRatedByNum = new HashMap<>();
        for(Rater r:RaterDatabase.getRaters()){
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
    
    private double dotProduct(Rater me,Rater r){
        ArrayList<String> meMovieIdList = me.getItemsRated();
        ArrayList<String> rMovieIdList = r.getItemsRated();
        ArrayList<String> bothRatedMovieIdList = new ArrayList<>();
        for(String meMovieId:meMovieIdList){
            for(String rMovieId:rMovieIdList){
                if(meMovieId.equals(rMovieId)){
                    bothRatedMovieIdList.add(rMovieId);
                }
            }
        }
        ArrayList<Double> meRatings5 = new ArrayList<>();
        ArrayList<Double> rRatings5 = new ArrayList<>();
        for(String movieId:bothRatedMovieIdList){
            meRatings5.add(me.getRating(movieId)-5);
            rRatings5.add(r.getRating(movieId)-5);
        }
        double dotProduct = 0;
        for(int k=0;k<rRatings5.size();k++){
            dotProduct = dotProduct+meRatings5.get(k)*rRatings5.get(k);
        }
        return dotProduct;
    }
    
    public void testdotProduct(){
        ArrayList<Rating> gs = getSimilarities("1034");
        for(Rating r:gs){
            System.out.print("rater:"+r.getItem()+" ");
            System.out.println("dotProduct:"+r.getValue());
        }
        ArrayList<Rating> gsr = getSimilarRatings("65",20,5);
        for(Rating r:gsr){
            System.out.print("Movieid:"+r.getItem()+" ");
            System.out.print("["+MovieDatabase.getTitle(r.getItem())+"] ");
            System.out.println("Weighted:"+r.getValue()); 
            System.out.println("  "+MovieDatabase.getDirector(r.getItem()));
        }

    }
    
    
    private ArrayList<Rating> getSimilarities(String id){
        //logger.info("Starting getSimilarities for raterId"+id);
        Rater me = RaterDatabase.getRater(id);
        ArrayList<Rating> raterId_dotProduct = new ArrayList<>();
        ArrayList<Rating> onlyPositive = new ArrayList<>();
        for(Rater r:RaterDatabase.getRaters()){
            if(!r.getID().equals(id)){
                raterId_dotProduct.add(new Rating(r.getID(),dotProduct(me,r)));
            }
        }
        for(Rating r:raterId_dotProduct){
            if(r.getValue()>0){
                onlyPositive.add(new Rating(r.getItem(),r.getValue()));
                //logger.info("raterId:"+r.getItem()+" dotProduct:"+r.getValue());
            }
        }
        //logger.info("finished caculating dotProdcut with all other raters");
        Collections.sort(onlyPositive,Collections.reverseOrder());
        return onlyPositive;
    }
    
    public ArrayList<Rating> getSimilarRatings(String id,int numSimilarRaters,int minimalRaters){
        ArrayList<Rating> dotProduct = getSimilarities(id);
        ArrayList<Rating> topSimilarites = new ArrayList<>();
        //the index of top raters starts at 0

        for(int k=0;(numSimilarRaters<=dotProduct.size())?k<numSimilarRaters:k<dotProduct.size();k++) {
            topSimilarites.add(dotProduct.get(k));
            //logger.info("top similarity "+k+" is raterId:"+
            //    dotProduct.get(k).getItem()+" With dotProduct:"+
            //    dotProduct.get(k).getValue());
        }
        //get the movieIds rated by the rater
        ArrayList<String> MovieRatedByMe = RaterDatabase.getRater(id).getItemsRated();
        ArrayList<Rating> MovieId_weightedAverge = new ArrayList<Rating>();
        ArrayList<String> allMovies = MovieDatabase.filterBy(new TrueFilter());
        //logger.info("there are "+allMovies.size()+" movies.");
        //get rid of the movies that are already rated by the rater
        HashSet<String> MovieIdSet = new HashSet<>();
        for(String movie:allMovies){
            for(String m:MovieRatedByMe){
                if(!m.equals(movie)){
                    MovieIdSet.add(movie);
                }
            }
        }
        ArrayList<String> MovieIdList = new ArrayList<>(MovieIdSet);
        // for(Rating r:topSimilarites){
            // for(String movie:RaterDatabase.getRater(r.getItem()).getItemsRated()){
                // MovieIdList.add(movie);
            // }
        // }
        
        //logger.info("Start caculating weighted average");
        for(String movieId:MovieIdList){
            //logger.info("Start with movieId:"+movieId);
            double total = 0;
            int count = 0;
            for(Rating r:topSimilarites){
                //logger.info("Start with raterId:"+r.getItem());
                //check whether the similar rater rated the movie
                if(RaterDatabase.getRater(r.getItem()).hasRating(movieId)){
                    //logger.info("raterId:"+r.getItem()+" has rated"+" movieId");
                    //System.out.println(r.getValue()+" "+
                    //RaterDatabase.getRater(r.getItem()).getRating(movieId));
                    //logger.info("add the product of dotproduct*rating");
                    //logger.info("the dotproduct is:"+r.getValue());
                    //logger.info("the rating is:"+RaterDatabase.getRater(r.getItem()).getRating(movieId));
                    total=total+r.getValue()*
                    RaterDatabase.getRater(r.getItem()).getRating(movieId);
                    count=count+1;
                    
                    //logger.info("the total now is "+total);
                    //System.out.println(total+" "+count);
                }
            }
            
            if(count>=minimalRaters){
                double weightedAverage = total/count;
                MovieId_weightedAverge.add(new Rating(movieId,weightedAverage));
                //logger.info("the weighted average is"+weightedAverage);
                //logger.info("End with movie:"+movieId);
            }//else{
                //logger.info("does not have enough minimal raters");
                //logger.info("End with movieId:"+movieId);
            //}
        }
        
        //logger.info("End caculating weighted average");
        Collections.sort(MovieId_weightedAverge,Collections.reverseOrder());
        return MovieId_weightedAverge;
    }
    
    public ArrayList<Rating> getSimilarRatingsByFilter(String id,
        int numSimilarRaters,int minimalRaters,Filter filterCriteria){
        ArrayList<Rating> dotProduct = getSimilarities(id);
        ArrayList<Rating> topSimilarites = new ArrayList<>();
        for(int k=0;k<numSimilarRaters;k++){
            topSimilarites.add(dotProduct.get(k));
        }
        //get the movieIds rated by the rater
        ArrayList<String> MovieIdList = RaterDatabase.getRater(id).getItemsRated();
        ArrayList<String> MovieIdListFinal = MovieDatabase.filterBy(filterCriteria);
        ArrayList<Rating> MovieId_weightedAverge = new ArrayList<Rating>();
        double total = 0;
        int count = 0;
        for(String movieId:MovieIdListFinal){
            for(Rating r:topSimilarites){
                //check whether the similar rater rated the movie which is rated by rater id 
                if(RaterDatabase.getRater(r.getItem()).hasRating(movieId)){
                    total=total+r.getValue()*
                    RaterDatabase.getRater(r.getItem()).getRating(movieId);
                    count=count+1;
                }
            }
            double weightedAverage = total/count;
            if(count>=minimalRaters){
                MovieId_weightedAverge.add(new Rating(movieId,weightedAverage));
            }
            total = 0;
            count = 0;
        }
        Collections.sort(MovieId_weightedAverge,Collections.reverseOrder());
        return MovieId_weightedAverge;
    }
}