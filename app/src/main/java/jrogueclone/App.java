package jrogueclone;

import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println(
                          "░░░░░██╗██████╗░░█████╗░░██████╗░██╗░░░██╗███████╗\n"
                        + "░░░░░██║██╔══██╗██╔══██╗██╔════╝░██║░░░██║██╔════╝\n"
                        + "░░░░░██║██████╔╝██║░░██║██║░░██╗░██║░░░██║█████╗░░\n"
                        + "██╗░░██║██╔══██╗██║░░██║██║░░╚██╗██║░░░██║██╔══╝░░\n"
                        + "╚█████╔╝██║░░██║╚█████╔╝╚██████╔╝╚██████╔╝███████╗\n"
                        + "░╚════╝░╚═╝░░╚═╝░╚════╝░░╚═════╝░░╚═════╝░╚══════╝\n"
                        + "       By Samuel Wiseman and Aiden Lambert\n"
                        + "            (WHAT IS BOOLEAN STUDIOS)\n");
        System.out.print("Do you want to play another boring text adventure(y/n): ");
        String in = sc.nextLine();
        if (in.length() > 0 && in.charAt(0) == 'n') {
            System.out.println("Good, because you don't have a choice anyway.");
        } else {
            System.out.println("Too bad! You get something fun.");
        }

        System.out.print("Press enter to continue to the game: ");
        sc.nextLine();
        Global.initializeItems();
        Global.terminalHandler.initAlternateScreen();
        Global.terminalHandler.hideCursor();
        Global.getGameLoop().gameHandler();
        Global.terminalHandler.restoreState();
        sc.close();
    }
}
