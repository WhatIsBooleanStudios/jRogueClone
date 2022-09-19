package jrogueclone;

import jrogueclone.game.MapGeneration;
import jrogueclone.game.Room;
import jrogueclone.gfx.TerminalHandler;

import java.util.Vector;

public class App {
    public String getGreeting() {
        return "Hello World!";
    }

    public static void main(String[] args) {
        Global.terminalHandler.initAlternateScreen();
        Vector<Room> rooms = MapGeneration.GenerateRooms();
        Global.terminalHandler.begin();
        for(Room room : rooms) {
            room.draw();
        }
        Global.terminalHandler.end();

        while(!Global.terminalHandler.keyIsPressed('q')) Global.terminalHandler.updateKeyPresses();
        Global.terminalHandler.restoreState();
    }
}
