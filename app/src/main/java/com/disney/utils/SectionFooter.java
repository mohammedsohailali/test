package com.disney.utils;

public class SectionFooter implements Section {

    private int section;
    private final String title;

    public SectionFooter(String title) {
        this.section = section;
        this.title = title;
    }

    @Override
    public int type() {
        return FOOTER;
    }

    @Override
    public int sectionPosition() {
        return -1;
    }

    @Override
    public String sectionTitle() {
        return title;
    }
}