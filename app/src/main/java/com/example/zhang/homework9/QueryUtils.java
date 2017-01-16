package com.example.zhang.homework9;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhang on 11/29/2016.
 */

public class QueryUtils {
    private QueryUtils(){

    }

//Legislator Utilities


    public static List<Legislator> fetchLegislatorData(String requestUrl){
        //Log.d("zyl", "onCreate 11");
        URL url = createUrl(requestUrl);
        String jsonResponse = null;
        try{
            jsonResponse = makeHttpRequest(url);
        }
        catch (IOException e){

        }
        //Log.d("zyl", "onCreate 7");
        List<Legislator> legislators = extractFeatureFromJsonLeg(jsonResponse);
        return legislators;
    }

    public static List<Bill> fetchBillData(String requestUrl){
        Log.d("bill", "enter fetchBillData");
        URL url = createUrl(requestUrl);
        String jsonResponse = null;
        try{
            jsonResponse = makeHttpRequest(url);
        }
        catch (IOException e){

        }
        //Log.d("zyl", "onCreate 7");
        List<Bill> bills = extractFeatureFromJsonBill(jsonResponse);
        return bills;
    }

    public static List<Committee> fetchCommitteeData(String requestUrl){
        Log.d("committee", "enter fetchCommitteeData");
        URL url = createUrl(requestUrl);
        String jsonResponse = null;
        try{
            jsonResponse = makeHttpRequest(url);
        }
        catch (IOException e){

        }
        //Log.d("zyl", "onCreate 7");
        List<Committee> committees = extractFeatureFromJsonCommittee(jsonResponse);
        return committees;
    }




    private static List<Bill> extractFeatureFromJsonBill(String billJson){
        Log.d("bill", "enter extract json feature");
        Log.d("bill", billJson.substring(1, 100));
        if(TextUtils.isEmpty(billJson)) return null;

        List<Bill> bills = new ArrayList<>();

        try{
            Log.d("bill", "enter extract json feature try");
            JSONObject baseJsonResponse = new JSONObject(billJson);
            JSONArray billArray = baseJsonResponse.getJSONArray("results");
            for(int i = 0; i < billArray.length(); ++i){
                Log.d("bill", "enter extract json feature loop");
                JSONObject curBill = billArray.getJSONObject(i);
                Bill b = new Bill();

                if(!curBill.isNull("bill_id")){
                    b.billId = curBill.getString("bill_id");
                }
                if(!curBill.isNull("short_title")){
                    b.billStortTitle = curBill.getString("short_title");
                }
                if(!curBill.isNull("introduced_on")){
                    b.introduced = curBill.getString("introduced_on");
                }
                if(!curBill.isNull("history") && !curBill.getJSONObject("history").isNull("active")){
                    b.isActive = !curBill.getJSONObject("history").getBoolean("active");
                }
                if(!curBill.isNull("bill_type")){
                    b.billType = curBill.getString("bill_type");
                }
                if(!curBill.isNull("sponsor")){
                    String s = "";
                    if(!curBill.getJSONObject("sponsor").isNull("title")){
                        s += curBill.getJSONObject("sponsor").getString("title") + ". ";
                    }
                    if(!curBill.getJSONObject("sponsor").isNull("last_name")){
                        s += curBill.getJSONObject("sponsor").getString("last_name") + ", ";
                    }
                    if(!curBill.getJSONObject("sponsor").isNull("first_name")){
                        s += curBill.getJSONObject("sponsor").getString("first_name");
                    }
                    b.sponsor = s;
                }
                if(!curBill.isNull("chamber")){
                    b.chamber = curBill.getString("chamber");
                }
                if(!curBill.isNull("history") && !curBill.getJSONObject("history").isNull("active")){
                    b.status = curBill.getJSONObject("history").getString("active");
                }
                if(!curBill.isNull("urls") && !curBill.getJSONObject("urls").isNull("congress")){
                    b.congressUrl = curBill.getJSONObject("urls").getString("congress");
                }
                if(!curBill.isNull("last_version") && !curBill.getJSONObject("last_version").isNull("version_name")){
                    b.versionStatus = curBill.getJSONObject("last_version").getString("version_name");
                }
                if(!curBill.isNull("urls") && !curBill.getJSONObject("urls").isNull("pdf")){
                    b.url = curBill.getJSONObject("urls").getString("pdf");
                }

                bills.add(b);
            }
        }catch (JSONException e){

        }
        return bills;
    }



    private static List<Committee> extractFeatureFromJsonCommittee(String committeeJson){
        Log.d("committee", "enter extract json feature");
        Log.d("committee", committeeJson.substring(1, 100));
        if(TextUtils.isEmpty(committeeJson)) return null;

        List<Committee> committees = new ArrayList<>();

        try{
            Log.d("committee", "enter extract json feature try");
            JSONObject baseJsonResponse = new JSONObject(committeeJson);
            JSONArray committeeArray = baseJsonResponse.getJSONArray("results");
            for(int i = 0; i < committeeArray.length(); ++i){
                Log.d("committee", "enter extract json feature loop");
                JSONObject curCommittee = committeeArray.getJSONObject(i);
                Committee c = new Committee();

                if(!curCommittee.isNull("committee_id")){
                    c.committeeId = curCommittee.getString("committee_id");
                }
                if(!curCommittee.isNull("name")){
                    c.committeeName = curCommittee.getString("name");
                }
                if(!curCommittee.isNull("chamber")){
                    c.chamber = curCommittee.getString("chamber");
                }
                if(!curCommittee.isNull("parent_committee_id")){
                    c.parentCommitteeId = curCommittee.getString("parent_committee_id");
                }
                if(!curCommittee.isNull("phone")){
                    c.contact = curCommittee.getString("phone");
                }
                if(!curCommittee.isNull("office")){
                    c.office = curCommittee.getString("office");
                }
                committees.add(c);
            }
        }catch (JSONException e){

        }
        return committees;
    }



    private static List<Legislator> extractFeatureFromJsonLeg(String legislatorJson){
        if(TextUtils.isEmpty(legislatorJson)) return null;
        Log.d("zyl", "Enter Json Feature Extraction and entered json string is" + legislatorJson.substring(1, 100));

        List<Legislator> legislators = new ArrayList<>();

        try{
            JSONObject baseJsonResponse = new JSONObject(legislatorJson);
            JSONArray legislatorArray = baseJsonResponse.getJSONArray("results");
            Log.d("zyl", "Is about to extract data and the length of json array is" + legislatorArray.length());
            for(int i = 0; i < legislatorArray.length(); ++i){
                Log.d("zyl", "Enter Loop and the current Legislator is the " + i);
                JSONObject currentLegislator = legislatorArray.getJSONObject(i);
                Legislator l = new Legislator();

                if(!currentLegislator.isNull("bioguide_id")){
                    l.bioguide_id = currentLegislator.getString("bioguide_id");
                }
                if(!currentLegislator.isNull("first_name")){
                    l.firstname = currentLegislator.getString("first_name");
                }
                if(!currentLegislator.isNull("last_name")){
                    l.lastname = currentLegislator.getString("last_name");
                }
                if(!currentLegislator.isNull("title")){
                    l.title = currentLegislator.getString("title");
                }
                if(!currentLegislator.isNull("birthday")){
                    l.birthday = currentLegislator.getString("birthday");
                }
                if(!currentLegislator.isNull("term_start")){
                    l.startTerm = currentLegislator.getString("term_start");
                }
                if(!currentLegislator.isNull("term_end")){
                    l.endTerm = currentLegislator.getString("term_end");
                }
                if(!currentLegislator.isNull("party")){
                    l.party = currentLegislator.getString("party");
                }
                if(!currentLegislator.isNull("chamber")){
                    l.chamber = currentLegislator.getString("chamber");
                }
                if(!currentLegislator.isNull("phone")){
                    l.contact = currentLegislator.getString("phone");
                }
                if(!currentLegislator.isNull("fax")){
                    l.fax = currentLegislator.getString("fax");
                }
                if(!currentLegislator.isNull("office")){
                    l.office = currentLegislator.getString("office");
                }
                if(!currentLegislator.isNull("facebook_id")){
                    l.facebook = "https://www.facebook.com/" + currentLegislator.getString("facebook_id");
                }
                if(!currentLegislator.isNull("twitter_id")){
                    l.twitter = "https://www.twitter.com/" + currentLegislator.getString("twitter_id");
                }
                if(!currentLegislator.isNull("website")){
                    l.website = currentLegislator.getString("website");
                }
                if(!currentLegislator.isNull("state")){
                    l.state = currentLegislator.getString("state");
                }
                if(!currentLegislator.isNull("state_name")){
                    l.stateName = currentLegislator.getString("state_name");
                }
                if(!currentLegislator.isNull("oc_email")){
                    l.email = currentLegislator.getString("oc_email");
                }
                if(!currentLegislator.isNull("district")){
                    l.district = currentLegislator.getString("district");
                }


                legislators.add(l);
                Log.d("zyl", "The " + i + "Legislator is extracted");
            }
        }catch(JSONException e){

        }
        //Log.d("zyl", "" + legislators.size());
        return legislators;
    }

//Bill Utilities




//Committee Utilities


    private static URL createUrl(String stringUrl){
        URL url = null;
        try{
            url = new URL(stringUrl);
        }catch(MalformedURLException e){

        }
        return url;
    }

    private static String makeHttpRequest(URL url) throws IOException{
        String jsonResponse = "";

        if(url == null) return jsonResponse;

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try{

            Log.d("zyl", "before http");
            urlConnection = (HttpURLConnection) url.openConnection();
            //Log.d("zyl", "onCreate 14");
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.setRequestMethod("GET");
            //Log.d("zyl", "onCreate 15");
            urlConnection.connect();
            //Log.d("zyl", Integer.toString(urlConnection.getResponseCode()));

            if(urlConnection.getResponseCode() == 200){
                Log.d("zyl", "http success");
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            }else{

            }
        }catch(IOException e){
            //Log.d("zyl", "onCreate 17");
        } finally{
            if(urlConnection != null){
                inputStream.close();
            }
        }
        return jsonResponse;
    }


    private static String readFromStream(InputStream inputStream) throws IOException{
        StringBuilder output = new StringBuilder();
        int counter = 0;
        if(inputStream != null){
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();

            while(line != null){
                output.append(line);
                line = reader.readLine();
                ++counter;
            }
        }
        Log.d("zyl", "Json is prepared and the totle json line is" + counter);
        Log.d("zyl", output.toString().substring(1, 100));
        return output.toString();
    }



}
