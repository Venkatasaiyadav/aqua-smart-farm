// src/main/java/com/aquafarm/common/exception/ResourceNotFoundException.java
package com.aquafarm.common.exception;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String resource, Long id) {
        super(resource + " not found with ID: " + id);
    }

    public ResourceNotFoundException(String message) {
        super(message);
    }
}