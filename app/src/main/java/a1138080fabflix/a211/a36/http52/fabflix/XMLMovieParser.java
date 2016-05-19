package a1138080fabflix.a211.a36.http52.fabflix;

import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

/**
 * Created by raele on 5/17/2016.
 */
public class XMLMovieParser implements MovieParser{

        private static final String TAG = "XMLMovieParser";

        @Override
        public Movie getMovie(InputStream in) {
            try {
                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                factory.setNamespaceAware(true);
                XmlPullParser parser = factory.newPullParser();

                parser.setInput(in, null);
                return readXML(parser);
            }
            catch(XmlPullParserException e){
                Log.d(TAG, "XMLPullParserException");
                e.printStackTrace();
            }
            return null;
        }

        @Override
        public Movie getMovie(String s) {
            return getMovie(new ByteArrayInputStream(s.getBytes(Charset.defaultCharset())));
        }

        private Movie readXML(XmlPullParser parser) {

            Movie b = new Movie();
            String text = null;

            try {
                int eventType = parser.getEventType();

                while(eventType != XmlPullParser.END_DOCUMENT){
                    switch(eventType){
                        case XmlPullParser.TEXT:
                            text = parser.getText();
                            break;
                        case XmlPullParser.END_TAG:
                            String name = parser.getName();
                            if (name.equals("title")){
                                b.setTitle(text);
                            }else if(name.equals("director")){
                                b.setDirector(text);
                            }else if(name.equals("year")){
                                b.setYear(Integer.valueOf(text));
                            }else if(name.equals("banner url")){
                                b.setBanner_url(text);
                            }else if(name.equals("trailer url")){
                                b.setTrailer_url(text);
                            }else if(name.equals("stars")){
                                b.setStars(text);
                            }else if(name.equals("genres")){
                                b.setGenres(text);
                            }else if(name.equals("entry")){
                                return b;
                            }
                            break;
                        default:
                            break;
                    }
                    eventType = parser.next();
                }
            }
            catch (XmlPullParserException e) {
                Log.d(TAG, "XMLPullParserException");
                e.printStackTrace();
            }
            catch (IOException e){
                Log.d(TAG, "IOException");
                e.printStackTrace();
            }
            return b;
        }

    }
