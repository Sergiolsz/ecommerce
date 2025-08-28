package com.example.ecommerce.infrastructure.rest.dto;

public record ErrorResponse(String error, String message, int status) {

}