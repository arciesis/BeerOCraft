package xyz.beerocraft.view;

import javafx.application.Preloader;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class BeerOCraftPreloader extends Preloader {
    ProgressBar progressBar;
    Stage preloaderStage;

    private Scene createPreloaderScene(){
        progressBar = new ProgressBar();

        BorderPane p = new BorderPane();
        p.setBottom(progressBar);

        return new Scene(p,300,150);
    }

    @Override
    public void start(Stage stage) throws Exception{
        this.preloaderStage = stage;
        this.preloaderStage.setScene(createPreloaderScene());
        this.preloaderStage.show();
    }


    public void handleProgressNotification(Preloader.ProgressNotification pn){
        progressBar.setProgress(pn.getProgress());
    }


    public void handleStateChangeNotification(StateChangeNotification evt){
        if (evt.getType() == StateChangeNotification.Type.BEFORE_START){
            this.preloaderStage.hide();
        }
    }

}
