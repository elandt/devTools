package com.elandt.lil.spring_demo.builder;

/**
 * This Contact object is immutable because it doesn't need to be mutable
 */
public class Contact {

    private final String firstName;
    private final String lastName;
    private final String emailAddress;

    private Contact(String firstName, String lastName, String emailAddress) {
        super();
        this.firstName = firstName;
        this.lastName = lastName;
        this.emailAddress = emailAddress;
    }

    public String getFirstName() {
        return firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public String getEmailAddress() {
        return emailAddress;
    }

    /**
     * This `builder()` method allows getting the builder directly from the Contact base class.
     *
     * Alternatively, and this is what is shown in the course video, we can simply call
     * `ContactBuilder.getInstance()` directly.
     *
     * @return a {@link ContactBuilder}
     */
    public static Contact.ContactBuilder builder() {
        return ContactBuilder.getInstance();
    }

    public static class ContactBuilder {
        private String firstName;
        private String lastName;
        private String emailAddress;

        private ContactBuilder() {
            super();
        }

        public static ContactBuilder getInstance() {
            return new ContactBuilder();
        }

        public ContactBuilder firstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public ContactBuilder lastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public ContactBuilder emailAddress(String emailAddress) {
            this.emailAddress = emailAddress;
            return this;
        }

        public Contact build() {
            return new Contact(this.firstName, this.lastName, this.emailAddress);
        }
    }
}
