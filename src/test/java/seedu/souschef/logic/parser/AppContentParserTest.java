package seedu.souschef.logic.parser;

public class AppContentParserTest {
    /*private Model model;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final AppContentParser parser = new AppContentParser();

    //TODO : add
    *//*@Test
    public void parseCommand_add() throws Exception {
        Recipe recipe = new RecipeBuilder().build();
        AddCommand command = (AddCommand) parser.parseCommand(model, RecipeUtil.getAddCommand(recipe));
        assertEquals(new AddCommand(model, recipe), command);
    }*//*

    @Test
    public void parseCommand_clear() throws Exception {
        assertTrue(parser.parseCommand(model, ClearCommand.COMMAND_WORD, ) instanceof ClearCommand);
        assertTrue(parser.parseCommand(model, ClearCommand.COMMAND_WORD + " 3", ) instanceof ClearCommand);
    }

    //TODO : delete
    *//*@Test
    public void parseCommand_delete() throws Exception {
        DeleteCommand command = (DeleteCommand) parser.parseCommand(
                model, DeleteCommand.COMMAND_WORD + " " + INDEX_FIRST_RECIPE.getOneBased());
        assertEquals(new DeleteCommand(INDEX_FIRST_RECIPE), command);
    }*//*

    @Test
    public void parseCommand_edit() throws Exception {
        Recipe recipe = new RecipeBuilder().build();
        EditCommand.EditRecipeDescriptor descriptor = new EditRecipeDescriptorBuilder(recipe).build();
        EditCommand command = (EditCommand) parser.parseCommand(model, EditCommand.COMMAND_WORD + " "
                + INDEX_FIRST_RECIPE.getOneBased() + " " + RecipeUtil.getEditRecipeDescriptorDetails(descriptor), );
        assertEquals(new EditCommand(INDEX_FIRST_RECIPE, descriptor), command);
    }

    @Test
    public void parseCommand_exit() throws Exception {
        assertTrue(parser.parseCommand(model, ExitCommand.COMMAND_WORD, ) instanceof ExitCommand);
        assertTrue(parser.parseCommand(model, ExitCommand.COMMAND_WORD + " 3", ) instanceof ExitCommand);
    }

    @Test
    public void parseCommand_find() throws Exception {
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        FindCommand command = (FindCommand) parser.parseCommand(model,
                FindCommand.COMMAND_WORD + " " + keywords.stream().collect(Collectors.joining(" ")), );
        assertEquals(new FindCommand(new NameContainsKeywordsPredicate(keywords)), command);
    }

    @Test
    public void parseCommand_help() throws Exception {
        assertTrue(parser.parseCommand(model, HelpCommand.COMMAND_WORD, ) instanceof HelpCommand);
        assertTrue(parser.parseCommand(model, HelpCommand.COMMAND_WORD + " 3", ) instanceof HelpCommand);
    }

    @Test
    public void parseCommand_history() throws Exception {
        assertTrue(parser.parseCommand(model, HistoryCommand.COMMAND_WORD, ) instanceof HistoryCommand);
        assertTrue(parser.parseCommand(model, HistoryCommand.COMMAND_WORD + " 3", ) instanceof HistoryCommand);

        try {
            parser.parseCommand(model, "histories", );
            throw new AssertionError("The expected ParseException was not thrown.");
        } catch (ParseException pe) {
            assertEquals(MESSAGE_UNKNOWN_COMMAND, pe.getMessage());
        }
    }

    @Test
    public void parseCommand_list() throws Exception {
        assertTrue(parser.parseCommand(model, ListCommand.COMMAND_WORD, ) instanceof ListCommand);
        assertTrue(parser.parseCommand(model, ListCommand.COMMAND_WORD + " 3", ) instanceof ListCommand);
    }

    @Test
    public void parseCommand_select() throws Exception {
        SelectCommand command = (SelectCommand) parser.parseCommand(model,
                SelectCommand.COMMAND_WORD + " " + INDEX_FIRST_RECIPE.getOneBased(), );
        assertEquals(new SelectCommand(INDEX_FIRST_RECIPE), command);
    }

    @Test
    public void parseCommand_redoCommandWord_returnsRedoCommand() throws Exception {
        assertTrue(parser.parseCommand(model, RedoCommand.COMMAND_WORD, ) instanceof RedoCommand);
        assertTrue(parser.parseCommand(model, "redo 1", ) instanceof RedoCommand);
    }

    @Test
    public void parseCommand_undoCommandWord_returnsUndoCommand() throws Exception {
        assertTrue(parser.parseCommand(model, UndoCommand.COMMAND_WORD, ) instanceof UndoCommand);
        assertTrue(parser.parseCommand(model, "undo 3", ) instanceof UndoCommand);
    }

    @Test
    public void parseCommand_unrecognisedInput_throwsParseException() throws Exception {
        thrown.expect(ParseException.class);
        thrown.expectMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        parser.parseCommand(model, "", );
    }

    @Test
    public void parseCommand_unknownCommand_throwsParseException() throws Exception {
        thrown.expect(ParseException.class);
        thrown.expectMessage(MESSAGE_UNKNOWN_COMMAND);
        parser.parseCommand(model, "unknownCommand", );
    }*/
}
