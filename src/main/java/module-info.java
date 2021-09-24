module MailRegister {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.logging;
    requires opencsv;

    opens mappe.del3.addressregister.ui to javafx.graphics;
    opens mappe.del3.addressregister to javafx.base;
}