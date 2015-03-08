package csc4700;

import csc4700.exceptions.ContactNotFoundException;
import csc4700.exceptions.DuplicateContactException;

import java.util.ArrayList;
import java.util.List;

public class ContactList {

    private List<Contact> contacts = new ArrayList<Contact>();

    public void addContact(Contact addMe) throws DuplicateContactException {
        if (addMe == null) {
            throw new NullPointerException();
        }
        if (contacts.contains(addMe)) {
            throw new DuplicateContactException();
        }
        contacts.add(addMe);
    }

    public void removeContact(Contact deleteMe) throws ContactNotFoundException {
        if (deleteMe == null) {
            throw new NullPointerException();
        }
        if (!contacts.contains(deleteMe)) {
            throw new ContactNotFoundException();
        }
        contacts.remove(deleteMe);
    }

    public void removeContactsByLastName(String lastName) {

        // Determine all contacts with the given last name (case sensitive).
        List<Contact> deleteUs = new ArrayList<Contact>();
        for (Contact c : contacts) {
            if (c.getLastName().equals(lastName)) {
                deleteUs.add(c);
            }
        }

        // Remove any that are found; this call will not error if the size
        // of deleteUs is 0.
        contacts.removeAll(deleteUs);
    }

    public void removeAllContacts() {
        contacts.clear();
    }

    public List<Contact> getContacts() {
        return contacts;
    }
}
