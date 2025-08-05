package com.ltp.contacts.service;

import java.util.List;

import com.ltp.contacts.pojo.Contact;

public interface ContactService {
    List<Contact> getAllContacts();
    Contact getContactById(String id);
    void saveContact(Contact contact);
    void updateContact(String id, Contact contact);
    void deleteContact(String id);
}
