package com.qoopercucumber.bdd.mapping;

public enum MapMethodType {

    CLICK_ELEMENT("click-element"), IS_ELEMENT("is-element"), INPUT_ELEMENT("input-element"), PAGE_LOADED(
            "page-loaded"), SELECT_OPTION("select-option"), DEFAULT_ELEMENT("element");

    private final String value;

    MapMethodType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}
