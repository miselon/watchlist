package service.impl;

import service.MovieServiceQuery;

import java.util.LinkedList;
import java.util.List;

// Zapytanie do API OMDB
public class OMDBQuery implements MovieServiceQuery {

    private String apiKey;
    private OMDBSearchType OMDBSearchType;
    private String searchValue;
    private int year;
    private List<OMDBQueryParameter> parameters;
    private String queryURL;

    // Prywatny konstruktor
    // Zapytanie można stworzyć tylko poprzez buildera
    private OMDBQuery() {
    }

    // Static factory method (by się ładniej budowało)
    public static Builder builder() {
        return new Builder();
    }

    // Builder
    public static final class Builder {

        private final String DOMAIN = "http://www.omdbapi.com/";
        private String apiKey;
        private OMDBSearchType OMDBSearchType;
        private String searchValue;
        private int year;
        private List<OMDBQueryParameter> parameters = new LinkedList<OMDBQueryParameter>();
        private StringBuilder query = new StringBuilder();

        public Builder apiKey(String key) {
            this.apiKey = key;
            this.query.append("&apikey=").append(key);
            return this;
        }

        public Builder search(OMDBSearchType type, String value) {
            this.OMDBSearchType = type;
            value = value.replace(' ', '+');
            this.searchValue = value;
            this.query.append(type.getValue()).append(value);
            return this;
        }

        public Builder year(int year) {
            this.year = year;
            this.query.append("&y=").append(year);
            return this;
        }

        public Builder param(OMDBQueryParameter param) {
            this.parameters.add(param);
            this.query.append(param.getValue());
            return this;
        }

        public OMDBQuery build() {

            if(this.apiKey == null)
                throw new IllegalStateException("No API key.");
            else if(this.OMDBSearchType == null)
                throw new IllegalStateException("No search type specified.");

            this.query.setCharAt(0, '?');

            OMDBQuery queryObj = new OMDBQuery();
            queryObj.apiKey = this.apiKey;
            queryObj.OMDBSearchType = this.OMDBSearchType;
            queryObj.searchValue = this.searchValue;
            queryObj.year = this.year;
            queryObj.parameters = this.parameters;
            queryObj.queryURL = this.DOMAIN + this.query.toString();

            //System.out.println("Query builder output: " + queryObj.queryURL);

            return queryObj;
        }

    }

    // Tylko gettery
    public String getApiKey() {
        return apiKey;
    }

    public OMDBSearchType getOMDBSearchType() {
        return OMDBSearchType;
    }

    public String getSearchValue() {
        return searchValue;
    }

    public int getYear() {
        return year;
    }

    public List<OMDBQueryParameter> getParameters() {
        return parameters;
    }

    public String getQueryURL() {
        return queryURL;
    }

}
