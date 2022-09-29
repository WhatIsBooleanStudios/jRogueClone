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
        System.out.print("Do you want to play another text adventure(y/n): ");
        String in = sc.nextLine();
        if (in.length() > 0 && in.charAt(0) == 'y') {
            System.out.println("Too bad, cry about it. Baby");
        } else {
            System.out.println("Good, because you don't have a choice anyway.");
        }
        /*
         * try {
         * Thread.sleep(2000);
         * } catch(InterruptedException e) {
         * e.printStackTrace();
         * }
         */
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
