package com.ltp.contacts.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.ltp.contacts.pojo.Contact;
import com.ltp.contacts.service.ContactService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PutMapping;




@RestController
public class ContactController {
    
    @Autowired
    private ContactService contactService;

    @GetMapping("/contact/all")
    public ResponseEntity<List<Contact>> getAllContacts() {
         List<Contact> allContacts = contactService.getAllContacts();
         ResponseEntity<List<Contact>> response = new ResponseEntity<>(allContacts, HttpStatus.OK);
         return response;
    }
    

    @GetMapping("/contact/{id}")
    public ResponseEntity<Contact> getContact(@PathVariable String id) {
        Contact contact = contactService.getContactById(id);
        ResponseEntity<Contact> response = new ResponseEntity<>(contact, HttpStatus.OK);
        return response;
    }

    @PostMapping("/contact")
    public ResponseEntity<HttpStatus> createContact(@RequestBody Contact contact) {
        contactService.saveContact(contact);
        ResponseEntity<HttpStatus> response = new ResponseEntity<>(HttpStatus.CREATED);
        return response;
    }
    
    @PutMapping("contact/{id}")
    public ResponseEntity<Contact> updateContact(@PathVariable String id, @RequestBody Contact contact) {
        contactService.updateContact(id, contact);
        Contact updatedContact = contactService.getContactById(id);
        ResponseEntity<Contact> response = new ResponseEntity<>(updatedContact, HttpStatus.OK);
        return response;
    }

    @DeleteMapping("contact/{id}")
    public ResponseEntity<Contact> deleteContact(@PathVariable String id) {
        Contact contactToBeDeleted = contactService.getContactById(id);
        contactService.deleteContact(id);
        ResponseEntity<Contact> response = new ResponseEntity<>(contactToBeDeleted, HttpStatus.OK);
        return response;
    }
}
