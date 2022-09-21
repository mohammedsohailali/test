package com.disney.utils;

public interface Section {
    int HEADER = 0;
    int ITEM = 1;
    int FOOTER = 2;

    int type();

    int sectionPosition();

    String sectionTitle();
}