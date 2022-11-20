public enum Operator {
    PLUS("+", "\\+"),
    MINUS("-", "-"),
    MULTIPLICATION("*", "\\*"),
    DIVISION("/", "/");
    private String operator;
    private String operatorForRegex;

    Operator(String operator, String operatorForRegex) {
        this.operator = operator;
        this.operatorForRegex = operatorForRegex;
    }

    public String getOperator() {
        return operator;
    }

    public String getOperatorForRegex() {
        return operatorForRegex;
    }
}
