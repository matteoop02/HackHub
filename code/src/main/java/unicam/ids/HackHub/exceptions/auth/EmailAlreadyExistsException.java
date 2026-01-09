package unicam.ids.HackHub.exceptions.auth;

public class EmailAlreadyExistsException extends RuntimeException {
    public EmailAlreadyExistsException(String email) {
        super("Email già registrata: " + email);
    }
}