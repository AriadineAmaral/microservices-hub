package com.github.ariadineamaral.ms_pagamento.service.exceptions;

public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException (String message){
        super(message);
    }
}
