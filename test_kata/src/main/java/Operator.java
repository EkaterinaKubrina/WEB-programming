public enum Operator {
    PLUS("+"),
    MINUS("-"),
    MULTIPLICATION("*"),
    DIVISION("/");
    private String operator;

    Operator(String operator) {
        this.operator = operator;
    }

    public String getOperator() {
        return operator;
    }

    public static Operator getOperator(String s) {
        switch (s) {
            case ("+"):
                return PLUS;
            case ("-"):
                return MINUS;

            case ("*"):
                return MULTIPLICATION;

            case ("/"):
                return DIVISION;
            default:
                throw new IllegalArgumentException("Некорректный символ операции");
        }
    }
}
