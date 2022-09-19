package jrogueclone.game;

import jrogueclone.Global;

import java.util.Vector;
import java.awt.Rectangle;

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

	  Global.terminalHandler.begin();
	  for(Room room : rooms) {
		  room.draw();
	  }
	  Global.terminalHandler.end();
      // move them around
      for(int i = 0; i < rooms.size(); i++) {
        	// TODO: move the rooms around randomly
			int tgtHorizMovement = 10;//(int)(Math.random() * 3 + 1);
			int tgtHorizDirection = (int)(Math.random() * 2) == 1 ? 1 : -1; // -1 = left, +1 = right
			int tgtVertMovement = 10;//(int)(Math.random() * 2 + 1);
			int tgtVertDirection = (int)(Math.random() * 2) == 1 ? 1 : -1;
			
			while(Math.abs(tgtHorizMovement) > 0) {
				boolean collides = false;
				for(int j = 0; j < rooms.size(); j++) {
					if(j == i) continue;
					Rectangle curRectangle = new Rectangle(
						rooms.get(i).getRoomPosition().getX() - minimumRoomHorizontalPadding + tgtHorizDirection,
						rooms.get(i).getRoomPosition().getY() - minimumRoomVerticalPadding,
						rooms.get(i).getRoomWidth() + minimumRoomHorizontalPadding * 2,
						rooms.get(i).getRoomHeight() + minimumRoomVerticalPadding * 2
					);
					// System.out.println(i + "rect: " + curRectangle);
					// System.out.println("j=" + j + " rect: " + rooms.get(j).getRect());
					Rectangle newRectangle = rooms.get(j).getRect();
					if (curRectangle.intersects(newRectangle) || newRectangle.intersects(curRectangle)
					    || (curRectangle.x + curRectangle.width > Global.cols) && tgtHorizDirection == 1
						|| (curRectangle.x < 0) && tgtHorizDirection == -1) {
						// System.out.println(curRectangle + " collides");
						collides = true;
						break;
					}
				}


				if(collides) {
					//System.out.println("collides");
					break;
				} else {
					//System.out.println("move " + i + " right");
					Vector2D currentPosition = rooms.get(i).getRoomPosition();
					currentPosition.setX(currentPosition.getX() + tgtHorizDirection);
					rooms.get(i).setRoomPosition(currentPosition);
					tgtHorizMovement -= tgtHorizDirection;
				}
				//System.out.println(i + " position: " + rooms.get(i).getRoomPosition());

			}

			while(Math.abs(tgtVertMovement) > 0) {
				boolean collides = false;
				for(int j = 0; j < rooms.size(); j++) {
					if(j == i) continue;
					Rectangle curRectangle = new Rectangle(
						rooms.get(i).getRoomPosition().getX() - minimumRoomHorizontalPadding,
						rooms.get(i).getRoomPosition().getY() - minimumRoomVerticalPadding + tgtVertDirection,
						rooms.get(i).getRoomWidth() + minimumRoomHorizontalPadding * 2,
						rooms.get(i).getRoomHeight() + minimumRoomVerticalPadding * 2
					);
					// System.out.println(i + "rect: " + curRectangle);
					// System.out.println("j=" + j + " rect: " + rooms.get(j).getRect());
					Rectangle newRectangle = rooms.get(j).getRect();
					if (curRectangle.intersects(newRectangle) || newRectangle.intersects(curRectangle)
					    || (curRectangle.y + curRectangle.height > Global.rows) && tgtVertDirection == 1
						|| (curRectangle.y < 0) && tgtVertDirection == -1) {
						// System.out.println(curRectangle + " collides");
						collides = true;
						break;
					}
				}


				if(collides) {
					//System.out.println("collides");
					break;
				} else {
					//System.out.println("move " + i + " right");
					Vector2D currentPosition = rooms.get(i).getRoomPosition();
					currentPosition.setY(currentPosition.getY() + tgtVertDirection);
					rooms.get(i).setRoomPosition(currentPosition);
					tgtVertMovement -= tgtVertDirection;
				}
				//System.out.println(i + " position: " + rooms.get(i).getRoomPosition());
			}
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
