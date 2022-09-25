package com.bridgelabz.bookstoreuserservice.util;

import lombok.Data;
import lombok.ToString;
import org.springframework.stereotype.Component;

/**
 *  :  Email Entity class
 */

@Component
@Data
@ToString
public class Email {
    private String to;
    private String from;
    private String subject;
    private String body;
}