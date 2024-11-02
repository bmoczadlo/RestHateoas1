package com.classcast.resthateoas.employee;

import com.classcast.resthateoas.MyHateoasException;

class EmployeeNotFoundException extends MyHateoasException {

    public EmployeeNotFoundException(String generalMessage, String detailMessage) {
        super(generalMessage, detailMessage);
//        this.setGeneralMessage(generalMessage);
//        this.setDetailMessage(detailMessage);
    }
}