import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.control.ComboBox;
import java.text.NumberFormat;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.FontPosture;
import java.util.HashMap;
import java.util.Set;
import javafx.collections.ObservableList;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.RowConstraints;

import javax.management.openmbean.OpenDataException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;
import javafx.collections.FXCollections;

import javafx.scene.text.Text;
import javafx.scene.input.*;
import javafx.scene.Group;

import javafx.scene.layout.Pane;
import javafx.scene.Cursor;

import javafx.scene.shape.Circle;

import javafx.animation.Timeline;
import javafx.util.Duration;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import java.util.Timer;
import java.util.TimerTask;


public class CountryQuiz extends Application {

    //Main Menu Buttons
    private Button openEndedButton = new Button();
    private Button multChoiceButton = new Button();
    private Button dragDropButton = new Button();

    //Universal Buttons
    private Button backToMenu = new Button("Back to Main Menu");
    //private Button hint = new Button("Hint");

    //Country data HashMap
    private HashMap<String, Country> countries;
    private Set<String> keySet;
    private ArrayList<String> countryList;

    //Random
    Random rand = new Random();

    //Data format for drag and drop
    static final DataFormat ELEMENT_LIST = new DataFormat("ElementList");


    public void start(Stage stage) {
        CountryData countryData = new CountryData();
        countries = countryData.getDataList();
        keySet = countries.keySet();
        countryList = new ArrayList<String>(keySet);
        Collections.sort(countryList);

        splashScreen(stage);
        //setMainMenu(stage);
        openEndedButtonHandler(stage);
        multChoiceButtonHandler(stage);
        dragDropButtonHandler(stage);
    }


    public void splashScreen(Stage stage){
        Image splashImage = new Image("splashbackground.jpg");
        ImageView splashView = new ImageView(splashImage);
        splashView.setFitHeight(350);
        splashView.setFitWidth(670);
        //splashView.setPreserveRatio(true);

        Text msg = new Text("Country");
        Text msg2 = new Text("Trivia");
        Text msg3 = new Text("Challenge");

        msg.setTextOrigin(VPos.TOP);
        msg2.setTextOrigin(VPos.CENTER);
        msg3.setTextOrigin(VPos.BOTTOM);
        
        VBox tbox = new VBox(20);

        tbox.getChildren().addAll(msg,msg2,msg3);
        GridPane scene = new GridPane();
        //scene.setGraphic(splashImage);
        //scene.setStyle("-fx-base: lightgreen;");
        stage.setScene(new Scene(scene,670,350));

        scene.getChildren().addAll(splashView,tbox);
        stage.setTitle("Welcome");
        stage.show();

        msg.setFont(Font.font("Verdana",  FontPosture.REGULAR, 60));
        msg2.setFont(Font.font("Verdana", FontPosture.REGULAR, 60));
        msg3.setFont(Font.font("Verdana", FontPosture.REGULAR, 60));

        //Dimensions for the text
        double sceneWidth = scene.getWidth();
        double sceneHeight = scene.getHeight();
        double msgWidth = msg.getLayoutBounds().getWidth();
        double msgWidth2 = msg2.getLayoutBounds().getWidth();
        double msgWidth3 = msg3.getLayoutBounds().getWidth();
        double msg3Height = msg3.getLayoutBounds().getHeight();

        //Starting positions for text animations
        KeyValue initKeyValue = new KeyValue(msg.translateXProperty(), sceneWidth);
        KeyFrame initFrame = new KeyFrame(Duration.ZERO, initKeyValue);
        KeyValue initKeyValue2 = new KeyValue(msg2.translateXProperty(), 0-msgWidth2);
        KeyFrame initFrame2 = new KeyFrame(Duration.ZERO, initKeyValue2);
        KeyValue initKeyValue3 = new KeyValue(msg3.translateYProperty(), sceneHeight + msg3Height);
        KeyFrame initFrame3 = new KeyFrame(Duration.ZERO, initKeyValue3);
        KeyValue initKeyValue4 = new KeyValue(msg3.translateXProperty(), 670/2);
        KeyFrame initFrame4 = new KeyFrame(Duration.ZERO, initKeyValue4);
        KeyValue initKeyValue5 = new KeyValue(msg3.translateXProperty(), -1200);
        KeyFrame initFrame5 = new KeyFrame(Duration.ZERO, initKeyValue5);

        //Ending positions for text animations
        KeyValue endKeyValue = new KeyValue(msg.translateXProperty(), 670/2 - msgWidth/2);
        KeyFrame endFrame = new KeyFrame(Duration.seconds(.5), endKeyValue);
        KeyValue endKeyValue2 = new KeyValue(msg2.translateXProperty(), 670/2 - msgWidth2/2);
        KeyFrame endFrame2 = new KeyFrame(Duration.seconds(.5), endKeyValue2);
        KeyValue endKeyValue3 = new KeyValue(msg3.translateYProperty(), msg2.getY());
        KeyFrame endFrame3 = new KeyFrame(Duration.seconds(.5), endKeyValue3);
        KeyValue endKeyValue4 = new KeyValue(msg3.translateXProperty(), 670/2 - msgWidth3/2);
        KeyFrame endFrame4 = new KeyFrame(Duration.seconds(.5), endKeyValue4);
        KeyValue endKeyValue5 = new KeyValue(msg3.translateXProperty(), -2000);
        KeyFrame endFrame5 = new KeyFrame(Duration.seconds(.5), endKeyValue5);
        KeyValue endKeyValue6 = new KeyValue(msg2.translateXProperty(), -2000);
        KeyFrame endFrame6 = new KeyFrame(Duration.seconds(.5), endKeyValue6);

        //Creating the animations
        Timeline timeline = new Timeline(initFrame, endFrame);
        Timeline timeline2 = new Timeline(initFrame2, endFrame2);
        Timeline timeline3 = new Timeline(initFrame3, endFrame3);
        Timeline timeline4 = new Timeline(initFrame4, endFrame4);
        //These animations ensure that "Quiz" and "App" text stays offscreen
        Timeline timeline5 = new Timeline(initFrame5, endFrame5);
        Timeline timeline6 = new Timeline(initFrame2, endFrame6);
        

        //Play first two animations, one after the other
        timeline.setCycleCount(1);
        timeline.play();
        timeline.setOnFinished(evt->{
            timeline2.setCycleCount(1);
            timeline2.play();
        });

        timeline5.play();
        timeline6.play();

        //Wait a bit before bringing the "App" text on screen
        timeline2.setOnFinished(evt->{
            timeline3.setCycleCount(1);
            timeline3.play();
            timeline4.setCycleCount(1);
            timeline4.play();

        });

        timeline4.setOnFinished(evt->{
            try {
                Thread.sleep(2000);
              } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            setMainMenu(stage);
        });
    }

    public void setMainMenu(Stage stage) {
        GridPane menuPane = new GridPane();

        Image openEndedImage = new Image("openEnded.png");
        ImageView openEndedView = new ImageView(openEndedImage);
        openEndedView.setFitHeight(500);
        openEndedView.setFitWidth(200);
        openEndedView.setPreserveRatio(true);
        openEndedButton.setStyle("-fx-base: lightgreen;");
        openEndedButton.setPrefSize(Integer.MAX_VALUE, Integer.MAX_VALUE);
        menuPane.add(openEndedButton, 0, 0);
        Label openEndedLabel = new Label("Open-Ended");
        openEndedLabel.setFont(Font.font("Verdana", FontPosture.ITALIC, 20));
        VBox openEndedBox = new VBox();
        openEndedBox.setAlignment(Pos.CENTER);
        openEndedBox.setMargin(openEndedView, new Insets(0,0,70,0));
        openEndedBox.getChildren().addAll(openEndedView, openEndedLabel);
        openEndedButton.setGraphic(openEndedBox);

        Image multChoiceImage = new Image("multChoice.png");
        ImageView multChoiceView = new ImageView(multChoiceImage);
        multChoiceView.setFitHeight(500);
        multChoiceView.setFitWidth(200);
        multChoiceView.setPreserveRatio(true);
        //Button multChoiceButton = new Button();
        multChoiceButton.setStyle("-fx-base: khaki;");
        multChoiceButton.setPrefSize(Integer.MAX_VALUE, Integer.MAX_VALUE);
        menuPane.add(multChoiceButton, 1, 0);
        Label multChoiceLabel = new Label("Multiple Choice");
        multChoiceLabel.setFont(Font.font("Verdana", FontPosture.ITALIC, 20));
        VBox multChoiceBox = new VBox();
        multChoiceBox.setAlignment(Pos.CENTER);
        multChoiceBox.setMargin(multChoiceView, new Insets(0,0,110,0));
        multChoiceBox.getChildren().addAll(multChoiceView, multChoiceLabel);
        multChoiceButton.setGraphic(multChoiceBox);

        Image dragDropImage = new Image("dragDrop.png");
        ImageView dragDropView = new ImageView(dragDropImage);
        dragDropView.setFitHeight(500);
        dragDropView.setFitWidth(200);
        dragDropView.setPreserveRatio(true);
        //Button dragDropButton = new Button();
        dragDropButton.setStyle("-fx-base: lightblue;");
        dragDropButton.setPrefSize(Integer.MAX_VALUE, Integer.MAX_VALUE);
        menuPane.add(dragDropButton, 2, 0);
        Label dragDropLabel = new Label("Drag and Drop");
        dragDropLabel.setFont(Font.font("Verdana", FontPosture.ITALIC, 20));
        VBox dragDropBox = new VBox();
        dragDropBox.setAlignment(Pos.CENTER);
        dragDropBox.setMargin(dragDropView, new Insets(0,0,110,0));
        dragDropBox.getChildren().addAll(dragDropView, dragDropLabel);
        dragDropButton.setGraphic(dragDropBox);

        stage.setTitle("Country Quiz");
        stage.setScene(new Scene(menuPane, 770, 580));
        stage.show();
    }

    public void openEndedButtonHandler(Stage stage) {
        openEndedButton.setOnAction( evt -> {setOpenEndedMode(stage);});
    }

    public void multChoiceButtonHandler(Stage stage) {
        multChoiceButton.setOnAction( evt -> {setMultChoiceMode(stage);});
    }

    public void dragDropButtonHandler(Stage stage) {
        dragDropButton.setOnAction( evt -> {setDragDropMode(stage);});
    }








    public void setOpenEndedMode(Stage stage) {
        GridPane openEndedMode = new GridPane();
        openEndedMode.setStyle("-fx-background-color: rgb(216, 252, 207);");
        openEndedMode.getColumnConstraints().add(new ColumnConstraints(163));
        openEndedMode.getColumnConstraints().add(new ColumnConstraints(280));
        for (int i = 0; i < 2; i++) {
            ColumnConstraints column = new ColumnConstraints(163);
            openEndedMode.getColumnConstraints().add(column);
        }
        for (int i = 0; i < 4; i++) {
            RowConstraints row = new RowConstraints(70);
            openEndedMode.getRowConstraints().add(row);
        }
        openEndedMode.getRowConstraints().add(new RowConstraints(300));
        ComboBox<String> guessBox  = new ComboBox<String>(FXCollections.observableArrayList(countryList));
        Label prompt = new Label("This country has:");
        Label hint1 = new Label();
        Label hint2 = new Label();
        ArrayList<Integer> whichHint = new ArrayList<Integer>();
        String answer = countryList.get(rand.nextInt(countryList.size()));
        Button guessButton = new Button("Guess");
        Button playAgainButton = new Button("Play Again");
        Label resultLabel = new Label();
        int[] chances = {4};
        Label chancesLabel = new Label(chances[0]+"/4");
        resultLabel.setFont(Font.font("Verdana", 17));
        chancesLabel.setFont(Font.font("Verdana", 17));
        Button hint = new Button("Hint");
        Label hintLabel =new Label();
        Label guess1 = new Label();
        Label guess2 = new Label();
        Label guess3 = new Label();
        Label guess4 = new Label();
        Label answerLabel = new Label();
        guess1.setFont(Font.font("Verdana", 14));
        guess2.setFont(Font.font("Verdana", 14));
        guess3.setFont(Font.font("Verdana", 14));
        guess4.setFont(Font.font("Verdana", 14));
        answerLabel.setFont(Font.font("Verdana", 15));
        VBox guessLabels = new VBox();
        guessLabels.getChildren().addAll(guess1, guess2, guess3, guess4, answerLabel);

        while (whichHint.size() != 2) {
            int num = rand.nextInt(3);
            if (!whichHint.contains(num)) {
                whichHint.add(num);
            }
        }

        if (whichHint.contains(0)) {
            hint1.setText("'" + countries.get(answer).getCapital() + "' as its capital.");
            if (whichHint.contains(1)){
                hint2.setText("'" + countries.get(answer).getPopulation() + "' people.");
            } else {
                hint2.setText("'" + countries.get(answer).getLanguage() + "' as its official language.");
            }
        } else {
            hint1.setText("'" + countries.get(answer).getPopulation() + "' people.");
            hint2.setText("'" + countries.get(answer).getLanguage() + "' as its official language.");
        }

        AutoComplete.autoCompleteComboBoxPlus(guessBox, (typedText, itemToCompare) -> itemToCompare.toLowerCase().contains(typedText.toLowerCase()));

        guessButton.setOnAction( evt -> {
            if (guessBox.getValue() != null) {
                if (guessBox.getValue().equals(answer)) {
                    resultLabel.setText("Correct!");
                    guessButton.setStyle("-fx-base: green");
                    guessButton.setDisable(true);
                } else {
                    resultLabel.setText("Wrong!");
                    chances[0]--;
                    chancesLabel.setText(chances[0]+"/4");
                }
                if (chances[0] == 3){
                    guess1.setText("First guess: "+ guessBox.getValue());
                }
                if (chances[0] == 2){
                    guess2.setText("Second guess: "+ guessBox.getValue());
                }
                if (chances[0] == 1){
                    guess3.setText("Third guess: "+ guessBox.getValue());
                    chancesLabel.setTextFill(Color.RED);
                    //chancesLabel.setStyle("-fx-font-weight: bold");
                }
                if (chances[0]==0) {
                    guess4.setText("Final guess: "+ guessBox.getValue());
                    answerLabel.setText("The correct answer is: "+ answer);
                    chancesLabel.setTextFill(Color.BLACK);
                    //chancesLabel.setStyle("-fx-font-weight: regular");
                    guessButton.setDisable(true);
                    hint.setDisable(true);
                }
            }
        });

        backToMenuHandler(stage);
        playAgainButton.setOnAction(evt->{setOpenEndedMode(stage);});
        
        openEndedMode.add(prompt, 0, 0, 4, 1);
        openEndedMode.add(hint1, 0, 1, 4, 1);
        openEndedMode.add(hint2, 0, 2, 4, 1);
        openEndedMode.add(chancesLabel, 3, 3);
        openEndedMode.add(guessBox, 1, 3);
        openEndedMode.add(guessButton, 2, 3);
        openEndedMode.add(resultLabel,0,3);
        openEndedMode.add(backToMenu,0,4);
        openEndedMode.add(guessLabels, 1, 4, 2, 1);
        openEndedMode.add(hintLabel, 1, 4, 2, 1);
        openEndedMode.add(playAgainButton, 1, 4, 2, 1);
        openEndedMode.add(hint, 3, 4);
        guessBox.setPrefWidth(Integer.MAX_VALUE);
        
        openEndedMode.setHalignment(prompt, HPos.CENTER);
        openEndedMode.setHalignment(hint1, HPos.CENTER);
        openEndedMode.setHalignment(hint2, HPos.CENTER);
        openEndedMode.setValignment(backToMenu, VPos.BOTTOM);
        openEndedMode.setValignment(playAgainButton, VPos.BOTTOM);
        openEndedMode.setValignment(hint, VPos.BOTTOM);
        openEndedMode.setHalignment(playAgainButton, HPos.CENTER);
        openEndedMode.setHalignment(hint, HPos.RIGHT);
        openEndedMode.setHalignment(chancesLabel, HPos.CENTER);
        openEndedMode.setHalignment(guessButton, HPos.CENTER);
        openEndedMode.setHalignment(resultLabel, HPos.CENTER);
        openEndedMode.setValignment(hintLabel, VPos.CENTER);
        openEndedMode.setHalignment(hintLabel, HPos.CENTER);

        prompt.setFont(Font.font("Verdana", 25));
        hint1.setFont(Font.font("Verdana", FontPosture.ITALIC, 25));
        hint2.setFont(Font.font("Verdana", FontPosture.ITALIC, 25));
        hintLabel.setFont(Font.font("Verdana", 20));
        openEndedMode.setMargin(prompt, new Insets(10, 0, 0, 0));
        openEndedMode.setMargin(hint1, new Insets(10, 0, 0, 0));
        openEndedMode.setMargin(hint2, new Insets(10, 0, 0, 0));
        openEndedMode.setMargin(backToMenu, new Insets(Integer.MAX_VALUE, 0, 10, 10));
        openEndedMode.setMargin(playAgainButton, new Insets(Integer.MAX_VALUE, 0, 10, 0));
        openEndedMode.setMargin(hint, new Insets(Integer.MAX_VALUE, 10, 10, 0));
        
        backToMenu.getStyleClass().add("navigator");
        playAgainButton.getStyleClass().add("navigator");
        hint.getStyleClass().add("navigator");

        guessButton.getStyleClass().add("guessButton");

        hint.setOnAction(evt -> {
            if (whichHint.contains(0)){
                if (whichHint.contains(1)) {
                    hintLabel.setText("Hint: They speak '" + countries.get(answer).getLanguage() + "'");
                } else {
                    hintLabel.setText("Hint: It's in '" + countries.get(answer).getContinent() + "'.");
                }
            } else {
                hintLabel.setText("Hint: '" + countries.get(answer).getCapital() + "' is the capital.");
            }
            hint.setDisable(true);
        });

        stage.setTitle("Country Quiz - Open Ended");
        Scene scene = new Scene(openEndedMode, 770, 580);
        scene.getStylesheets().add("OpenEndedMode.css");
        stage.setScene(scene);
    }









    public void setMultChoiceMode(Stage stage) {
        GridPane multChoiceMode = new GridPane();
        multChoiceMode.setStyle("-fx-background-color: rgb(245, 246, 222);");
        GridPane questionPane = new GridPane();
        GridPane buttonPane = new GridPane();
        VBox answerBox = new VBox();
        VBox correctBox = new VBox();
        VBox chancesBox = new VBox();
        VBox continentBox = new VBox();
        VBox capitalBox = new VBox();
        VBox populationBox = new VBox();
        Button continentButton1 = new Button();
        Button continentButton2 = new Button();
        Button continentButton3 = new Button();
        Button continentButton4 = new Button();
        Button capitalButton1 = new Button();
        Button capitalButton2 = new Button();
        Button capitalButton3 = new Button();
        Button capitalButton4 = new Button();
        Button populationButton1 = new Button();
        Button populationButton2 = new Button();
        Button populationButton3 = new Button();
        Button populationButton4 = new Button();
        Button[] continentButtons = {continentButton1, continentButton2, continentButton3, continentButton4};
        Button[] capitalButtons = {capitalButton1, capitalButton2, capitalButton3, capitalButton4};
        Button[] populationButtons = {populationButton1, populationButton2, populationButton3, populationButton4};
        String answer;
        ArrayList<String> problems = new ArrayList<String>();
        int[] chances = {4};
        int[] numCorrect = {0};
        Button playAgainButton = new Button("Play Again");
        Button hint = new Button("Hint");

        capitalBox.getStyleClass().add("multBox");
        continentBox.getStyleClass().add("multBox");
        populationBox.getStyleClass().add("multBox");


        while (problems.size() != 4) {
            int num = rand.nextInt(countryList.size());
            if (!problems.contains(countryList.get(num))) {
                problems.add(countryList.get(num));
            }
        }

        answer = problems.get(rand.nextInt(4));

        Label questionLabel = new Label(answer);
        questionLabel.setFont(Font.font("Verdana", FontPosture.ITALIC, 25));
        answerBox.getChildren().addAll(questionLabel);

        Label chancesLabel = new Label(chances[0]+"/4");
        chancesLabel.setFont(Font.font("Verdana", FontPosture.ITALIC, 25));
        chancesBox.getChildren().addAll(chancesLabel);
        Label correctLabel = new Label();
        correctLabel.setFont(Font.font("Verdana", FontPosture.ITALIC, 25));
        correctBox.getChildren().addAll(correctLabel);



        Label continentLabel = new Label("Continent");
        Label capitalLabel = new Label("Capital");
        Label populationLabel = new Label("Population");
        continentLabel.setFont(Font.font("Verdana", 17));
        capitalLabel.setFont(Font.font("Verdana", 17));
        populationLabel.setFont(Font.font("Verdana", 17));

        continentBox.getChildren().addAll(continentLabel);
        capitalBox.getChildren().addAll(capitalLabel);
        populationBox.getChildren().addAll(populationLabel);
        continentBox.setMargin(continentLabel, new Insets(0, 0, 10, 0));
        capitalBox.setMargin(capitalLabel, new Insets(0, 0, 10, 0));
        populationBox.setMargin(populationLabel, new Insets(0, 0, 10, 0));

        
        int i = 0;
        ArrayList<String> continentUsed = new ArrayList<String>();
        for (Button continentButton : continentButtons) {
            continentButton.getStyleClass().add("guessButton");
            if (!continentUsed.contains(countries.get(problems.get(i)).getContinent())) {
                continentButton.setPrefSize(Integer.MAX_VALUE, Integer.MAX_VALUE);
                continentButton.setText(countries.get(problems.get(i)).getContinent());
                continentButton.setText(countries.get(problems.get(i)).getContinent());
                continentButton.setFont(Font.font("Verdana", FontPosture.ITALIC, 15));
                continentButton.setStyle("-fx-base: rgb(237, 218, 163);");
                continentBox.setMargin(continentButton, new Insets(0,0,10,0));
                continentBox.getChildren().addAll(continentButton);
                continentUsed.add(countries.get(problems.get(i)).getContinent());
                continentButton.setOnAction(evt -> {
                    if (continentButton.getText().equals(countries.get(answer).getContinent())) {
                        for (Button button : continentButtons) {
                            button.setDisable(true);
                        }
                        continentButton.setStyle("-fx-base: green;");
                        numCorrect[0]++;
                    } else {
                        chances[0]--;
                        chancesLabel.setText(chances[0]+"/4");
                        continentButton.setStyle("-fx-base: red");
                        continentButton.setDisable(true);
                    }
                    if (numCorrect[0]==3){
                        correctLabel.setText("Correct!");
                        hint.setDisable(true);
                    }
                    if (chances[0]==1){
                        chancesLabel.setTextFill(Color.RED);
                    }
                    if (chances[0]==0){
                        chancesLabel.setTextFill(Color.BLACK);
                        correctLabel.setText("Wrong!");
                        for (Button button : continentButtons) {
                            if (button.getText().equals(countries.get(answer).getContinent())){
                                button.setStyle("-fx-base: green;");
                            }
                            button.setDisable(true);
                        }
                        for (Button button : capitalButtons) {
                            if (button.getText().equals(countries.get(answer).getCapital())){
                                button.setStyle("-fx-base: green;");
                            }
                            button.setDisable(true);
                        }
                        for (Button button : populationButtons) {
                            if (button.getText().equals((String.valueOf(countries.get(answer).getPopulation())))){
                                button.setStyle("-fx-base: green;");
                            }
                            button.setDisable(true);
                        }
                        hint.setDisable(true);
                    }
                });
            }
            i++;
        }
        i = 0;
        Collections.shuffle(problems);
        for (Button capitalButton : capitalButtons) {
            capitalButton.getStyleClass().add("guessButton");
            capitalButton.setPrefSize(Integer.MAX_VALUE, Integer.MAX_VALUE);
            capitalButton.setText(countries.get(problems.get(i)).getCapital());
            capitalButton.setFont(Font.font("Verdana", FontPosture.ITALIC, 15));
            capitalButton.setStyle("-fx-base: rgb(237, 218, 163);");
            capitalBox.setMargin(capitalButton, new Insets(0,0,10,0));
            capitalBox.getChildren().addAll(capitalButton);
            capitalButton.setOnAction(evt -> {
                if (capitalButton.getText().equals(countries.get(answer).getCapital())) {
                    for (Button button : capitalButtons) {
                        button.setDisable(true);
                    }
                    capitalButton.setStyle("-fx-base: green;");
                    numCorrect[0]++;
                } else {
                    chances[0]--;
                    chancesLabel.setText(chances[0]+"/4");
                    capitalButton.setStyle("-fx-base: red");
                    capitalButton.setDisable(true);
                }
                if (numCorrect[0]==3){
                    correctLabel.setText("Correct!");
                }
                if (chances[0]==1){
                    chancesLabel.setTextFill(Color.RED);
                }
                if (chances[0]==0){
                    chancesLabel.setTextFill(Color.BLACK);
                    correctLabel.setText("Wrong!");
                        for (Button button : continentButtons) {
                            if (button.getText().equals(countries.get(answer).getContinent())){
                                button.setStyle("-fx-base: green;");
                            }
                            button.setDisable(true);
                        }
                        for (Button button : capitalButtons) {
                            if (button.getText().equals(countries.get(answer).getCapital())){
                                button.setStyle("-fx-base: green;");
                            }
                            button.setDisable(true);
                        }
                        for (Button button : populationButtons) {
                            if (button.getText().equals((String.valueOf(countries.get(answer).getPopulation())))){
                                button.setStyle("-fx-base: green;");
                            }
                            button.setDisable(true);
                        }
                        hint.setDisable(true);
                }
            });
            i++;
        }
        i = 0;
        Collections.shuffle(problems);
        for (Button populationButton : populationButtons) {
            populationButton.getStyleClass().add("guessButton");
            populationButton.setPrefSize(Integer.MAX_VALUE, Integer.MAX_VALUE);
            populationButton.setText(String.valueOf(countries.get(problems.get(i)).getPopulation()));
            populationButton.setFont(Font.font("Verdana", FontPosture.ITALIC, 15));
            populationButton.setStyle("-fx-base: rgb(237, 218, 163);");
            populationBox.setMargin(populationButton, new Insets(0,0,10,0));
            populationBox.getChildren().addAll(populationButton);
            populationButton.setOnAction(evt -> {
                if (populationButton.getText().equals(String.valueOf(countries.get(answer).getPopulation()))) {
                    for (Button button : populationButtons) {
                        button.setDisable(true);
                    }
                    populationButton.setStyle("-fx-base: green;");
                    numCorrect[0]++;
                } else {
                    chances[0]--;
                    chancesLabel.setText(chances[0]+"/4");
                    populationButton.setStyle("-fx-base: red");
                    populationButton.setDisable(true);
                }
                if (numCorrect[0]==3){
                    correctLabel.setText("Correct!");
                }
                if (chances[0]==1){
                    chancesLabel.setTextFill(Color.RED);
                }
                if (chances[0]==0){
                    correctLabel.setText("Wrong!");
                    chancesLabel.setTextFill(Color.BLACK);
                        for (Button button : continentButtons) {
                            if (button.getText().equals(countries.get(answer).getContinent())){
                                button.setStyle("-fx-base: green;");
                            }
                            button.setDisable(true);
                        }
                        for (Button button : capitalButtons) {
                            if (button.getText().equals(countries.get(answer).getCapital())){
                                button.setStyle("-fx-base: green;");
                            }
                            button.setDisable(true);
                        }
                        for (Button button : populationButtons) {
                            if (button.getText().equals((String.valueOf(countries.get(answer).getPopulation())))){
                                button.setStyle("-fx-base: green;");
                            }
                            button.setDisable(true);
                        }
                        hint.setDisable(true);
                }
            });
            i++;
        }

        questionPane.setAlignment(Pos.CENTER);
        
        buttonPane.add(correctBox, 0, 0);
        buttonPane.add(answerBox, 1, 0);
        buttonPane.add(chancesBox, 2, 0);
        correctBox.setAlignment(Pos.CENTER);
        answerBox.setAlignment(Pos.CENTER);
        chancesBox.setAlignment(Pos.CENTER);
        buttonPane.setMargin(correctBox, new Insets(10,0,0,0));
        buttonPane.setMargin(answerBox, new Insets(10,0,0,0));
        buttonPane.setMargin(chancesBox, new Insets(10,0,0,0));

        continentBox.setAlignment(Pos.CENTER);
        capitalBox.setAlignment(Pos.CENTER);
        populationBox.setAlignment(Pos.CENTER);
        continentBox.setPrefSize(Integer.MAX_VALUE, Integer.MAX_VALUE);
        capitalBox.setPrefSize(Integer.MAX_VALUE, Integer.MAX_VALUE);
        populationBox.setPrefSize(Integer.MAX_VALUE, Integer.MAX_VALUE);
        buttonPane.setMargin(continentBox, new Insets(10,10,0,10));
        buttonPane.setMargin(capitalBox, new Insets(10,10,0,0));
        buttonPane.setMargin(populationBox, new Insets(10,10,0,0));
        buttonPane.add(continentBox,0,1);
        buttonPane.add(capitalBox,1,1);
        buttonPane.add(populationBox,2,1);
        multChoiceMode.add(buttonPane, 0, 1);

        buttonPane.add(backToMenu, 0, 2);
        buttonPane.setMargin(backToMenu, new Insets(0, 0, 10, 10));
        buttonPane.add(playAgainButton, 1, 2);
        buttonPane.setMargin(playAgainButton, new Insets(0,0,10,0));
        buttonPane.setHalignment(playAgainButton, HPos.CENTER);
        buttonPane.add(hint, 2, 2);
        buttonPane.setMargin(hint, new Insets(0, 10, 10, 0));
        buttonPane.setHalignment(hint, HPos.RIGHT);


        backToMenu.getStyleClass().add("navigator");
        playAgainButton.getStyleClass().add("navigator");
        hint.getStyleClass().add("navigator");

        Scene scene = new Scene(multChoiceMode, 770, 580);
        scene.getStylesheets().add("multChoiceMode.css");
        
        stage.setTitle("Country Quiz - Multiple Choice");
        stage.setScene(scene);
        //stage.show();

        backToMenuHandler(stage);
        playAgainButton.setOnAction(evt->{setMultChoiceMode(stage);});

        hint.setOnAction(evt -> {
            for (Button button : continentButtons) {
                if (button.isDisabled() == false) {
                    if (button.getText().equals(countries.get(answer).getContinent())){
                        button.setStyle("-fx-base: green;");
                        for (Button again : continentButtons) {
                            again.setDisable(true);
                        }
                        numCorrect[0]++;
                        if (numCorrect[0]==3){
                            correctLabel.setText("Correct!");
                        }
                        hint.setDisable(true);
                        return;
                    }
                }
            }
            for (Button button : capitalButtons) {
                if (button.isDisabled() == false) {
                    if (button.getText().equals(countries.get(answer).getCapital())){
                        button.setStyle("-fx-base: green;");
                        for (Button again : capitalButtons) {
                            again.setDisable(true);
                        }
                        numCorrect[0]++;
                        if (numCorrect[0]==3){
                            correctLabel.setText("Correct!");
                        }
                        hint.setDisable(true);
                        return;
                    }
                }
            }
            for (Button button : populationButtons) {
                if (button.isDisabled() == false) {
                    if (button.getText().equals(String.valueOf(countries.get(answer).getPopulation()))){
                        button.setStyle("-fx-base: green;");
                        for (Button again : populationButtons) {
                            again.setDisable(true);
                        }
                        numCorrect[0]++;
                        if (numCorrect[0]==3){
                            correctLabel.setText("Correct!");
                        }
                        hint.setDisable(true);
                        return;
                    }
                }
            }
        });
    }



    public void setDragDropMode(Stage stage) {
        GridPane dragDropMode = new GridPane();
        dragDropMode.setStyle("-fx-background-color: rgb(207, 232, 242);");
        Button playAgainButton = new Button("Play Again");
        Button guessButton = new Button("Guess");
        dragDropMode.setHalignment(guessButton, HPos.CENTER);
        int[] chances = {4};

        ListView<String> sourceList = new ListView<String>();
        ListView<String> countryList1 = new ListView<String>();
        ListView<String> countryList2 = new ListView<String>();
        ListView<String> countryList3 = new ListView<String>();

        Button hint = new Button("Hint");

        Label countryLabel1 = new Label();
        Label countryLabel2 = new Label();
        Label countryLabel3 = new Label();
        Label instruction = new Label("Match each item to the correct country");
        Label feedback = new Label();
        Label chancesLabel = new Label(chances[0]+"/4");
        countryLabel1.setFont(Font.font("Verdana", 17));
        countryLabel2.setFont(Font.font("Verdana", 17));
        countryLabel3.setFont(Font.font("Verdana", 17));
        instruction.setFont(Font.font("Verdana", 17));
        chancesLabel.setFont(Font.font("Verdana", 17));
        feedback.setFont(Font.font("Verdana", 17));
        dragDropMode.setHalignment(feedback, HPos.CENTER);
        dragDropMode.setHalignment(countryLabel1, HPos.CENTER);
        dragDropMode.setHalignment(countryLabel2, HPos.CENTER);
        dragDropMode.setHalignment(countryLabel3, HPos.CENTER);
        dragDropMode.setHalignment(instruction, HPos.CENTER);
        dragDropMode.setHalignment(chancesLabel, HPos.CENTER);
        dragDropMode.setHalignment(playAgainButton, HPos.CENTER);
        dragDropMode.setHalignment(hint, HPos.RIGHT);
        
        ArrayList<String> problems = new ArrayList<String>();
        Label[] labels = {countryLabel1, countryLabel2, countryLabel3};
        ArrayList<String> elements = new ArrayList<String>();

        while (problems.size() != 3) {
            int num = rand.nextInt(countryList.size());
            if (!problems.contains(countryList.get(num))) {
                problems.add(countryList.get(num));
            }
        }

        for (int i =0; i < 3; i++) {
            labels[i].setText(problems.get(i));
            elements.add(countries.get(problems.get(i)).getCapital());
            elements.add(countries.get(problems.get(i)).getContinent());
            elements.add(String.valueOf(countries.get(problems.get(i)).getPopulation()));
        }

        Collections.shuffle(elements);

        sourceList.getItems().addAll(elements);

        setDrag(sourceList);
        setDrag(countryList1);
        setDrag(countryList2);
        setDrag(countryList3);
 
        dragDropMode.add(instruction, 1, 0, 2, 1);
        dragDropMode.add(chancesLabel, 3, 0);
        dragDropMode.add(feedback, 0, 3);
        dragDropMode.add(countryLabel1, 1, 3);
        dragDropMode.add(countryLabel2, 2, 3);
        dragDropMode.add(countryLabel3, 3, 3);
        dragDropMode.add(sourceList,0,4);
        dragDropMode.add(countryList1,1,4);
        dragDropMode.add(countryList2,2,4);
        dragDropMode.add(countryList3, 3, 4);
        dragDropMode.add(guessButton, 0, 5, 4, 1);
        dragDropMode.add(backToMenu, 0, 6);
        dragDropMode.add(playAgainButton, 1, 6, 2, 1);
        dragDropMode.add(hint, 3, 6);
        dragDropMode.setMargin(instruction, new Insets(10, 0, 0, 0));
        dragDropMode.setMargin(chancesLabel, new Insets(10,10,0,0));
        dragDropMode.setMargin(feedback, new Insets(10, 10, 0, 0));
        dragDropMode.setMargin(countryLabel1, new Insets(10, 10, 0, 0));
        dragDropMode.setMargin(countryLabel2, new Insets(10, 10, 0, 0));
        dragDropMode.setMargin(countryLabel3, new Insets(10, 10, 0, 0));
        dragDropMode.setMargin(sourceList, new Insets(10, 0, 0, 10));
        dragDropMode.setMargin(countryList1, new Insets(10, 0, 0, 10));
        dragDropMode.setMargin(countryList2, new Insets(10, 0, 0, 10));
        dragDropMode.setMargin(countryList3, new Insets(10, 10, 0, 10));
        dragDropMode.setMargin(guessButton, new Insets(10, 0, 0, 0));
        dragDropMode.setMargin(backToMenu, new Insets(40, 0, 10, 10));
        dragDropMode.setMargin(playAgainButton, new Insets(40, 0 , 10, 10));
        dragDropMode.setMargin(hint, new Insets(40, 10, 10, 0));

        guessButton.setOnAction( evt -> {
            ArrayList<String> toRemove = new ArrayList<String>();
            for (String item : countryList1.getItems()) {
                if (!item.equals(countries.get(problems.get(0)).getCapital()) && !item.equals(countries.get(problems.get(0)).getContinent()) && !item.equals(String.valueOf(countries.get(problems.get(0)).getPopulation()))) {
                    toRemove.add(item);
                }
            }
            countryList1.getItems().removeAll(toRemove);
            sourceList.getItems().addAll(toRemove);
            toRemove.clear();
            for (String item : countryList2.getItems()) {
                if (!item.equals(countries.get(problems.get(1)).getCapital()) && !item.equals(countries.get(problems.get(1)).getContinent()) && !item.equals(String.valueOf(countries.get(problems.get(1)).getPopulation()))) {
                    toRemove.add(item);
                }
            }
            countryList2.getItems().removeAll(toRemove);
            sourceList.getItems().addAll(toRemove);
            toRemove.clear();
            for (String item : countryList3.getItems()) {
                if (!item.equals(countries.get(problems.get(2)).getCapital()) && !item.equals(countries.get(problems.get(2)).getContinent()) && !item.equals(String.valueOf(countries.get(problems.get(2)).getPopulation()))) {
                    toRemove.add(item);
                }
            }
            countryList3.getItems().removeAll(toRemove);
            sourceList.getItems().addAll(toRemove);
            if (countryList1.getItems().size() == 3 && countryList2.getItems().size()==3 && countryList3.getItems().size()==3) {
                guessButton.setDisable(true);
                guessButton.setStyle("-fx-base: green");
                feedback.setText("Correct!");
                hint.setDisable(true);
            } else {
                feedback.setText("Try again!");
                chances[0]--;
                chancesLabel.setText(chances[0]+"/4");
            }
            if (chances[0]==1) {
                chancesLabel.setTextFill(Color.RED);
            }
            if (chances[0]==0) {
                guessButton.setDisable(true);
                feedback.setText("Wrong!");
                chancesLabel.setTextFill(Color.BLACK);
                while (sourceList.getItems().size()!=0){
                    String feature = sourceList.getItems().get(0);
                    for (int i=0; i<3; i++){
                        if (feature.equals(countries.get(problems.get(i)).getContinent()) || feature.equals(countries.get(problems.get(i)).getCapital()) || feature.equals(String.valueOf(countries.get(problems.get(i)).getPopulation()))) {
                            if (i == 0){
                                if (!countryList1.getItems().contains(feature)){
                                    sourceList.getItems().remove(feature);
                                    countryList1.getItems().add(feature);
                                }
                            } else if (i==1){
                                if (!countryList2.getItems().contains(feature)){
                                    sourceList.getItems().remove(feature);
                                    countryList2.getItems().add(feature);
                                }
                            } else {
                                if (!countryList3.getItems().contains(feature)){
                                    sourceList.getItems().remove(feature);
                                    countryList3.getItems().add(feature);
                                }
                            }
                        }
                    }
                }
                hint.setDisable(true);
            }
        });

        backToMenu.getStyleClass().add("navigator");
        playAgainButton.getStyleClass().add("navigator");
        hint.getStyleClass().add("navigator");

        guessButton.getStyleClass().add("guessButton");

        Scene scene = new Scene(dragDropMode, 770, 580);
        scene.getStylesheets().add("DragDropMode.css");

        hint.setOnAction(evt -> {
            String feature = sourceList.getItems().get(0);
            for (int i=0; i<3; i++){
                if (feature.equals(countries.get(problems.get(i)).getContinent()) || feature.equals(countries.get(problems.get(i)).getCapital()) || feature.equals(String.valueOf(countries.get(problems.get(i)).getPopulation()))) {
                    sourceList.getItems().remove(feature);
                    if (i == 0){
                        countryList1.getItems().add(feature);
                    } else if (i==1){
                        countryList2.getItems().add(feature);
                    } else {
                        countryList3.getItems().add(feature);
                    }
                    hint.setDisable(true);
                    return;
                }
            }
        });

        stage.setTitle("Country Quiz - Drag and Drop");
        stage.setScene(scene);

        playAgainButton.setOnAction(evt->{setDragDropMode(stage);});
        backToMenuHandler(stage);
    }

    public void backToMenuHandler(Stage stage) {
        backToMenu.setOnAction(evt -> {setMainMenu(stage);});
    }

    private void setDrag(ListView<String> list) {
        list.setOnDragDetected(new EventHandler <MouseEvent>() {
            public void handle(MouseEvent event) {
                dragDetected(event,list);
            }
        });

        list.setOnDragOver(new EventHandler <DragEvent>() {
            public void handle(DragEvent event) {
                dragOver(event, list);
            }
        });

        list.setOnDragDropped(new EventHandler <DragEvent>() {
            public void handle(DragEvent event) {
                dragDropped(event, list);
            }
        });

        list.setOnDragDone(new EventHandler <DragEvent>() {
            public void handle(DragEvent event) {
                dragDone(event, list);
            }
        });
    }

    private void dragDetected(MouseEvent event, ListView<String> listView) {
        // Make sure at least one item is selected
        int selectedCount = listView.getSelectionModel().getSelectedIndices().size();
 
        if (selectedCount == 0) {
            event.consume();
            return;
        }

        // Initiate a drag-and-drop gesture
        Dragboard dragboard = listView.startDragAndDrop(TransferMode.MOVE);
 
        // Put the the selected items to the dragboard
        ArrayList<String> selectedItems = this.getSelectedElements(listView);
 
        ClipboardContent content = new ClipboardContent();
        content.put(ELEMENT_LIST, selectedItems);
 
        dragboard.setContent(content);
        event.consume();
    }
 
    private void dragOver(DragEvent event, ListView<String> listView) {
        // If drag board has an ITEM_LIST and it is not being dragged
        // over itself, we accept the MOVE transfer mode
        Dragboard dragboard = event.getDragboard();
 
        if (event.getGestureSource() != listView && dragboard.hasContent(ELEMENT_LIST)) {
            event.acceptTransferModes(TransferMode.MOVE);
        }
 
        event.consume();
    }
 
    @SuppressWarnings("unchecked")
    private void dragDropped(DragEvent event, ListView<String> listView) {
        boolean dragCompleted = false;
 
        // Transfer the data to the target
        Dragboard dragboard = event.getDragboard();
 
        if(dragboard.hasContent(ELEMENT_LIST)) {
            ArrayList<String> list = (ArrayList<String>)dragboard.getContent(ELEMENT_LIST);
            listView.getItems().addAll(list);
            // Data transfer is successful
            dragCompleted = true;
        }
 
        // Data transfer is not successful
        event.setDropCompleted(dragCompleted);
        event.consume();
    }
 
    private void dragDone(DragEvent event, ListView<String> listView) {
        // Check how data was transfered to the target
        // If it was moved, clear the selected items
        TransferMode tm = event.getTransferMode();
 
        if (tm == TransferMode.MOVE)
        {
            removeSelectedElements(listView);
        }
        event.consume();
    }

    private ArrayList<String> getSelectedElements(ListView<String> listView) {
        // serializable and can be stored in a Dragboard.
        ArrayList<String> list = new ArrayList<>(listView.getSelectionModel().getSelectedItems());
 
        return list;
    }
 
    private void removeSelectedElements(ListView<String> listView) {
        // Get all selected elements in a separate list to avoid the shared list issue
        ArrayList<String> selectedList = new ArrayList<>();
 
        for(String element : listView.getSelectionModel().getSelectedItems()) {
            selectedList.add(element);
        }
 
        // Clear the selection
        listView.getSelectionModel().clearSelection();
        // Remove items from the selected list
        listView.getItems().remove(selectedList.get(0));
    }
}
