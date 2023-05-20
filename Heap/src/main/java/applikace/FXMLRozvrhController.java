/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package applikace;

import java.io.File;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.Iterator;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.FileChooser;
import javafx.util.StringConverter;
import misc.Generator;
import misc.eTypKey;
import misc.eTypProhl;
import pamatky.GPS;
import pamatky.IPamatky;
import pamatky.Pamatky;
import pamatky.Zamek;

/**
 * FXML Controller class
 *
 * @author wille
 */
public class FXMLRozvrhController implements Initializable {
    
    @FXML
    private Button btnGeneruj;
    
    @FXML
    private Button btnExport;
    
    @FXML
    private Button btnImport;
    
    @FXML
    private Button btnVlozProces;
    
    @FXML
    private Button btnNajdi;
    
    @FXML
    private Button btnNajdiNejblizsi;
    
    @FXML
    private Button btnOdeber;
    
    @FXML
    private Button btnPrebuduj;
    
    @FXML
    private Button btnZrus;
    
    @FXML
    private ListView<Zamek> listView;
    
    @FXML
    private RadioButton radiobtnGPS;
    
    @FXML
    private RadioButton radiobtnNazev;
    
    @FXML
    private RadioButton radiobtnInOrder;
    
    @FXML
    private RadioButton radiobtnBreadthFirst;
    
    @FXML
    private ToggleGroup radioToggle;
    
    @FXML
    private ToggleGroup radioToggle2;
    
    @FXML
    private Spinner<Double> spinnerE;
    
    @FXML
    private Spinner<Double> spinnerN;
    
    @FXML
    private Spinner<Double> spinnerVlozE;
    
    @FXML
    private Spinner<Double> spinnerVlozN;
    
    @FXML
    private Spinner<Integer> spinnerGenerujCisloProcesu;
    
    @FXML
    private TextField tfNazev;
    
    @FXML
    private TextField tfNazevVloz;
    
    private IPamatky pamatky = new Pamatky();
    private eTypProhl typProhl = eTypProhl.IN_ORDER;
    private eTypKey typKey = eTypKey.NAZEV;
    
    private StringConverter<Double> doubleConverter = new StringConverter<Double>() {
        private final DecimalFormat df = new DecimalFormat("#.######");
        
        @Override
        public String toString(Double object) {
            if (object == null) {
                return "";
            }
            
            return df.format(object);
        }
        
        @Override
        public Double fromString(String string) {
            try {
                if (string == null) {
                    return null;
                }
                
                string = string.trim();
                
                if (string.length() < 1) {
                    return null;
                }
                
                return df.parse(string).doubleValue();
            } catch (ParseException ex) {
                throw new RuntimeException(ex);
            }
        }
    };

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        SpinnerValueFactory svfE = new SpinnerValueFactory.DoubleSpinnerValueFactory(-180, 180, 0.0, 0.000001);
        spinnerE.setValueFactory(svfE);
        
        SpinnerValueFactory svfN = new SpinnerValueFactory.DoubleSpinnerValueFactory(-90, 90, 0.0, 0.000001);
        spinnerN.setValueFactory(svfN);
        
        SpinnerValueFactory svfVlozE = new SpinnerValueFactory.DoubleSpinnerValueFactory(-180, 180, 0.0, 0.000001);
        spinnerVlozE.setValueFactory(svfVlozE);
        
        SpinnerValueFactory svfVlozN = new SpinnerValueFactory.DoubleSpinnerValueFactory(-90, 90, 0.0, 0.000001);
        spinnerVlozN.setValueFactory(svfVlozN);
        
        SpinnerValueFactory svfCisloProcesu = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 50);
        spinnerGenerujCisloProcesu.setValueFactory(svfCisloProcesu);
        
        svfE.setConverter(doubleConverter);
        
        svfN.setConverter(doubleConverter);
        
        svfVlozE.setConverter(doubleConverter);
        
        svfVlozN.setConverter(doubleConverter);
        
        pamatky.nastavKlic(eTypKey.NAZEV);
    }
    
    @FXML
    private void btnExport(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Ulozit soubor...");
        final FileChooser.ExtensionFilter extensionFilterCSV = new FileChooser.ExtensionFilter("CSV soubory", "*.csv");
        fileChooser.getExtensionFilters().add(extensionFilterCSV);
        fileChooser.setInitialDirectory(new File("."));
        File file = fileChooser.showSaveDialog(listView.getScene().getWindow());
        if (file != null) {
            pamatky.exportDat(file.getName());
        }
    }
    
    @FXML
    private void btnImport(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Nacist soubor...");
        final FileChooser.ExtensionFilter extensionFilterCSV = new FileChooser.ExtensionFilter("CSV soubory", "*.csv");
        fileChooser.getExtensionFilters().add(extensionFilterCSV);
        fileChooser.setInitialDirectory(new File("."));
        File file = fileChooser.showOpenDialog(listView.getScene().getWindow());
        if (file != null) {
            pamatky.importDatZTXT(file.getName());
            refreshListView();
        }
    }
    
    @FXML
    void btnGeneruj(ActionEvent event) {
        int amountOfGenerations = spinnerGenerujCisloProcesu.getValue();
        Zamek[] zamky = Generator.genZamek(amountOfGenerations);
        for (int i = 0; i < amountOfGenerations; i++) {
            pamatky.vlozZamek(zamky[i]);
        }
        refreshListView();
    }
    
    @FXML
    void btnNajdi(ActionEvent event) {
        Zamek zamek = pamatky.najdiZamek(keyFromInput());
        
        if (zamek == null) {
            alert("Zámek nenalezen");
            return;
        }
        
        refreshListView(zamek);
    }
    
    @FXML
    void btnNajdiNejblizsi(ActionEvent event) {
        if (this.typKey != eTypKey.GPS) {
            alert("Zvolte GPS");
            return;
        }
        
        Zamek zamek = pamatky.najdiNejbliz(keyFromInput());
        
        if (zamek == null) {
            alert("Zámek nenalezen");
            return;
        }
        
        refreshListView(zamek);
    }
    
    @FXML
    void btnOdeber(ActionEvent event) {
        Zamek zamek = pamatky.odeberZamek(keyFromInput());
        
        if (zamek == null) {
            alert("Zámek nenalezen");
            return;
        }
        
        refreshListView();
    }
    
    @FXML
    void btnPrebuduj(ActionEvent event) {
        pamatky.prebuduj();
        
        refreshListView();
    }
    
    @FXML
    void btnVloz(ActionEvent event) {
        if (tfNazevVloz.getText().isBlank()) {
            alert("Název prázdný");
            return;
        }
        Zamek zamek = new Zamek(tfNazevVloz.getText(), new GPS(spinnerVlozN.getValue(), spinnerVlozE.getValue()));
        funcVloz(zamek);
    }
    
    @FXML
    void btnZrus(ActionEvent event) {
        pamatky.zrus();
        
        refreshListView();
    }
    
    @FXML
    void radioBreadthFirst(ActionEvent event) {
        typProhl = eTypProhl.BREADTH_FIRST;
        
        refreshListView();
    }
    
    @FXML
    void radioInOrder(ActionEvent event) {
        typProhl = eTypProhl.IN_ORDER;
        
        refreshListView();
    }
    
    @FXML
    void radioGPS(ActionEvent event) {
        pamatky.nastavKlic(eTypKey.GPS);
        typKey = eTypKey.GPS;
        
        tfNazev.setDisable(true);
        tfNazev.setText("");
        spinnerN.setDisable(false);
        spinnerE.setDisable(false);
    }
    
    @FXML
    void radioNazev(ActionEvent event) {
        pamatky.nastavKlic(eTypKey.NAZEV);
        typKey = eTypKey.NAZEV;
        
        tfNazev.setDisable(false);
        spinnerN.setDisable(true);
        spinnerE.setDisable(true);
        spinnerN.getValueFactory().setValue(0d);
        spinnerE.getValueFactory().setValue(0d);
    }
    
    private void refreshListView() {
        refreshListView(null);
    }
    
    private void refreshListView(Zamek zamek) {
        listView.getItems().clear();
        
        Iterator<Zamek> iter = pamatky.vytvorIterator(typProhl);
        while (iter.hasNext()) {
            listView.getItems().add(iter.next());
        }
        
        listView.getSelectionModel().select(zamek);
    }
    
    private void funcVloz(Zamek zamek) {
        pamatky.vlozZamek(zamek);
        
        refreshListView();
    }
    
    private void alert(String text) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        
        alert.setHeaderText(null);
        alert.setTitle("Alert");
        alert.setContentText(text);
        
        alert.showAndWait();
    }
    
    private String keyFromInput() {
        String key = "";
        
        switch (this.typKey) {
            case NAZEV -> {
                key = tfNazev.getText();
            }
            case GPS -> {
                key = String.format(Locale.ROOT, "%.6f;%.6f", spinnerN.getValue(), spinnerE.getValue());
            }
        }
        
        return key;
    }
    
}
