package com.simplertutorials.android.milestonev2.DataHolder;

import com.simplertutorials.android.milestonev2.domain.Company;
import com.simplertutorials.android.milestonev2.domain.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

public class ApiRequest {

    private static ApiRequest instance = new ApiRequest();
    private static int numberOfAvailablePage =1;


    public ApiRequest() {
    }

    public HashMap<Integer, String> fetchGenreList(String languageCode) throws MalformedURLException, JSONException {
        String requestUrl = "https://api.themoviedb.org/3/genre/movie/list?api_key=" +
                DataHolder.getInstance().getApiKey()+"&language="+languageCode;

        HashMap<Integer, String> map = new HashMap<>();
        URL url = new URL(requestUrl);

        JSONObject genreJson = getJSONFromApi(url);
        JSONArray genreList = genreJson.getJSONArray("genres");

        for (int i = 0; i < genreList.length(); i++) {
            JSONObject jsonSingleGenre = genreList.getJSONObject(i);
            int genreId = jsonSingleGenre.getInt("id");
            String genreName = jsonSingleGenre.getString("name");

            map.put(genreId, genreName);
        }
        return map;
    }

    public ArrayList<Movie> requestPopularMovies(int page, String language) throws MalformedURLException, JSONException {

        ArrayList<Movie> movieList = new ArrayList<>();

        String urlString = DataHolder.getInstance().getApiBaseMoviesUrl()
                + "/popular?api_key=" + DataHolder.getInstance().getApiKey()
                + "&language=" + language
                + "&page=" + String.valueOf(page);

        URL url = new URL(urlString);

        JSONObject jsonMovies = getJSONFromApi(url);

        numberOfAvailablePage = jsonMovies.getInt("total_pages");
        JSONArray resultList = jsonMovies.getJSONArray("results");

        for (int i = 0; i < resultList.length(); i++) {
            JSONObject jsonSingleMovie = resultList.getJSONObject(i);
            Movie currentMovie = createMovieFromJSON(jsonSingleMovie, false);
            movieList.add(currentMovie);

        }
        return movieList;
    }

    private Movie createMovieFromJSON(JSONObject jsonSingleMovie, boolean isDetailed) throws JSONException {

        //this parts is same for both,  movie Detail json and movie list json
        Movie movie = new Movie(jsonSingleMovie.getString("id"),
                jsonSingleMovie.getString("title"),
                jsonSingleMovie.getString("overview"));

        movie = getBasicInfoFromJSON(movie, jsonSingleMovie);


        //This part is for movie details json.
        if (isDetailed) {
            movie = getDetailedInfoFromJSON(movie, jsonSingleMovie);
        } else {
            //There is difference between detailed json genre...
            JSONArray genreArray = jsonSingleMovie.getJSONArray("genre_ids");
            for (int j = 0; j < genreArray.length(); j++) {
                movie.getGenreIds().add((Integer) genreArray.get(j));
            }
        }

        return movie;
    }

    private Movie getDetailedInfoFromJSON(Movie movie, JSONObject jsonSingleMovie) throws JSONException {

        movie.setBudget(jsonSingleMovie.getInt("budget"));

        JSONArray genreArray = jsonSingleMovie.getJSONArray("genres");
        for (int j = 0; j < genreArray.length(); j++) {
            JSONObject genre = genreArray.getJSONObject(j);
            movie.getGenreIds().add((Integer) genre.getInt("id"));
        }

        movie.setHomePage(jsonSingleMovie.getString("homepage"));
        movie.setImdbLink(jsonSingleMovie.getString("imdb_id"));

        JSONArray companyArray = jsonSingleMovie.getJSONArray("production_companies");
        for (int j = 0; j < companyArray.length(); j++) {
            JSONObject companyJSON = companyArray.getJSONObject(j);
            movie.getCompanies().add(createCompanyFromJSON(companyJSON));
        }

        JSONArray countriesArray = jsonSingleMovie.getJSONArray("production_countries");
        for (int j = 0; j < countriesArray.length(); j++) {
            JSONObject countryJSON = countriesArray.getJSONObject(j);
            movie.getCountries().add(countryJSON.getString("name"));
        }

        movie.setRevenue(jsonSingleMovie.getInt("revenue"));
        movie.setRunTime(jsonSingleMovie.getInt("runtime"));

        JSONArray languagesArray = jsonSingleMovie.getJSONArray("spoken_languages");
        for (int j = 0; j < languagesArray.length(); j++) {
            JSONObject languagesJSON = languagesArray.getJSONObject(j);
            movie.getLaguages().add(languagesJSON.getString("name"));
        }

        movie.setStatus(jsonSingleMovie.getString("status"));
        movie.setTagLine(jsonSingleMovie.getString("tagline"));
        return movie;
    }

    private Movie getBasicInfoFromJSON(Movie movie, JSONObject jsonSingleMovie) throws JSONException {

        movie.setVoteCount(jsonSingleMovie.getInt("vote_count"));
        movie.setVoteAverage(jsonSingleMovie.getDouble("vote_average"));
        movie.setPopularity(jsonSingleMovie.getInt("popularity"));
        movie.setPosterUrl(jsonSingleMovie.getString("poster_path"));
        movie.setOriginalLanguage(jsonSingleMovie.getString("original_language"));
        movie.setOriginalTitle(jsonSingleMovie.getString("original_title"));
        movie.setBackdropUrl(jsonSingleMovie.getString("backdrop_path"));
        movie.setReleaseDate(jsonSingleMovie.getString("release_date"));
        return movie;
    }

    private Company createCompanyFromJSON(JSONObject companyJSON) throws JSONException {
        Company company = new Company(companyJSON.getInt("id"));
        company.setLogoUrl(companyJSON.getString("logo_path"));
        company.setName(companyJSON.getString("name"));
        company.setCountry(companyJSON.getString("origin_country"));
        return company;
    }

    private JSONObject getJSONFromApi(URL url) throws JSONException {

        HttpURLConnection urlConnection = null;
        InputStreamReader inputStreamReader = null;
        StringBuilder stringBuilder = new StringBuilder();
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.setDoOutput(true);
            urlConnection.connect();

            inputStreamReader = new InputStreamReader(url.openStream());

            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            String line;

            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line + "\n");
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStreamReader != null) {
                try {
                    inputStreamReader.close();
                } catch (IOException ignore) {

                }
            }

            if (urlConnection != null)
                urlConnection.disconnect();
        }


        return new JSONObject(stringBuilder.toString());
    }

    public static ApiRequest getInstance() {
        return instance;
    }

    public static int getNumberOfAvailablePage() {
        return numberOfAvailablePage;
    }

    public Movie getMovieById(int movieId, String language) throws MalformedURLException, JSONException {
        String requestUrl = DataHolder.getInstance().getApiBaseMoviesUrl()
                + "/" + movieId
                + "?api_key=" + DataHolder.getInstance().getApiKey()
                + "&language=" + language;

        URL url = new URL(requestUrl);

        JSONObject jsonMovieDetails = getJSONFromApi(url);

        return createMovieFromJSON(jsonMovieDetails, true);
    }
}
