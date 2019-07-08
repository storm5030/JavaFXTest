package org.dimigo.gui.helloworld;

public class SearchType {
    private String text;
    private String value;

    public SearchType(String text, String value) {
        this.text = text;
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return text;
    }
}
