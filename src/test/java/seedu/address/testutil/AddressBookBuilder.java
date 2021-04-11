package seedu.address.testutil;

import seedu.address.model.TeachingAssistant;
import seedu.address.model.person.Person;

/**
 * A utility class to help with building Addressbook objects.
 * Example usage: <br>
 *     {@code TeachingAssistant ab = new AddressBookBuilder().withPerson("John", "Doe").build();}
 */
public class AddressBookBuilder {

    private TeachingAssistant addressBook;

    public AddressBookBuilder() {
        addressBook = new TeachingAssistant();
    }

    public AddressBookBuilder(TeachingAssistant addressBook) {
        this.addressBook = addressBook;
    }

    /**
     * Adds a new {@code Person} to the {@code TeachingAssistant} that we are building.
     */
    public AddressBookBuilder withPerson(Person person) {
        addressBook.addPerson(person);
        return this;
    }

    public TeachingAssistant build() {
        return addressBook;
    }
}
