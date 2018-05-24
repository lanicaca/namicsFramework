package app;

import lombok.Data;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name ="Color")
public class Color {
    @XmlElement (name = "r")
    int r;
    @XmlElement (name = "g")
    int g;
    @XmlElement (name = "b")
    int b;
    public Color(){}
    public Color(int red, int green, int blue){
        this.r=red;
        this.g=green;
        this.b=blue;
    }

    @Override
    public String toString() {
        return "RED: " + this.r + " GREEN: " + this.g + " BLUE: " + this.b;
    }
}
