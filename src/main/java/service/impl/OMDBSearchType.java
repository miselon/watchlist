package service.impl;

public enum OMDBSearchType {

    SEARCH("&s="),
    TITLE("&t="),
    ID("&i=");

    private String value;

    OMDBSearchType(String value) {
        this.value = value;
    }

    public String getValue(){
        return this.value;
    }

}
