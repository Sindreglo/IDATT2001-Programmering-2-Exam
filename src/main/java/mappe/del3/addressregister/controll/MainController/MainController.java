package mappe.del3.addressregister.controll.MainController;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import mappe.del3.addressregister.Address;
import mappe.del3.addressregister.AddressRegister;
import mappe.del3.addressregister.percistence.FileManagement;
import mappe.del3.addressregister.ui.Factory;
import mappe.del3.addressregister.ui.AddressDialog;

import java.util.Optional;

/**
 * The main controller of the application. All mayor interactions (action events) from the
 * GUI uses mainController methods.
 *
 * @author Sindre Glomnes
 * @version 2021-05-14
 */
public class MainController {

    private final AddressRegister register = AddressRegister.getInstance(); // accesses the static instance of AddressRegister;
    private final FileManagement fileManagement = new FileManagement(); //Creates an instance of fileManagement.

    /**
     * Adds an address to the address register through the AddressDialog
     * and updates the tableView list.
     * Shows error alert if the address is a duplicate.
     * @param parent Instance of factory.
     */
    public void addAddress(Factory parent) {
        // Creates an instance of AddressDialog.
        AddressDialog dialog = new AddressDialog(this);
        // Uses interface method sets the mode to new address
        dialog.newAddress();
        // Stores the result of the dialog.
        Optional<Address> result = dialog.showAndWait();
        // Checks if the result is present
        if (result.isPresent()) {
            try {
                // Creates an address from the result of dialog
                Address newAddress = result.get();
                // Tries to add it to register
                register.addAddress(newAddress);
            } catch (IllegalArgumentException e) {
                error(e.getMessage());
            }
        }
        // updates the list.
        parent.updateObservableList();
    }

    /**
     * Edits a selected address from the tableView through AddressDialog
     * and updates the tableView list.
     * shows error alert if a address is not selected from the tableView.
     *
     * @param selectedAddress Address to be edited.
     * @param parent Instance of factory.
     */
    public void editAddress(Address selectedAddress, Factory parent) {
        // Error alert if address is not selected.
        if (selectedAddress == null) {
            Alert notChosenAddress = new Alert(Alert.AlertType.ERROR);
            notChosenAddress.setTitle("Address Details - Edit");
            notChosenAddress.setHeaderText("Address not selected");
            notChosenAddress.setContentText("You must select a Address from the list to edit");
            notChosenAddress.showAndWait();
        } else {
            // Creates an instance of AddressDialog
            AddressDialog addressDialog = new AddressDialog(this);
            // Uses interface method to set mode to edit address
            addressDialog.editAddress(selectedAddress);
            // Stores the result of the dialog.
            Optional<Address> result = addressDialog.showAndWait();

            // Checks if the result is present
            if (result.isPresent()) {
                try {
                    // Creates a new address from result of dialog
                    Address newAddress = result.get();
                    // Tries to add the address to register
                    register.addAddress(newAddress);
                    // Removes the selected address if addAddress does not throw exception
                    register.removeAddress(selectedAddress);
                } catch (IllegalArgumentException e) {
                    error(e.getMessage());
                }
            }
            //Updates the tableview
            parent.updateObservableList();
        }
    }


    /**
     * Removes a selected address from the register, and updates
     * the tableView.
     * Shows an error alert if a address is not selected from
     * the tableview. Shows an confirmation alert for removing
     * the address.
     *
     * @param selectedAddress
     * @param parent
     */
    public void removeAddress(Address selectedAddress, Factory parent) {
        // Error alert if a address is not selected
        if (selectedAddress == null) {
            Alert notChosenAddress = new Alert(Alert.AlertType.ERROR);
            notChosenAddress.setTitle("Delete confirmation");
            notChosenAddress.setHeaderText("Address not selected");
            notChosenAddress.setContentText("You must select a Address from the list to remove");
            notChosenAddress.showAndWait();
        } else {
            // confirmation alert for removing an address
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Delete confirmation");
            alert.setHeaderText("Delete confirmation");
            alert.setContentText("Are you sure you want to delete this item?");
            Optional<ButtonType> result = alert.showAndWait();

            // Removes the address from the register.
            if (result.isPresent() && (result.get() == ButtonType.OK)) {
                try {
                    register.removeAddress(selectedAddress);
                } catch (IllegalArgumentException e) {
                    error(e.getMessage());
                }
            }
        }
        // Updates the tableView
        parent.updateObservableList();
    }

    /**
     * Imports a txt file of addresses and adds
     * them to the register. Clears the register
     * from all previous addresses.
     * Updates the tableView.
     * Updates the statusbar to "Import successful"
     *
     * @param parent Instance of Factory
     */
    public void importTxt(Factory parent) {
        try {
            //Adds all addresses from the arraylist returned from fileManagement class.
            for (Address m: fileManagement.importTxt(this)) {
                try {
                    register.addAddress(m);
                } catch (IllegalArgumentException e) {
                    error(e.getMessage());
                }
            }
            parent.updateObservableList();
            parent.updateStatusBar("Import successful");
        } catch (Exception e) {
            parent.updateStatusBar("Import failed");
            error("File was not imported");
        }
    }

    /**
     * Exports all addresses in the register to
     * a txt file. Updates the statusbar.
     *
     * @param parent Instance of Factory
     */
    public void exportTxt(Factory parent) {
        try {
             if (fileManagement.exportTxt(this)) {
                 parent.updateStatusBar("Export successful");
             }
        } catch (Exception e) {
            parent.updateStatusBar("Export failed");
            error(e.getMessage());
        }
    }

    /**
     * Imports a csv file of addresses and adds
     * them to the register. Clears the register
     * from all previous addresses.
     * Updates the tableView.
     * Updates the statusbar to "Import successful"
     *
     * @param parent Instance of Factory
     */
    public void importCsv(Factory parent) {
        try {
            //Adds all addresses from the arraylist returned from fileManagement class.
            for (Address m: fileManagement.importCsv(this)) {
                try {
                    register.addAddress(m);
                } catch (IllegalArgumentException e) {
                    error(e.getMessage());
                }
            }
            parent.updateObservableList();
            parent.updateStatusBar("Import successful");
        } catch (Exception e) {
            parent.updateStatusBar("Import failed");
            error("File was not imported");
        }
    }

    /**
     * Exports all addresses in the register to
     * a csv file. Updates the statusbar.
     *
     * @param parent Instance of Factory
     */
    public void exportCsv(Factory parent) {
        try {
            if (fileManagement.exportCsv(this)) {
                parent.updateStatusBar("Export successful");
            }
        } catch (Exception e) {
            parent.updateStatusBar("Export failed");
            error(e.getMessage());
        }
    }

    /**
     * Error alert used to showcase all exceptions
     * useful to the user.
     * @param e The exception message.
     */
    public void error(String e) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Something went wrong...");
        alert.setHeaderText("Something went wrong!");
        alert.setContentText(e);
        alert.showAndWait();
    }

    /**
     * Resets the register. Removes all
     * addresses and updates the tableView
     *
     * @param parent Instance of Factory
     */
    public void reset(Factory parent) {
        register.clearRegister();
        parent.updateObservableList();
    }
}
