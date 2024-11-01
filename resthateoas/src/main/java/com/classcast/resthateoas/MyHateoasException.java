package com.classcast.resthateoas;

import lombok.Setter;

@Setter
public class MyHateoasException extends RuntimeException {

    private String generalMessage;
    private String detailMessage;

    public MyHateoasException(String generalMessage, String detailMessage) {
        super(generalMessage + " " + detailMessage);
    }
}
