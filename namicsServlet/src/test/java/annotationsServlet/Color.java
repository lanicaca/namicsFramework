package annotationsServlet;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@Data
@XmlRootElement(name = "Color")
@XmlAccessorType(XmlAccessType.FIELD)
public class Color {
    @XmlElement(name = "r")
    int r;
    @XmlElement(name = "g")
    int g;
    @XmlElement(name = "b")
    int b;

    public Color() {
    }

    public Color(int red, int green, int blue) {
        this.r = red;
        this.g = green;
        this.b = blue;
    }
}
