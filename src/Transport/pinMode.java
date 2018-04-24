package Transport;

public enum pinMode {
    INPUT("INPUT"),
    OUTPUT("OUTPUT");

    private String mode;

    pinMode(String mode) {
        this.mode = mode;
    }

    public String getMode() {
        return mode;
    }
}
