package jrogueclone;

public class App {
    public String getGreeting() {
        return "Hello World!";
    }

    public static void main(String[] args) {
        Global.getGameLoop().gameHandler();

    }
}
