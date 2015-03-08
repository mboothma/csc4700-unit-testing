package csc4700;

import csc4700.exceptions.DuplicateContactException;
import csc4700.exceptions.SerializedFormatException;

import java.io.*;

public class Backup {

    public static final String FIELD_SEPARATOR = ",";
    public static final String LINE_SEPARATOR = System.getProperty("line.separator");

    public String serializeContactList(ContactList list) {
        if (list == null) {
            throw new NullPointerException();
        }

        StringBuffer allContacts = new StringBuffer();
        for (Contact c : list.getContacts()) {
            StringBuffer contactLine = new StringBuffer();
            contactLine.append(c.getFirstName());
            contactLine.append(FIELD_SEPARATOR);
            contactLine.append(c.getLastName());
            contactLine.append(FIELD_SEPARATOR);
            contactLine.append(c.getStreetAddress());
            contactLine.append(FIELD_SEPARATOR);
            contactLine.append(c.getCity());
            contactLine.append(FIELD_SEPARATOR);
            contactLine.append(c.getState());
            contactLine.append(FIELD_SEPARATOR);
            contactLine.append(c.getZipCode());

            allContacts.append(contactLine.toString());
            allContacts.append(LINE_SEPARATOR);
        }

        return allContacts.toString();
    }

    public ContactList deserializeContactList(String s) throws SerializedFormatException {
        if (s == null) {
            throw new NullPointerException();
        }

        ContactList result = new ContactList();

        String[] allContactLines = s.split(LINE_SEPARATOR);
        for (String contactLine : allContactLines) {
            String[] contactPieces = contactLine.split(FIELD_SEPARATOR);
            if (contactPieces.length != 6) {
                throw new SerializedFormatException();
            }

            Contact addMe = new Contact();
            addMe.setFirstName(contactPieces[0]);
            addMe.setLastName(contactPieces[1]);
            addMe.setStreetAddress(contactPieces[2]);
            addMe.setCity(contactPieces[3]);
            addMe.setState(contactPieces[4]);
            addMe.setZipCode(contactPieces[5]);

            try {
                result.addContact(addMe);
            }
            catch (DuplicateContactException e) {
                // If this occurred, the serialized version of the contact list was
                // corrupted and duplicate entries were allowed. For simplicity in
                // the project, we'll consider this an invalid serialized format.
                throw new SerializedFormatException();
            }
        }

        return result;
    }

    public void saveContactList(ContactList saveMe, File location) throws IOException {

        // If there is already a file at the given location, delete it before continuing.
        if (location.exists()) {
            location.delete();
        }

        // Serialize the contact list to be written to the file.
        String serialized = serializeContactList(saveMe);

        BufferedWriter bw = new BufferedWriter(new FileWriter(location));
        bw.write(serialized);
        bw.close();
    }

    public ContactList loadContactList(File location)
            throws IOException, SerializedFormatException {

        // If the file isn't found, throw an error.
        if (!location.exists()) {
            throw new FileNotFoundException();
        }

        // Read in the contents of the serialized file.
        BufferedReader br = new BufferedReader(new FileReader(location));
        String line;
        StringBuffer allLines = new StringBuffer();
        while ((line = br.readLine()) != null) {
            allLines.append(line);
            allLines.append(LINE_SEPARATOR);
        }

        // Deserialize the contents into a ContactList.
        ContactList result = deserializeContactList(allLines.toString());
        return result;
    }

}
