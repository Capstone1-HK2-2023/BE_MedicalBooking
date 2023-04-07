package com.example.be_medicalbooking.utils;

public interface EmailService {
    boolean sendEmail(String to, String subject, String message);
}