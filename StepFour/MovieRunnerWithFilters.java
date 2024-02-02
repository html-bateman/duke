import java.util.HashMap;
import java.util.ArrayList;
import java.util.Collections;

/**
 * 在这里给出对类 MovieRunnerWithFilters 的描述。
 * 
 * @作者（你的名字）
 * @版本（一个版本号或者一个日期）
 */
public class MovieRunnerWithFilters {
    private ThirdRatings sr;
    public MovieRunnerWithFilters(){
        sr = new ThirdRatings("data/ratings.csv");
        MovieDatabase.initialize("ratedmoviesfull.csv");
        int numOfMovies = MovieDatabase.size();
        System.out.println("number of movies:"+numOfMovies);
        int numOfRaters = sr.getRaterSize();
        System.out.println("number of raters:"+numOfRaters);
    }
    
    public void printAverageRatings(){
        ArrayList<Rating> moviesAverage;
        moviesAverage = sr.getAverageRatings(35);
        System.out.println("There are "+moviesAverage.size()+" movies with ratings are returned.");
        //for(Rating r:moviesAverage) System.out.println(r.getItem()+" "+r.getValue());
        
        ArrayList<Double> rate = new ArrayList<>();
        HashMap<Double,String> rateToName = new HashMap<>();
        for(Rating rating:moviesAverage){
            rate.add(rating.getValue());
            rateToName.put(rating.getValue(),MovieDatabase.getTitle(rating.getItem()));
        }
        //Collections.sort(rate,Collections.reverseOrder());
        Collections.sort(rate);
        for(Double r:rate){
            System.out.print(r+" ");
            System.out.println(rateToName.get(r));
        }
    }
    
    
    public void printAverageRatingsByYear(){
        ArrayList<Rating> moviesAverage = new ArrayList<>();
        moviesAverage = sr.getAverageRatingsByFilter(20,new YearAfterFilter(2000));
        System.out.println("There are "+moviesAverage.size()+" movies with ratings are returned.");
        //for(Rating r:moviesAverage) System.out.println(r.getItem()+" "+r.getValue());
        
        ArrayList<Double> rate = new ArrayList<>();
        HashMap<Double,Integer> rateToYear = new HashMap<>();
        HashMap<Double,String> rateToName = new HashMap<>();
        for(Rating rating:moviesAverage){
            rate.add(rating.getValue());
            rateToYear.put(rating.getValue(),MovieDatabase.getYear(rating.getItem()));
            rateToName.put(rating.getValue(),MovieDatabase.getTitle(rating.getItem()));
        }
        //Collections.sort(rate,Collections.reverseOrder());
        Collections.sort(rate);
        for(Double r:rate){
            System.out.print(r+" ");
            System.out.print(rateToYear.get(r)+" ");
            System.out.println(rateToName.get(r));
        }
    }
    
    public void printAverageRatingsByGenre(){

        ArrayList<Rating> moviesAverage = new ArrayList<>();
        moviesAverage = sr.getAverageRatingsByFilter(20,new GenreFilter("Comedy"));
        System.out.println("There are "+moviesAverage.size()+" movies with ratings are returned.");
        //for(Rating r:moviesAverage) System.out.println(r.getItem()+" "+r.getValue());
        
        ArrayList<Double> rate = new ArrayList<>();
        HashMap<Double,String> rateToGenre = new HashMap<>();
        HashMap<Double,String> rateToName = new HashMap<>();
        for(Rating rating:moviesAverage){
            rate.add(rating.getValue());
            rateToGenre.put(rating.getValue(),MovieDatabase.getGenres(rating.getItem()));
            rateToName.put(rating.getValue(),MovieDatabase.getTitle(rating.getItem()));
        }
        //Collections.sort(rate,Collections.reverseOrder());
        Collections.sort(rate);
        for(Double r:rate){
            System.out.print(r+" ");
            System.out.println(rateToName.get(r));
            System.out.println("    "+rateToGenre.get(r));
        }
    }
    
    public void printAverageRatingsByMinutes(){
 
        ArrayList<Rating> moviesAverage = new ArrayList<>();
        moviesAverage = sr.getAverageRatingsByFilter(5,new MinutesFilter(105,135));
        System.out.println("There are "+moviesAverage.size()+" movies with ratings are returned.");
        //for(Rating r:moviesAverage) System.out.println(r.getItem()+" "+r.getValue());
        
        ArrayList<Double> rate = new ArrayList<>();
        HashMap<Double,Integer> rateToMinutes = new HashMap<>();
        HashMap<Double,String> rateToName = new HashMap<>();
        for(Rating rating:moviesAverage){
            rate.add(rating.getValue());
            rateToMinutes.put(rating.getValue(),MovieDatabase.getMinutes(rating.getItem()));
            rateToName.put(rating.getValue(),MovieDatabase.getTitle(rating.getItem()));
        }
        //Collections.sort(rate,Collections.reverseOrder());
        Collections.sort(rate);
        for(Double r:rate){
            System.out.print(r+" ");
            System.out.print(rateToMinutes.get(r)+" ");
            System.out.println(rateToName.get(r));
        }
    }
    
    public void printAverageRatingsByDirectors(){
        ArrayList<Rating> moviesAverage = new ArrayList<>();
        moviesAverage = sr.getAverageRatingsByFilter(4,
        new DirectorsFilter("Clint Eastwood,Joel Coen,Martin Scorsese,Roman Polanski,Nora Ephron,Ridley Scott,Sydney Pollack"));
        System.out.println("There are "+moviesAverage.size()+" movies with ratings are returned.");
        //for(Rating r:moviesAverage) System.out.println(r.getItem()+" "+r.getValue());
        
        ArrayList<Double> rate = new ArrayList<>();
        HashMap<Double,String> rateToDirector = new HashMap<>();
        HashMap<Double,String> rateToName = new HashMap<>();
        for(Rating rating:moviesAverage){
            rate.add(rating.getValue());
            rateToDirector.put(rating.getValue(),MovieDatabase.getDirector(rating.getItem()));
            rateToName.put(rating.getValue(),MovieDatabase.getTitle(rating.getItem()));
        }
        //Collections.sort(rate,Collections.reverseOrder());
        Collections.sort(rate);
        for(Double r:rate){
            System.out.print(r+" ");
            System.out.println(rateToName.get(r));
            System.out.println("    "+rateToDirector.get(r));
        }
    }
    
    public void printAverageRatingsByYearAfterAndGenre(){
        ArrayList<Rating> moviesAverage = new ArrayList<>();
        AllFilters allFilters = new AllFilters();
        allFilters.addFilter(new YearAfterFilter(1990));
        allFilters.addFilter(new GenreFilter("Drama"));
        moviesAverage = sr.getAverageRatingsByFilter(8,allFilters);
        
        //for(Rating r:moviesAverage) System.out.println(r.getItem()+" "+r.getValue());
        
        ArrayList<Double> rate = new ArrayList<>();
        HashMap<Double,String> rateToGenre = new HashMap<>();
        HashMap<Double,Integer> rateToYear = new HashMap<>();
        HashMap<Double,String> rateToName = new HashMap<>();
        for(Rating rating:moviesAverage){
            rate.add(rating.getValue());
            rateToYear.put(rating.getValue(),MovieDatabase.getYear(rating.getItem()));
            rateToGenre.put(rating.getValue(),MovieDatabase.getGenres(rating.getItem()));
            rateToName.put(rating.getValue(),MovieDatabase.getTitle(rating.getItem()));
        }
        //Collections.sort(rate,Collections.reverseOrder());
        Collections.sort(rate);
        for(Double r:rate){
            System.out.print(r+" ");
            System.out.print(rateToYear.get(r)+" ");
            System.out.println(rateToName.get(r));
            System.out.println("    "+rateToGenre.get(r));
        }
        System.out.println("There are "+moviesAverage.size()+" movies with ratings are returned.");
    }


    public void printAverageRatingsByDirectorsAndMinutes(){
        ArrayList<Rating> moviesAverage = new ArrayList<>();
        AllFilters allFilters = new AllFilters();
        allFilters.addFilter(new DirectorsFilter(
        "Clint Eastwood,Joel Coen,Tim Burton,Ron Howard,Nora Ephron,Sydney Pollack"));
        allFilters.addFilter(new MinutesFilter(90,180));
        moviesAverage = sr.getAverageRatingsByFilter(3,allFilters);
        System.out.println("There are "+moviesAverage.size()+" movies with ratings are returned.");
        //for(Rating r:moviesAverage) System.out.println(r.getItem()+" "+r.getValue());
        
        for(Rating r:moviesAverage){
            System.out.print(r.getValue()+" ");
            System.out.print("Time:"+MovieDatabase.getMinutes(r.getItem())+" ");
            System.out.println(MovieDatabase.getTitle(r.getItem()));
            System.out.println("      "+MovieDatabase.getDirector(r.getItem()));
        }
    }
    
    
}
