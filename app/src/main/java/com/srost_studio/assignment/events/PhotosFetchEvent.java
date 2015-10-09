package com.srost_studio.assignment.events;

import br.com.condesales.models.Photos;

public class PhotosFetchEvent {

    private final Photos photos;

    public PhotosFetchEvent(Photos photos) {
        this.photos = photos;
    }

    public Photos getPhotos() {
        return photos;
    }
}
