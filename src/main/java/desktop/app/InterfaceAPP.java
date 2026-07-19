package desktop.app;

import java.io.File;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
import services.FilesPC;
import services.GeminiService;

public class InterfaceAPP extends Application {

    GeminiService service = new GeminiService();
    FilesPC uploadService = new FilesPC();

    private File arquivoSelecionado = null;
    private VBox centerBox;
    private VBox messagesContainer;
    private ScrollPane messagesScroll;
    private boolean firstMessageSent = false;
    private ImageView previewImage = new ImageView();
    private Stage primaryStage;

    // ----------------------------------------------------------------
    // Área de mensagens
    // ----------------------------------------------------------------
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

    // ----------------------------------------------------------------
    // Bolha do usuário — só texto
    // ----------------------------------------------------------------
    private HBox createUserBubble(String text) {
        return createUserBubble(text, null);
    }

    // ----------------------------------------------------------------
    // Bolha do usuário — texto + imagem opcional
    // ----------------------------------------------------------------
    private HBox createUserBubble(String text, File imageFile) {
        VBox content = new VBox(8);
        content.setPadding(new Insets(14, 18, 14, 18));

        if (imageFile != null) {
            ImageView imgView = new ImageView(new Image(imageFile.toURI().toString()));
            imgView.setFitWidth(200);
            imgView.setFitHeight(150);
            imgView.setPreserveRatio(true);
            content.getChildren().add(imgView);
        }

        if (text != null && !text.isBlank()) {
            Text msg = new Text(text);
            msg.setFont(Font.font("Segoe UI", 14));
            msg.setFill(Color.web("#1a1a1a"));
            TextFlow flow = new TextFlow(msg);
            flow.setMaxWidth(500);
            content.getChildren().add(flow);
        }

        StackPane bubble = new StackPane(content);
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

    // ----------------------------------------------------------------
    // Bolha da IA
    // ----------------------------------------------------------------
    private HBox createAiBubble(String text) {
        Text msg = new Text(text);
        msg.setFont(Font.font("Segoe UI", 14));
        msg.setFill(Color.web("#1a1a1a"));

        TextFlow flow = new TextFlow(msg);
        flow.setMaxWidth(500);
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

    // ----------------------------------------------------------------
    // start
    // ----------------------------------------------------------------
    @Override
    public void start(Stage stage) throws Exception {
        this.primaryStage = stage;

        stage.getIcons().add(new Image(getClass().getResourceAsStream("/images/icon.png")));

        centerBox = new VBox(18);
        centerBox.setAlignment(Pos.CENTER);

        Text title = new Text("Olá!");
        title.setFont(Font.font("Segoe UI", FontWeight.MEDIUM, 32));
        title.setFill(Color.web("#1a1a1a"));
        centerBox.getChildren().add(title);

        ScrollPane messagesArea = createMessagesArea();
        StackPane centerStack = new StackPane(messagesArea, centerBox);

        VBox inputBar = createInputBar();   // agora retorna VBox (preview + barra)
        VBox bottomBox = new VBox(inputBar);
        bottomBox.setAlignment(Pos.CENTER);
        bottomBox.setPadding(new Insets(0, 0, 40, 0));

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

    // ----------------------------------------------------------------
    // Barra de input — retorna VBox com preview + barra
    // ----------------------------------------------------------------
    private VBox createInputBar() {

        // --- campo de texto ---
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

        // --- botão enviar ---
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

        SVGPath sendIcon = new SVGPath();
        sendIcon.setContent("M 2 12 L 22 2 L 14 22 L 11 13 L 2 12 Z");
        sendIcon.setFill(Color.web("#9a9a9a"));
        sendIcon.setScaleX(0.8);
        sendIcon.setScaleY(0.8);
        sendButton.setGraphic(sendIcon);

        // --- botão imagem ---
        Button imageButton = new Button();
        imageButton.setPrefSize(52, 52);
        imageButton.setStyle(
                "-fx-background-color: white;" +
                "-fx-background-radius: 26;" +
                "-fx-border-radius: 26;" +
                "-fx-border-color: #d9d9d9;" +
                "-fx-border-width: 1;" +
                "-fx-cursor: hand;"
        );

        SVGPath imageIcon = new SVGPath();
        imageIcon.setContent("M 3 5 L 21 5 L 21 19 L 3 19 Z M 3 14 L 8 9 L 13 14 L 16 11 L 21 16");
        imageIcon.setFill(Color.TRANSPARENT);
        imageIcon.setStroke(Color.web("#9a9a9a"));
        imageIcon.setStrokeWidth(1.5);
        imageIcon.setScaleX(0.8);
        imageIcon.setScaleY(0.8);
        imageButton.setGraphic(imageIcon);

        // --- preview da imagem selecionada ---
        previewImage.setFitHeight(160);
        previewImage.setFitWidth(160);
        previewImage.setPreserveRatio(true);
        previewImage.setVisible(false);
        previewImage.setManaged(false); // não ocupa espaço enquanto invisível

        // --- ações ---
        inputField.setOnAction(e -> sendButton.fire());

        // imageButton: só seleciona o arquivo e mostra preview
        imageButton.setOnAction(e -> {
            arquivoSelecionado = uploadService.uploadImages(primaryStage);
            if (arquivoSelecionado != null) {
                previewImage.setImage(new Image(arquivoSelecionado.toURI().toString()));
                previewImage.setVisible(true);
                previewImage.setManaged(true);
                System.out.println("Imagem selecionada: " + arquivoSelecionado.getName());
            }
        });

        // sendButton: envia texto (com ou sem imagem)
        sendButton.setOnAction(e -> {
            String message = inputField.getText();
            if (message != null && !message.isBlank()) {

                // bolha do usuário (com imagem se houver)
                messagesContainer.getChildren().add(createUserBubble(message, arquivoSelecionado));
                hideGreetingIfNeeded();
                scrollToBottom();
                inputField.clear();

                if (arquivoSelecionado != null) {
                    String resposta = service.uploadArquivo(arquivoSelecionado, message);
                    appendAiMessage(resposta);
                    
                    // limpa preview e arquivo após envio
                    previewImage.setVisible(false);
                    previewImage.setManaged(false);
                    previewImage.setImage(null);
                    arquivoSelecionado = null;
                    
                } else {
                    appendAiMessage(service.gerarConteudo(message));
                }

                System.out.println("Mensagem enviada: " + message);
            }
        });

        // --- monta a barra ---
        HBox bar = new HBox(10, imageButton, inputField, sendButton);
        bar.setAlignment(Pos.CENTER);
        bar.setPadding(new Insets(0, 20, 0, 20));

        // --- wrapper com preview acima da barra ---
        VBox wrapper = new VBox(6, previewImage, bar);
        wrapper.setAlignment(Pos.CENTER);
        return wrapper;
    }

    // ----------------------------------------------------------------
    // Helpers
    // ----------------------------------------------------------------
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