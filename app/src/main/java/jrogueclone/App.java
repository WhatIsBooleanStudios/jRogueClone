package jrogueclone;

import jrogueclone.gfx.TerminalHandler;

public class App {
    public String getGreeting() {
        return "Hello World!";
    }

    public static void main(String[] args) {
        System.out.println(new App().getGreeting());
        while(true) {
            int data = Global.terminalHandler.read();
            if((char)data == 'f') {
                System.out.println("Pressed F");
            }
        }
    }
}
