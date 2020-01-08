package service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import service.MovieService;
import utill.exceptions.MovieServiceException;
import model.FullEntry;
import model.Response;
import model.SearchResult;

import java.net.HttpURLConnection;
import java.net.URL;

public class OMDBMovieServiceImpl implements MovieService {

    // Jackson object mapper
    private ObjectMapper objectMapper;

    public OMDBMovieServiceImpl() {
        this.objectMapper = new ObjectMapper();
    }

    // Wysyłanie zapytania do API
    public Response sendQuery(OMBDQuery query) throws MovieServiceException {

        HttpURLConnection connection = null;
        URL url;

        try {
            // Konfiguracja połączenia
            url = new URL(query.getQueryURL());
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Accept", "application/json");

            // Wyrzuca MovieServiceException przy złej odpowiedzi HTTP
            if(connection.getResponseCode() != 200)
                throw new MovieServiceException("HTTP " + connection.getResponseCode());

            Response ret;

            // Zwraca SearchResult
            if(query.getOMDBSearchType().equals(OMDBSearchType.SEARCH))
                        ret = this.objectMapper.readValue(connection.getInputStream(), SearchResult.class);
            // Zwraca FullEntry
            else if(query.getOMDBSearchType().equals(OMDBSearchType.TITLE) ||
                    query.getOMDBSearchType().equals(OMDBSearchType.ID))
                        ret = this.objectMapper.readValue(connection.getInputStream(), FullEntry.class);

            // W razie gdyby coś poszło nie tak
            else throw new MovieServiceException("Unknown error");

            connection.disconnect();
            return ret;

          // Wyrzuca MovieServiceException przy braku wyników
        } catch (UnrecognizedPropertyException e){
            connection.disconnect();
            throw new MovieServiceException("No results");
          // Wyrzuca MovieServiceException jeśli coś pójdzie nie tak
        } catch (Exception e) {
            connection.disconnect();
            throw new MovieServiceException(e.getMessage());
        }

    }

}
