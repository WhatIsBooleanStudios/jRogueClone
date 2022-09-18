package jrogueclone.game;

import jrogueclone.Global;

import java.util.Vector;

public class MapGeneration {
   public static Vector<Room> GenerateRooms() {

      int roomCount = (int)((Math.random() * 3) + 7);
      Vector<Room> rooms = new Vector<Room>();

      int minimumRoomHorizontalPadding = 3;
      int minimumRoomVerticalPadding = 1;
      int maximumRoomWidth = 15;
      int maximumRoomHeight = 5;
      int maximumRoomsHorizontal = (int)(Global.cols / (minimumRoomHorizontalPadding + maximumRoomWidth));
      int maximumRoomsVertical = (int)(Global.rows / (minimumRoomVerticalPadding + maximumRoomHeight));


      // Generate rooms
      for(int i = 0; i < maximumRoomsVertical; i++) {
         for(int j = 0; j < maximumRoomsHorizontal; j++) {
            rooms.add(new Room(
                    new Vector2D(j * maximumRoomWidth + (j + 1) * minimumRoomHorizontalPadding, i * maximumRoomHeight + (i + 1) * minimumRoomVerticalPadding),
                    maximumRoomWidth - (int)(Math.random() * 5 + 0),
                    maximumRoomHeight - (int)(Math.random() * 3 + 0)
            ));
         }
      }

      // Delete random room
      rooms.remove((int)(Math.random() * rooms.size()));

      // move them around
      for(int i = 0; i < rooms.size(); i++) {
         // TODO: move the rooms around randomly
      }


      /*
      System.out.println("Position: (" + rooms.get(0).getRoomPosition().getX() + "," + rooms.get(0).getRoomPosition().getY() + ")");
      System.out.println("Width: " + rooms.get(0).getRoomWidth());
      System.out.println("Height: " + rooms.get(0).getRoomHeight());
      */

      return rooms;
   }

   private static final int m_MaximumRoomArea = 66, m_MinimumRoomWidth = 6,
         m_MinimumRoomHeight = 6, m_MinimumRoomArea = m_MinimumRoomHeight * m_MinimumRoomWidth;
}
