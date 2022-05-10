package com.qoopercucumber.bdd.mapping;

public enum MapValue {

    ID("id"), CLASSNAME("className"), LINKTEXT("linkText"), NAME("name"), CSSSELECTOR("cssSelector"), XPATH("xpath"), CONTAINS(
            "contains"), ACCESSIBILITYID("accessID");

    private final String text;

    MapValue(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
