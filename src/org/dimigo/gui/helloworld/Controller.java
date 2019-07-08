package org.dimigo.gui.helloworld;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

import java.awt.*;
import java.io.*;
import java.net.URI;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML
    private Button btn_start;
    @FXML
    private ComboBox<SearchType> cbSearch;
    @FXML
    private Button btn_rankUpdate;
    @FXML
    private Label lbl_type;
    @FXML
    private Label lbl_conference;
    @FXML
    private ListView<String> list_rank;
    @FXML
    private TextField textfield_search;
    @FXML
    private Button btn_search;
    @FXML
    private Button btn_conference;
    @FXML
    private WebView web_view;
    @FXML
    private Button btn_openWindow;

    List<String> list2 = new ArrayList<>();
    List<String> myList = new ArrayList<>();
    String conference = "EAST";
    String uri_text;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        ObservableList<SearchType> comboItems =
                FXCollections.observableArrayList();
        comboItems.add(new SearchType("Player Rank", "Player"));
        comboItems.add(new SearchType("Team Rank", "Team"));

        cbSearch.setItems(comboItems);
        btn_conference.setText("EAST");
        btn_conference.setVisible(false);
    }

    @FXML
    public void handleStartAction(ActionEvent event) throws IOException {

        Stage stage = (Stage) btn_start.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/dimigo/gui/helloworld/sample.fxml"));

        Parent root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }

    @FXML
    public void handleRankAction(ActionEvent event) {
        try {
            textfield_search.clear();
            list2.removeAll(list2);
            int num = 1;
            SearchType item = cbSearch.getSelectionModel().getSelectedItem();
            String type = item.getValue();
            System.out.printf("type : %s\n", type);

            List<String> rankList;
            if ("Player".equals(type)) {
                btn_conference.setVisible(false);
                lbl_conference.setVisible(false);

                rankList = Crawler.getRankList(type, " ");
                List<String> list = new ArrayList<>();
                for (int i = 0; i < rankList.size(); i++) {
                    if (i % 2 != 0) {
                        list.add(rankList.get(i));
                    }
                }
                rankList.removeAll(list);


                for (int i = 0; i < rankList.size(); i++) {
                    list2.add(i + 1 + ". " + rankList.get(i));
                }
                ObservableList<String> oList = FXCollections.observableArrayList(list2);
                list_rank.setItems(oList);

            } else if ("Team".equals(type)) {
                btn_conference.setVisible(true);
                btn_conference.setText("EAST");
                lbl_conference.setVisible(true);
                conference = "EAST";

                rankList = Crawler.getRankList(type, conference);
                for (int i = 0; i < rankList.size(); i++) {
                    list2.add(i + 1 + ". " + rankList.get(i));
                }
                ObservableList<String> oList = FXCollections.observableArrayList(list2);
                list_rank.setItems(oList);
            }
            lbl_type.setText(type);

        } catch (UnknownHostException e) {
            errorMessage("Please Connect Internet");

        } catch (NullPointerException e) {
            errorMessage("Please Select Rank Type");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @FXML
    public void handleConferenceAction() {
        try {
            textfield_search.clear();
            if (conference.equals("EAST")) {
                conference = "WEST";
                btn_conference.setText(conference);

            } else if (conference.equals("WEST")) {
                conference = "EAST";
                btn_conference.setText(conference);
            }
            list2.removeAll(list2);
            List<String> rankList;
            rankList = Crawler.getRankList("Team", conference);
            for (int i = 0; i < rankList.size(); i++) {
                list2.add(i + 1 + ". " + rankList.get(i));
            }
            ObservableList<String> oList = FXCollections.observableArrayList(list2);
            list_rank.setItems(oList);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @FXML
    public void handleWebSearchAction() {
        try {
            String text = list_rank.getSelectionModel().getSelectedItem();
            System.out.println(text);
            text = text.replace(" ", "+");
            SearchType item = cbSearch.getSelectionModel().getSelectedItem();
            String type = item.getValue();
            text = text.substring(text.indexOf('.') + 1);
            if (type.equals("Team")) {
                text = text + "+nba";
            }
//            URI uri = new URI("http://www.google.com/search?q=" + text);
            uri_text = "http://www.google.com/search?q=" + text + "&ie=UTF-8";
//            Desktop.getDesktop().browse(uri);
            WebEngine webEngine = web_view.getEngine();
            webEngine.load(uri_text);
        } catch (NullPointerException e) {
            errorMessage("List is Empty");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleOpenWindowAction() {
        try {
            if(uri_text != null) {
                URI uri = new URI(uri_text);
                Desktop.getDesktop().browse(uri);
            } else {
                errorMessage("Webview is Empty");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleListSearchAction() {
        try {
            String search_text = textfield_search.getText();
            if(search_text.equals("")) {
                errorMessage("Textfield is Empty");
                return;
            }
            System.out.println(search_text);

            List<String> rankList = list2;
            List<String> newList = new ArrayList<>();

            for (int i = 0; i < rankList.size(); i++) {
                if (rankList.get(i).contains(search_text)) {
                    newList.add(rankList.get(i));
                }
            }

            for (int i = 0; i < newList.size(); i++) {
                System.out.print(newList.get(i));
            }

            ObservableList<String> oList = FXCollections.observableArrayList(newList);
            list_rank.setItems(oList);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void errorMessage(String errorText) {
        System.out.println("ERROR");
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Error Message");
        alert.setHeaderText("ERROR!");
        alert.setContentText(errorText);
        alert.showAndWait();
    }


}
