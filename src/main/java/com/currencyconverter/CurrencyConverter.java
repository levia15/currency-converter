package com.currencyconverter;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Properties;

import org.apache.http.client.utils.URIBuilder;

public final class CurrencyConverter {
    public static void main(String[] args) throws URISyntaxException, IOException {
        Properties prop=new Properties();
        FileInputStream file= new FileInputStream("config.properties");
        prop.load(file);
        URIBuilder exchangeRatesURI = new URIBuilder("https://openexchangerates.org/api/latest.json");
        exchangeRatesURI.setParameter("app_id",prop.getProperty("API_KEY")); //get API_Key and set as query parameter
        String readLine = null;
        URL get = new URL(exchangeRatesURI.build().toString()); //URL for request
        HttpURLConnection connection = (HttpURLConnection) get.openConnection();
        connection.setRequestMethod("GET"); //Get request
        int responseCode = connection.getResponseCode();
        
        //print out get call response (to be used, just printing for testing now)
        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader in = new BufferedReader(
                new InputStreamReader(connection.getInputStream()));
            StringBuffer response = new StringBuffer();
            while ((readLine = in .readLine()) != null) {
                response.append(readLine);
            } in .close();
            System.out.println("JSON String Result " + response.toString());
        } else {
            System.out.println("GET NOT WORKED");
        }
    
    }
}
