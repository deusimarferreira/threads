package com.example.threads.test;

import java.io.*;
import java.math.*;
import java.security.*;
import java.text.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.function.*;
import java.util.regex.*;
import java.util.stream.*;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.reducing;
import static java.util.stream.Collectors.toList;
import java.net.*;
import org.json.simple.*;
import org.json.simple.parser.*;
import java.net.http.*;
import java.net.URI;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import com.google.gson.*;

class Result {

    /*
     * Complete the 'topArticles' function below.
     *
     * The function is expected to return a STRING_ARRAY.
     * The function accepts INTEGER limit as parameter.
     * base url for copy/paste:
     * https://jsonmock.hackerrank.com/api/articles?page=<pageNumber>
     */

    public static List<String> topArticles(int limit) {
        List<String> listTopArticles = new ArrayList<>();
        Map<JSONObject, Long> articles = new TreeMap<>(new Comparator<JSONObject>() {

            @Override
            public int compare(JSONObject o1, JSONObject o2) {
                Long numComments1 = o1.get("num_comments") != null ? (Long) o1.get("num_comments") : 0;
                Long numComments2 = o2.get("num_comments") != null ? (Long) o2.get("num_comments") : 0;

                int i = numComments1.compareTo(numComments2);
                if (i == 0) {
                    String title1 = o1.get("title") != null ? (String) o1.get("title") : (o1.get("story_title") != null ? (String) o1.get("story_title") : null);
                    String title2 = o2.get("title") != null ? (String) o2.get("title") : (o2.get("story_title") != null ? (String) o2.get("story_title") : null);
                    if (title1 != null && title2 != null) {
                        i = title1.compareTo(title2);
                    }
                }

                return i;
            }
            
        });
        long page = 1;
        long totalPages = 0; 
        
        String baseUrl = "https://jsonmock.hackerrank.com/api/articles?page=";
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(baseUrl + page))
                .header("Content-Type", "application/json")
                .build();

        try {
            HttpResponse<String> json = client.send(request, HttpResponse.BodyHandlers.ofString());
            JSONParser parser = new JSONParser();
            JSONObject jsonObject = (JSONObject) parser.parse(json.body());
            totalPages = (long) jsonObject.get("total_pages");
            List<JSONObject> datas = new ArrayList<>(); 
            JSONArray data = (JSONArray) jsonObject.get("data");
            datas.addAll(data.stream().toList());

            for(page = 2; page <= totalPages; page++) {
                request = HttpRequest.newBuilder()
                    .uri(URI.create(baseUrl + page))
                    .header("Content-Type", "application/json")
                    .build();
                json = client.send(request, HttpResponse.BodyHandlers.ofString());
                jsonObject = (JSONObject) parser.parse(json.body());
                data = (JSONArray) jsonObject.get("data");
                datas.addAll(data.stream().toList());
            }

            for (JSONObject object : datas) {
                long comments = object.get("num_comments") == null ? 0 : (Long) object.get("num_comments");
                if (object.get("title") != null) {
                    articles.put(object, comments);
                } else if (object.get("story_title") != null) {
                    articles.put(object, comments);
                }
            }

            int size = articles.size();
            int contador = 1;
            for (Map.Entry<JSONObject, Long> entry : articles.entrySet()) {
                if (contador > (size - limit)) {
                    if (entry.getKey().get("title") != null) {
                        listTopArticles.add((String) entry.getKey().get("title"));
                    } else if (entry.getKey().get("story_title") != null) {
                        listTopArticles.add((String) entry.getKey().get("story_title"));
                    }
                }

                contador++;
            }
        } catch (IOException | InterruptedException | ParseException e) {
            e.printStackTrace();
        }

        Collections.reverse(listTopArticles);
        return listTopArticles;
    }

}


public class SolutionRestAPITopArticles {
    public static void main(String[] args) throws IOException {
        // BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        // BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

        // int limit = Integer.parseInt(bufferedReader.readLine().trim());

        List<String> result = Result.topArticles(18);
        System.out.println(result.toString());

        // bufferedWriter.write(
        //     result.stream()
        //         .collect(joining("\n"))
        //     + "\n"
        // );

        // bufferedReader.close();
        // bufferedWriter.close();
    }
}
