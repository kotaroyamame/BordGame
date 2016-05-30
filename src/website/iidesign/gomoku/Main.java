package website.iidesign.gomoku;
import java.io.IOException;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.*;
public class Main  extends Application{
	@Override
	    public void start(Stage stage) throws Exception {
	        // fxml ÇÃì«Ç›çûÇ›
	        Parent root = FXMLLoader.load(getClass().getResource("/index.fxml"));
	        // Scene ÇÃçÏê¨ÅEìoò^
	        Scene scene = new Scene(root);
	        stage.setScene(scene);

	        // ï\é¶
	        stage.show();
	    }

  public static void main(String[] args) throws IOException{
	  Application.launch();

  }
}