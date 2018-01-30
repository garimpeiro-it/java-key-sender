# java-key-sender
Send keyboard events to the operational system using a jar file.

# Requirements
Java 8

# How it works
You should execute the jar file passing the key as arguments. The key will be sent as keyboard input to the operational system.

One key or multiple keys can be sent in a single execution, and also, press multiple keys at the same time (example: Ctrl + V).

To send one key at a time pass the arguments separated with space (example: W O R L D).

To press multiple keys at the same time pass he arguments separated with hyphen (example: CTRL-SHIFT-V).

The list of keys can be extracted from Java [KeyEvent class]( https://docs.oracle.com/javase/7/docs/api/java/awt/event/KeyEvent.html) documentation. Given the constants (example: VK_A), just take out "VK_" from the constant name and use it as an argument for the key. The constant numerical value can also be used (with @). For example:

VK_A = use "a" or "@65"

VK_CONTROL = use "control" or "@17"

# Usage examples
To send the string "ABC" (one key at a time):

    java -jar key-sender.jar A B C
   
To send the combination Ctrl + "V" (will be pressed together):

    java -jar key-sender.jar CONTROL-V
    
To send key down and key up events for letter "F":

    java -jar key-sender.jar f.down f.up
    
To wait 1 second between keys "G" and "H":

    java -jar key-sender.jar g.w1000 h
    
To keep "G" key pressed 1 second:

    java -jar key-sender.jar g.down.w1000 g.up
    
To start waiting 1 second before typing the letter "T":

    java -jar key-sender.jar t -sd 1000
    
To send the key A using its numerical value (65):

    java -jar key-sender.jar @65
    
To show help execute without arguments:

    java -jar key-sender.jar

# Project dependencies
To download the project dependencies run the following:

    mvn install

# Building
The jar can be generated with:
    
    mvn package
    
The jar file is built inside `/target/` folder.
