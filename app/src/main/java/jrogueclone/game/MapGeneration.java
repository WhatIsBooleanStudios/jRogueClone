package jrogueclone.game;

import java.util.Vector;

public class MapGeneration {
   private static Vector<Room> generateRooms() {

      return null;
   }

   public static Level generateLevel() {
      return new Level(generateRooms(), new Vector<Vector2D>());
   }
}
