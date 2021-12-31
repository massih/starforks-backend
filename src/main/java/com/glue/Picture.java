package com.glue;

import dev.morphia.annotations.Id;

import javax.validation.constraints.NotNull;

public class Picture {

    @NotNull
    private final String title;

    @NotNull
    private final byte[] image;

    public Picture( String title, byte[] image) {
        this.title = title;
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public byte[] getImage() {
        return image;
    }
}
