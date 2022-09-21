package jrogueclone.game;

import java.util.Vector;

import javafx.util.Pair;
import jrogueclone.Global;

import java.awt.Rectangle;

public class MapGeneration {
	public static Vector<Room> generateRooms() {

		Vector<Room> rooms = new Vector<Room>();

		int minimumRoomHorizontalPadding = 3;
		int minimumRoomVerticalPadding = 1;
		int maximumRoomWidth = 15;
		int maximumRoomHeight = 7;
		int maximumRoomsHorizontal = (int) (Global.columns / (minimumRoomHorizontalPadding + maximumRoomWidth));
		int maximumRoomsVertical = (int) (Global.rows / (minimumRoomVerticalPadding + maximumRoomHeight));

		// Generate rooms
		for (int i = 0; i < maximumRoomsVertical; i++) {
			for (int j = 0; j < maximumRoomsHorizontal; j++) {
				rooms.add(new Room(
						new Vector2D(j * maximumRoomWidth + (j + 1) * minimumRoomHorizontalPadding,
								i * maximumRoomHeight + (i + 1) * minimumRoomVerticalPadding),
						maximumRoomWidth - (int) (Math.random() * 5 + 0),
						maximumRoomHeight - (int) (Math.random() * 3 + 0)));
			}
		}

		// Delete random room
		for(int i = 0; i < 4; i++)
			rooms.remove((int) (Math.random() * rooms.size()));

		// move them around
		for(int it = 0; it < 3; it++)
		for (int i = 0; i < rooms.size(); i++) {
			int tgtHorizMovement = 10;// (int)(Math.random() * 3 + 1);
			int tgtHorizDirection = (int) (Math.random() * 2) == 1 ? 1 : -1; // -1 = left, +1 = right
			int tgtVertMovement = 10;// (int)(Math.random() * 2 + 1);
			int tgtVertDirection = (int) (Math.random() * 2) == 1 ? 1 : -1;

			while (Math.abs(tgtHorizMovement) > 0) {
				boolean collides = false;
				for (int j = 0; j < rooms.size(); j++) {
					if (j == i)
						continue;
					Rectangle curRectangle = new Rectangle(
							rooms.get(i).getRoomPosition().getX() - minimumRoomHorizontalPadding + tgtHorizDirection,
							rooms.get(i).getRoomPosition().getY() - minimumRoomVerticalPadding,
							rooms.get(i).getRoomWidth() + minimumRoomHorizontalPadding * 2,
							rooms.get(i).getRoomHeight() + minimumRoomVerticalPadding * 2);
					// System.out.println(i + "rect: " + curRectangle);
					// System.out.println("j=" + j + " rect: " + rooms.get(j).getRect());
					Rectangle newRectangle = rooms.get(j).getRect();
					if (curRectangle.intersects(newRectangle) || newRectangle.intersects(curRectangle)
							|| (curRectangle.x + curRectangle.width > Global.columns) && tgtHorizDirection == 1
							|| (curRectangle.x < 0) && (tgtHorizDirection == -1)) {
						// System.out.println(curRectangle + " collides");
						collides = true;
						break;
					}
				}

				if (collides) {
					// System.out.println("collides");
					break;
				} else {
					Vector2D currentPosition = rooms.get(i).getRoomPosition();
					currentPosition.setX(currentPosition.getX() + tgtHorizDirection);
					rooms.get(i).setRoomPosition(currentPosition);
					tgtHorizMovement--;
				}
			}
			while (Math.abs(tgtVertMovement) > 0) {
				boolean collides = false;
				for (int j = 0; j < rooms.size(); j++) {
					if (j == i)
						continue;
					Rectangle curRectangle = new Rectangle(
							rooms.get(i).getRoomPosition().getX() - minimumRoomHorizontalPadding,
							rooms.get(i).getRoomPosition().getY() - minimumRoomVerticalPadding + tgtVertDirection,
							rooms.get(i).getRoomWidth() + minimumRoomHorizontalPadding * 2,
							rooms.get(i).getRoomHeight() + minimumRoomVerticalPadding * 2);
					// System.out.println(i + "rect: " + curRectangle);
					// System.out.println("j=" + j + " rect: " + rooms.get(j).getRect());
					Rectangle newRectangle = rooms.get(j).getRect();
					if (curRectangle.intersects(newRectangle) || newRectangle.intersects(curRectangle)
							|| (curRectangle.y + curRectangle.height > Global.rows) && tgtVertDirection == 1
							|| (curRectangle.y < 0) && (tgtVertDirection == -1)) {
						// System.out.println(curRectangle + " collides");
						collides = true;
						break;
					}
				}


				if(collides) {
					
					break;
				} else {
					Vector2D currentPosition = rooms.get(i).getRoomPosition();
					currentPosition.setY(currentPosition.getY() + tgtVertDirection);
					rooms.get(i).setRoomPosition(currentPosition);
					tgtVertMovement--;
				}
			}
		}
		

      return rooms;
   }
   public static Level generateLevel() {
      return new Level(generateRooms(), new Vector<Pair<Vector2D, Vector2D>>());
   }

   public static Level tgenerateLevel() {
      Vector<Room> rooms = generateRooms(), tempRooms = rooms;
      Vector<Pair<Vector2D, Vector2D>> connectors = new Vector<>();
      int closestDistance = 1000;

      // our random start room
      Room currentRoom = rooms.elementAt((int) Math.round((Math.random() * (rooms.size() - 1)) + 1)),
            nextRoom = null;

      tempRooms.remove(currentRoom);
      while (tempRooms.size() > 0) {
         Vector2D currentRoomMiddle = new Vector2D(currentRoom.getRoomPosition().getX() / 2,
               currentRoom.getRoomPosition().getY() / 2);

         // find the closest room
         for (Room room : tempRooms) {
            if (room == currentRoom)
               continue;

            // d = sqrt((y2 - y1)^2 + (x2 - x1)^2)
            Vector2D roomMiddle = new Vector2D(room.getRoomPosition().getX() / 2,
                  room.getRoomPosition().getY() / 2);

            int distance = (int) Math.round(Math.sqrt(Math.pow(roomMiddle.getY() - currentRoomMiddle.getY(), 2)
                  + Math.pow(roomMiddle.getX() - currentRoomMiddle.getX(), 2)));

            if (distance <= closestDistance) {
               nextRoom = room;
            }
         }
         tempRooms.remove(nextRoom); // we don't need to count this room again

         /*
          * CONNECT THEM HERE
          */
         Vector2D roomMiddle = new Vector2D(nextRoom.getRoomPosition().getX() / 2,
               nextRoom.getRoomPosition().getY() / 2);
         int vX = currentRoomMiddle.getX();
         while (vX < currentRoom.getRoomWidth() + currentRoom.getRoomPosition().getX() - 1) {
            vX++;
         }
         // set the closest room to the next closest
         currentRoom = nextRoom;

      }

      return new Level(rooms, connectors);
   }
}
