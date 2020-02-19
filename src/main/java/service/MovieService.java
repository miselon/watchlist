package service;

import model.Response;

public interface MovieService {

    Response sendQuery(MovieServiceQuery query);

}
