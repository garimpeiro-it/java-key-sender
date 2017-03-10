# java-key-sender
Send keyboard events to the operational system using a jar file.

# How it works
You should execute the jar file passing the key as arguments. The key will be sent as keyboard input to the operational system.

One key or multiple keys can be sent in a single execution, and also, press multiple keys at the same time (example: Ctrl + V).

To send one key at a time pass the arguments separated with space (example: W O R L D).

To press multiple keys at the same time pass he arguments separated with hyphen (example: CTRL-SHIFT-V).

The list of keys can be extracted from Java KeyEvent class documentation: https://docs.oracle.com/javase/7/docs/api/java/awt/event/KeyEvent.html. Given the constants (example: VK_A), just take out "VK_" and use it as an argument for the key. For example:

VK_A = a

VK_CONTROL = control

# Usage examples
To send the string "ABC" (one key at a time):

    java -jar key-sender.jar A B C
   
To send the combination Ctrl + "V" (will be pressed together):

    java -jar key-sender.jar CONTROL-V

# Project dependencies
To download the project dependencies run the following:

    mvn install

# Building
The jar can be generated with:
    
    mvn package
    
The jar file is built inside `/target/` folder.
