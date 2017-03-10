package club.garimpeiro.keysender;

import org.apache.commons.cli.CommandLine;

import java.awt.*;
import java.awt.event.KeyEvent;

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
        String pdelay = cmd.getOptionValue("pdelay");

        if (pdelay != null) {
            robot.setAutoDelay(Integer.parseInt(pdelay));
        }

        for (String arg: cmd.getArgList()) {
            if (arg.contains("-")) {
                processCombination(arg);
            } else {
                processKey(arg, true, true);
            }
        }
    }

    protected void processKey(String arg, Boolean hold, Boolean release) {
        int key = getKeyConstantValue(arg);
        String delay = cmd.getOptionValue("delay");

        typeKey(key, isUpperCase(arg), hold, release);

        if (delay != null) {
            robot.delay(Integer.parseInt(delay));
        }
    }

    protected void processCombination(String args) {
        for (String arg: args.split("-")) {
            processKey(arg, true, false);
        }

        for (String arg: args.split("-")) {
            processKey(arg, false, true);
        }
    }

    protected void typeKey(int key, Boolean upperCase, Boolean hold, Boolean release) {
        if (key == 0 || (hold == false && release == false)) {
            return;
        }

        String caseCorrection = cmd.getOptionValue("case-correction", "1");

        Boolean holdShift = upperCase && isCapsLockOff() || !upperCase && !isCapsLockOff();

        if (holdShift && caseCorrection.equals("1")) {
            robot.keyPress(KeyEvent.VK_SHIFT);
        }

        if (hold) {
            robot.keyPress(key);
        }

        if (release) {
            robot.keyRelease(key);
        }

        if (holdShift && caseCorrection.equals("1")) {
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
