package mappe.del3.addressregister.percistence;

import com.opencsv.CSVReader;
import javafx.stage.FileChooser;
import mappe.del3.addressregister.Address;
import mappe.del3.addressregister.AddressRegister;
import mappe.del3.addressregister.controll.MainController.MainController;
import mappe.del3.addressregister.ui.FileDialog;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

/**
 * FileManagement is used for import and export of
 * txt files.
 *
 * @author Sindre Glomnes
 * @version 2021-05-14
 */
public class FileManagement {
    private final ArrayList<Address> addressList = new ArrayList<>(); // List of addresses imported.
    private FileChooser fileChooser; // Chooses filepath
    private File file; // The txt file of addresses
    private FileReader reader; // Reads files
    private final FileDialog dialog = new FileDialog(); // Instance of FileDialog
    private final AddressRegister addressRegister = AddressRegister.getInstance(); // Instance of addressRegister singleton

    /**
     * Chooses a txt UTF-8 file from the computer to read. Returns its addresses
     * as a Arraylist. File chooser dialog only accepts txt files, and will therefore
     * show a warning alert if a not valid file is chosen.
     *
     * @param mainController Instance of MainController
     * @return Arraylist of addresses.
     */
    public ArrayList<Address> importTxt(MainController mainController) {

        // Checks if the register contains any addresses to be overwritten.
        if (addressRegister.getAddresses().size() != 0) {
            // Dialog for overwriting.
            if (dialog.overWrite()) {
                return null;
            }
        }

        //Clears the addressRegister from all addresses.
        addressRegister.clearRegister();
        //Clears the arraylist from previous imports.
        addressList.clear();

        //Chooses a file from the file chooser dialog.
        FileChooser.ExtensionFilter txtFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
        fileChooser = new FileChooser();
        // Sets default name of file
        fileChooser.setInitialFileName("AddressRegister");

        fileChooser.getExtensionFilters().add(txtFilter);

        // Stops if the fileChooser dialog is canceled.
        if ((file = fileChooser.showOpenDialog(null)) == null) {
            return null;
        }

        // Shows warning alert while the selected file is not txt
        // Should be unnecessary, but is added as an extra barrier
        while (!(String.valueOf(file).contains(".txt"))) {
            if (dialog.notValid()) {
                return null;
            }
            file = fileChooser.showOpenDialog(null);
        }

        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(file), StandardCharsets.ISO_8859_1));

            String[] Address;
            String str;
            // Repeats for every line with text in the file
            while ((str = in.readLine()) != null) {
                //Splits the text into array of every variable in address.
                Address = str.split("\t");

                // creates a new address from the array, and adds it to the arraylist.
                addressList.add(new Address(Integer.parseInt(Address[0].replaceAll("\\uFEFF", "")),Address[1]
                        ,Integer.parseInt(Address[2].replace("\\uFEFF", "")),Address[3],Address[4].charAt(0)));
            }
            return addressList;
        } catch (IOException e) {
            mainController.error(e.getMessage());
        }
        return null;
    }

    /**
     * Exports the addresses from the application to a txt file.
     * The filename and filepath is determined by the user from a dialog.
     * Shows warning alert if overwriting a old file.
     *
     * @param mainController Instance of MainController.
     */
    public boolean exportTxt(MainController mainController) {

        // Opens dialog for filepath with default filename of "newFile.txt"
        fileChooser = new FileChooser();

        // Sets default name of file
        fileChooser.setInitialFileName("AddressRegister");

        // Filter for .txt
        FileChooser.ExtensionFilter txtFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");

        // File is always .txt
        fileChooser.getExtensionFilters().add(txtFilter);

        // Stops if the fileChooser dialog is canceled.
        if ((file = fileChooser.showSaveDialog(null)) == null) {
            return false;
        }


        try (FileOutputStream fos = new FileOutputStream(file);
             OutputStreamWriter osw = new OutputStreamWriter(fos, StandardCharsets.UTF_8);
             BufferedWriter writer = new BufferedWriter(osw)
        ) {

            //Adds lines to the file for every address in the register.
            for (Address m : addressRegister.getAddresses()) {
                writer.append(m.getZipCode());
                writer.append("\t");
                writer.append(m.getPostal());
                writer.append("\t");
                writer.append(String.valueOf(m.getMunicipalCode()));
                writer.append("\t");
                writer.append(m.getMunicipalityName());
                writer.append("\t");
                writer.append(m.getCategory());
                writer.newLine();
            }

        } catch (IOException e) {
            mainController.error(e.getMessage());
        }
        return true;
    }

    /**
     * Chooses a csv file from the computer to read. Returns its addresses
     * as a Arraylist. File chooser dialog only accepts csv files, and will therefore
     * show a warning alert if a not valid file is chosen.
     *
     * @param mainController Instance of MainController
     * @return Arraylist of addresses.
     */
    public ArrayList<Address> importCsv(MainController mainController) {

        // Checks if the register contains any addresses to be overwritten.
        if (addressRegister.getAddresses().size() != 0) {
            // Dialog for overwriting.
            if (dialog.overWrite()) {
                return null;
            }
        }

        //Clears the addressRegister from all addresses.
        addressRegister.clearRegister();

        //Clears the arraylist from previous imports.
        addressList.clear();

        // Filter for only choosing .csv files
        FileChooser.ExtensionFilter csvFilter = new FileChooser.ExtensionFilter("CSV Files", "*.csv");

        //Chooses a file from the file chooser dialog.
        fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(csvFilter);

        // Stops if the fileChooser dialog is canceled.
        if ((file = fileChooser.showOpenDialog(null)) == null) {
            return null;
        }

        // Shows warning alert while the selected file is not txt
        // Should be unnecessary, but is added as an extra barrier
        while (!(String.valueOf(file).contains(".csv"))) {
            try {
                if (dialog.notValid()) {
                    return null;
                }
            } catch (Exception ignored) {}

            file = fileChooser.showOpenDialog(null);
        }

        try {
            reader = new FileReader(file);
        } catch (Exception e) {
            mainController.error(e.getMessage());
        }

        CSVReader csvReader = new CSVReader(reader);
        addressList.clear();

        try {
            String[] nextLine;
            while ((nextLine = csvReader.readNext()) != null) {
                for (var e: nextLine){
                    String[] address = e.split(";");
                    try {
                        addressList.add(new Address(Integer.parseInt(address[0]), address[1],
                                Integer.parseInt(address[2]),address[3], address[4].charAt(0)));
                    } catch (IllegalArgumentException i) {
                        mainController.error(i.getMessage());
                        // breaks the loop in case there are thousands of illegal addresses which throws
                        // All addresses may be in wrong format.
                        return null;
                    }
                }
            }
        } catch (IOException e) {
            mainController.error(e.getMessage());
        }
        return addressList;
    }

    /**
     * Exports the addresses from the application to a csv file.
     * The filename and filepath is determined by the user from a dialog.
     * Shows warning alert if overwriting a old file.
     *
     * @param mainController Instance of MainController.
     */
    public boolean exportCsv(MainController mainController) {
        try {
            // Chooses file
            fileChooser = new FileChooser();
            // Default name
            fileChooser.setInitialFileName("AddressRegister");
            // Filter for .csv
            FileChooser.ExtensionFilter csvFilter = new FileChooser.ExtensionFilter("CSV Files", "*.csv");
            // File is always .csv
            fileChooser.getExtensionFilters().add(csvFilter);

            // Stops if the fileChooser dialog is canceled.
            if ((file = fileChooser.showSaveDialog(null)) == null) {
                return false;
            }

            // Prints the list to the file in csv format
            PrintWriter printWriter = new PrintWriter(file);
            StringBuilder sb = new StringBuilder();
            for (Address p: addressRegister.getAddresses()) {
                sb.append(p.getZipCode());
                sb.append(";");
                sb.append(p.getPostal());
                sb.append(";");
                sb.append(p.getMunicipalCode());
                sb.append(";");
                sb.append(p.getMunicipalityName());
                sb.append(";");
                sb.append(p.getCategory());
                sb.append("\n");
            }
            printWriter.write(sb.toString());
            printWriter.close();
        } catch (IOException e) {
            mainController.error(e.getMessage());
        }
        return true;
    }




}
