package desktop.app;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class InterfaceAPP extends Application {

	@Override
	public void start(Stage stage) throws Exception {
		stage.getIcons().add(new Image(getClass().getResourceAsStream("/images/icon.png")));
		stage.setTitle("PureChat AI");
		
		BorderPane root = new BorderPane();
		
		VBox chatContainer = new VBox(10);
		chatContainer.setPadding(new Insets(20));
		
		ScrollPane scroll = new ScrollPane(chatContainer);
		
		scroll.setFitToWidth(true);
		scroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
		scroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
		
		root.setCenter(scroll);
		
		Label msg1 = new Label("Olá! Eu sou o PureChat AI.");
		Label msg2 = new Label("Pode me perguntar qualquer coisa.");

		chatContainer.getChildren().add(
		        new Label("Mensagem 1"));

		chatContainer.getChildren().add(
		        new Label("Mensagem 2"));

		chatContainer.getChildren().add(
		        new Label("Mensagem 3"));
		
		chatContainer.getChildren().addAll(msg1, msg2);
		
		Scene scene = new Scene(root, 650, 500);
			
		stage.setScene(scene);
		
		stage.show();
		
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	

}
