package mappe.del3.addressregister.ui;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;

import java.util.Optional;

/**
 * Dialog for choosing a filetype not supported by the application.
 *
 * @author Sindre Glomnes
 * @version 2021-05-14
 */
public class FileDialog {

    /**
     * Shows warning alert for choosing a file type not supported
     * by the application (filetype not .txt)
     * Gives user option to cancel operation or select new file.
     *
     * @return true = cancel, false = choose new file
     */
    public boolean notValid() {
        // Creates buttons
        ButtonType select = new ButtonType("Select file", ButtonBar.ButtonData.OK_DONE);
        ButtonType cancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        // Sets alert type, text and buttons
        Alert alert = new Alert(Alert.AlertType.WARNING,
                "The chosen file type is not valid.\n" +
                        "Please choose another file\nclicking on Select file or Cancel the operation",
                select,
                cancel);

        // Sets title
        alert.setTitle("File Details - Chosen file not valid");

        // Shows alert and waits for result (Button clicked)
        Optional<ButtonType> result = alert.showAndWait();

        // Returns true or false based on button
        if (result.isPresent() && (result.get() == cancel)) {
            alert.close();
            return true;
        } else return !result.isPresent() || (result.get() != select);
    }

    /**
     * Shows alert for overwriting content of the application with
     * imported content.
     * Gives user option to overwrite (OK) or cancel operation (Cancel)
     *
     * @return true = cancel, false = overwrite
     */
    public boolean overWrite() {
        ButtonType ok = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        ButtonType cancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        Alert alert = new Alert(Alert.AlertType.WARNING,
                "Import of file will overwrite all addresses in register\n" +
                        "clicking on OK to overwrite or Cancel the operation",
                ok,
                cancel);

        alert.setTitle("File Details - overwrite");

        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && (result.get() == cancel)) {
            alert.close();
            return true;
        } else return !result.isPresent() || (result.get() != ok);
    }

}
