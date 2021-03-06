package seedu.ta.logic.parser;

import static seedu.ta.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.ta.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.ta.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.ta.testutil.TypicalIndices.INDEX_FIRST;

import org.junit.jupiter.api.Test;

import seedu.ta.logic.commands.DeleteContactCommand;

/**
 * As we are only doing white-box testing, our test cases do not cover path variations
 * outside of the DeleteContactCommand code. For example, inputs "1" and "1 abc" take the
 * same path through the DeleteContactCommand, and therefore we test only one of them.
 * The path variation for those two cases occur inside the ParserUtil, and
 * therefore should be covered by the ParserUtilTest.
 */
public class DeleteContactCommandParserTest {

    private final DeleteContactCommandParser parser = new DeleteContactCommandParser();

    @Test
    public void parse_validArgs_returnsDeleteContactCommand() {
        assertParseSuccess(parser, "1", new DeleteContactCommand(INDEX_FIRST));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        String msg = DeleteContactCommand.MESSAGE_USAGE;
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT, msg));
    }
}
