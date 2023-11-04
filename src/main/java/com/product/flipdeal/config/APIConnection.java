package com.product.flipdeal.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.product.flipdeal.response.ProductDiscounts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import java.io.FileWriter;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Component
@Slf4j
public class APIConnection {

    public boolean isConnectionCreated(URL url) {
        try {
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod(HttpMethod.GET.toString());
            conn.connect();

            //Getting the response code
            int responseCode = conn.getResponseCode();
            if (responseCode == 200) {
                return true;
            }
        } catch (IOException e) {
            log.error("Error in opening connection {}", e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    public void writeOutput(List<ProductDiscounts> productDiscounts) {
        String filename = "output.json";
        String pathTemp = System.getProperty("user.dir");
        String updatedPath = pathTemp + "/target/" + filename;
        Path path = Paths.get(updatedPath);
        try (FileWriter file = new FileWriter(String.valueOf(path))) {
            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(productDiscounts);
            file.write(json);
            file.flush();
        } catch (IOException e) {
            log.error("Error in writing to output file {}", e.getMessage());
            e.printStackTrace();
        }
    }
}
