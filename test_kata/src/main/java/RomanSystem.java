public enum RomanSystem {
    C(100, "C"),
    LC(90, "LC"),
    L(50, "L"),
    XL(40, "XL"),
    X(10, "X"),
    IX(9, "IX"),
    V(5, "V"),
    IV(4, "IV"),
    I(1, "I");
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
