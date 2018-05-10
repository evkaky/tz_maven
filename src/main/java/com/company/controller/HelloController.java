package com.company.controller;

import com.company.entity.Contact;
import com.company.repository.ContactRepository;
import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.regex.Pattern;
import java.util.stream.Stream;

@RestController
@RequestMapping("hello")
public class HelloController {
    private ContactRepository contactRepository;
    private ObjectMapper mapper;

    public HelloController(ContactRepository contactRepository, ObjectMapper mapper) {
        this.contactRepository = contactRepository;
        this.mapper = mapper;
    }

    @GetMapping("contacts")
    public void findContacts(@RequestParam("nameFilter") String filter, HttpServletResponse response) throws IOException {
        Pattern regex = Pattern.compile(filter);
        Stream<Contact> matchedContacts = contactRepository
                .findAll()
                .filter(contact -> !regex.matcher(contact.getName()).matches());

        response.setContentType("application/json");

        JsonGenerator jg = mapper.getFactory().createGenerator(response.getOutputStream(), JsonEncoding.UTF8);
        jg.writeStartObject();
        jg.writeFieldName("contacts");
        jg.writeStartArray();

        matchedContacts.forEach(contact -> {
            try {
                jg.writeStartObject();
                jg.writeNumberField("id", contact.getId());
                jg.writeStringField("name", contact.getName());
                jg.writeEndObject();
            } catch (IOException e) {  // checked exceptions one love
                e.printStackTrace();
            }
        });

        jg.writeEndArray();
        jg.writeEndObject();
        jg.close();
    }
}




