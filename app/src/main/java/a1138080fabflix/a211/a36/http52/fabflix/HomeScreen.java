package a1138080fabflix.a211.a36.http52.fabflix;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URLEncoder;

public class HomeScreen extends AppCompatActivity {

    private EditText mSearchText;
    private Button mSearchButton;
    private TextView mMovieTextView;
    private View mSearchView;
    private View mProgressView;
    private SearchTask mSearchTask = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mSearchText = (EditText) findViewById(R.id.search_box);
        mSearchButton = (Button) findViewById(R.id.search_button);
        mMovieTextView = (TextView) findViewById(R.id.movie_results);
        mSearchView = findViewById(R.id.search_view);
        mProgressView = findViewById(R.id.search_progress);

        mSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showProgress(true);
                String searchTerm = mSearchText.getText().toString();
                mSearchTask = new SearchTask(searchTerm);
                mSearchTask.execute((Void) null);
            }
        });

    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mMovieTextView.setVisibility(show ? View.GONE : View.VISIBLE);
            mMovieTextView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mMovieTextView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mMovieTextView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }


    public class SearchTask extends AsyncTask<Void, Void, Boolean> {

        private final String mSearchTerm;
        private String jsonResponse;

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
                String urlParams = String.format("query=%s&limit=-1", URLEncoder.encode(mSearchTerm, "UTF-8"));
                String url = urlBase + urlParams;
                MyHTTPConnection httpConnection = new MyHTTPConnection(url);
                httpConnection.setConnectTimeout(5000);
                httpConnection.setReadTimeout(5000);
                httpConnection.setHeader("Accept", "application/json");
                httpConnection.setHeader("Content-type", "application/json");

                int responseCode = httpConnection.getResponseCode();
                Log.d("responseCode", String.valueOf(responseCode));
                if (responseCode == 200){
                    //IMPORTANT: have server send true if sent userJSON is in customers table
                    jsonResponse = httpConnection.getResponseMessage();
                    return  true;
                } else {
                    String responseMessage = httpConnection.getResponseMessage();
                    Log.d("Debug", responseMessage);
                    return false;
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
                Log.d("Debug", jsonResponse);
                try {
                    String movieListString = "";
                    JSONObject jsonObject = new JSONObject(jsonResponse);
                    JSONArray moviesArray = jsonObject.getJSONArray("movies");
                    if (moviesArray.length() > 0) {
                        for (int i = 0; i < moviesArray.length(); i++) {
                            movieListString += moviesArray.get(i) + "\n\n";
                        }
                    } else {
                        movieListString = "No Movies Found :(";
                    }
                    showProgress(false);
                    mMovieTextView.setText(movieListString);
                } catch (JSONException jsonE) {
                    showProgress(false);
                }


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
