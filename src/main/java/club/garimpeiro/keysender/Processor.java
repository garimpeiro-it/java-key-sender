package club.garimpeiro.keysender;

import org.apache.commons.cli.CommandLine;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.OptionalLong;
import java.util.stream.IntStream;

public class Processor {

    protected CommandLine cmd;
    protected static Robot robot;

    public Processor(CommandLine cmd) {
        this.cmd = cmd;

        try {
            robot = new Robot();
        } catch (Exception e) {

        }
    }

    public void process() {
        try {
            String pdelay = cmd.getOptionValue("pdelay");
            String sdelay = cmd.getOptionValue("sdelay");
            String delay = cmd.getOptionValue("delay");

            if (pdelay != null) {
                robot.setAutoDelay(Integer.parseInt(pdelay));
            }

            if (sdelay != null) {
                robot.delay(Integer.parseInt(sdelay));
            }

            for (String arg: cmd.getArgList()) {
                int localDelay = getDelayArgument(arg);
                String keyArg = getKeyArgument(arg);
                Boolean isUp = getUpArgument(arg);
                Boolean isDown = getDownArgument(arg);

                Boolean hold = true;
                Boolean release = true;
                if (isUp || isDown) {
                    hold = isDown;
                    release = isUp;
                }

                if (arg.contains("-")) {
                    processCombination(keyArg, hold, release);
                } else {
                    processKey(keyArg, hold, release);
                }

                if (delay != null || localDelay != 0) {
                    robot.delay(localDelay != 0 ? localDelay : Integer.parseInt(delay));
                }
            }
        }  catch (Exception e) {

        }
    }

    protected String getKeyArgument(String arg) {
        String[] args = arg.split("\\.");

        if (args.length > 0) {
            return args[0];
        } else {
            return "";
        }
    }

    protected int getDelayArgument(String arg) {
        String[] args = arg.split("\\.");

        OptionalLong delay = IntStream.range(1, args.length)
                .filter(i -> args[i].startsWith("w"))
                .mapToLong(i -> Long.parseLong(args[i].substring(1)))
                .findFirst();

        if (delay.isPresent()) {
            return (int) delay.getAsLong();
        } else {
            return 0;
        }
    }

    protected Boolean getUpArgument(String arg) {
        String[] args = arg.split("\\.");
        long count = IntStream.range(1, args.length)
                .filter(i -> args[i].equals("up"))
                .count();

        return count > 0;
    }

    protected Boolean getDownArgument(String arg) {
        String[] args = arg.split("\\.");
        long count = IntStream.range(1, args.length)
                .filter(i -> args[i].equals("down"))
                .count();

        return count > 0;
    }

    protected void processKey(String arg, Boolean hold, Boolean release) {
        if (arg.startsWith("@")) {
            String args[] = arg.split("@");
            int keyCode = Integer.parseInt(args[1]);

            typeKey(keyCode, false, hold, release);
        } else {
            int keyCode = getKeyConstantValue(arg);
            typeKey(keyCode, isUpperCase(arg), hold, release);
        }
    }

    protected void processCombination(String args, Boolean hold, Boolean release) {
        if (hold) {
            for (String arg : args.split("-")) {
                processKey(arg, true, false);
            }
        }

        if (release) {
            for (String arg : args.split("-")) {
                processKey(arg, false, true);
            }
        }
    }

    protected void typeKey(int key, Boolean upperCase, Boolean hold, Boolean release) {
        if (key == 0 || (hold == false && release == false)) {
            return;
        }

        String caseCorrection = cmd.getOptionValue("case-correction", "1");
        Boolean isLetter = key >= KeyEvent.VK_A && key <= KeyEvent.VK_Z;

        Boolean holdShift = upperCase && isCapsLockOff() || !upperCase && !isCapsLockOff();

        if (holdShift && caseCorrection.equals("1") && isLetter) {
            robot.keyPress(KeyEvent.VK_SHIFT);
        }

        if (hold) {
            robot.keyPress(key);
        }

        if (release) {
            robot.keyRelease(key);
        }

        if (holdShift && caseCorrection.equals("1") && isLetter) {
            robot.keyRelease(KeyEvent.VK_SHIFT);
        }
    }

    protected int getKeyConstantValue(String letter) {
        int keyCode = 0;

        try {
            String keyConstant = "VK_" + letter.toUpperCase();
            keyCode = KeyEvent.class.getField(keyConstant).getInt(null);
        } catch(Exception e) {

        }

        return keyCode;
    }

    protected Boolean isUpperCase(String letter) {
        return Character.isUpperCase(letter.charAt(0));
    }

    protected Boolean isCapsLockOff() {
        return !Toolkit.getDefaultToolkit().getLockingKeyState(KeyEvent.VK_CAPS_LOCK);
    }
}
