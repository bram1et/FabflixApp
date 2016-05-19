package a1138080fabflix.a211.a36.http52.fabflix;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.loopj.android.http.*;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URLEncoder;

public class HomeScreen extends AppCompatActivity {

    private EditText mSearchView;
    private Button mSearchButton;
    private SearchTask mSearchTask = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mSearchView = (EditText) findViewById(R.id.search_box);

        mSearchButton = (Button) findViewById(R.id.search_button);

        mSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String searchTerm = mSearchView.getText().toString();
                mSearchTask = new SearchTask(searchTerm);
                mSearchTask.execute((Void) null);
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Searching....", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    public class SearchTask extends AsyncTask<Void, Void, Boolean> {

        private final String mSearchTerm;

        SearchTask(String searchTerm) {

            mSearchTerm = searchTerm;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            // NOTE: comment out this for loop to test network connection (doesn't work right now)

            // NOTE: not a valid account, register if necessary.
            //return false;

            try {
                //establish network connection
                String urlBase = "http://52.36.211.113:8080/fabflix/typeahead?";
                String urlParams = String.format("query=%s", URLEncoder.encode(mSearchTerm, "UTF-8"));
                String url = urlBase + urlParams;
                MyHTTPConnection httpConnection = new MyHTTPConnection(url);
                httpConnection.setConnectTimeout(5000);
                httpConnection.setReadTimeout(5000);
                httpConnection.setHeader("Accept", "application/json");
                httpConnection.setHeader("Content-type", "application/json");

                int responseCode = httpConnection.getResponseCode();
                Log.d("responseCode", String.valueOf(responseCode));
                if (responseCode == HttpURLConnection.HTTP_CREATED){
                    //IMPORTANT: have server send true if sent userJSON is in customers table
                    String responseMessage = httpConnection.getResponseMessage();
                    Log.d("Debug", responseMessage);
                    if (responseMessage == "true"){
                        return true; //logged in
                    }
                } else {
                    String responseMessage = httpConnection.getResponseMessage();
                    Log.d("Debug", responseMessage);
                }
                // OLD: Simulate network access.
                //Thread.sleep(2000);
            }catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }

            return false;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
//            mAuthTask = null;
//            showProgress(false);

            if (success) {
                Log.d("Debug", "This is dog");
                //NOTE: redirects from login to homescreen
//                Intent i = new Intent(Login.this, HomeScreen.class);

                // Intent i = new Intent(Login.this, ListViewLoader.class);
//                startActivity(i);

                //finish();
            } else {
                Log.d("Debug", "This is NOT dog");
//                mPasswordView.setError(getString(R.string.error_incorrect_password));
//                mPasswordView.requestFocus();
            }
        }

        @Override
        protected void onCancelled() {
//            mAuthTask = null;
//            showProgress(false);
        }
    }

}
