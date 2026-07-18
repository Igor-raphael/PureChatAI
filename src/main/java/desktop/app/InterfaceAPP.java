package desktop.app;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.RadialGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Circle;
import javafx.scene.shape.SVGPath;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import services.GeminiService;

public class InterfaceAPP extends Application {

	GeminiService service = new GeminiService();
	
	private VBox centerBox;
	private VBox messagesContainer;
	private ScrollPane messagesScroll;
	private boolean firstMessageSent = false;
	
	private ScrollPane createMessagesArea() {
        messagesContainer = new VBox(16);
        messagesContainer.setPadding(new Insets(40, 60, 40, 60));
        messagesContainer.setAlignment(Pos.TOP_CENTER);

        messagesScroll = new ScrollPane(messagesContainer);
        messagesScroll.setFitToWidth(true);
        messagesScroll.setStyle("-fx-background-color: transparent; -fx-background: transparent;");
        messagesScroll.setPannable(true);

        return messagesScroll;
    }
	
	private HBox createUserBubble(String text) {
	    Text msg = new Text(text);
	    msg.setFont(Font.font("Segoe UI", 14));
	    msg.setFill(Color.web("#1a1a1a"));

	    TextFlow flow = new TextFlow(msg);
	    flow.setMaxWidth(420);
	    flow.setPadding(new Insets(14, 18, 14, 18));

	    StackPane bubble = new StackPane(flow);
	    bubble.setStyle(
	            "-fx-background-color: white;" +
	            "-fx-background-radius: 14;" +
	            "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.08), 8, 0, 0, 2);"
	    );

	    Region sideBar = new Region();
	    sideBar.setPrefWidth(4);
	    sideBar.setStyle("-fx-background-color: #1a1a1a; -fx-background-radius: 2;");

	    HBox bubbleWithBar = new HBox(0, bubble, sideBar);
	    bubbleWithBar.setAlignment(Pos.CENTER_LEFT);

	    Circle avatar = new Circle(18, Color.web("#f2a789"));

	    HBox row = new HBox(10, bubbleWithBar, avatar);
	    row.setAlignment(Pos.CENTER_RIGHT);
	    row.setPadding(new Insets(0, 10, 0, 60));
	    return row;
	}
	
	private HBox createAiBubble(String text) {
	    Text msg = new Text(text);
	    msg.setFont(Font.font("Segoe UI", 14));
	    msg.setFill(Color.web("#1a1a1a"));

	    TextFlow flow = new TextFlow(msg);
	    flow.setMaxWidth(360);
	    flow.setPadding(new Insets(14, 18, 14, 18));

	    StackPane bubble = new StackPane(flow);
	    bubble.setStyle(
	            "-fx-background-color: white;" +
	            "-fx-background-radius: 14;" +
	            "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.08), 8, 0, 0, 2);"
	    );

	    Region sideBar = new Region();
	    sideBar.setPrefWidth(4);
	    sideBar.setStyle("-fx-background-color: #2f6fed; -fx-background-radius: 2;");

	    HBox bubbleWithBar = new HBox(0, sideBar, bubble);
	    bubbleWithBar.setAlignment(Pos.CENTER_LEFT);

	    Circle avatar = new Circle(18, Color.web("#7fd8c9"));

	    HBox row = new HBox(10, avatar, bubbleWithBar);
	    row.setAlignment(Pos.CENTER_LEFT);
	    row.setPadding(new Insets(0, 60, 0, 10));
	    return row;
	}
	
	@Override
	public void start(Stage stage) throws Exception {
		stage.getIcons().add(new Image(getClass().getResourceAsStream("/images/icon.png")));
		
		 // ---------- Center content (icon + title) ----------
	    centerBox = new VBox(18);
	    centerBox.setAlignment(Pos.CENTER);

	    Text title = new Text("Olá!");
	    title.setFont(Font.font("Segoe UI", FontWeight.MEDIUM, 32));
	    title.setFill(Color.web("#1a1a1a"));

	    centerBox.getChildren().add(title);

	    // ---------- Área de mensagens ----------
	    ScrollPane messagesArea = createMessagesArea();

	    // centerBox fica sobreposto à área de mensagens (visível só enquanto vazia)
	    StackPane centerStack = new StackPane(messagesArea, centerBox);

	    // ---------- Barra de input ----------
	    HBox inputBar = createInputBar();
	    VBox bottomBox = new VBox(inputBar);
	    bottomBox.setAlignment(Pos.CENTER);
	    bottomBox.setPadding(new Insets(0, 0, 40, 0));

	    // ---------- Root com BorderPane ----------
	    BorderPane root = new BorderPane();
	    root.setBackground(new Background(new BackgroundFill(
	            new RadialGradient(
	                    0, 0, 0.5, 0.75, 1.1, true, CycleMethod.NO_CYCLE,
	                    new Stop(0, Color.web("#e9e9e9")),
	                    new Stop(0.55, Color.web("#f4f4f4")),
	                    new Stop(1, Color.web("#ffffff"))
	            ),
	            CornerRadii.EMPTY, Insets.EMPTY
	    )));

	    root.setCenter(centerStack);
	    root.setBottom(bottomBox);

	    Scene scene = new Scene(root, 1280, 832);
	    stage.setTitle("PureChat AI");
	    stage.setScene(scene);
	    stage.show();
    }
 

 
    // ---------- Input bar with rounded field + send button ----------
    private HBox createInputBar() {
        TextField inputField = new TextField();
        inputField.setPromptText("Pergunte alguma coisa");
        inputField.setPrefHeight(52);
        inputField.setPrefWidth(820);
        inputField.setStyle(
                "-fx-background-color: white;" +
                "-fx-background-radius: 26;" +
                "-fx-border-radius: 26;" +
                "-fx-border-color: #d9d9d9;" +
                "-fx-border-width: 1;" +
                "-fx-font-size: 18px;" +
                "-fx-padding: 0 20 0 20;" +
                "-fx-prompt-text-fill: #9a9a9a;"
        );
 
        Button sendButton = new Button();
        sendButton.setPrefSize(52, 52);
        sendButton.setStyle(
                "-fx-background-color: white;" +
                "-fx-background-radius: 26;" +
                "-fx-border-radius: 26;" +
                "-fx-border-color: #d9d9d9;" +
                "-fx-border-width: 1;" +
                "-fx-cursor: hand;"
        );
        
        inputField.setOnAction(e -> sendButton.fire());
 
        SVGPath sendIcon = new SVGPath();
        sendIcon.setContent("M 2 12 L 22 2 L 14 22 L 11 13 L 2 12 Z");
        sendIcon.setFill(Color.web("#9a9a9a"));
        sendIcon.setScaleX(0.8);
        sendIcon.setScaleY(0.8);
        sendButton.setGraphic(sendIcon);
 
        sendButton.setOnAction(e -> {
            String message = inputField.getText();
            if (message != null && !message.isBlank()) {
            	
            	appendUserMessage(message);
            	//service.gerarConteudo(message);
         
            	appendAiMessage("Ola como posso ajudar");
            	
                System.out.println("Mensagem enviada: " + message);
                inputField.clear();
            }
        });
 
        HBox bar = new HBox(10, inputField, sendButton);
        bar.setAlignment(Pos.CENTER);
        bar.setPadding(new Insets(0, 20, 0, 20));
 
        // Subtle drop shadow container feel via background wrapper
        HBox wrapper = new HBox(bar);
        wrapper.setAlignment(Pos.CENTER);
        return wrapper;

	}
    
    private void appendUserMessage(String text) {
        hideGreetingIfNeeded();
        messagesContainer.getChildren().add(createUserBubble(text));
        scrollToBottom();
    }

    private void appendAiMessage(String text) {
        hideGreetingIfNeeded();
        messagesContainer.getChildren().add(createAiBubble(text));
        scrollToBottom();
    }

    private void hideGreetingIfNeeded() {
        if (!firstMessageSent) {
            firstMessageSent = true;
            centerBox.setVisible(false);
            centerBox.setManaged(false);
        }
    }

    private void scrollToBottom() {
        Platform.runLater(() -> messagesScroll.setVvalue(1.0));
    }
	
	public static void main(String[] args) {
		launch(args);
	}
	

}
