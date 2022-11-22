package net.thumbtack.school.concert.settings;

public enum Mode {
    SQL, RAM, HIBERNATE;

    public static Mode modeFromString(String s) {
        switch (s) {
            case "SQL":
                return SQL;
            case "RAM":
                return RAM;
            case "HIBERNATE":
                return HIBERNATE;
        }
        return null;
    }
}
