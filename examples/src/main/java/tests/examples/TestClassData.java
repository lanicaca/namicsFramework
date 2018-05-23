package tests.examples;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@Data
@XmlRootElement(name = "testclassdata")
@XmlAccessorType(XmlAccessType.FIELD)

public class TestClassData {
    @XmlElement(name = "testint")
    private int anInt;
    @XmlElement(name = "teststring")
    private String string;
    @XmlElement(name = "testbool")
    private boolean aBoolean;

    public TestClassData() {
    }

    public TestClassData(int anInt, String string, boolean aBoolean) {
        this.anInt = anInt;
        this.string = string;
        this.aBoolean = aBoolean;
    }
}
