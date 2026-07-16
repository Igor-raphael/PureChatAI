package desktop.app;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class InterfaceAPP extends Application {

	@Override
	public void start(Stage stage) throws Exception {
		Label label = new Label("Bem-vindo ao JavaFX");
		
		Scene scene = new Scene(label, 400, 200);
			
		stage.setTitle("Teste inicial");
		
		stage.setScene(scene);
		
		stage.show();
		
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	

}
