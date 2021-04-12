package seedu.ta.logic.parser;

import static seedu.ta.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.ta.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.ta.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.ta.testutil.TypicalIndices.INDEX_FIRST;

import org.junit.jupiter.api.Test;

import seedu.ta.logic.commands.DeleteEntryCommand;

public class DeleteEntryCommandParserTest {

    private DeleteEntryCommandParser parser = new DeleteEntryCommandParser();

    @Test
    public void parse_validArgs_returnsDeleteEntryCommand() {
        assertParseSuccess(parser, "1", new DeleteEntryCommand(INDEX_FIRST));
    }

    @Test
    public void parser_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "name",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteEntryCommand.MESSAGE_USAGE));
    }
}
