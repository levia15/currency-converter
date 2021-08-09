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
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public final class CurrencyConverter {
    public static void main(String[] args) throws URISyntaxException, IOException, ParseException {
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
        
        //get call response 
        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuffer response = new StringBuffer();
            while ((readLine = in .readLine()) != null) {
                response.append(readLine);
            } 
            in.close();
            //Using the JSON simple library parse the string into a json object
            JSONParser parser = new JSONParser();
            JSONObject responseBody = (JSONObject) parser.parse(response.toString());

            //Get the rate object from the above created JSONObject
            JSONObject rates = (JSONObject) responseBody.get("rates");
        
        } else {
            throw new RuntimeException("HttpResponseCode: " + responseCode);
        }
    
    }
}
