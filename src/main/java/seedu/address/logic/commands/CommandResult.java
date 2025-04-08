package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Objects;
import java.util.Optional;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.wedding.Wedding;

/**
 * Represents the result of a command execution.
 */
public class CommandResult {

    private final String feedbackToUser;

    /** Help information should be shown to the user. */
    private final boolean showHelp;

    /** The application should exit. */
    private final boolean exit;

    private final boolean clearDisplay;

    private final Optional<Wedding> wedding;

    /**
     * Constructs a {@code CommandResult} with the specified fields.
     */
    public CommandResult(String feedbackToUser, boolean showHelp, boolean exit) {
        this.feedbackToUser = requireNonNull(feedbackToUser);
        this.showHelp = showHelp;
        this.exit = exit;
        this.clearDisplay = false;
        this.wedding = Optional.empty();
    }

    /**
     * Constructs a {@code CommandResult} with the specified {@code feedbackToUser}
     * and a flag indicating whether to clear the display.
     *
     * @param feedbackToUser Feedback message to be shown to the user.
     * @param clearDisplay Whether the display should be cleared.
     */
    public CommandResult(String feedbackToUser, boolean clearDisplay) {
        this.feedbackToUser = requireNonNull(feedbackToUser);
        this.showHelp = false;
        this.exit = false;
        this.clearDisplay = true;
        this.wedding = Optional.empty();
    }

    /**
     * Constructs a {@CommandResult} containing the wedding to show
     */
    public CommandResult(String feedbackToUser, Wedding wedding) {
        this.feedbackToUser = requireNonNull(feedbackToUser);
        this.showHelp = false;
        this.exit = false;
        this.clearDisplay = false;
        this.wedding = Optional.of(requireNonNull(wedding));
    }

    /**
     * Constructs a {@code CommandResult} with the specified {@code feedbackToUser},
     * and other fields set to their default value.
     */
    public CommandResult(String feedbackToUser) {
        this(feedbackToUser, false, false);
    }

    public String getFeedbackToUser() {
        return feedbackToUser;
    }

    public boolean isShowHelp() {
        return showHelp;
    }

    public boolean isExit() {
        return exit;
    }

    public boolean isClearDisplay() {
        return clearDisplay;
    }

    public Optional<Wedding> getSelectedWedding() {
        return wedding;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof CommandResult)) {
            return false;
        }

        CommandResult otherCommandResult = (CommandResult) other;
        return feedbackToUser.equals(otherCommandResult.feedbackToUser)
                && showHelp == otherCommandResult.showHelp
                && exit == otherCommandResult.exit;
    }

    @Override
    public int hashCode() {
        return Objects.hash(feedbackToUser, showHelp, exit);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("feedbackToUser", feedbackToUser)
                .add("showHelp", showHelp)
                .add("exit", exit)
                .toString();
    }

}
