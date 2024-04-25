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
        BorderPane alueet = new BorderPane();
        ListView lvAlueet = new ListView();
        alueet.setLeft(lvAlueet);
        Scene alueetScene = new Scene(alueet, 700, 250);
        TextField tfAlueID = new TextField();
        Label lbAlueID = new Label("Alue ID");
        TextField tfAlueNimi = new TextField();
        Label lbAlueNimi = new Label("Alue nimi");
        Button btLisaaAlue = new Button("Lisää alue");
        Button btMuokkaaAlue = new Button("Muokkaa alue");
        Button btPoistaAlue = new Button("Poista alue");
        Button btAlueIDHaku = new Button("Hae alue ID:llä");
        Button btAlueNimiHaku = new Button("Hae alueen nimellä");
        HBox hbAlueNapit = new HBox();
        hbAlueNapit.getChildren().addAll(btLisaaAlue, btMuokkaaAlue, btPoistaAlue);

        GridPane gpAlue = new GridPane();
        gpAlue.add(lbAlueID, 0, 0);
        gpAlue.add(tfAlueID, 1, 0);
        gpAlue.add(btAlueIDHaku, 2, 0);
        gpAlue.add(lbAlueNimi, 0, 1);
        gpAlue.add(tfAlueNimi, 1, 1);
        gpAlue.add(btAlueNimiHaku, 2, 1);
        VBox vbAlue = new VBox();
        vbAlue.setSpacing(10);
        vbAlue.getChildren().addAll(gpAlue, hbAlueNapit);

        alueet.setCenter(vbAlue);
        btAlueet.setOnAction(e->{
            alueet.setTop(hbValikko);
            primaryStage.setScene(alueetScene);
        });
        btLisaaAlue.setOnAction(e->{
            //Lisää alue tietokantaan
        });
        btPoistaAlue.setOnAction(e->{
            //Poista alue tietokannasta
        });
        btMuokkaaAlue.setOnAction(e->{
            //Muokkaa alue tietokannassa
        });


        primaryStage.show();

        //Luodaan mökit näkymä
        BorderPane mokit = new BorderPane();
        ListView lvMokit = new ListView();
        mokit.setLeft(lvMokit);
        Scene mokitScene = new Scene(mokit, 700, 250);
        btMokit.setOnAction(e->{
            mokit.setTop(hbValikko);
            primaryStage.setScene(mokitScene);
        });

        //Luodaan asiakkaat näkymä
        BorderPane asiakkaat = new BorderPane();
        ListView lvAsiakkaat = new ListView();
        asiakkaat.setLeft(lvAsiakkaat);
        Scene asiakkaatScene = new Scene(asiakkaat, 700, 250);
        btAsiakkaat.setOnAction(e->{
            asiakkaat.setTop(hbValikko);
            primaryStage.setScene(asiakkaatScene);
        });

        //Luodaan palvelut näkymä
        BorderPane palvelut = new BorderPane();
        ListView lvPalvelut = new ListView();
        palvelut.setLeft(lvPalvelut);
        Scene palvelutScene = new Scene(palvelut, 700, 250);
        btPalvelut.setOnAction(e->{
            palvelut.setTop(hbValikko);
            primaryStage.setScene(palvelutScene);
        });

        //Luodaan varaus näkymä
        BorderPane varaus = new BorderPane();
        ListView lvVaraus = new ListView();
        varaus.setLeft(lvVaraus);
        Scene varausScene = new Scene(varaus, 700, 250);
        btVaraus.setOnAction(e->{
            varaus.setTop(hbValikko);
            primaryStage.setScene(varausScene);
        });

    }
}
