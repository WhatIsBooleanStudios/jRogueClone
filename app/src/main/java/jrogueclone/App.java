package jrogueclone;

import jrogueclone.gfx.TerminalHandler;

public class App {
    public String getGreeting() {
        return "Hello World!";
    }

    public static void main(String[] args) {
        System.out.println(new App().getGreeting());
        while(true) {
            Global.terminalHandler.updateKeyPresses();
            if(Global.terminalHandler.keyIsPressed((int)'q')) {
                System.out.println("quit!");
                return;
            }
            if(Global.terminalHandler.keyIsPressed((int)'f')) {
                System.out.println("F is pressed");
            }
            if(Global.terminalHandler.keyIsPressed((int)'\n')) {
                System.out.println("Enter is pressed");
            }
        }
    }
}
