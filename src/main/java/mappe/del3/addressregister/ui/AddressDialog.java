package mappe.del3.addressregister.ui;

import javafx.geometry.Insets;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import mappe.del3.addressregister.Address;
import mappe.del3.addressregister.AddressRegister;
import mappe.del3.addressregister.controll.MainController.MainController;

/**
 * Dialog for both Adding and editing addresses.
 * Implements ConstructAddress for choosing mode
 * (Mode NEW = new address, Mode EDIT = edit address).
 *
 * @author Sindre Glomnes
 * @version 2021-05-14
 */
public class AddressDialog extends Dialog<Address> implements ConstructAddress {

    // Modes for new address and edit address.
    private enum Mode {
        NEW,EDIT
    }

    private Mode mode; // NEW and EDIT mode
    private Address existingAddress = null; // Address to be edited
    private final MainController main; // Instance of controller

    /**
     * Constructor for add and edit address.
     *
     * @param main Instance of MainController
     */
    public AddressDialog(MainController main) {
        super();
        this.main = main;
    }

    /**
     * Interface method for editing address.
     * Sets the existing address to the selected address from
     * the tableview. Sets the mode to EDIT.
     * Constructs the address.
     *
     * @param existingAddress selected address from tableView.
     */
    @Override
    public void editAddress(Address existingAddress) {
        this.mode = Mode.EDIT;
        this.existingAddress = existingAddress;
        constructAddress();
    }

    /**
     * Interface method for adding new address.
     * Sets the mode to NEW and constructs the address.
     */
    @Override
    public void newAddress() {
        this.mode = Mode.NEW;
        constructAddress();
    }

    /**
     * Creates a dialog window based on the mode; NEW and EDIT.
     * A new address dialog has empty text fields.
     * A edit address dialog has text fields filled with the variables
     * from the existing address.
     */
    private void constructAddress() {

        // Sets the title of the dialog based on mode.
        switch (this.mode) {
            case NEW:
                setTitle("Address Details - Add");
                break;

            case EDIT:
                setTitle("Address Details - Edit");
                break;

            default:
                setTitle("Address Details - Unknown");
                break;
        }

        // Adds OK and Cancel buttons
        getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        // Creates the gridPane
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        // Sets the TextField and Label for each variable of a Address object
        TextField zipCodeField = new TextField();
        zipCodeField.setPromptText("zip code");
        Label zipCodeLabel = new Label("Zip Code:");

        TextField postalField = new TextField();
        postalField.setPromptText("postal");
        Label postalLabel = new Label("Postal:");

        TextField municipalCodeField = new TextField();
        municipalCodeField.setPromptText("municipal code");
        Label municipalCodeLabel = new Label("Municipal Code:");

        TextField municipalityNameField = new TextField();
        municipalityNameField.setPromptText("municipality name");
        Label municipalityNameLabel = new Label("Municipality Name:");

        TextField categoryField = new TextField();
        categoryField.setPromptText("category");
        Label categoryLabel = new Label("Category:");

        // Sets each TextField and Label to correct grid
        grid.add(zipCodeLabel, 0, 0);
        grid.add(zipCodeField, 1, 0);
        grid.add(postalLabel, 0, 1);
        grid.add(postalField, 1, 1);
        grid.add(municipalCodeLabel, 0, 2);
        grid.add(municipalCodeField, 1, 2);
        grid.add(municipalityNameLabel, 0, 3);
        grid.add(municipalityNameField, 1, 3);
        grid.add(categoryLabel, 0, 4);
        grid.add(categoryField, 1, 4);

        // Fills the text fields with variables from the existing address in EDIT mode
        if (mode == Mode.EDIT) {
            zipCodeField.setText(String.valueOf(existingAddress.getZipCode()));
            postalField.setText(existingAddress.getPostal());
            municipalCodeField.setText(String.valueOf(existingAddress.getMunicipalCode()));
            municipalityNameField.setText(existingAddress.getMunicipalityName());
            categoryField.setText(String.valueOf(existingAddress.getCategory()));
        }

        // Opens the window
        getDialogPane().setContent(grid);

        // Sets the actions for OK based on mode
        setResultConverter((ButtonType button) -> {
            Address result = null;
            if (button == ButtonType.OK) {
                try {
                    //Creates a Address object based on the input of each text field
                    result = new Address(Integer.parseInt(zipCodeField.getText()),postalField.getText()
                            ,Integer.parseInt(municipalCodeField.getText()),municipalityNameField.getText()
                            , String.valueOf(categoryField.getText()).toUpperCase().charAt(0));
                } catch (IllegalArgumentException e) {
                    main.error(e.getMessage());
                }
            }
            // Returns the Address object.
            return result;
        });
    }
}
