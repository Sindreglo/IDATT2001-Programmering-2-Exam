package mappe.del3.addressregister;

import java.text.DecimalFormat;
import java.util.Objects;

/**
 * Creates an instance of a Norwegian Address. An address must consists of a zip code,
 * postal, municipal code, municipality name and category.
 *
 * @author Sindre Glomnes
 * @version 2021-05-14
 */
public class Address {
    private final String zipCode;
    private final String postal;
    private final String municipalCode;
    private final String municipalityName;
    private final char category;
    private static final DecimalFormat decimalFormat = new DecimalFormat("0000"); // gives a number for digits (1 = 0001)

    /**
     * Constructor. Creates an instance of Address
     *
     * @param zipCode Number between (0001-9999)
     * @param postal Name of the address
     * @param municipalCode Number between 0301-9999
     * @param municipalityName Name of the municipality
     * @param category A character between a-z
     */
    public Address(int zipCode, String postal, int municipalCode, String municipalityName, char category) {
        if (zipCode < 1 || zipCode > 9999) {
            throw new IllegalArgumentException("Zip code must be between 0001-9999");
        }
        if (postal.equals("")) {
            throw new IllegalArgumentException("Address must have a postal");
        }
        if (municipalCode < 1 || municipalCode > 9999) {
            throw new IllegalArgumentException("Municipal code must be between 0001-9999");
        }
        if (municipalityName.equals("")) {
            throw new IllegalArgumentException("Address must have a municipality name");
        }
        if (!((category >= 'a' && category <= 'z') || (category >= 'A' && category <= 'Z'))) {
            throw new IllegalArgumentException("Category must be a letter");
        }

        this.zipCode = decimalFormat.format(zipCode); // Changes the int zipCode to a String with 4 digits. (int 1 = String 0001)
        this.postal = postal.toUpperCase(); //gives the postal uppercase letters
        this.municipalCode = decimalFormat.format(municipalCode); // Changes the int zipCode to a String with 4 digits. (int 1 = String 0001)
        this.municipalityName = municipalityName.toUpperCase();
        this.category = category;
    }

    // Get methods
    public String getZipCode() {
        return zipCode;
    }

    public String getPostal() {
        return postal;
    }

    public String getMunicipalCode() {
        return municipalCode;
    }

    public String getMunicipalityName() {
        return municipalityName;
    }

    public char getCategory() {
        return category;
    }

    /**
     * Checks if an instance of object Address equals
     * this Address object. The objects are equal if all
     * variables are equal.
     * @param o An Address object
     * @return true=equal false=not equal
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address Address = (Address) o;
        return zipCode.equals(Address.zipCode) &&
                municipalCode.equals(Address.municipalCode) &&
                category == Address.category &&
                Objects.equals(postal, Address.postal) &&
                Objects.equals(municipalityName, Address.municipalityName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(zipCode, postal, municipalCode, municipalityName, category);
    }

    /**
     * String with all information about the Address object.
     * @return information String.
     */
    @Override
    public String toString() {
        return "Address: " +
                "zipCode: " + zipCode +
                ", postal: " + postal +
                ", municipalCode: " + municipalCode +
                ", municipalityName: " + municipalityName +
                ", category: " + category;
    }


}
