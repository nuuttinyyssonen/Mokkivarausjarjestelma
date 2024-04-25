package com.example.ohjelmistotuotanto;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class Kayttoliittyma extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        //Luodaan valikko ylös
        HBox hbValikko = new HBox();
        Button btVaraus = new Button("Varaukset");
        Button btPalvelut = new Button("Palvelut");
        Button btAsiakkaat = new Button("Asiakkaat");
        Button btAlueet = new Button("Alueet");
        Button btMokit = new Button("Mökit");
        hbValikko.setSpacing(10);
        hbValikko.getChildren().addAll(btAlueet,btMokit,btPalvelut,btVaraus,btAsiakkaat);
        hbValikko.setPadding(new Insets(10,10,10,10));

        //Luodaan alotus näyttö paneeli
        BorderPane alkuNakyma = new BorderPane();
        TextField tfHaku = new TextField();
        Button btHaku = new Button("Hae");
        HBox hbHaku = new  HBox(tfHaku, btHaku);
        alkuNakyma.setCenter(hbHaku);
        hbHaku.setAlignment(Pos.CENTER);
        alkuNakyma.setTop(hbValikko);


        Scene alkuScene = new Scene(alkuNakyma, 700, 250);
        primaryStage.setTitle("Alkunäyttö");
        primaryStage.setScene(alkuScene);

        //Luodaan alueet näkymä
        btAlueet.setOnAction(e->{
            BorderPane alueet = new BorderPane();
            ListView lvAlueet = new ListView();
            alueet.setLeft(lvAlueet);
            Scene alueetScene = new Scene(alueet, 700, 250);
            alueet.setTop(hbValikko);
            primaryStage.setScene(alueetScene);
        });
        primaryStage.show();

        //Luodaan mökit näkymä
        btMokit.setOnAction(e->{
            BorderPane mokit = new BorderPane();
            ListView lvMokit = new ListView();
            mokit.setLeft(lvMokit);
            Scene mokitScene = new Scene(mokit, 700, 250);
            mokit.setTop(hbValikko);
            primaryStage.setScene(mokitScene);
        });

        //Luodaan asiakkaat näkymä
        btAsiakkaat.setOnAction(e->{
            BorderPane asiakkaat = new BorderPane();
            ListView lvAsiakkaat = new ListView();
            asiakkaat.setLeft(lvAsiakkaat);
            Scene asiakkaatScene = new Scene(asiakkaat, 700, 250);
            asiakkaat.setTop(hbValikko);
            primaryStage.setScene(asiakkaatScene);
        });

        //Luodaan palvelut näkymä
        btPalvelut.setOnAction(e->{
            BorderPane palvelut = new BorderPane();
            ListView lvPalvelut = new ListView();
            palvelut.setLeft(lvPalvelut);
            Scene palvelutScene = new Scene(palvelut, 700, 250);
            palvelut.setTop(hbValikko);
            primaryStage.setScene(palvelutScene);
        });

        //Luodaan varaus näkymä
        btVaraus.setOnAction(e->{
            BorderPane varaus = new BorderPane();
            ListView lvVaraus = new ListView();
            varaus.setLeft(lvVaraus);
            Scene varausScene = new Scene(varaus, 700, 250);
            varaus.setTop(hbValikko);
            primaryStage.setScene(varausScene);
        });
        
    }
}
