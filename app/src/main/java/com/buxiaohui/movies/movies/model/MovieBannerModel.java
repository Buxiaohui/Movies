package com.buxiaohui.movies.movies.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class MovieBannerModel extends BaseModel implements Serializable {

    private String Title;
    private String Year;
    private String Rated;
    private String Released;
    private String Runtime;
    private String Genre;
    private String Director;
    private String Writer;
    private String Actors;
    private String Plot;
    private String Language;
    private String Country;
    private String Awards;
    private String Poster;
    private List<Ratings> Ratings;
    private String Metascore;
    private String imdbRating;
    private String imdbVotes;
    private String imdbID;
    private String Type;
    private String DVD;
    private String BoxOffice;
    private String Production;
    private String Website;
    private String Response;

    public String getTitle() {
        return Title;
    }

    public void setTitle(String Title) {
        this.Title = Title;
    }

    public String getYear() {
        return Year;
    }

    public void setYear(String Year) {
        this.Year = Year;
    }

    public String getRated() {
        return Rated;
    }

    public void setRated(String Rated) {
        this.Rated = Rated;
    }

    public String getReleased() {
        return Released;
    }

    public void setReleased(String Released) {
        this.Released = Released;
    }

    public String getRuntime() {
        return Runtime;
    }

    public void setRuntime(String Runtime) {
        this.Runtime = Runtime;
    }

    public String getGenre() {
        return Genre;
    }

    public void setGenre(String Genre) {
        this.Genre = Genre;
    }

    public String getDirector() {
        return Director;
    }

    public void setDirector(String Director) {
        this.Director = Director;
    }

    public String getWriter() {
        return Writer;
    }

    public void setWriter(String Writer) {
        this.Writer = Writer;
    }

    public String getActors() {
        return Actors;
    }

    public void setActors(String Actors) {
        this.Actors = Actors;
    }

    public String getPlot() {
        if (Plot == null) {
            Plot = "";
        }
        return Plot;
    }

    public void setPlot(String Plot) {
        this.Plot = Plot;
    }

    public String getLanguage() {
        return Language;
    }

    public void setLanguage(String Language) {
        this.Language = Language;
    }

    public String getCountry() {
        return Country;
    }

    public void setCountry(String Country) {
        this.Country = Country;
    }

    public String getAwards() {
        return Awards;
    }

    public void setAwards(String Awards) {
        this.Awards = Awards;
    }

    public String getPoster() {
        return Poster;
    }

    public void setPoster(String Poster) {
        this.Poster = Poster;
    }

    public List<Ratings> getRatings() {
        return Ratings;
    }

    public void setRatings(List<Ratings> Ratings) {
        this.Ratings = Ratings;
    }

    public String getMetascore() {
        return Metascore;
    }

    public void setMetascore(String Metascore) {
        this.Metascore = Metascore;
    }

    public String getImdbRating() {
        return imdbRating;
    }

    public void setImdbRating(String imdbRating) {
        this.imdbRating = imdbRating;
    }

    public String getImdbVotes() {
        return imdbVotes;
    }

    public void setImdbVotes(String imdbVotes) {
        this.imdbVotes = imdbVotes;
    }

    public String getImdbID() {
        return imdbID;
    }

    public void setImdbID(String imdbID) {
        this.imdbID = imdbID;
    }

    public String getType() {
        return Type;
    }

    public void setType(String Type) {
        this.Type = Type;
    }

    public String getDVD() {
        return DVD;
    }

    public void setDVD(String DVD) {
        this.DVD = DVD;
    }

    public String getBoxOffice() {
        return BoxOffice;
    }

    public void setBoxOffice(String BoxOffice) {
        this.BoxOffice = BoxOffice;
    }

    public String getProduction() {
        return Production;
    }

    public void setProduction(String Production) {
        this.Production = Production;
    }

    public String getWebsite() {
        return Website;
    }

    public void setWebsite(String Website) {
        this.Website = Website;
    }

    public String getResponse() {
        return Response;
    }

    public void setResponse(String Response) {
        this.Response = Response;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("SingleMovieBannerModel{");
        sb.append("Title='").append(Title).append('\'');
        sb.append(", Year='").append(Year).append('\'');
        sb.append(", Rated='").append(Rated).append('\'');
        sb.append(", Released=").append(Released);
        sb.append(", Runtime='").append(Runtime).append('\'');
        sb.append(", Genre='").append(Genre).append('\'');
        sb.append(", Director='").append(Director).append('\'');
        sb.append(", Writer='").append(Writer).append('\'');
        sb.append(", Actors='").append(Actors).append('\'');
        sb.append(", Plot='").append(Plot).append('\'');
        sb.append(", Language='").append(Language).append('\'');
        sb.append(", Country='").append(Country).append('\'');
        sb.append(", Awards='").append(Awards).append('\'');
        sb.append(", Poster='").append(Poster).append('\'');
        sb.append(", Ratings=").append(Ratings);
        sb.append(", Metascore='").append(Metascore).append('\'');
        sb.append(", imdbRating='").append(imdbRating).append('\'');
        sb.append(", imdbVotes='").append(imdbVotes).append('\'');
        sb.append(", imdbID='").append(imdbID).append('\'');
        sb.append(", Type='").append(Type).append('\'');
        sb.append(", DVD=").append(DVD);
        sb.append(", BoxOffice='").append(BoxOffice).append('\'');
        sb.append(", Production='").append(Production).append('\'');
        sb.append(", Website='").append(Website).append('\'');
        sb.append(", Response='").append(Response).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
