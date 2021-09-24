package mappe.del3.addressregister.ui;

import javafx.scene.control.*;
import mappe.del3.addressregister.AddressRegister;

import java.util.Optional;

/**
 * Dialog for searching in register.
 * Can search by: zipcode, postal, municipalCode,
 * municipalityName, category.
 *
 * @author Sindre Glomnes
 * @version 2021-05-14
 */
public class SearchDialog extends Dialog<String> {
    AddressRegister addressRegister = AddressRegister.getInstance(); // gets instance of addressRegister

    /**
     * Constructor
     */
   public SearchDialog() {
   }

    /**
     * Creates dialog for searching by zipcode.
     * Requires a zip code (as string), and sends the
     * result to the searchByZipCode method in AddressRegister
     * Does not require all four digits to work.
     */
   public void byZipCode() {
       TextInputDialog dialog = new TextInputDialog("0001");
       dialog.setTitle("Search - Zip code");
       dialog.setHeaderText("Search in register by Zip Code");
       dialog.setContentText("Please enter a zip code:");

       Optional<String> result = dialog.showAndWait();
       result.ifPresent(s -> addressRegister.searchByZipCode(s));
   }

    /**
     * Creates dialog for searching by postal.
     * Requires a postal name, and sends the result
     * to the searchByPostal method in AddressRegister.
     * Does not require the full name to work.
     */
    public void byPostal() {
        TextInputDialog dialog = new TextInputDialog("OSLO");
        dialog.setTitle("Search - Postal");
        dialog.setHeaderText("Search in register by Postal");
        dialog.setContentText("Please enter a postal:");

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(s -> addressRegister.searchByPostal(s));
    }

    /**
     * Creates dialog for searching by municipal code.
     * Requires a municipal name, and sends the result
     * to the searchByMunicipalCode method in AddressRegister.
     * Does not require all four digits to work.
     */
    public void byMunicipalCode() {
        TextInputDialog dialog = new TextInputDialog("0301");
        dialog.setTitle("Search - Municipal Code");
        dialog.setHeaderText("Search in register by Municipal Code");
        dialog.setContentText("Please enter a Municipal Code:");

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(s -> addressRegister.searchByMunicipalCode(s));

    }

    /**
     * Creates dialog for searching by municipality name.
     * Requires a municipality name, and sends the result
     * to the searchByMunicipalityName method in AddressRegister.
     * Does not require the full name to work.
     */
    public void byMunicipalityName() {
        TextInputDialog dialog = new TextInputDialog("OSLO");
        dialog.setTitle("Search - Municipality Name");
        dialog.setHeaderText("Search in register by Municipality Name");
        dialog.setContentText("Please enter a zip code:");

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(s -> addressRegister.searchByMunicipalityName(s));

    }

    /**
     * Creates dialog for searching by category.
     * Requires a category character, and sends the result
     * to the searchByCategory method in AddressRegister.
     * Does not require the full name to work.
     */
    public void byCategory() {
        TextInputDialog dialog = new TextInputDialog("P");
        dialog.setTitle("Search - Category");
        dialog.setHeaderText("Search in register by Category");
        dialog.setContentText("Please enter a Category:");

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(s -> addressRegister.searchByCategory(s.toUpperCase().charAt(0)));
    }

}

