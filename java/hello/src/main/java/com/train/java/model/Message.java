package com.train.java.model;

import lombok.Data;

import java.util.Date;

@Data
public class Message {

    private String content;
    private String sender;
    private Date received;
}
