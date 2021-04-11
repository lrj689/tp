package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalTeachingAssistant.AVA;
import static seedu.address.testutil.TypicalTeachingAssistant.CONSULTATION_MATH;
import static seedu.address.testutil.TypicalTeachingAssistant.HANNAH;
import static seedu.address.testutil.TypicalTeachingAssistant.IVAN;
import static seedu.address.testutil.TypicalTeachingAssistant.REMEDIAL;
import static seedu.address.testutil.TypicalTeachingAssistant.getTypicalTeachingAssistant;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyTeachingAssistant;
import seedu.address.model.TeachingAssistant;

public class JsonTeachingAssistantStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonTeachingAssistantStorageTest");

    @TempDir
    public Path testFolder;

    @Test
    public void readAddressBook_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> readAddressBook(null));
    }

    private java.util.Optional<ReadOnlyTeachingAssistant> readAddressBook(String filePath) throws Exception {
        return new JsonTeachingAssistantStorage(Paths.get(filePath))
                .readTeachingAssistant(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readAddressBook("NonExistentFile.json").isPresent());
    }

    @Test
    public void read_notJsonFormat_exceptionThrown() {
        assertThrows(DataConversionException.class, () -> readAddressBook("notJsonFormatTeachingAssistant.json"));
    }

    @Test
    public void readAddressBook_invalidPersonAddressBook_throwDataConversionException() {
        assertThrows(DataConversionException.class, () -> readAddressBook("invalidContactTeachingAssistant.json"));
    }

    @Test
    public void readAddressBook_invalidAndValidPersonAddressBook_throwDataConversionException() {
        assertThrows(DataConversionException.class, () -> readAddressBook("invalidAndValidTeachingAssistant.json"));
    }

    @Test
    public void readAddressBook_invalidEntryTeachingAssistant_throwDataConversionException() {
        assertThrows(DataConversionException.class, () -> readAddressBook("invalidEntryTeachingAssistant.json"));
    }

    @Test
    public void readAddressBook_invalidAndValidEntryTeachingAssistant_throwDataConversionException() {
        assertThrows(DataConversionException.class, () ->
                readAddressBook("invalidAndValidEntryTeachingAssistant.json"));
    }

    @Test
    public void readAndSaveAddressBook_allInOrder_success() throws Exception {
        Path filePath = testFolder.resolve("TempTeachingAssistant.json");
        TeachingAssistant original = getTypicalTeachingAssistant();
        JsonTeachingAssistantStorage jsonAddressBookStorage = new JsonTeachingAssistantStorage(filePath);

        // Save in new file and read back
        jsonAddressBookStorage.saveTeachingAssistant(original, filePath);
        ReadOnlyTeachingAssistant readBack = jsonAddressBookStorage.readTeachingAssistant(filePath).get();
        assertEquals(original, new TeachingAssistant(readBack));

        // Modify data, overwrite exiting file, and read back
        original.addContact(HANNAH);
        original.removeContact(AVA);
        original.addEntry(REMEDIAL);
        jsonAddressBookStorage.saveTeachingAssistant(original, filePath);
        readBack = jsonAddressBookStorage.readTeachingAssistant(filePath).get();
        assertEquals(original, new TeachingAssistant(readBack));

        // Save and read without specifying file path
        original.addContact(IVAN);
        original.addEntry(CONSULTATION_MATH);
        jsonAddressBookStorage.saveTeachingAssistant(original); // file path not specified
        readBack = jsonAddressBookStorage.readTeachingAssistant().get(); // file path not specified
        assertEquals(original, new TeachingAssistant(readBack));

    }

    @Test
    public void saveAddressBook_nullAddressBook_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveAddressBook(null, "SomeFile.json"));
    }

    /**
     * Saves {@code addressBook} at the specified {@code filePath}.
     */
    private void saveAddressBook(ReadOnlyTeachingAssistant addressBook, String filePath) {
        try {
            new JsonTeachingAssistantStorage(Paths.get(filePath))
                    .saveTeachingAssistant(addressBook, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveAddressBook_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveAddressBook(new TeachingAssistant(), null));
    }
}
