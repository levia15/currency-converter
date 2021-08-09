package com.currencyconverter;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URISyntaxException;
import java.net.URL;

import org.apache.http.client.utils.URIBuilder;

public final class CurrencyConverter {
    public static void main(String[] args) throws URISyntaxException, IOException {
        URIBuilder exchangeRatesURI = new URIBuilder("https://openexchangerates.org/api/latest.json");
        exchangeRatesURI.setParameter("app_id",""); //need to find way to hide API key
        String readLine = null;
        URL get = new URL(exchangeRatesURI.build().toString());
        HttpURLConnection conection = (HttpURLConnection) get.openConnection();
        conection.setRequestMethod("GET");
        int responseCode = conection.getResponseCode();
       
        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader in = new BufferedReader(
                new InputStreamReader(conection.getInputStream()));
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
