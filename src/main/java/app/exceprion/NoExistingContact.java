package app.exceprion;

public class NoExistingContact extends RuntimeException {
    public NoExistingContact(String message) {
        super(message);
    }

    public NoExistingContact() {}
}
