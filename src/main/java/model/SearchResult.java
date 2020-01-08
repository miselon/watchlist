package model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class SearchResult implements Response {

    @JsonProperty("Response")
    private String response;
    @JsonProperty("Search")
    private List<Entry> results = null;
    @JsonProperty("totalResults")
    private int resultCount;

    public boolean getResponse() {
        if(this.response == null)
            return false;
        else return this.response.toLowerCase().equals("true");
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public List<Entry> getResults() {
        return results;
    }

    public void setResults(List<Entry> results) {
        this.results = results;
    }

    public int getResultCount() {
        return resultCount;
    }

    public void setResultCount(int resultCount) {
        this.resultCount = resultCount;
    }
}
