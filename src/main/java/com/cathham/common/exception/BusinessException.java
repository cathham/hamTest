package com.cathham.common.exception;


public class BusinessException extends Exception {
    
    /**
     * 序列化ID
     */
    private static final long serialVersionUID = 1L;

    public BusinessException(String msg) {
        super(msg);
    }
}