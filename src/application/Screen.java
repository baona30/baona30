package application;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public interface Screen {
	public String getScreen();
	public String getTitle();
	
	public default void loadScreen() {
        try {
        	Scene scene = FXMLLoader.load(getClass().getResource(getScreen()));
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle(getTitle());
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(FXMain.class.getName()).log(Level.SEVERE, null, ex);
        }        
	}	
}
