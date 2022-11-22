package net.thumbtack.school.concert.settings;
import static net.thumbtack.school.concert.settings.Mode.modeFromString;

public class Settings {
    Mode mode;

    public Settings(Mode mode) {
        this.mode = mode;

    }

    public Settings(String str) {
        mode = modeFromString(str);
    }

    public Mode getMode() {
        return mode;
    }

    public void setMode(Mode mode) {
        this.mode = mode;
    }
}
