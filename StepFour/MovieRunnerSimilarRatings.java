import java.util.HashMap;
import java.util.ArrayList;
import java.util.Collections;

/**
 * 在这里给出对类 MovieRunnerWithFilters 的描述。
 * 
 * @作者（你的名字）
 * @版本（一个版本号或者一个日期）
 */
public class MovieRunnerSimilarRatings {
    private FourthRatings sr;
    public MovieRunnerSimilarRatings(){
        sr = new FourthRatings();
        MovieDatabase.initialize("ratedmoviesfull.csv");
        int numOfMovies = MovieDatabase.size();
        System.out.println("number of movies:"+numOfMovies);
        int numOfRaters = RaterDatabase.size();
        System.out.println("number of raters:"+numOfRaters);
    }
    
    public void printSimilarRatings(){
        ArrayList<Rating> moviesAverage;
        String raterID = "71";
        int numOfSimiliarRaters = 20;
        int minimalNum = 5;
        moviesAverage = sr.getSimilarRatings(raterID,numOfSimiliarRaters,minimalNum);
        System.out.println("There are "+moviesAverage.size()+" movies with ratings are returned.");
        //for(Rating r:moviesAverage) System.out.println(r.getItem()+" "+r.getValue());
        
      for(Rating r:moviesAverage){
            System.out.print(r.getValue()+" ");
            System.out.println(MovieDatabase.getTitle(r.getItem()));
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
    
    public void printSimilarRatingsByGenre(){        
        ArrayList<Rating> moviesAverage;
        String raterID = "964";
        int numOfSimiliarRaters = 20;
        int minimalNum = 5;
        Filter filter = new GenreFilter("Mystery");
        moviesAverage = sr.getSimilarRatingsByFilter(raterID,numOfSimiliarRaters,
            minimalNum,filter);
        System.out.println("There are "+moviesAverage.size()+" movies with ratings are returned.");
        //for(Rating r:moviesAverage) System.out.println(r.getItem()+" "+r.getValue());
        
      for(Rating r:moviesAverage){
            System.out.print(MovieDatabase.getTitle(r.getItem())+" ");
            System.out.println(r.getValue()+" ");
            System.out.println("    "+MovieDatabase.getGenres(r.getItem()));
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
    
    public void printSimilarRatingsByDirectors(){
        ArrayList<Rating> moviesAverage;
        String raterID = "120";
        int numOfSimiliarRaters = 10;
        int minimalNum = 2;
        Filter filter = new DirectorsFilter("Clint Eastwood,J.J. Abrams,Alfred Hitchcock,Sydney Pollack,David Cronenberg,Oliver Stone,Mike Leigh");
        moviesAverage = sr.getSimilarRatingsByFilter(raterID,numOfSimiliarRaters,
            minimalNum,filter);
        System.out.println("There are "+moviesAverage.size()+" movies with ratings are returned.");
        //for(Rating r:moviesAverage) System.out.println(r.getItem()+" "+r.getValue());
        
        for(Rating r:moviesAverage){
            System.out.print(MovieDatabase.getTitle(r.getItem())+" ");
            System.out.println(r.getValue()+" ");
            System.out.println("    "+MovieDatabase.getDirector(r.getItem()));
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
    
    public void printSimilarRatingsByGenreAndMinutes(){        
        ArrayList<Rating> moviesAverage;
        String raterID = "168";
        int numOfSimiliarRaters = 10;
        int minimalNum = 3;
        Filter genreFilter = new GenreFilter("Drama");
        Filter minutesFilter = new MinutesFilter(80,160);
        AllFilters allFilter = new AllFilters();
        allFilter.addFilter(genreFilter);
        allFilter.addFilter(minutesFilter);
        moviesAverage = sr.getSimilarRatingsByFilter(raterID,numOfSimiliarRaters,
            minimalNum,allFilter);
        System.out.println("There are "+moviesAverage.size()+" movies with ratings are returned.");
        //for(Rating r:moviesAverage) System.out.println(r.getItem()+" "+r.getValue());
        
      for(Rating r:moviesAverage){
            System.out.print(MovieDatabase.getTitle(r.getItem())+" ");
            System.out.print(r.getValue()+" ");
            System.out.println(MovieDatabase.getMinutes(r.getItem()));
            System.out.println("    "+MovieDatabase.getGenres(r.getItem()));
        }
    }
    
    public void printSimilarRatingsByYearAfterAndMinutes(){        
        ArrayList<Rating> moviesAverage;
        String raterID = "314";
        int numOfSimiliarRaters = 10;
        int minimalNum = 5;
        Filter yearFilter = new YearAfterFilter(1975);
        Filter minutesFilter = new MinutesFilter(70,200);
        AllFilters allFilter = new AllFilters();
        allFilter.addFilter(yearFilter);
        allFilter.addFilter(minutesFilter);
        moviesAverage = sr.getSimilarRatingsByFilter(raterID,numOfSimiliarRaters,
            minimalNum,allFilter);
        System.out.println("There are "+moviesAverage.size()+" movies with ratings are returned.");
        //for(Rating r:moviesAverage) System.out.println(r.getItem()+" "+r.getValue());
        
      for(Rating r:moviesAverage){
            System.out.print(MovieDatabase.getTitle(r.getItem())+" ");
            System.out.print(MovieDatabase.getYear(r.getItem())+" ");
            System.out.print(MovieDatabase.getMinutes(r.getItem())+" ");
            System.out.println(r.getValue()+" ");
        }
    }
    
    
}
