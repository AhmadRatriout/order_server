import org.apache.log4j.BasicConfigurator;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import static spark.Spark.*;

public class orderServer {
    public static void main(String[] args) {
        BasicConfigurator.configure();
        //this is the purchase request recieved by the order server from the front-end-server
        post("/purchase/:value", (request, response) -> {

            String value = request.params(":value");
           
            //here we take the value from the request URL and add it to the URL in the request to be send to the catalog server
            
            URL url = new URL("http://192.168.1.250:4567/purchase/"+value);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");
            
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line+"\n");
            }
            br.close();
           
            return sb.toString();

        });



    }
}