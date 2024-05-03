package com.example.ohjelmistotuotanto;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
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
        Scene alueetScene = new Scene(alueet, 700, 700);
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
        gpAlue.setVgap(5);
        gpAlue.setPadding(new Insets(10,10,10,10));
        VBox vbAlue = new VBox();
        vbAlue.setSpacing(10);
        hbAlueNapit.setPadding(new Insets(10,10,10,10));
        vbAlue.getChildren().addAll(gpAlue, hbAlueNapit);
        alueet.setCenter(vbAlue);
        lvAlueet.getItems().add("Alue 1");


        btAlueet.setOnAction(e->{
            alueet.setTop(hbValikko);
            primaryStage.setScene(alueetScene);
        });
        btLisaaAlue.setOnAction(e->{
            String alueNimi = tfAlueNimi.getText();
            DatabaseUtils.insertAlue(alueNimi);
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
        TextField tfMokkiID = new TextField();
        Label lbMokkiID = new Label("Mökki ID");
        TextField tfMokkiAlueId = new TextField();
        tfMokkiAlueId.setPrefWidth(70);
        Label lbMokkiAlueId = new Label("Alue ID");
        TextField tfPostnro = new TextField();
        tfPostnro.setPrefWidth(70);
        Label lbPostnro = new Label("Postinumero");
        TextField tfMokkiNimi = new TextField();
        tfMokkiNimi.setPrefWidth(70);
        Label lbMokkiNimi = new Label("Mökin nimi");
        TextArea taMokkiKuvaus = new TextArea();
        taMokkiKuvaus.setPrefWidth(70);
        Label lbMokkiKuvaus = new Label("Mökin kuvaus");
        TextField tfMokkiHinta = new TextField();
        tfMokkiHinta.setPrefWidth(70);
        Label lbMokkiHinta = new Label("Mökin hinta");
        TextArea taMokkiVarustelu = new TextArea();
        taMokkiVarustelu.setPrefWidth(70);
        Label lbMokkiVarustelu = new Label("Mökin varustelu");
        TextField tfOsoite = new TextField();
        tfOsoite.setPrefWidth(70);
        Label lbOsoite = new Label("Osoite");
        TextField tfHenkilomaara = new TextField();
        tfHenkilomaara.setPrefWidth(70);
        Label lbHenkilomaara = new Label("Henkilömäärä");
        Button btLisaaMokki = new Button("Lisää mökki");
        Button btMuokkaaMokki = new Button("Muokkaa mökki");
        Button btPoistaMokki = new Button("Poista mökki");
        Button btMokkiHaku = new Button("Hae");

        btLisaaMokki.setOnAction(e->{
            int alue_id = Integer.parseInt(tfMokkiAlueId.getText());
            String postinro = tfPostnro.getText();
            String mokkiNimi = tfMokkiNimi.getText();
            String osoite = tfOsoite.getText();
            int henkilomaara = Integer.parseInt(tfHenkilomaara.getText());
            double hinta = Double.parseDouble(tfMokkiHinta.getText());
            String kuvaus = taMokkiKuvaus.getText();
            String varustelu = taMokkiVarustelu.getText();
            DatabaseUtils.insertMokki(alue_id, postinro, mokkiNimi, osoite, hinta, kuvaus, henkilomaara, varustelu);
        });

        GridPane gpMokki = new GridPane();
        gpMokki.add(lbMokkiID, 0, 0);
        gpMokki.add(tfMokkiID, 1, 0);
        gpMokki.add(lbMokkiAlueId, 0, 1);
        gpMokki.add(tfMokkiAlueId, 1, 1);
        gpMokki.add(lbPostnro, 0, 2);
        gpMokki.add(tfPostnro, 1, 2);
        gpMokki.add(lbMokkiNimi, 0, 3);
        gpMokki.add(tfMokkiNimi, 1, 3);
        gpMokki.add(lbOsoite, 0, 4);
        gpMokki.add(tfOsoite, 1, 4);
        gpMokki.add(lbHenkilomaara, 0, 5);
        gpMokki.add(tfHenkilomaara, 1, 5);
        gpMokki.add(lbMokkiHinta, 0, 6);
        gpMokki.add(tfMokkiHinta, 1, 6);
        gpMokki.add(lbMokkiKuvaus, 0, 7);
        gpMokki.add(taMokkiKuvaus, 1, 7);
        gpMokki.add(lbMokkiVarustelu, 0, 8);
        gpMokki.add(taMokkiVarustelu, 1, 8);
        gpMokki.add(btMokkiHaku, 2, 0);
        HBox hbMokkiNapit = new HBox();
        hbMokkiNapit.getChildren().addAll(btLisaaMokki, btMuokkaaMokki, btPoistaMokki);
        VBox vbMokki = new VBox();
        vbMokki.setSpacing(10);
        hbMokkiNapit.setPadding(new Insets(10,10,10,10));
        vbMokki.getChildren().addAll(gpMokki, hbMokkiNapit);

        mokit.setCenter(vbMokki);
        gpMokki.setVgap(5);
        gpMokki.setPadding(new Insets(10,10,10,10));
        Scene mokitScene = new Scene(mokit, 700, 700);
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
        GridPane gpPalvelut = new GridPane();
        TextField tfPalveluID = new TextField();

        Label lbPalveluID = new Label("Palvelu ID");
        TextField tfPalveluNimi = new TextField();

        Label lbPalveluNimi = new Label("Palvelu nimi");
        TextField tfPalveluHinta = new TextField();

        Label lbPalveluHinta = new Label("Hinta");
        TextField tfAlueIDPalvelu = new TextField();

        Label lbAlueIDPalvelu = new Label("Alue ID");
        TextArea taPalveluKuvaus = new TextArea();
        taPalveluKuvaus.setPrefWidth(70);
        Label lbPalveluKuvaus = new Label("Kuvaus");
        Button btLisaaPalvelu = new Button("Lisää palvelu");
        Button btMuokkaaPalvelu = new Button("Muokkaa palvelu");
        Button btPoistaPalvelu = new Button("Poista palvelu");
        Button btPalveluHaku = new Button("Hae");
        gpPalvelut.add(lbPalveluID, 0, 0);
        gpPalvelut.add(tfPalveluID, 1, 0);
        gpPalvelut.add(lbPalveluNimi, 0, 1);
        gpPalvelut.add(tfPalveluNimi, 1, 1);
        gpPalvelut.add(lbPalveluHinta, 0, 2);
        gpPalvelut.add(tfPalveluHinta, 1, 2);
        gpPalvelut.add(lbAlueIDPalvelu, 0, 3);
        gpPalvelut.add(tfAlueIDPalvelu, 1, 3);
        gpPalvelut.add(lbPalveluKuvaus, 0, 4);
        gpPalvelut.add(taPalveluKuvaus, 1, 4);
        gpPalvelut.add(btPalveluHaku, 2, 0);
        HBox hbPalveluNapit = new HBox();
        hbPalveluNapit.getChildren().addAll(btLisaaPalvelu, btMuokkaaPalvelu, btPoistaPalvelu);
        VBox vbPalvelu = new VBox();
        vbPalvelu.setSpacing(10);
        hbPalveluNapit.setPadding(new Insets(10,10,10,10));
        vbPalvelu.getChildren().addAll(gpPalvelut, hbPalveluNapit);
        palvelut.setCenter(vbPalvelu);
        gpPalvelut.setVgap(5);
        gpPalvelut.setPadding(new Insets(10,10,10,10));

        Scene palvelutScene = new Scene(palvelut, 700, 700);
        btPalvelut.setOnAction(e->{
            palvelut.setTop(hbValikko);
            primaryStage.setScene(palvelutScene);
        });

        //Luodaan varaus näkymä
        BorderPane varaus = new BorderPane();
        ListView lvVaraus = new ListView();
        varaus.setLeft(lvVaraus);
        Scene varausScene = new Scene(varaus, 700, 700);
        TextField tfVarausID = new TextField();
        Label lbVarausID = new Label("Varaus ID");
        TextField tfAsiakasID = new TextField();
        Label lbAsiakasID = new Label("Asiakas ID");
        TextField tfMokkiIDVaraus = new TextField();
        Label lbMokkiIDVaraus = new Label("Mökin ID");
        TextField tfVarattuAlkuPvm = new TextField();
        Label lbVarattuAlkuPvm = new Label("Varattu alku pvm");
        TextField tfVarattuLoppuPvm = new TextField();
        Label lbVarattuLoppuPvm = new Label("Varattu loppu pvm");
        TextField tfVahvistusPvm = new TextField();
        Label lbVahvistusPvm = new Label("Vahvistus pvm");
        TextField tfVarattuPvm = new TextField();
        Label lbVarattuPvm = new Label("Varattu pvm");
        Button btLisaaVaraus = new Button("Lisää varaus");
        Button btMuokkaaVaraus = new Button("Muokkaa varaus");
        Button btPoistaVaraus = new Button("Poista varaus");
        Button btVarausHaku = new Button("Hae");
        GridPane gpVaraus = new GridPane();
        gpVaraus.add(lbVarausID, 0, 0);
        gpVaraus.add(tfVarausID, 1, 0);
        gpVaraus.add(lbAsiakasID, 0, 1);
        gpVaraus.add(tfAsiakasID, 1, 1);
        gpVaraus.add(lbMokkiIDVaraus, 0, 2);
        gpVaraus.add(tfMokkiIDVaraus, 1, 2);
        gpVaraus.add(lbVarattuAlkuPvm, 0, 3);
        gpVaraus.add(tfVarattuAlkuPvm, 1, 3);
        gpVaraus.add(lbVarattuLoppuPvm, 0, 4);
        gpVaraus.add(tfVarattuLoppuPvm, 1, 4);
        gpVaraus.add(lbVahvistusPvm, 0, 5);
        gpVaraus.add(tfVahvistusPvm, 1, 5);
        gpVaraus.add(lbVarattuPvm, 0, 6);
        gpVaraus.add(tfVarattuPvm, 1, 6);
        gpVaraus.add(btVarausHaku, 2, 0);
        HBox hbVarausNapit = new HBox();
        hbVarausNapit.getChildren().addAll(btLisaaVaraus, btMuokkaaVaraus, btPoistaVaraus);
        VBox vbVaraus = new VBox();
        vbVaraus.setSpacing(10);
        hbVarausNapit.setPadding(new Insets(10,10,10,10));
        vbVaraus.getChildren().addAll(gpVaraus, hbVarausNapit);
        varaus.setCenter(vbVaraus);
        gpVaraus.setVgap(5);
        gpVaraus.setPadding(new Insets(10,10,10,10));

        btVaraus.setOnAction(e->{
            varaus.setTop(hbValikko);
            primaryStage.setScene(varausScene);
        });

    }
}
