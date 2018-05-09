package com.company.dto;

import com.company.entity.Contact;

import java.util.stream.Stream;

public class ContactsDto {
    public Stream<Contact> contacts;

    public ContactsDto(Stream<Contact> contacts) {
        this.contacts = contacts;
    }
}
