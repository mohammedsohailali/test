package com.disney.utils;

public class SectionItem implements Section {

    private int section;
    private String sectionTtl;
    private Boolean selected;
    private Boolean hasReservation;

    public SectionItem(int section,String sectionTtl,Boolean hasReservation) {
        this.section = section;
        this.sectionTtl = sectionTtl;
        this.hasReservation = hasReservation;
    }

    @Override
    public int type() {
        return ITEM;
    }

    @Override
    public int sectionPosition() {
        return section;
    }

    @Override
    public String sectionTitle() {
        return sectionTtl;
    }

    public Boolean getHasReservation() {
        return hasReservation;
    }

    public void setHasReservation(Boolean hasReservation) {
        this.hasReservation = hasReservation;
    }
}