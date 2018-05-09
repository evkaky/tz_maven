package com.company.repository;

import com.company.entity.Contact;
import org.springframework.data.repository.Repository;

import java.util.stream.Stream;

public interface ContactRepository extends Repository<Contact, Long> {
    Stream<Contact> findAll();
}
