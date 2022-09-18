package jrogueclone;

import java.util.Vector;

import jrogueclone.game.MapGeneration;
import jrogueclone.game.Room;

public class App {
    public String getGreeting() {
        return "Hello World!";
    }

    public static void main(String[] args) {

        Vector<Room> rooms = MapGeneration.GenerateRooms();
        for(Room room : rooms) {
            //System.out.println(room.getRoomHeight() * room.getRoomWidth());
        }
    }
}
