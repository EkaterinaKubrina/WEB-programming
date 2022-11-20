public enum RomanSystem {
    I(1, "I"),
    V(5, "V"),
    X(10, "X"),
    L(50, "L"),
    C(100, "C");
    private int anInt;
    private String value;

    RomanSystem(int anInt, String value) {
        this.anInt = anInt;
        this.value = value;
    }

    public int getAnInt(){
        return anInt;
    }

    public String getValue(){
        return value;
    }
}
