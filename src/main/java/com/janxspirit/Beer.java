package com.janxspirit;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Beer {
    
    private String name;
    private int sizeOunces;

    public Beer() {}

    public Beer(String name, int sizeOunces) {
        this.name = name;
        this.sizeOunces = sizeOunces;
    }

    @XmlElement(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @XmlElement(name = "sizeOunces")
    public int getSizeOunces() {
        return sizeOunces;
    }

    public void setSizeOunces(int sizeOunces) {
        this.sizeOunces = sizeOunces;
    }

}
