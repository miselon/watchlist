package service.impl;

public enum OMDBQueryParameter {

    TYPE_MOVIE("&type=movie"),
    TYPE_SHOW("&type=show"),
    TYPE_EPISODE("&type=episode"),
    PLOT_SHORT("&plot=short"),
    PLOT_FULL("&plot=full");

    private String value;

    OMDBQueryParameter(String value) {
        this.value = value;
    }

    public String getValue(){
        return this.value;
    }

}
