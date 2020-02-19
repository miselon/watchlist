package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import model.Entry;
import model.FullEntry;
import model.SearchResult;
import service.MovieService;
import service.impl.OMDBMovieServiceImpl;
import service.impl.OMDBQuery;
import service.impl.OMDBQueryParameter;
import service.impl.OMDBSearchType;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;


public class AppViewController {

    @FXML
    Rectangle mainBg;
    @FXML
    Rectangle sideBg;
    @FXML
    ImageView posterHolder;
    @FXML
    Text title;
    @FXML
    Text pgRating;
    @FXML
    Text imdbRating;
    @FXML
    Text metascoreRating;
    @FXML
    Text plot;
    @FXML
    Text director;
    @FXML
    Text starring;
    @FXML
    Text runtime;
    @FXML
    TextField searchField;
    @FXML
    Button searchButton;
    @FXML
    TableView<Entry> searchResultTable;
    TableColumn searchResultTitleCol;
    TableColumn searchResultYearCol;

    private String OMDB_API_KEY = "634c4692";

    private MovieService movieService;
    private SearchResult searchResult;
    private FullEntry selectedEntry;
    private ObservableList<Entry> searchResultList;

    @FXML
    private void initialize(){

        initMovieService();
        initComponents();
        initListeners();

    }

    private void initMovieService(){
        movieService = new OMDBMovieServiceImpl();
    }

    private void initComponents(){

        this.searchResultTitleCol = new TableColumn("Title");
        this.searchResultTitleCol.setPrefWidth(258);
        this.searchResultTitleCol.setResizable(false);
        this.searchResultTitleCol.setCellValueFactory(
                new PropertyValueFactory<Entry, String>("title"));

        this.searchResultYearCol = new TableColumn("Year");
        this.searchResultYearCol.setResizable(false);
        this.searchResultYearCol.setCellValueFactory(
                new PropertyValueFactory<Entry, String>("year"));

        this.searchResultTable.getColumns().addAll(searchResultTitleCol, searchResultYearCol);
        this.searchResultTable.setEditable(false);
        this.searchResultTable.setPlaceholder(new Label("Let's find some movies!"));

        this.posterHolder.setPreserveRatio(true);
    }

    private void initListeners(){

        this.searchButton.setOnAction(click -> {
            this.searchResult = getSearchResult(this.searchField.getText());
            updateSearchResults();
        });

        this.searchResultTable.setOnMouseClicked(click -> {
            Entry tempEntry = this.searchResultTable
                    .getSelectionModel()
                    .getSelectedItem();
            this.selectedEntry = getFullEntry(tempEntry.getImdbID());
            updateSelectedEntry();
        });

    }

    private void updateSearchResults(){

        this.searchResultList = FXCollections.observableArrayList(this.searchResult.getResults());
        this.searchResultTable.setItems(this.searchResultList);

    }

    private void updateSelectedEntry(){

        this.posterHolder.setImage(getPoster(this.selectedEntry.getPoster()));
        this.title.setText(this.selectedEntry.getTitle() + " (" + this.selectedEntry.getYear() + ")");
        this.pgRating.setText(this.selectedEntry.getRated());
        this.imdbRating.setText("IMDB: " + this.selectedEntry.getImdbRating());
        this.metascoreRating.setText("Metascore: " + this.selectedEntry.getMetascore() + "%");
        this.director.setText("Directed by " + this.selectedEntry.getDirector());
        this.starring.setText("Starring " + this.selectedEntry.getActors());
        this.plot.setText(this.selectedEntry.getPlot());
        this.runtime.setText(this.selectedEntry.getRuntime());

    }

    private Image getPoster(String posterURL){

        Image img = null;

        try {

            URL url = new URL(posterURL);
            URLConnection conn = url.openConnection();
            conn.setRequestProperty("User-Agent", "Mozilla/5.0");

            conn.connect();
            InputStream urlStream = conn.getInputStream();
            img = new Image(urlStream);

        }catch (Exception e){

            System.out.println("Poster fetching error");
            return null;

        }

        return img;

    }

    private SearchResult getSearchResult(String title){

        OMDBQuery q = OMDBQuery
                .builder()
                .apiKey(this.OMDB_API_KEY)
                .search(OMDBSearchType.SEARCH, title)
                //.param(OMDBQueryParameter.PLOT_FULL)
                .build();

        return (SearchResult) this.movieService.sendQuery(q);

    }

    private FullEntry getFullEntry(String imdbID){

        OMDBQuery q = OMDBQuery
                .builder()
                .apiKey(this.OMDB_API_KEY)
                .search(OMDBSearchType.ID, imdbID)
                .param(OMDBQueryParameter.PLOT_FULL)
                .build();

        return (FullEntry) this.movieService.sendQuery(q);

    }

}
