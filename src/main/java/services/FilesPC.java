package services;

import java.io.File;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class FilesPC {
	
	public File uploadImages(Stage stage) {
		
		FileChooser chooser = new FileChooser();
				chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Imagens", "*.png", "*.jpg", "*.jpg"));
		
				File file = chooser.showOpenDialog(stage);
				if (file != null) {
					Image img = new Image(file.toURI().toString());
					ImageView view = new ImageView();
				}
				
		return file;
	}

}
