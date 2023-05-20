/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package rozhrani;

import generator.Generator;
import java.io.File;
import java.net.URL;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import proces.Proces;
import proces.ProcesManualni;
import proces.ProcesRoboticky;
import proces.VyrobniProces;
import proces.enumPozice;
import proces.enumReorg;

/**
 * FXML Controller class
 *
 * @author wille
 */
public class FXMLRozvrhController implements Initializable {

    @FXML
    private TextField tfID;
    @FXML
    private Button btnImport;
    @FXML
    private Button btnExport;
    @FXML
    private Button btnVlozPrvni;
    @FXML
    private Button btnVlozNaslednik;
    @FXML
    private Button btnVlozPosledni;
    @FXML
    private Button btnVlozPredchudce;
    @FXML
    private Button btnZpristupniPrvni;
    @FXML
    private Button btnZpristupniNaslednik;
    @FXML
    private Button btnZpristupniPosledni;
    @FXML
    private Button btnZpristupniPredchudce;
    @FXML
    private Button btnOdeberPrvni;
    @FXML
    private Button btnOdeberNaslednik;
    @FXML
    private Button btnOdeberAktualni;
    @FXML
    private Button btnOdeberPosledni;
    @FXML
    private Button btnOdeberPredchudce;
    @FXML
    private Button btnZrus;
    @FXML
    private Button btnAgregace;
    @FXML
    private Button btnDekompozice;
    @FXML
    private Button btnGeneruj;
    @FXML
    private Spinner<Integer> spinnerReorgCas;
    @FXML
    private Spinner<Integer> spinnerVlkadaniPocetOsob;
    @FXML
    private Spinner<Integer> spinnerVkladaniCasProcesu;
    @FXML
    private Spinner<Integer> spinnerGenerujCisloProcesu;
    @FXML
    private ListView<String> listView;

    private VyrobniProces vyrobniProces = new VyrobniProces();
    private ObservableList<String> oList;
    private Proces pomocnyProces;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        oList = FXCollections.observableArrayList();
        listView.setItems(oList);

        SpinnerValueFactory svfReorganizace = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 60);
        spinnerReorgCas.setValueFactory(svfReorganizace);

        SpinnerValueFactory svfCasProcesu = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 60);
        spinnerVkladaniCasProcesu.setValueFactory(svfCasProcesu);

        SpinnerValueFactory svfPocetOsob = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 30);
        spinnerVlkadaniPocetOsob.setValueFactory(svfPocetOsob);

        SpinnerValueFactory svfCisloProcesu = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 50);
        spinnerGenerujCisloProcesu.setValueFactory(svfCisloProcesu);
    }

    @FXML
    private void btnFileUlozit(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Ulozit soubor...");
        final ExtensionFilter extensionFilterCSV = new ExtensionFilter("CSV soubory", "*.csv");
        fileChooser.getExtensionFilters().add(extensionFilterCSV);
        fileChooser.setInitialDirectory(new File("."));
        File file = fileChooser.showSaveDialog(listView.getScene().getWindow());
        if (file != null) {
            vyrobniProces.exportDat(file.getName());
        }
    }

    @FXML
    private void btnFileNacist(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Nacist soubor...");
        final ExtensionFilter extensionFilterCSV = new ExtensionFilter("CSV soubory", "*.csv");
        fileChooser.getExtensionFilters().add(extensionFilterCSV);
        fileChooser.setInitialDirectory(new File("."));
        File file = fileChooser.showOpenDialog(listView.getScene().getWindow());
        if (file != null) {
            vyrobniProces.importDat(file.getName());
            refreshListView();
        }

    }

    @FXML
    private void btnVlozPrvniClick(ActionEvent event) {
        vlozProces(enumPozice.PRVNI);

    }

    @FXML
    private void btnVlozNaslednikClick(ActionEvent event) {

        try {
            vlozProces(enumPozice.NASLEDNIK);
        } catch (NoSuchElementException e) {
            alertUtil(e.getMessage());
        }
    }

    @FXML
    private void btnVlozPosledniClick(ActionEvent event) {
        vlozProces(enumPozice.POSLEDNI);

    }

    @FXML
    private void btnVlozPredchudceClick(ActionEvent event) {

        try {
            vlozProces(enumPozice.PREDCHUDCE);
        } catch (NoSuchElementException e) {
            alertUtil(e.getMessage());
        }
    }

    @FXML
    private void btnZpristupPrvniClick(ActionEvent event) {
        zpristupniProces(enumPozice.PRVNI);
    }

    @FXML
    private void btnZpristupNaslednikClick(ActionEvent event) {
        zpristupniProces(enumPozice.NASLEDNIK);
    }

    @FXML
    private void btnZpristupPosledniClick(ActionEvent event) {
        zpristupniProces(enumPozice.POSLEDNI);
    }

    @FXML
    private void btnZpristupPredchudceClick(ActionEvent event) {
        zpristupniProces(enumPozice.PREDCHUDCE);
    }

    @FXML
    private void btnOdeberPrvniClick(ActionEvent event) {
        odeberProces(enumPozice.PRVNI);
    }

    @FXML
    private void btnOdeberNaslednikClick(ActionEvent event) {
        odeberProces(enumPozice.NASLEDNIK);
    }

    @FXML
    private void btnOdeberAktualniClick(ActionEvent event) {
        odeberProces(enumPozice.AKTUALNI);
    }

    @FXML
    private void btnOdeberPosledniClick(ActionEvent event) {
        odeberProces(enumPozice.POSLEDNI);
    }

    @FXML
    private void btnOdeberPredchudceClick(ActionEvent event) {
        odeberProces(enumPozice.PREDCHUDCE);
    }

    @FXML
    private void btnZrusClick(ActionEvent event) {
        vyrobniProces.zrus();
        refreshListView();
    }

    @FXML
    private void btnAgregaceClick(ActionEvent event) {
        var cas = spinnerReorgCas.getValue();
        vyrobniProces.reorganizace(enumReorg.AGREGACE, vyrobniProces.vytipujKandidatiReorg(cas, enumReorg.AGREGACE));

        refreshListView();
    }

    @FXML
    private void btnDekompoziceClick(ActionEvent event) {
        var cas = spinnerReorgCas.getValue();
        vyrobniProces.reorganizace(enumReorg.DEKOMPOZICE, vyrobniProces.vytipujKandidatiReorg(cas, enumReorg.DEKOMPOZICE));

        refreshListView();
    }

    @FXML
    private void btnGenerujClick(ActionEvent event) {
        int pocetGeneraci = spinnerGenerujCisloProcesu.getValue();
        Proces[] procesy = Generator.generujRandomniProces(pocetGeneraci);
        for (int i = 0; i < pocetGeneraci; i++) {
            vyrobniProces.vlozProces(procesy[i], enumPozice.POSLEDNI);
        }

        refreshListView();
    }

    private void alertUtil(String text) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("ERROR!");
        alert.setHeaderText(null);
        alert.setContentText(text);

        alert.showAndWait();
    }

    private void refreshListView() {
        oList.clear();
        Iterator<Proces> iterVyrobniProces = vyrobniProces.iterator();
        while (iterVyrobniProces.hasNext()) {
            oList.add(iterVyrobniProces.next().toString());
        }
        listView.refresh();
        gotoPos();
    }

    private void vlozProces(enumPozice pozice) {
        if (tfID.getText().isBlank()) {
            alertUtil("Prazdne ID!");
            return;
        }
        var cas = spinnerVkladaniCasProcesu.getValue();
        var osoby = spinnerVlkadaniPocetOsob.getValue();
        var id = tfID.getText();

        Proces proces;

        if (osoby == 0) {
            proces = new ProcesRoboticky(id, cas);
        } else {
            proces = new ProcesManualni(id, osoby, cas);
        }

        vyrobniProces.vlozProces(proces, pozice);

        tfID.setText("");
        spinnerVlkadaniPocetOsob.getValueFactory().setValue(0);
        spinnerVkladaniCasProcesu.getValueFactory().setValue(1);

        refreshListView();
    }

    private void odeberProces(enumPozice pozice) {
        try {
            vyrobniProces.odeberProces(pozice);
            refreshListView();
        } catch (NoSuchElementException e) {
            alertUtil(e.getMessage());
        }

        refreshListView();
    }

    private void zpristupniProces(enumPozice pozice) {
        try {
            pomocnyProces = vyrobniProces.zpristupniProces(pozice);
            refreshListView();
        } catch (NoSuchElementException e) {
            alertUtil(e.getMessage());
        }

        refreshListView();
    }

    private void gotoPos() {
        if (pomocnyProces != null) {
            int index = 0;
            for (Proces nextProces : vyrobniProces) {
                if (nextProces == pomocnyProces) {
                    listView.getSelectionModel().select(index);
                }
                index++;
            }
        }
    }

}
