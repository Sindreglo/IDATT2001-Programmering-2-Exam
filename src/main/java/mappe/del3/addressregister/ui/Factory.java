package mappe.del3.addressregister.ui;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import mappe.del3.addressregister.Address;
import mappe.del3.addressregister.AddressRegister;
import mappe.del3.addressregister.controll.MainController.MainController;
import java.util.Optional;

/**
 * Factory class for the GUI. Creates all elements for
 * the borderpane root stage.
 *
 * @author Sindre Glomnes
 * @version 2021-05-14
 */
public class Factory {

    private final MainController mainController; // Instance of MainController
    private final AddressRegister register = AddressRegister.getInstance(); // Instance of AddressRegister
    private final SearchDialog searchDialog = new SearchDialog(); // Instance of SearchDialog
    private final HBox statusBar = new HBox(); // The statusbar
    private TableView<Address> AddressTableView; // The TableView
    private ObservableList<Address> AddressRegisterListWrapper; // The content of the TableView

    /**
     * Constructor. Creates an instance of MainController.
     */
    public Factory() {
        this.mainController = new MainController();
    }

    /**
     * Creates a statusbar node with the
     * default status of OK.
     *
     * @return statusbar node
     */
    public Node createStatusBar() {
        statusBar.getChildren().add(new Text("Status: OK"));
        statusBar.setStyle("-fx-background-color: #999999;");

        return statusBar;
    }

    /**
     * Updates the statusbar with the current status
     * of the application.
     *
     * @param status "Import successful" "Export successful"
     */
    public void updateStatusBar(String status) {
        // Clears the statusbar of its previous status
        statusBar.getChildren().clear();
        // Sets the new status
        statusBar.getChildren().add(new Text("Status: " + status));
    }

    /**
     * Creates an AnchorPane with the buttons:
     * Add (Add address)
     * Edit (Edit address)
     * Remove (Remove address)
     *
     * @return AnchorPane with buttons
     */
    public AnchorPane createButtons() {
        // Creates the AnchorPane
        AnchorPane buttons = new AnchorPane();

        // Creates the three buttons
        Button addButton = new Button();
        Button removeButton = new Button();
        Button editButton = new Button();

        // Sets and formats the images shown on the buttons
        ImageView addImage = new ImageView(new Image("file:src/main/resources/images/add.png"));
        addImage.setFitHeight(35);
        addImage.setPreserveRatio(true);

        ImageView removeImage = new ImageView(new Image("file:src/main/resources/images/remove.png"));
        removeImage.setFitHeight(35);
        removeImage.setPreserveRatio(true);

        ImageView editImage = new ImageView(new Image("file:src/main/resources/images/edit.png"));
        editImage.setFitHeight(35);
        editImage.setPreserveRatio(true);

        addButton.setGraphic(addImage);
        removeButton.setGraphic(removeImage);
        editButton.setGraphic(editImage);

        // Sets layout for each button (Size and position)
        addButton.setLayoutX(10);
        addButton.setLayoutY(10);
        addButton.setPrefWidth(60);
        addButton.setPrefHeight(60);

        removeButton.setLayoutX(80);
        removeButton.setLayoutY(10);
        removeButton.setPrefWidth(60);
        removeButton.setPrefHeight(60);

        editButton.setLayoutX(150);
        editButton.setLayoutY(10);
        editButton.setPrefWidth(60);
        editButton.setPrefHeight(60);

        // Sets actions events for each button
        addButton.setOnAction(event -> mainController.addAddress(this));
        editButton.setOnAction(event -> mainController.editAddress(this.AddressTableView.getSelectionModel().getSelectedItem(), this));
        removeButton.setOnAction(event -> mainController.removeAddress(this.AddressTableView.getSelectionModel().getSelectedItem(), this));

        // Adds the buttons to the AnchorPane
        buttons.getChildren().addAll(addButton, removeButton, editButton);

        // Returns the AnchorPane
        return buttons;
    }

    /**
     * Creates a menuBar with the four menus:
     * Files (for import and export of files)
     * Edit (for add, edit and removal of addresses)
     * Search (for searching in list)
     * Help (information and options)
     *
     * @return The menuBar
     */
    public MenuBar createMenus() {

        // Creates a file menu
        Menu menuFile = new Menu("File");

        // Creates each item
        MenuItem importTxt = new MenuItem("Import from .txt...");
        MenuItem exportTxt = new MenuItem("Export to .txt...");
        SeparatorMenuItem separator = new SeparatorMenuItem();
        MenuItem importCsv = new MenuItem("Import from .csv...");
        MenuItem exportCsv = new MenuItem("Export to .csv...");
        SeparatorMenuItem separator1 = new SeparatorMenuItem();
        MenuItem reset = new MenuItem("Reset");
        MenuItem exit = new MenuItem("Exit");

        // Shortcut for exit application (X + CTRL)
        KeyCombination exitShortCut = new KeyCodeCombination(KeyCode.X, KeyCombination.CONTROL_DOWN);
        exit.setAccelerator(exitShortCut);

        // Sets action event for each item
        importTxt.setOnAction(event -> mainController.importTxt(this));
        exportTxt.setOnAction(event -> mainController.exportTxt(this));
        importCsv.setOnAction(event -> mainController.importCsv(this));
        exportCsv.setOnAction(event -> mainController.exportCsv(this));
        reset.setOnAction(event -> resetApplication());
        exit.setOnAction(event -> exitApplication());

        // Adds the items to the File menu
        menuFile.getItems().addAll(importTxt,exportTxt,separator,importCsv,exportCsv,separator1,reset,exit);

        // Edit
        Menu menuEdit = new Menu("Edit");

        MenuItem add = new MenuItem("Add new Address...");
        MenuItem remove = new MenuItem("Remove Selected Address");
        MenuItem edit = new MenuItem("Edit Selected Address");

        // Shortcut for adding address (A + CTRL)
        KeyCombination addShortCut = new KeyCodeCombination(KeyCode.A, KeyCombination.CONTROL_DOWN);
        add.setAccelerator(addShortCut);

        // Shortcut for removing address (R + CTRL)
        KeyCombination removeShortCut = new KeyCodeCombination(KeyCode.R, KeyCombination.CONTROL_DOWN);
        remove.setAccelerator(removeShortCut);

        // Shortcut for editing address (E + CTRL)
        KeyCombination editShortCut = new KeyCodeCombination(KeyCode.E, KeyCombination.CONTROL_DOWN);
        edit.setAccelerator(editShortCut);

        add.setOnAction(event -> mainController.addAddress(this));
        edit.setOnAction(event -> mainController.editAddress(this.AddressTableView.getSelectionModel().getSelectedItem(), this));
        remove.setOnAction(event -> mainController.removeAddress(this.AddressTableView.getSelectionModel().getSelectedItem(), this));

        menuEdit.getItems().addAll(add,edit,remove);

        // Search menu
        Menu search = new Menu("Search by");
        MenuItem byZipCode = new MenuItem("Zip Code");
        MenuItem byPostal = new MenuItem("Postal");
        MenuItem byMunicipalCode = new MenuItem("Municipal Code");
        MenuItem byMunicipalityName = new MenuItem("Municipality Name");
        MenuItem byCategory = new MenuItem("Category");
        MenuItem seperator2 = new SeparatorMenuItem();
        MenuItem removeFilter = new MenuItem("Remove Filter");

        // Shortcut for removing filter (F + CTRL)
        KeyCombination removeFilterShortCut = new KeyCodeCombination(KeyCode.F, KeyCombination.CONTROL_DOWN);
        removeFilter.setAccelerator(removeFilterShortCut);

        byZipCode.setOnAction(event -> {
            // Searches by zip code
            searchDialog.byZipCode();
            // Updates observableList with searched list
            setObservableListBySearch();
        });

        byPostal.setOnAction(event -> {
            searchDialog.byPostal();
            setObservableListBySearch();
        });

        byMunicipalCode.setOnAction(event -> {
            searchDialog.byMunicipalCode();
            setObservableListBySearch();
        });

        byMunicipalityName.setOnAction(event -> {
            searchDialog.byMunicipalityName();
            setObservableListBySearch();
        });

        byCategory.setOnAction(event -> {
            searchDialog.byCategory();
            setObservableListBySearch();
        });

        // Updates the observableList with list of addresses
        removeFilter.setOnAction(event -> updateObservableList());

        search.getItems().addAll(byZipCode,byPostal,byMunicipalCode
                ,byMunicipalityName,byCategory,seperator2,removeFilter);

        // Help menu
        Menu menuHelp = new Menu("Help");
        MenuItem about = new MenuItem("About");
        about.setOnAction(event -> helpAbout());
        menuHelp.getItems().addAll(about);

        // Creates the menu bar and adds all menus to it.
        MenuBar menuBar = new MenuBar();
        menuBar.getMenus().addAll(menuFile,menuEdit,search,menuHelp);
        return menuBar;
    }

    /**
     * Creates the TableView (list) with the five columns:
     * Zip code, postal, municipal code, municipality name and category,
     *
     * @return the tableView
     */
    public TableView<Address> createCentreContent() {
        // Creates the column for zip code
        TableColumn<Address, String> zipCodeColumn = new TableColumn<>("Zip Code");
        // Sets a minimum width for the column
        zipCodeColumn.setMinWidth(100);
        // Sets the cellValue for the column
        zipCodeColumn.setCellValueFactory(new PropertyValueFactory<>("zipCode"));

        TableColumn<Address, String> postalColumn = new TableColumn<>("Postal");
        postalColumn.setMinWidth(200);
        postalColumn.setCellValueFactory(new PropertyValueFactory<>("postal"));

        TableColumn<Address, Long> municipalCodeColumn = new TableColumn<>("Municipal Code");
        municipalCodeColumn.setMinWidth(100);
        municipalCodeColumn.setCellValueFactory(new PropertyValueFactory<>("municipalCode"));

        TableColumn<Address, String> municipalityNameColumn = new TableColumn<>("Municipality Name");
        municipalityNameColumn.setMinWidth(200);
        municipalityNameColumn.setCellValueFactory(new PropertyValueFactory<>("municipalityName"));

        TableColumn<Address, String> categoryColumn = new TableColumn<>("Category");
        categoryColumn.setMinWidth(100);
        categoryColumn.setCellValueFactory(new PropertyValueFactory<>("category"));

        // Creates the tableView
        TableView<Address> tableView = new TableView<>();
        ObservableList<Address> observableList = this.getAddressTableView();

        // Sets the list to the objects to be shown
        tableView.setItems(observableList);
        // Adds each column to the tableView
        tableView.getColumns().addAll(zipCodeColumn,postalColumn,municipalCodeColumn,municipalityNameColumn,categoryColumn);

        this.AddressTableView = tableView;
        return tableView;
    }

    /**
     * Sets the observableList used in the tableView equal to the the arrayList of
     * addresses in AddressRegister
     *
     * @return the ObservableList
     */
    private ObservableList<Address> getAddressTableView() {
        if (this.register == null) {
            this.AddressRegisterListWrapper = null;
        } else {
            this.AddressRegisterListWrapper = FXCollections.observableArrayList(this.register.getAddresses());
        }
        return this.AddressRegisterListWrapper;
    }

    /**
     * Updates the list (updates the visual tableview list)
     */
    public void updateObservableList() {
        this.AddressRegisterListWrapper.setAll(this.register.getAddresses());
    }

    /**
     * Sets the tableView content to what the user
     * searched for.
     */
    public void setObservableListBySearch() {
        this.AddressRegisterListWrapper.setAll(this.register.getAddressesBySearch());
    }

    /**
     * Shows information alert used in the "About" menuItem.
     */
    private void helpAbout() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information Dialog - About");
        alert.setHeaderText("Address Register\nv0.1-SNAPSHOT");
        alert.setContentText("A brilliant application created by\n(C)Sindre Glomnes\n2021-05-15");
        alert.showAndWait();
    }

    /**
     * Shows confirmation alert used in the "exit" menuItem.
     */
    private void exitApplication() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText("Exit Application ?");
        alert.setContentText("Are you sure you want to exit this application?");

        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && (result.get() == ButtonType.OK)) {
            Platform.exit();
        }
    }

    /**
     * Shows confirmation alert used in the "reset" menuitem.
     * If confirmed, all addresses will be removed from the application.
     */
    private void resetApplication() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText("All Addresss will be removed!");
        alert.setContentText("Are you sure you want to reset this application?");

        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && (result.get() == ButtonType.OK)) {
            mainController.reset(this);
        }
    }
}
