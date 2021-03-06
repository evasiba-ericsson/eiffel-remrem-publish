package com.ericsson.eiffel.remrem.publish.cli;

import static org.junit.Assert.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.ericsson.eiffel.remrem.publish.config.PropertiesConfig;
import com.ericsson.eiffel.remrem.publish.cli.CliOptions;;

public class CliOptionsUnitTests {
    private PrintStream console;
    private ByteArrayOutputStream bytes;
	
	@Before public void setUp() throws Exception {
        String key = PropertiesConfig.TEST_MODE;
        System.setProperty(key, "true");
        //Switch std out to another stream
        bytes   = new ByteArrayOutputStream();
        console = System.out;
        System.setOut(new PrintStream(bytes));
    }

    @After
    public void tearDown() {
        System.clearProperty(PropertiesConfig.TEST_MODE);
        System.setOut(console);
        // reset error code since it is static
        CliOptions.cleanErrorCodes();
    }
    
    @Test
    public void testParseEmptyCLIOptionsFails() throws Exception {	
        String[] args = new String[0];	    

        CliOptions.parse(args);
        int code = CLIExitCodes.CLI_MISSING_OPTION_EXCEPTION;
        assertTrue(CliOptions.getErrorCodes().contains(code));
    }

    @Test
    public void testHelpOptionOnlyWorks() throws Exception {
        String[] args = {"-h"};
        CliOptions.parse(args);
        assertTrue(CliOptions.getErrorCodes().contains(0));
        assertTrue(CliOptions.getErrorCodes().size() == 1);
    }
    
    @Test
    public void testTlsOption() throws Exception {
        String[] args = {"-f", "/a/b/c/test.file", "-rk", "test", "-tls", "1"};
        CliOptions.parse(args);
        assertTrue(CliOptions.getErrorCodes().isEmpty());
    }
    
    @Test
    public void testTlsOptionFails() throws Exception {
        String[] args = {"-f", "/a/b/c/test.file", "-rk", "test", "-tlsa", "1.2"};
        CliOptions.parse(args);
        int code = CLIExitCodes.CLI_MISSING_OPTION_EXCEPTION;
        assertTrue(CliOptions.getErrorCodes().contains(code));
    }

    @Test
    public void testTlsVer13OptionFails() throws Exception {
        String[] args = {"-f", "/a/b/c/test.file", "-rk", "test", "-tls", "1.3"};
        CliOptions.parse(args);
        int code = CLIExitCodes.CLI_MISSING_OPTION_EXCEPTION;
        assertTrue(CliOptions.getErrorCodes().contains(code));
    }

    @Test
    public void testMbOption() throws Exception {
        String[] args = {"-f", "/a/b/c/test.file", "-rk", "test", "-mb", "MbInstance1"};
        CliOptions.parse(args);
        assertTrue(CliOptions.getErrorCodes().isEmpty());
    }
    
    @Test
    public void testMbOptionFails() throws Exception {
        String[] args = {"-f", "/a/b/c/test.file", "-rk", "test", "-mbe", "MbInstance1"};
        CliOptions.parse(args);
        int code = CLIExitCodes.CLI_MISSING_OPTION_EXCEPTION;
        assertTrue(CliOptions.getErrorCodes().contains(code));
    }
    
    public void testEnOption() throws Exception {
        String[] args = {"-f", "/a/b/c/test.file", "-rk", "test", "-en", "exchange_name1"};
        CliOptions.parse(args);
        assertTrue(CliOptions.getErrorCodes().isEmpty());
    }
    
    @Test
    public void testEnOptionFails() throws Exception {
        String[] args = {"-f", "/a/b/c/test.file", "-rk", "test", "-enf", "exchange_name1"};
        CliOptions.parse(args);
        int code = CLIExitCodes.CLI_MISSING_OPTION_EXCEPTION;
        assertTrue(CliOptions.getErrorCodes().contains(code));
    }
    
    public void testNpOption() throws Exception {
        String[] args = {"-f", "/a/b/c/test.file", "-rk", "test", "-np", "non_persistent"};
        CliOptions.parse(args);
        assertTrue(CliOptions.getErrorCodes().isEmpty());
    }
    
    @Test
    public void testNpOptionFails() throws Exception {
        String[] args = {"-f", "/a/b/c/test.file", "-rk", "test", "-npf", "non_persistent"};
        CliOptions.parse(args);
        int code = CLIExitCodes.CLI_MISSING_OPTION_EXCEPTION;
        assertTrue(CliOptions.getErrorCodes().contains(code));
    }

    public void testPortOption() throws Exception {
        String[] args = {"-f", "/a/b/c/test.file", "-rk", "test", "-port", "portA"};
        CliOptions.parse(args);
        assertTrue(CliOptions.getErrorCodes().isEmpty());
    }

    @Test
    public void testPortOptionFails() throws Exception {
        String[] args = {"-f", "/a/b/c/test.file", "-rk", "test", "-portf", "portB"};
        CliOptions.parse(args);
        int code = CLIExitCodes.CLI_MISSING_OPTION_EXCEPTION;
        assertTrue(CliOptions.getErrorCodes().contains(code));
    }
    
    public void testFOption() throws Exception {
        String[] args = {"-f", "/a/b/c/test.file", "-rk", "test", "-port", "portA"};
        CliOptions.parse(args);
        assertTrue(CliOptions.getErrorCodes().isEmpty());
    }

    @Test
    public void testFOptionFails() throws Exception {
        String[] args = {"-fab", "/a/b/c/test.file", "-rk", "test", "-portf", "portB"};
        CliOptions.parse(args);
        int code = CLIExitCodes.CLI_MISSING_OPTION_EXCEPTION;
        assertTrue(CliOptions.getErrorCodes().contains(code));
    }
    
}
