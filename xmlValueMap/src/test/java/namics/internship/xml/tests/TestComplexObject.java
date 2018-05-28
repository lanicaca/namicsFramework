package namics.internship.xml.tests;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@Data
@XmlRootElement(name = "complexObject")
@XmlAccessorType(XmlAccessType.FIELD)
class TestComplexObject {
    @XmlElement(name = "testInt")
    private int anInt;
    @XmlElement(name = "testString")
    private String string;
    @XmlElement(name = "testBool")
    private boolean aBoolean;

    public TestComplexObject(int anInt, String string, boolean aBoolean) {
        this.anInt = anInt;
        this.string = string;
        this.aBoolean = aBoolean;
    }

    public TestComplexObject() {}
}
