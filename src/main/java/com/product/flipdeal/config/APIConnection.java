package com.product.flipdeal.config;

import java.net.HttpURLConnection;
import java.net.URL;

public class APIConnection {

    public boolean isConnectionCreated(URL url) {
        try {
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();

            //Getting the response code
            int responsecode = conn.getResponseCode();
            if (responsecode == 200) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
