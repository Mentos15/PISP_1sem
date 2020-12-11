package by.ermakovich.search_command.entity;

public class CustomError {
    private String errorMessage;

    public CustomError() { }

    public CustomError(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
