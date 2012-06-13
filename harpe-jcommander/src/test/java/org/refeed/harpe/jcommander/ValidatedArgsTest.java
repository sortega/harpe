package org.refeed.harpe.jcommander.examples;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParameterException;
import com.beust.jcommander.converters.CommaParameterSplitter;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.refeed.harpe.Validation;
import org.refeed.harpe.jcommander.HarpeValidator;

import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.refeed.harpe.IntegerValidation.between;
import static org.refeed.harpe.IntegerValidation.integer;
import static org.refeed.harpe.StringValidation.matches;

public class ValidatedArgsTest {
    @Rule
    public ExpectedException thrownException = ExpectedException.none();

    public static class VerbosityValidator extends HarpeValidator {
        @Override
        protected Validation<String, Integer> getValidation() {
            return integer(between(1, 4));
        }
    }

    public static class GroupsValidator extends HarpeValidator {
        @Override
        protected Validation<String, String> getValidation() {
            return matches("^[a-zA-Z0-9]+(,[a-zA-Z0-9]+)*$",
                           "can contains comma-separated alphanumeric words only");
        }
    }

    public static class CommandLine {
        @Parameter
        public List<String> parameters = new ArrayList<String>();

        @Parameter(names = { "-log", "-verbose" },
                   validateWith = VerbosityValidator.class,
                   description = "Level of verbosity from 1 to 4")
        public Integer verbose = 1;

        @Parameter(names = "-groups",
                   validateWith = GroupsValidator.class,
                   splitter = CommaParameterSplitter.class,
                   description = "Comma-separated list of group names to be run. " +
                                 "Names can contain alphanumeric characters only.")
        private List<String> groups;

        @Parameter(names = "-debug",
                   description = "Debug mode")
        private boolean debug = false;
    }

    public CommandLine parseCli(String... args) {
        CommandLine cli = new CommandLine();
        new JCommander(cli, args);
        return cli;
    }

    @Test
    public void shouldParseValidCommandLine() {
        CommandLine cli = parseCli("-log", "2", "-groups", "unit,second", "a", "b",
                                   "c");
        assertEquals(cli.parameters, asList("a", "b", "c"));
        assertEquals(cli.verbose.intValue(), 2);
        assertEquals(asList("unit", "second"), cli.groups);
        assertFalse(cli.debug);
    }

    @Test
    public void shouldThrowExceptionWhenConversionError() {
        thrownException.expect(ParameterException.class);
        thrownException.expectMessage("-log is not a valid integer");
        parseCli("-log", "not-a-number", "-groups", "unit", "a", "b", "c");
    }

    @Test
    public void shouldThrowExceptionWhenLogValidationError() {
        thrownException.expect(ParameterException.class);
        thrownException.expectMessage("-log must be at most 4");
        parseCli("-log", "10", "-groups", "unit", "a", "b", "c");
    }

    @Test
    public void shouldThrowExceptionWhenGroupsValidationError() {
        thrownException.expect(ParameterException.class);
        thrownException.expectMessage("-groups can contains comma-separated " +
                                      "alphanumeric words only");
        parseCli("-log", "2", "-groups", "foo,bar,b$zz", "a", "b", "c");
    }
}
