package a1138080fabflix.a211.a36.http52.fabflix;

/**
 * Created by raele on 5/17/2016.
 */
import java.util.ArrayList;

public class Movie {
    private String title;
    private String director;
    private int year;
    private int id;
    private String banner_url;
    private String trailer_url;
    String stars;
    String genres;

    public Movie(){
    }
    public Movie(int id, String title, String director, int year, String banner_url, String trailer_url, String stars, String genres) {
        this.id = id;
        this.title = title;
        this.director = director;
        this.year = year;
        this.banner_url = banner_url;
        this.trailer_url = trailer_url;
        this.stars = stars;
        this.genres = genres;
    }

    public Movie(int id, String title, String director, int year, String banner_url, String trailer_url) {
        this.id = id;
        this.title = title;
        this.director = director;
        this.year = year;
        this.banner_url = banner_url;
        this.trailer_url = trailer_url;
        this.stars = "";
        this.genres = "";
    }
    //might not need this
    public Movie(int id, String title){
        this.id = id;
        this.title = title;
    }
    //or this
    public Movie(String title){
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBanner_url() {
        return banner_url;
    }

    public void setBanner_url(String banner_url) {
        this.banner_url = banner_url;
    }

    public String getTrailer_url() {
        return trailer_url;
    }

    public void setTrailer_url(String trailer_url) {
        this.trailer_url = trailer_url;
    }

    public String getStars() {
        return stars;
    }

    public void setStars(String stars) {
        this.stars = stars;
    }

    public String getGenres() {
        return genres;
    }

    public void setGenres(String genres) {
        this.genres = genres;
    }
}
