package club.garimpeiro.keysender;

import org.apache.commons.cli.*;

public class ArgsParser {

    public CommandLine parse(String args[]) {
        Options options = buildOptions();

        CommandLineParser parser = new DefaultParser();
        CommandLine cmd;

        try {
            if (args.length == 0) {
                throw new Exception("No arguments");
            }

            cmd = parser.parse(options, args);
        } catch (Exception e) {
            System.out.println(e.getMessage());

            HelpFormatter formatter = new HelpFormatter();
            String NL = System.getProperty("line.separator");
            String syntax = NL;
            syntax = syntax + "java -jar key-sender.jar [key1, key2, ...] # ONE KEY AT A TIME" + NL;
            syntax = syntax + "java -jar key-sender.jar [key1-key2-...] # MULTIPLE KEYS AT SAME THE TIME" + NL;
            syntax = syntax + "key: To see the list of keys, consult Java KeyEvent contants. You should";
            syntax = syntax + "     take out \"VK_\" from the constant name. ";
            syntax = syntax + "     https://docs.oracle.com/javase/7/docs/api/java/awt/event/KeyEvent.html";
            syntax = syntax + NL;
            syntax = syntax + NL;
            formatter.printHelp(syntax, options);

            System.exit(1);

            return null;
        }

        return cmd;
    }

    protected Options buildOptions() {
        Options options = new Options();
        options.addOption("d", "delay", true, "Default delay between keystrokes (in miliseconds)");
        options.addOption("pd", "pdelay", true, "Key press delay (in miliseconds)");
        options.addOption("c", "case-correction", true, "Case correction (1=on, 0=off, default=on)");

        return options;
    }
}
