package data;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URLEncoder;
import java.net.URL;
import org.json.JSONArray;
import org.json.JSONObject;

public class LocationSearch {

    public static class SearchResult {
        public double latitude;
        public double longitude;
        public String type;
    }
    

    public static SearchResult search(String query) {
    try {
        String encodedQuery = URLEncoder.encode(query, "UTF-8");
        String urlStr = "https://nominatim.openstreetmap.org/search?q=" + encodedQuery + "&format=json&addressdetails=1&limit=5";
        URL url = new URL(urlStr);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestProperty("User-Agent", "Mozilla/5.0");

        BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }
        reader.close();

        JSONArray results = new JSONArray(sb.toString());
        if (results.length() == 0) return null;

        // Önce şehir tipi ("city", "town", "village") olanı bulmaya çalış
        for (int i = 0; i < results.length(); i++) {
            JSONObject place = results.getJSONObject(i);
            String type = place.getString("type");
            if (type.equals("city") || type.equals("town") || type.equals("village")) {
                SearchResult result = new SearchResult();
                result.latitude = place.getDouble("lat");
                result.longitude = place.getDouble("lon");
                result.type = type;
                return result;
            }
        }

        // Eğer şehir tipi yoksa ilk gelen sonucu kullan (örneğin "administrative" olabilir)
        JSONObject place = results.getJSONObject(0);
        SearchResult result = new SearchResult();
        result.latitude = place.getDouble("lat");
        result.longitude = place.getDouble("lon");
        result.type = place.getString("type");
        return result;

    } catch (Exception e) {
        e.printStackTrace();
    }
    return null;
}

}
