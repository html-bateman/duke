import java.util.Random;
import java.util.ArrayList;

/**
 * 在这里给出对类 RecommendationRunner 的描述。
 * 
 * @作者（你的名字）
 * @版本（一个版本号或者一个日期）
 */
public class RecommendationRunner implements Recommender {
    @Override
    public ArrayList<String> getItemsToRate(){
        ArrayList<String> itemsToRate = new ArrayList<>();
        ArrayList<String> allMovies = MovieDatabase.filterBy(new TrueFilter());
        Random random = new Random();
        for(int k=0;k<10;k++){
            int randomNum = random.nextInt(allMovies.size());
            itemsToRate.add(allMovies.get(randomNum));
        }
        return itemsToRate;
    } 
    
    @Override
    public void printRecommendationsFor(String webRaterID){
        ArrayList<Rating> recommended = new FourthRatings().getSimilarRatings
            (webRaterID,10,3);
        if(recommended.size()!=0){

                    System.out.println("<html>");
                    System.out.println("<head>");
                    System.out.println("<Style>");
                    System.out.println("table { margin: 0 auto; text-align: left; }");
                    System.out.println("</Style>");
                    System.out.println("<title>Sample HTML Table</title>");
                    System.out.println("</head>");
                    System.out.println("<body>");

                    // Print HTML table
                    System.out.println("<table border='1'>");
                    System.out.println("<tr>");
                    System.out.println("<th>Recommendations</th>");
                    System.out.println("</tr>");

                    // Print table rows
                    for (Rating r: recommended) {
                        System.out.println("<tr>");
                        System.out.println("<td>" + MovieDatabase.getTitle(r.getItem()) + "</td>");
                        System.out.println("</tr>");
                    }

                    // Print HTML table closing tag
                    System.out.println("</table>");

                    // Print HTML footer
                    System.out.println("</body>");
                    System.out.println("</html>");

        }else{
            System.out.println("No recommendations.");
        }
    }
}
