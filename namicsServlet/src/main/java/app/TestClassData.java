package app;

import lombok.Data;

@Data
public class TestClassData {
    private int anInt;
    private String string;
    private boolean aBoolean;

    public TestClassData(int anInt, String string, boolean aBoolean) {
        this.anInt = anInt;
        this.string = string;
        this.aBoolean = aBoolean;
    }
}
