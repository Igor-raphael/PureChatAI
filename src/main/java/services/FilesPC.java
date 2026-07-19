package services;

import java.io.File;

import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class FilesPC {
	
	public File uploadImages(Stage stage) {
		
		FileChooser chooser = new FileChooser();
				chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Imagens", "*.png", "*.jpg", "*.jpeg", "*.gif", "*.webp"));
		
			return chooser.showOpenDialog(stage);
			
		
	}

}
