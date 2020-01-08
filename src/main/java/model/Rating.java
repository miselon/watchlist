package model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Rating {

    @JsonProperty("Source")
    public String source;
    @JsonProperty("Value")
    public String value;

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
