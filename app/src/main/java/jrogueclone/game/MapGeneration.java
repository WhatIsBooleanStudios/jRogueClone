package jrogueclone.game;

import java.util.Vector;

public class MapGeneration {
   public static Vector<Room> GenerateRooms() {

      int roomCount = (int)Math.round(((Math.random() * 2) + 7)) , totalRoomArea = 0;
      Vector<Room> rooms = new Vector<Room>();

      for (int i = 0; i < roomCount; i++) {
         int roomWidth = 0, roomHeight = 0;
         while ((roomWidth * roomHeight) >= m_MaximumRoomArea || (roomWidth * roomHeight) <= m_MinimumRoomArea) {
            roomWidth = (int) ((Math.random() * 4) + m_MinimumRoomWidth);
            roomHeight = (int) ((Math.random() * 4) + m_MinimumRoomHeight);
         }
         
         totalRoomArea += roomHeight * roomWidth;
         System.out.println("room: " + (i + 1) + " room area: " + (roomHeight * roomWidth) + " total room area: " + totalRoomArea);
         rooms.add(new Room(roomWidth, roomHeight));
      }

      return rooms;
   }
   private static final int m_MaximumRoomArea = 60, m_MinimumRoomWidth = 6,
         m_MinimumRoomHeight = 6, m_MinimumRoomArea = m_MinimumRoomHeight * m_MinimumRoomWidth;
}
