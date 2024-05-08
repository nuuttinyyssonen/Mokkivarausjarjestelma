package com.example.ohjelmistotuotanto;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

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

        alkuNakyma.setCenter(hbValikko);
        hbValikko.setAlignment(Pos.CENTER);



        Scene alkuScene = new Scene(alkuNakyma, 700, 250);
        primaryStage.setTitle("Alkunäyttö");
        primaryStage.setScene(alkuScene);

        //Luodaan alueet näkymä
        BorderPane alueet = new BorderPane();
        ListView lvAlueet = new ListView();
        alueet.setLeft(lvAlueet);
        Scene alueetScene = new Scene(alueet, 700, 700);

        TextField tfAlueNimi = new TextField();
        Label lbAlueNimi = new Label("Hae nimellä");
        Button btLisaaAlue = new Button("Lisää alue");
        Button btMuokkaaAlue = new Button("Muokkaa alue");
        Button btPoistaAlue = new Button("Poista alue");
        Button btAlueHaku = new Button("Hae");

        HBox hbAlueNapit = new HBox();
        hbAlueNapit.getChildren().addAll(btLisaaAlue, btMuokkaaAlue, btPoistaAlue);

        GridPane gpAlue = new GridPane();

        gpAlue.add(btAlueHaku, 2, 0);
        gpAlue.add(lbAlueNimi, 0, 0);
        gpAlue.add(tfAlueNimi, 1, 0);
        gpAlue.setVgap(5);
        gpAlue.setPadding(new Insets(10,10,10,10));
        VBox vbAlue = new VBox();
        vbAlue.setSpacing(10);
        hbAlueNapit.setPadding(new Insets(10,10,10,10));
        vbAlue.getChildren().addAll(gpAlue, hbAlueNapit);
        alueet.setCenter(vbAlue);



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
        btAlueHaku.setOnAction(e->{
            int alue_id = DatabaseUtils.selectAlueetByName(tfAlueNimi.getText());
            List<Mokki> mokitLista = DatabaseUtils.selectMokitByAlueId(alue_id);
            lvAlueet.getItems().clear();
            lvAlueet.getItems().addAll(mokitLista);
        });


        primaryStage.show();

        //Luodaan mökit näkymä
        BorderPane mokit = new BorderPane();
        ListView<Mokki> lvMokit = new ListView();


        mokit.setLeft(lvMokit);
        TextField tfMokkiID = new TextField();
        Label lbMokkiID = new Label("Hae mökkiä nimellä");
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

        lvMokit.setOnMouseClicked(e->{
            Mokki valittuMokki = lvMokit.getSelectionModel().getSelectedItem();
            tfMokkiAlueId.setText(String.valueOf(valittuMokki.getAlue_id()));
            tfPostnro.setText(valittuMokki.getPostinro());
            tfMokkiNimi.setText(valittuMokki.getMokkinimi());
            tfOsoite.setText(valittuMokki.getKatuosoite());
            tfHenkilomaara.setText(String.valueOf(valittuMokki.getHenkilomaara()));
            tfMokkiHinta.setText(String.valueOf(valittuMokki.getHinta()));
            taMokkiKuvaus.setText(valittuMokki.getKuvaus());
            taMokkiVarustelu.setText(valittuMokki.getVarustelu());
        });

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

        btMokkiHaku.setOnAction(e-> {
            List<Mokki> mokitLista = DatabaseUtils.selectMokitByName(tfMokkiID.getText());
            lvMokit.getItems().clear();
            lvMokit.getItems().addAll(mokitLista);
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
        TextField tfAsiakasHaeNimella = new TextField();
        Label lbAsiakasHaeNimella = new Label("Hae nimellä");
        TextField tfEtunimi = new TextField();
        Label lbEtunimi = new Label("Etunimi");
        TextField tfSukunimi = new TextField();
        Label lbSukunimi = new Label("Sukunimi");
        TextField tfPuhelin = new TextField();
        Label lbPuhelin = new Label("Puhelin nro");
        TextField tfEmail = new TextField();
        Label lbEmail = new Label("Sähköposti");
        TextField tfLahiosoite = new TextField();
        Label lbLahiosoite = new Label("Lähiosoite");
        TextField tfPostinro = new TextField();
        Label lbPostinro = new Label("Postinumero");
        GridPane gpAsiakas = new GridPane();
        gpAsiakas.add(lbAsiakasHaeNimella, 0, 0);
        gpAsiakas.add(tfAsiakasHaeNimella, 1, 0);
        gpAsiakas.add(lbEtunimi, 0, 1);
        gpAsiakas.add(tfEtunimi, 1, 1);
        gpAsiakas.add(lbSukunimi, 0, 2);
        gpAsiakas.add(tfSukunimi, 1, 2);
        gpAsiakas.add(lbPuhelin, 0, 3);
        gpAsiakas.add(tfPuhelin, 1, 3);
        gpAsiakas.add(lbEmail, 0, 4);
        gpAsiakas.add(tfEmail, 1, 4);
        gpAsiakas.add(lbLahiosoite, 0, 5);
        gpAsiakas.add(tfLahiosoite, 1, 5);
        gpAsiakas.add(lbPostinro, 0, 6);
        gpAsiakas.add(tfPostinro, 1, 6);
        Button btLisaaAsiakas = new Button("Lisää asiakas");
        Button btMuokkaaAsiakas = new Button("Muokkaa asiakas");
        Button btPoistaAsiakas = new Button("Poista asiakas");
        Button btAsiakasHaku = new Button("Hae");
        gpAsiakas.add(btAsiakasHaku, 2, 0);
        HBox hbAsiakasNapit = new HBox();
        hbAsiakasNapit.getChildren().addAll(btLisaaAsiakas, btMuokkaaAsiakas, btPoistaAsiakas);
        VBox vbAsiakas = new VBox();
        vbAsiakas.setSpacing(10);
        hbAsiakasNapit.setPadding(new Insets(10,10,10,10));
        vbAsiakas.getChildren().addAll(gpAsiakas, hbAsiakasNapit);
        asiakkaat.setCenter(vbAsiakas);
        gpAsiakas.setVgap(5);
        gpAsiakas.setPadding(new Insets(10,10,10,10));

        Scene asiakkaatScene = new Scene(asiakkaat, 700, 700);
        btAsiakkaat.setOnAction(e->{
            asiakkaat.setTop(hbValikko);
            primaryStage.setScene(asiakkaatScene);
        });

       btAsiakasHaku.setOnAction(e->{
            List<Asiakas> asiakasLista = DatabaseUtils.selectAsiakasByName(tfAsiakasHaeNimella.getText());
            lvAsiakkaat.getItems().clear();
            lvAsiakkaat.getItems().addAll(asiakasLista);
       });
        btLisaaAsiakas.setOnAction(e->{
            String etunimi = tfEtunimi.getText();
            String sukunimi = tfSukunimi.getText();
            String puhelin = tfPuhelin.getText();
            String email = tfEmail.getText();
            String lahiosoite = tfLahiosoite.getText();
            String postinro = tfPostinro.getText();
            DatabaseUtils.insertAsiakas(etunimi, sukunimi, puhelin, email, lahiosoite, postinro);
        });

        //Luodaan palvelut näkymä
        BorderPane palvelut = new BorderPane();
        ListView <Palvelu> lvPalvelut = new ListView();
        palvelut.setLeft(lvPalvelut);
        GridPane gpPalvelut = new GridPane();
        TextField tfPalveluHaku = new TextField();

        Label lbPalveluHaku = new Label("Hae palvelua nimellä");
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
        gpPalvelut.add(lbPalveluHaku, 0, 0);
        gpPalvelut.add(tfPalveluHaku, 1, 0);
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

        btLisaaPalvelu.setOnAction(e->{
            int alue_id = Integer.parseInt(tfAlueIDPalvelu.getText());
            String palveluNimi = tfPalveluNimi.getText();
            double hinta = Double.parseDouble(tfPalveluHinta.getText());
            String kuvaus = taPalveluKuvaus.getText();
            DatabaseUtils.insertPalvelu(alue_id, palveluNimi, kuvaus, hinta);
        });
        btPalveluHaku.setOnAction(e->{
            List<Palvelu> palvelutLista = DatabaseUtils.selectPalvelutByName(tfPalveluHaku.getText());
            lvPalvelut.getItems().clear();
            lvPalvelut.getItems().addAll(palvelutLista);

        });
        lvPalvelut.setOnMouseClicked(e->{
            Palvelu valittuPalvelu = lvPalvelut.getSelectionModel().getSelectedItem();
            tfAlueIDPalvelu.setText(String.valueOf(valittuPalvelu.getAlue_id()));
            tfPalveluNimi.setText(valittuPalvelu.getNimi());
            tfPalveluHinta.setText(String.valueOf(valittuPalvelu.getHinta()));
            taPalveluKuvaus.setText(valittuPalvelu.getKuvaus());
        });


        //Luodaan varaus näkymä
        BorderPane varaus = new BorderPane();
        ListView lvVaraus = new ListView();
        varaus.setLeft(lvVaraus);
        Scene varausScene = new Scene(varaus, 700, 700);
        TextField tfVarausID = new TextField();
        Label lbVarausID = new Label("Hae mökkiä nimellä ");
        TextField tfAsiakasIDVaraus = new TextField();
        Label lbAsiakasIDVaraus = new Label("Asiakas ID");
        TextField tfMokkiIDVaraus = new TextField();
        Label lbMokkiIDVaraus = new Label("Mökin ID");
        DatePicker startDatePicker = new DatePicker();
        DatePicker endDatePicker = new DatePicker();
        Label lbVarattuAlkuPvm = new Label("Varattu alku pvm");
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
        gpVaraus.add(lbAsiakasIDVaraus, 0, 1);
        gpVaraus.add(tfAsiakasIDVaraus, 1, 1);
        gpVaraus.add(lbMokkiIDVaraus, 0, 2);
        gpVaraus.add(tfMokkiIDVaraus, 1, 2);
        gpVaraus.add(lbVarattuAlkuPvm, 0, 3);
        gpVaraus.add(startDatePicker, 1, 3);
        gpVaraus.add(lbVarattuLoppuPvm, 0, 4);
        gpVaraus.add(endDatePicker, 1, 4);
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

        btLisaaVaraus.setOnAction(e->{
            int asiakas_id = Integer.parseInt(tfAsiakasIDVaraus.getText());
            int mokki_id = Integer.parseInt(tfMokkiIDVaraus.getText());
            LocalDate varattu_pvm_alku = startDatePicker.getValue();
            LocalDate varattu_pvm_loppu = endDatePicker.getValue();
            DatabaseUtils.insertVaraus(asiakas_id, mokki_id, varattu_pvm_alku, varattu_pvm_loppu);
        });

        btVaraus.setOnAction(e->{
            varaus.setTop(hbValikko);
            primaryStage.setScene(varausScene);
        });
        btVarausHaku.setOnAction(e->{
            int mokki_id = Integer.parseInt(DatabaseUtils.getMokkiIdByMokkiName(tfVarausID.getText()));
            List<Varaus> varausLista = DatabaseUtils.selectVarausByMokkiID(mokki_id);
            lvVaraus.getItems().clear();
            lvVaraus.getItems().addAll(varausLista);
        });
        lvVaraus.setOnMouseClicked(e->{
            Varaus valittuVaraus = (Varaus) lvVaraus.getSelectionModel().getSelectedItem();
            tfAsiakasIDVaraus.setText(String.valueOf(valittuVaraus.getAsiakas_id()));
            tfMokkiIDVaraus.setText(String.valueOf(valittuVaraus.getMokki_id()));
            endDatePicker.setValue(valittuVaraus.getVarattu_loppupvm());
            startDatePicker.setValue(valittuVaraus.getVarattu_alkupvm());
        });
        btLisaaVaraus.setOnAction(e->{
            int asiakas_id = Integer.parseInt(tfAsiakasIDVaraus.getText());
            int mokki_id = Integer.parseInt(tfMokkiIDVaraus.getText());
            LocalDate varattu_pvm_alku = startDatePicker.getValue();
            LocalDate varattu_pvm_loppu = endDatePicker.getValue();
            DatabaseUtils.insertVaraus(asiakas_id, mokki_id, varattu_pvm_alku, varattu_pvm_loppu);
        });

    }
}