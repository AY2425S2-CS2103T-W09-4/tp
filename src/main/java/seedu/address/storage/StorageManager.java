package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.ReadOnlyWeddingPlanner;
import seedu.address.model.UserPrefs;

/**
 * Manages storage of AddressBook data in local storage.
 */
public class StorageManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private WeddingPlannerStorage weddingPlannerStorage;
    private UserPrefsStorage userPrefsStorage;

    /**
     * Creates a {@code StorageManager} with the given {@code AddressBookStorage} and {@code UserPrefStorage}.
     */
    public StorageManager(WeddingPlannerStorage weddingPlannerStorage, UserPrefsStorage userPrefsStorage) {
        this.weddingPlannerStorage = weddingPlannerStorage;
        this.userPrefsStorage = userPrefsStorage;
    }

    // ================ UserPrefs methods ==============================

    @Override
    public Path getUserPrefsFilePath() {
        return userPrefsStorage.getUserPrefsFilePath();
    }

    @Override
    public Optional<UserPrefs> readUserPrefs() throws DataLoadingException {
        return userPrefsStorage.readUserPrefs();
    }

    @Override
    public void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException {
        userPrefsStorage.saveUserPrefs(userPrefs);
    }


    // ================ WeddingPlanner methods ==============================

    @Override
    public Path getWeddingPlannerFilePath() {
        return weddingPlannerStorage.getWeddingPlannerFilePath();
    }

    @Override
    public Optional<ReadOnlyWeddingPlanner> readWeddingPlanner() throws DataLoadingException {
        return readWeddingPlanner(weddingPlannerStorage.getWeddingPlannerFilePath());
    }

    @Override
    public Optional<ReadOnlyWeddingPlanner> readWeddingPlanner(Path filePath) throws DataLoadingException {
        logger.fine("Attempting to read data from file: " + filePath);
        return weddingPlannerStorage.readWeddingPlanner(filePath);
    }

    @Override
    public void saveWeddingPlanner(ReadOnlyWeddingPlanner weddingPlanner) throws IOException {
        saveWeddingPlanner(weddingPlanner, weddingPlannerStorage.getWeddingPlannerFilePath());
    }

    @Override
    public void saveWeddingPlanner(ReadOnlyWeddingPlanner weddingPlanner, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        weddingPlannerStorage.saveWeddingPlanner(weddingPlanner, filePath);
    }

}
