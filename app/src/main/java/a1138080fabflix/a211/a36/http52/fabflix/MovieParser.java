package a1138080fabflix.a211.a36.http52.fabflix;

/**
 * Created by raele on 5/17/2016.
 */

import java.io.InputStream;

public interface MovieParser {

    Movie getMovie(InputStream in);
    Movie getMovie(String s);
}
