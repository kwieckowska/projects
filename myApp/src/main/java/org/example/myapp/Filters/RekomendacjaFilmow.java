package org.example.myapp.Filters;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class RekomendacjaFilmow {

    private static final String API_KEY = "74bf7c4678d4d7faac699ff91b5b360a";

    private static List<Integer> getDirectorIdsByNames(List<String> directorNames) {
        List<Integer> directorIds = new ArrayList<>();

        for (String directorName : directorNames) {
            try {
                String encodedDirectorName = URLEncoder.encode(directorName, StandardCharsets.UTF_8);
                String searchUrl = "https://api.themoviedb.org/3/search/person?api_key=" + API_KEY + "&query=" + encodedDirectorName;
                String response = sendGetRequest(searchUrl);
                JSONObject json = new JSONObject(response);

                if (json.getJSONArray("results").length() > 0) {
                    JSONObject director = json.getJSONArray("results").getJSONObject(0);
                    directorIds.add(director.getInt("id"));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return directorIds;
    }

    private static List<Integer> getActorIdsByNames(List<String> actorNames) {
        List<Integer> actorIds = new ArrayList<>();

        for (String actorName : actorNames) {
            try {
                String encodedActorName = URLEncoder.encode(actorName, StandardCharsets.UTF_8);
                String searchUrl = "https://api.themoviedb.org/3/search/person?api_key=" + API_KEY + "&query=" + encodedActorName;
                String response = sendGetRequest(searchUrl);
                JSONObject json = new JSONObject(response);

                if (json.getJSONArray("results").length() > 0) {
                    JSONObject actor = json.getJSONArray("results").getJSONObject(0);
                    actorIds.add(actor.getInt("id"));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return actorIds;
    }

    public String getMovieTitleById(int movieId) {
        try {
            String movieUrl = "https://api.themoviedb.org/3/movie/" + movieId + "?api_key=" + API_KEY;
            String response = sendGetRequest(movieUrl);
            JSONObject json = new JSONObject(response);
            return json.getString("title");
        } catch (Exception e) {
            e.printStackTrace();
            return "Nieznany film";
        }
    }

    public List<Integer> getMoviesByFilters(String genreNames, String startYear, String endYear, int minRuntime, int maxRuntime, Boolean includeAdult, String language, String sortBy, List<String> directorNames, List<String> actorNames) {
        List<Integer> movieIds = new ArrayList<>();

        try {
            Map<String, Integer> genreMap = getGenreMap();

            StringBuilder genreIds = new StringBuilder();
            if (!genreNames.trim().isEmpty()) {
                String[] genresArray = genreNames.split(",");
                for (String genreName : genresArray) {
                    genreName = genreName.trim().toLowerCase();
                    if (genreMap.containsKey(genreName)) {
                        if (genreIds.length() > 0) {
                            genreIds.append("|");
                        }
                        genreIds.append(genreMap.get(genreName));
                    }
                }
            }
            List<Integer> directorIds = new ArrayList<>();
            if (!directorNames.isEmpty()){
                directorIds = getDirectorIdsByNames(directorNames);
            }


            List<Integer> actorIds = getActorIdsByNames(actorNames);

            StringBuilder directorIdsString = new StringBuilder();

            if (!directorIds.isEmpty()){  // IF
                for (Integer directorId : directorIds) {
                    if (directorIdsString.length() > 0) {
                        directorIdsString.append("|");
                    }
                    directorIdsString.append(directorId);
                }
            }

            //DODANE
            if (directorIds.isEmpty()){
                directorIdsString = new StringBuilder("");
            }

            StringBuilder actorIdsString = new StringBuilder();
            for (Integer actorId : actorIds) {
                if (actorIdsString.length() > 0) {
                    actorIdsString.append("|");
                }
                actorIdsString.append(actorId);
            }
            // DODANE
            if (actorIds.isEmpty()){
                actorIdsString = new StringBuilder("");
            }

            if (startYear == null || startYear.isEmpty()) {
                startYear = "1900";
            }
            if (endYear == null || endYear.isEmpty()) {
                endYear = String.valueOf(java.time.Year.now().getValue());
            }

            int page = 1;
            int totalPages = 1;

            do {  // POPRAWIONE
                String discoverUrl = "https://api.themoviedb.org/3/discover/movie?api_key=" + API_KEY
                        + "&with_genres=" + genreIds
                        + "&sort_by=" + sortBy
                        + "&primary_release_date.gte=" + startYear + "-01-01"
                        + "&primary_release_date.lte=" + endYear + "-12-31"
                        + "&with_runtime.gte=" + minRuntime
                        + "&with_runtime.lte=" + maxRuntime
                        + "&include_adult=" + includeAdult
                        + "&with_original_language=" + language;

                if (directorIdsString.length() != 0) {
                    discoverUrl += "&with_crew=" + directorIdsString;
                }

                if (actorIdsString.length() != 0) {
                    discoverUrl += "&with_cast=" + actorIdsString;
                }

                discoverUrl += "&page=" + page;


                String response = sendGetRequest(discoverUrl);
                JSONObject json = new JSONObject(response);


//          ZAKOMENTUJ
//                totalPages = json.getInt("total_pages");

                JSONArray results = json.getJSONArray("results");
                for (int i = 0; i < results.length(); i++) {
                    if (movieIds.size() >= 10) break;
                    JSONObject movie = results.getJSONObject(i);
                    movieIds.add(movie.getInt("id"));
                }

                page++;
            } while (page <= totalPages);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return movieIds;
    }

    private static Map<String, Integer> getGenreMap() {
        Map<String, Integer> genreMap = new HashMap<>();
        try {
            String genreUrl = "https://api.themoviedb.org/3/genre/movie/list?api_key=" + API_KEY;
            String response = sendGetRequest(genreUrl);
            JSONObject json = new JSONObject(response);
            JSONArray genres = json.getJSONArray("genres");

            for (int i = 0; i < genres.length(); i++) {
                JSONObject genre = genres.getJSONObject(i);
                genreMap.put(genre.getString("name").toLowerCase(), genre.getInt("id"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return genreMap;
    }

    public Map<String, String> getMovieDetailsById(int movieId) {
        Map<String, String> movieDetails = new HashMap<>();
        try {
            String movieUrl = "https://api.themoviedb.org/3/movie/" + movieId + "?api_key=" + API_KEY;
            String response = sendGetRequest(movieUrl);
            JSONObject json = new JSONObject(response);

            movieDetails.put("Title", json.getString("title"));
            movieDetails.put("Overview", json.optString("overview", "Brak opisu"));

            String posterPath = json.optString("poster_path", "");
            if (!posterPath.isEmpty()) {
                movieDetails.put("Poster", "https://image.tmdb.org/t/p/w500" + posterPath);
            } else {
                movieDetails.put("Poster", "");
            }

            JSONArray genres = json.getJSONArray("genres");
            StringBuilder genresString = new StringBuilder();
            for (int i = 0; i < genres.length(); i++) {
                JSONObject genre = genres.getJSONObject(i);
                genresString.append(genre.getString("name"));
                if (i < genres.length() - 1) {
                    genresString.append(", ");
                }
            }
            movieDetails.put("Genres", genresString.toString());

            int runtime = json.optInt("runtime", 0);
            movieDetails.put("Runtime", runtime > 0 ? runtime + " min" : "Brak danych");

            JSONObject credits = sendCreditsRequest(movieId);
            JSONArray crew = credits.getJSONArray("crew");
            String director = "";
            for (int i = 0; i < crew.length(); i++) {
                JSONObject person = crew.getJSONObject(i);
                if (person.getString("job").equals("Director")) {
                    director = person.getString("name");
                    break;
                }
            }
            movieDetails.put("Director", director);

            JSONArray cast = credits.getJSONArray("cast");
            StringBuilder actorsString = new StringBuilder();
            for (int i = 0; i < cast.length(); i++) {
                JSONObject actor = cast.getJSONObject(i);
                actorsString.append(actor.getString("name"));
                if (i < cast.length() - 1) {
                    actorsString.append(", ");
                }
            }
            movieDetails.put("Actors", actorsString.toString());

            movieDetails.put("Original Language", json.getString("original_language"));
            movieDetails.put("Release Year", json.getString("release_date").split("-")[0]);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return movieDetails;
    }

    private static JSONObject sendCreditsRequest(int movieId) {
        try {
            String creditsUrl = "https://api.themoviedb.org/3/movie/" + movieId + "/credits?api_key=" + API_KEY;
            String response = sendGetRequest(creditsUrl);
            return new JSONObject(response);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new JSONObject();
    }

    private static String sendGetRequest(String urlString) throws Exception {
        URL url = new URL(urlString);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        return response.toString();
    }
}