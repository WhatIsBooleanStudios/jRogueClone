package jrogueclone.game;

import java.util.Vector;

public class MapGeneration {
   public static Vector<Room> GenerateRooms() {

      int roomCount = (int) ((Math.random() * 2) + 7), totalRoomArea = 0;
      Vector<Room> rooms = new Vector<Room>();

      for (int i = 0; i < roomCount; i++) {
         int roomWidth = 0, roomHeight = 0;
         while ((roomWidth * roomHeight) >= m_MaximumRoomArea || (roomWidth * roomHeight) <= m_MinimumRoomArea) {
            roomWidth = (int) ((Math.random() * 4) + m_MinimumRoomWidth);
            roomHeight = (int) ((Math.random() * 4) + m_MinimumRoomHeight);
         }
         
         totalRoomArea += roomHeight * roomWidth;
         System.out.println((i + 1) + " | " + (roomHeight * roomWidth) + " || " + totalRoomArea);
         rooms.add(new Room(roomWidth, roomHeight));
      }

      // return null because algo isn't finsihed
      return rooms;
   }

   /*
    * minimum 5x5
    * 2 for doors
    * 14 for walls
    * 9 for room space: 1 for potential chest and 8 for mobs or empty space
    * #####
    * #...#
    * |...|
    * #...#
    * #####
    * 
    */
   private static final int m_MaximumRoomArea = 60, m_MinimumRoomWidth = 6,
         m_MinimumRoomHeight = 6, m_MinimumRoomArea = m_MinimumRoomHeight * m_MinimumRoomWidth;
}
