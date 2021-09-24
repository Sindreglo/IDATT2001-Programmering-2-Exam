package mappe.del3.addressregister.ui;

import mappe.del3.addressregister.Address;

/**
 * Interface class for creating and editing addresses
 * in AddressDialog.
 *
 * @author Sindre Glomnes
 * @version 2021-05-14
 */
public interface ConstructAddress {

    /**
     * Edits a selected address chosen from the tableView
     *
     * @param selectedAddress Address from tableView
     */
    void editAddress(Address selectedAddress);

    /**
     * Creates new address
     */
    void newAddress();
}
