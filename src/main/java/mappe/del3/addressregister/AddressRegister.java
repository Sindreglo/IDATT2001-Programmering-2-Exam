package mappe.del3.addressregister;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

/**
 * A register of Addresses. The class is singleton.
 * Register stores all addresses added to
 * the application in an ArrayList.
 * Addresses can be added, removed, searched by.
 * The register can also be cleared of all addresses.
 *
 * @author Sindre Glomnes
 * @version 2021-05-14
 */
public class AddressRegister {
    private final ArrayList<Address> addressRegister = new ArrayList<>(); // ArrayList of all addresses added.
    private ArrayList<Address> filteredAddressRegister = new ArrayList<>(); // ArrayList of all addresses search of.
    private static final AddressRegister register = new AddressRegister(); // Static instance of AddressRegister.

    /**
     * Constructor
     */
    private AddressRegister() {
    }

    /**
     * Returns the instance of AddressRegister
     * @return instance of AddressRegister
     */
    public static AddressRegister getInstance() {
        return register;
    }

    /**
     * Adds an address to the register. Exception is thrown if the
     * register already contains the address.
     *
     * @param address new address
     */
    public void addAddress(Address address) {
        if (!addressRegister.contains(address)) {
            addressRegister.add(address);
        } else {
            throw new IllegalArgumentException("Address already exist in register.");
        }
    }

    /**
     * Removes an address from the register. Exception is thrown if the
     * register does not contain the address to be removed.
     *
     * @param address
     */
    public void removeAddress(Address address) {
        if (addressRegister.contains(address)) {
            addressRegister.remove(address);
        } else {
            throw new IllegalArgumentException("Selected Address does not exist.");
        }
    }

    /**
     * Method for searching for addresses based on zipcode.
     * All addresses which starts with this zipcode will be added
     * to the filteredAddressRegister (Arraylist).
     *
     * @param zipCode the zipcode searched with.
     */
    public void searchByZipCode(String zipCode) {
        filteredAddressRegister = (ArrayList<Address>) addressRegister.stream().
                filter(m -> m.getZipCode().startsWith(zipCode)).collect(Collectors.toList());
    }

    /**
     * Method for searching for addresses based on postal.
     * All addresses which starts with this postal will be added
     * to the filteredAddressRegister (Arraylist).
     *
     * @param postal the postal searched with.
     */
    public void searchByPostal(String postal) {
        filteredAddressRegister = (ArrayList<Address>) addressRegister.stream().
                filter(m -> m.getPostal().startsWith(postal.toUpperCase())).collect(Collectors.toList());
    }

    public void searchByMunicipalCode(String municipalCode) {
        filteredAddressRegister = (ArrayList<Address>) addressRegister.stream().
                filter(m -> m.getMunicipalCode().startsWith(municipalCode)).collect(Collectors.toList());
    }

    public void searchByMunicipalityName(String municipalityName) {
        filteredAddressRegister = (ArrayList<Address>) addressRegister.stream().
                filter(m -> m.getMunicipalityName().startsWith(municipalityName.toUpperCase())).collect(Collectors.toList());

    }

    public void searchByCategory(char category) {
        filteredAddressRegister = (ArrayList<Address>) addressRegister.stream().
                filter(m -> m.getCategory() == category).collect(Collectors.toList());

    }

    /**
     * @return All addresses in the register
     */
    public Collection<Address> getAddresses() {
        return addressRegister;
    }

    /**
     * @return all addresses from search.
     */
    public Collection<Address> getAddressesBySearch() {
        return filteredAddressRegister;
    }

    /**
     * Removes all addresses from the register.
     */
    public void clearRegister() {
        addressRegister.clear();
    }
}
