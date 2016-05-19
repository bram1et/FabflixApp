package a1138080fabflix.a211.a36.http52.fabflix;

/**
 * Created by Raelene on 5/10/2016.
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MyHTTPConnection {

    //
    private HttpURLConnection connection;

    public MyHTTPConnection(String url) throws IOException {
        connection = (HttpURLConnection) (new URL(url)).openConnection();
    }

    public void setConnectTimeout(int timeoutMillis){
        connection.setConnectTimeout(timeoutMillis);  //set timeout for connection
    }

    public void setReadTimeout(int timeoutMillis){
        connection.setReadTimeout(timeoutMillis);  //set timeout for response
    }

    public void setHeader(String field, String newValue){
        connection.setRequestProperty(field, newValue);
    }

    public int getResponseCode() throws IOException {
        return connection.getResponseCode();
    }

    public String getResponseMessage() throws IOException {

        InputStream in = null;

        try {
            in = connection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            return sb.toString();
        } catch (IOException e) {
            throw e;
        } finally {
            if (in != null){
                in.close();
            }
        }
    }

    public void setRequestMessage(String s) throws IOException{
        connection.setDoOutput(true); //implicitly POST
        connection.setRequestMethod( "POST" );
        OutputStreamWriter out = null;
        connection.getOutputStream().write(s.getBytes());
//        try {
//            out = new OutputStreamWriter(connection.getOutputStream());
//            out.write(s);
//        } catch (IOException e) {
//            throw e;
//        } finally {
//            if (out != null){
//                out.close();
//            }
//        }
    }
}
