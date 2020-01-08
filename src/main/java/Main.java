import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.FullEntry;
import service.impl.OMBDQuery;
import service.impl.OMDBMovieServiceImpl;
import service.impl.OMDBQueryParameter;
import service.impl.OMDBSearchType;

public class Main {


    public static void main(String[] args) {

        OMBDQuery q = OMBDQuery
                .builder()
                .apiKey("634c4692")
                .search(OMDBSearchType.TITLE, "batman")
                .param(OMDBQueryParameter.PLOT_FULL)
                .build();

        OMDBMovieServiceImpl ms = new OMDBMovieServiceImpl();
        FullEntry fe = (FullEntry) ms.sendQuery(q);

        System.out.println(fe.getPlot());
        System.out.println(fe.getResponse());

    }

}