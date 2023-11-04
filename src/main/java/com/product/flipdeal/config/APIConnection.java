package com.product.flipdeal.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.product.flipdeal.response.ProductDiscounts;
import org.springframework.stereotype.Component;

import java.io.FileWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Component
public class APIConnection {

    public boolean isConnectionCreated(URL url) {
        try {
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();

            //Getting the response code
            int responseCode = conn.getResponseCode();
            if (responseCode == 200) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public void writeOutput(List<ProductDiscounts> productDiscounts) {
        String filename = "output.json";
        String pathTemp = System.getProperty("user.dir");
        String updatedPath = pathTemp + "/target/" + filename;
        Path path = Paths.get(updatedPath);
        try {
            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(productDiscounts);
            FileWriter file = new FileWriter(String.valueOf(path));
            file.write(json);
            file.flush();
            file.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
