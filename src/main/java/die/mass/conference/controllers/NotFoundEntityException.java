package die.mass.conference.controllers;

public class NotFoundEntityException extends RuntimeException {

    public NotFoundEntityException() {
    }

    public NotFoundEntityException(String message) {
        super(message);
    }
}
