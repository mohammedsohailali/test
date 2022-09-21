package com.disney.utils;

public class SectionHeader implements Section {

    private int section;
    private String title;

    public SectionHeader(int section,String title) {
        this.section = section;
        this.title = title;
    }

    @Override
    public int type() {
        return HEADER;
    }

    @Override
    public int sectionPosition() {
        return section;
    }

    @Override
    public String sectionTitle() {
        return title;
    }
}