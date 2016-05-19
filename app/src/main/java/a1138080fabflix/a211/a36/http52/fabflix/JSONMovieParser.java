package a1138080fabflix.a211.a36.http52.fabflix;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by raele on 5/17/2016.
 */
public class JSONMovieParser implements MovieParser {

    @Override
    public Movie getMovie(InputStream in) {

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            return getMovie(sb.toString());
        }
        catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Movie getMovie(String s) {
        return parseJSON(s);
    }

    public Movie parseJSON(String s){
        try{
            JSONObject jsonRootObject = new JSONObject(s);
            return parseJSON(jsonRootObject);
        }
        catch(JSONException e){
            e.printStackTrace();
        }
        return null;
    }

    public Movie parseJSON(JSONObject rootObj){
        try {
            Movie b = new Movie();
            b.setId(rootObj.getInt("id"));
            b.setTitle(rootObj.getString("title"));
            b.setDirector(rootObj.getString("director"));
            b.setTitle(rootObj.getString("title"));
            b.setYear(rootObj.getInt("year"));
            b.setBanner_url(rootObj.getString("banner url"));
            b.setTrailer_url(rootObj.getString("trailer url"));
            b.setStars(rootObj.getString("stars"));
            b.setGenres(rootObj.getString("genres"));
            return b;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
