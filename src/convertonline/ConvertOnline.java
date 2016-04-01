/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package convertonline;

import gvjava.org.json.JSONException;
import gvjava.org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.Charset;

/**
 *
 * @author tomas
 */
public class ConvertOnline {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException, JSONException {   
        
        String from = "EUR";
        String to = "CZK";
        double amount = 100;
        
        System.out.println(convert(from,to,amount));
    }
    
    /**
     * Gets json of conversion and returns the result
     * @param from - currency code from which I want the rate
     * @param to - currency code to which the from is calculated
     * @param amount - amount of money I want to convert
     * @return - result of conversion
     * @throws IOException
     * @throws JSONException 
     */
    public static double convert(String from, String to, double amount) throws IOException, JSONException{
        double result = 0;
        JSONObject temp = readFromURL("http://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20yahoo.finance.xchange%20where%20pair%20in%20"
                + "(%22"+from+to+"%22)"
                + "&format=json" //also supporst format=xml
                + "&env=store://datatables.org/alltableswithkeys"
                + "&callback=");
        JSONObject query = temp.getJSONObject("query");
        JSONObject results = query.getJSONObject("results");
        JSONObject rate = results.getJSONObject("rate");
        
        System.out.println("Rate: "+rate.getDouble("Rate"));
        result = amount * rate.getDouble("Rate");
        return result;
    }
    
    /**
     * 
     * @param url - url request for json
     * @return - JSONObject of the returned query
     * @throws IOException
     * @throws JSONException 
     */
    public static JSONObject readFromURL(String url) throws IOException, JSONException {
        String jsonText = "";
        InputStream in = new URL(url).openStream();
        BufferedReader rd = new BufferedReader(new InputStreamReader(in, Charset.forName("UTF-8")));

        String line;
        while((line=rd.readLine())!=null){
            jsonText += line;
        }
        in.close();

        return new JSONObject(jsonText);
    }
    
}
