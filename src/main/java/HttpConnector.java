import model.PostRequest;
import org.apache.log4j.Logger;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Map;

public class HttpConnector {
    private static final Logger LOGGER = Logger.getLogger(Main.class);

    public String post(PostRequest postRequest) throws Exception {
        LOGGER.info("START post(" + postRequest + ")");
        HttpsURLConnection con = (HttpsURLConnection) new URL(postRequest.url).openConnection();
        con.setRequestMethod("POST");
        if (postRequest.header != null)
            for (Map.Entry<String, String> headerEntry : postRequest.header.entrySet()) {
                con.setRequestProperty(headerEntry.getKey(), headerEntry.getValue());
            }
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(postRequest.body);
        wr.flush();
        wr.close();
        int responseCode = con.getResponseCode();

        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        LOGGER.info("END post()[url : " + postRequest.url + "][code : " + responseCode + "]");
        LOGGER.info("END post()[response : " + response.toString() + "]");
        return response.toString();
    }
}
