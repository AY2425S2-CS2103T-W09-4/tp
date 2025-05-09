package seedu.address.logic.commands.wedding;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import seedu.address.logic.LogicMemory;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.WeddingModel;
import seedu.address.model.person.Person;
import seedu.address.model.wedding.Wedding;

/**
 * Adds a person to the currently active wedding (draft or open)
 */
public class AddWeddingPersonCommand extends Command {
    public static final String COMMAND_WORD = "add";
    public static final String MESSAGE_USAGE = COMMAND_WORD
        + ": Adds a person to the active wedding\n"
        + "Parameters: "
        + PREFIX_NAME + "NAME "
        + PREFIX_PHONE + "PHONE "
        + PREFIX_EMAIL + "EMAIL "
        + PREFIX_ADDRESS + "ADDRESS "
        + "[" + PREFIX_TAG + "ROLE]...\n"
        + "Example: " + COMMAND_WORD + " "
        + PREFIX_NAME + "Mary " + PREFIX_PHONE + "91234567 "
        + PREFIX_EMAIL + "mary@example.com " + PREFIX_ADDRESS + "123 Street "
        + PREFIX_TAG + "bride";

    public static final String MESSAGE_SUCCESS = "Added %1$s as %2$s";
    public static final String MESSAGE_SUCCESS_BRIDE = "Successfully added the bride. Now add the groom using \n"
            + "add n/NAME p/PHONE e/EMAIL a/ADDRESS t/groom";
    public static final String MESSAGE_SUCCESS_GROOM = "Successfully added the groom. Check out your new Wedding "
            + "on the left pane!";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the wedding\n";
    public static final String MESSAGE_NO_ACTIVE_WEDDING = "No active wedding! Create or open a wedding first.";
    public static final String MESSAGE_ROLE_CONFLICT = "The %s role is already assigned!";
    public static final String MESSAGE_INVALID_ROLE_TAG = "Invalid role tag! Use 'bride' or 'groom' for special roles.";

    private final Person toAdd;

    /**
     * Creates an AddPersonCommand to add the specified {@code Person}
     */
    public AddWeddingPersonCommand(Person toAdd) {
        requireNonNull(toAdd);
        this.toAdd = toAdd;
    }

    @Override
    public CommandResult execute(WeddingModel model) throws CommandException {
        requireNonNull(model);
        LogicMemory.DraftState state = LogicMemory.getDraftStage();

        if (state != LogicMemory.DraftState.NO_DRAFT) {

            Wedding activeWedding = model.getDraftWedding();

            if (model.weddingHasPerson(activeWedding, toAdd)) {
                throw new CommandException(MESSAGE_DUPLICATE_PERSON + LogicMemory.getDraftingMessage());
            }

            switch (state) {
            case ADDING_BRIDE:
                if (!toAdd.getTags().stream().anyMatch(t -> t.tagName.equalsIgnoreCase("bride"))) {
                    throw new CommandException(LogicMemory.getDraftingMessage());
                }
                activeWedding.setBride(toAdd);
                LogicMemory.setDraftStage(LogicMemory.DraftState.ADDING_GROOM);
                return new CommandResult(MESSAGE_SUCCESS_BRIDE);
            case ADDING_GROOM:
                if (!toAdd.getTags().stream().anyMatch(t -> t.tagName.equalsIgnoreCase("groom"))) {
                    throw new CommandException(LogicMemory.getDraftingMessage());
                }
                activeWedding.setGroom(toAdd);
                LogicMemory.resetLogicMemory();
                model.commitDraftWedding();
                System.out.println("Committing wedding...");
                return new CommandResult(MESSAGE_SUCCESS_GROOM);
            default:
                throw new CommandException(LogicMemory.getDraftingMessage());
            }
        }

        Wedding activeWedding = model.getCurrentWedding();

        if (activeWedding == null) {
            throw new CommandException(MESSAGE_NO_ACTIVE_WEDDING);
        }

        if (model.weddingHasPerson(activeWedding, toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        }

        // Determine role from tags
        String role = "member";
        if (toAdd.getTags().stream().anyMatch(t -> t.tagName.equalsIgnoreCase("bride"))) {
            role = "bride";
        } else if (toAdd.getTags().stream().anyMatch(t -> t.tagName.equalsIgnoreCase("groom"))) {
            role = "groom";
        }

        switch (role.toLowerCase()) {
        case "bride":
            if (activeWedding.getBride() != null) {
                throw new CommandException(String.format(MESSAGE_ROLE_CONFLICT, "bride"));
            }
            activeWedding.setBride(toAdd);
            break;

        case "groom":
            if (activeWedding.getGroom() != null) {
                throw new CommandException(String.format(MESSAGE_ROLE_CONFLICT, "groom"));
            }
            activeWedding.setGroom(toAdd);
            break;

        default:
            activeWedding.addMember(toAdd);
            break;
        }

        // Update model state
        model.setCurrentWedding(activeWedding);

        return new CommandResult(String.format(MESSAGE_SUCCESS,
            Messages.format(toAdd),
            role.toUpperCase()));
    }
}
