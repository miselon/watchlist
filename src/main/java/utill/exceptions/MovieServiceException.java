package utill.exceptions;

public class MovieServiceException extends RuntimeException {

    public MovieServiceException(String errorMsg) {
        super(errorMsg);
    }

}
