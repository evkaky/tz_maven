package com.company.controller;

import com.company.dto.ContactsDto;
import com.company.entity.Contact;
import com.company.repository.ContactRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.regex.Pattern;
import java.util.stream.Stream;

@RestController
@RequestMapping("hello")
public class HelloController {
    private ContactRepository contactRepository;

    public HelloController(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    @GetMapping("contacts")
    public ContactsDto findContacts(@RequestParam("nameFilter") String filter) {
        Pattern regex = Pattern.compile(filter);
        Stream<Contact> matches = contactRepository
                .findAll()
                .filter(contact -> !regex.matcher(contact.getName()).matches());
        return new ContactsDto(matches);
    }
}




