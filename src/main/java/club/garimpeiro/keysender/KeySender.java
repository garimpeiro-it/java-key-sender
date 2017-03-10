package club.garimpeiro.keysender;

import org.apache.commons.cli.CommandLine;

public class KeySender {
    public static void main(String[] args) {
        try {
            ArgsParser argsParser = new ArgsParser();
            CommandLine cmd = argsParser.parse(args);

            Processor processor = new Processor(cmd);
            processor.process();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
