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
import static java.util.stream.Collectors.toList;
import java.net.*;
import org.json.simple.*;
import org.json.simple.parser.*;
import java.net.http.*;
import java.net.URI;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import com.google.gson.*;

class Result2 {

    /*
     * Complete the 'topArticles' function below.
     *
     * The function is expected to return a STRING_ARRAY.
     * The function accepts following parameters:
     *  1. STRING username
     *  2. INTEGER limit
     *
     * base url for copy/paste
     * https://jsonmock.hackerrank.com/api/articles?author=<username>&page=<pageNumber>
     */

    public static List<String> topArticles(String username, int limit) {
        List<String> articles = new ArrayList<>();
        Map<JSONObject, Long> articlesMap = new TreeMap<>(new Comparator<JSONObject>() {

            @Override
            public int compare(JSONObject o1, JSONObject o2) {
                Long numComments1 = o1.get("num_comments") != null ? (Long) o1.get("num_comments") : 0L;
                Long numComments2 = o2.get("num_comments") != null ? (Long) o2.get("num_comments") : 0L;
                int i = numComments1.compareTo(numComments2);

                if (i == 0) {
                    String title1 = o1.get("title") != null ? (String) o1.get("title") : null;
                    String title2 = o2.get("story_title") != null ? (String) o2.get("story_title") : null;

                    if (title1 != null && title2 != null) {
                        i = title1.compareTo(title2);
                    }
                }

                return i;
            }
            
        });
        String baseUrl = "https://jsonmock.hackerrank.com/api/articles?author=%s&page=%s";
        HttpClient client = HttpClient.newHttpClient();
        
        try {
            long totalPages = 1;
            List<JSONObject> dados = new ArrayList<>();
            for (long page = 1; page <= totalPages; page++) {
                HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(String.format(baseUrl, username, page)))
                    .GET()
                    .header("Content-Type", "application/json")
                    .build();
                HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
                JSONParser parser = new JSONParser();
                JSONObject object = (JSONObject) parser.parse(response.body());
                totalPages = object.get("total_pages") != null ? (long) object.get("total_pages") : totalPages;
                JSONArray data = (JSONArray) object.get("data");
                
                if (data != null)
                    dados.addAll(data.stream().toList());
            }
            
            for (JSONObject o : dados) {
                Long comments = o.get("num_comments") != null ? (Long) o.get("num_comments") : 0L;
                articlesMap.put(o, comments);
            }
            
            for (Map.Entry<JSONObject, Long> entry : articlesMap.entrySet()) {
                String title = entry.getKey().get("title") != null ? (String) entry.getKey().get("title") : (String) entry.getKey().get("story_title");
                
                if (title != null)
                    articles.add(title);
            }
        } catch (IOException | InterruptedException | ParseException e) {
            
        }
        
        Collections.reverse(articles);
        int index = articles.size() >= limit ? limit : articles.size();
        return articles.subList(0, index);
    }

}

public class SolutionRestAPITopArticlesByAuthor {
    public static void main(String[] args) throws IOException {
        // BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        // BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

        // String username = bufferedReader.readLine();
        // int limit = Integer.parseInt(bufferedReader.readLine().trim());
        
        String username = "epaga";
        int limit = 2;

        List<String> result = Result2.topArticles(username, limit);
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

