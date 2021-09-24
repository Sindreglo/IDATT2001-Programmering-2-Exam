package mappe.del3.addressregister;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Junit tests for creating and editing addresses.
 * Adding/removing addresses from register, and searching for addresses.
 *
 * @author Sindre Glomnes
 * @version 2021-05-14
 */
public class AddressRegisterTest {

    // gets the instance of AddressRegister
    AddressRegister register = AddressRegister.getInstance();

    @Nested
    @DisplayName("Creating addresses, positive and negative")
    public class CreateAddressesTest {

         @Test
         @DisplayName("Can creating an address")
        public void createAddress() {
             assertDoesNotThrow(() -> new Address(1001, "Oslo", 301, "Oslo", 'P'));
         }

         @Test
         @DisplayName("Can not add address with illegal postal")
        public void createAddressNoPostal() {
             assertThrows(IllegalArgumentException.class, () -> new Address(1001, "", 301, "Oslo", 'P'));
         }

        @Test
        @DisplayName("Can not add address with illegal municipalCode")
        public void createAddressIllegalMunicipalCode() {
            assertThrows(IllegalArgumentException.class, () -> new Address(1001, "Oslo", 10000, "Oslo", 'P'));
        }

        @Test
        @DisplayName("Can not add address with no municipalityName")
        public void createAddressNoMunicipalName() {
            assertThrows(IllegalArgumentException.class, () -> new Address(1001, "Oslo", 301, "", 'P'));
        }

        @Test
        @DisplayName("Can not add Address with illegal category")
        public void createAddressNoCategory() {
            assertThrows(IllegalArgumentException.class, () -> new Address(1001, "Oslo", 301, "Oslo", ' '));
        }
    }

    @Nested
    @DisplayName("Adding addresses to register test, positive and negative")
    public class addAddresssTest {

        @Test
        @DisplayName("can add address")
        public void addAddress() {
            assertDoesNotThrow(() -> register.addAddress(new Address(1001, "Oslo", 3001, "Oslo", 'P')));
        }

        @Test
        @DisplayName("Can not add duplicate addresses")
        public void cannotAddDuplicateAddress() {
            register.addAddress(new Address(1001, "Test", 3001, "Oslo", 'P'));
            assertThrows(IllegalArgumentException.class, () -> register.addAddress(new Address(1001, "Test", 3001, "Oslo", 'P')));
        }

        @Test
        @DisplayName("Can remove address")
        public void removeAddress() {
            register.addAddress(new Address(1111, "OSLO", 3001, "OSLO", 'P'));
            assertDoesNotThrow(() -> register.removeAddress(new Address(1111, "OSLO", 3001, "OSLO", 'P')));
        }

        @Test
        @DisplayName("Can not remove non existing address")
        public void removeNonExistingAddress() {
            assertThrows(IllegalArgumentException.class, () -> register.removeAddress(new Address(1234, "123", 0301, "123", 'P')));
        }

        @Test
        @DisplayName("Can not add illegal address")
        public void cannotAddIllegalAddress() {
            assertThrows(IllegalArgumentException.class,() -> register.addAddress(new Address(0, "Oslo", 0301, "Oslo", 'P')));
        }

    }

    @Nested
    @DisplayName("Testing search feature")
    public class SearchingTest{

        @BeforeEach
        @DisplayName("Adds addresses before each search test")
        public void addAddresses() {
            register.clearRegister();
            register.addAddress(new Address(3123, "abcd", 0301, "Oslo", 'P'));
            register.addAddress(new Address(3134, "abce", 0301, "Oslo", 'P'));
            register.addAddress(new Address(3233, "abdd", 0301, "Oslo", 'P'));
            register.addAddress(new Address(4123, "bbcd", 0301, "Oslo", 'P'));
            register.addAddress(new Address(4134, "casd", 0301, "Oslo", 'P'));
        }

        @Test
        @DisplayName("Searches by zipCode with two digits gives correct numbers of addresses")
        public void searchByZipCodeWithTwoDigits() {
            register.searchByZipCode("31");
            assertEquals(register.getAddressesBySearch().size(), 2);
        }

        @Test
        @DisplayName("Searches by zipCode with three digits gives correct numbers of addresses")
        public void searchByZipCodeWithThreeDigits() {
            register.searchByZipCode("312");
            assertEquals(register.getAddressesBySearch().size(), 1);
        }

        @Test
        @DisplayName("Searches by postal with two letters gives correct numbers of addresses")
        public void searchByPostalWithTwoLetters() {
            register.searchByPostal("ab");
            assertEquals(register.getAddressesBySearch().size(), 3);
        }

        @Test
        @DisplayName("Searches by postal with three letters gives correct numbers of addresses")
        public void searchByPostalWithThreeLetters() {
            register.searchByPostal("abc");
            assertEquals(register.getAddressesBySearch().size(), 2);
        }

        @Test
        @DisplayName("Searches by postal with uppercase letters works")
        public void searchByPostalWithUppercaseLetters() {
            register.searchByPostal("AB");
            assertEquals(register.getAddressesBySearch().size(), 3);
        }

    }
}
