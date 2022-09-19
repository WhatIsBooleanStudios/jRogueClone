package jrogueclone.game;

import java.util.Vector;

import jrogueclone.Global;

import java.awt.Rectangle;

public class MapGeneration {
   private static Vector<Room> generateRooms() {
      Vector<Room> rooms = new Vector<Room>();

      int minimumRoomHorizontalPadding = 3;
      int minimumRoomVerticalPadding = 1;
      int maximumRoomWidth = 15;
      int maximumRoomHeight = 5;
      int maximumRoomsHorizontal = (int)(Global.columns / (minimumRoomHorizontalPadding + maximumRoomWidth));
      int maximumRoomsVertical = (int)(Global.rows / (minimumRoomVerticalPadding + maximumRoomHeight));

      for(int i = 0; i < maximumRoomsVertical; i++) {
         for(int j = 0; j < maximumRoomsHorizontal; j++) {
            rooms.add(new Room(
                    new Vector2D(j * maximumRoomWidth + (j + 1) * minimumRoomHorizontalPadding, i * maximumRoomHeight + (i + 1) * minimumRoomVerticalPadding),
                    maximumRoomWidth - (int)(Math.random() * 5 + 0),
                    maximumRoomHeight - (int)(Math.random() * 3 + 0)
            ));
         }
      }

      rooms.remove((int)(Math.random() * rooms.size()));
      for(int i = 0; i < rooms.size(); i++) {
			int tgtHorizMovement = 10;
			int tgtHorizDirection = (int)(Math.random() * 2) == 1 ? 1 : -1;
			int tgtVertMovement = 10;
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
					Rectangle newRectangle = rooms.get(j).getRect();
					if (curRectangle.intersects(newRectangle) || newRectangle.intersects(curRectangle)
					    || (curRectangle.x + curRectangle.width > Global.columns) && tgtHorizDirection == 1
						|| (curRectangle.x < 0) && tgtHorizDirection == -1) {
						collides = true;
						break;
					}
				}


				if(collides) {
					break;
				} else {
					Vector2D currentPosition = rooms.get(i).getRoomPosition();
					currentPosition.setX(currentPosition.getX() + tgtHorizDirection);
					rooms.get(i).setRoomPosition(currentPosition);
					tgtHorizMovement -= tgtHorizDirection;
				}
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
					Rectangle newRectangle = rooms.get(j).getRect();
					if (curRectangle.intersects(newRectangle) || newRectangle.intersects(curRectangle)
					    || (curRectangle.y + curRectangle.height > Global.rows) && tgtVertDirection == 1
						|| (curRectangle.y < 0) && tgtVertDirection == -1) {

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
					tgtVertMovement -= tgtVertDirection;
				}
			}
      }

      return rooms;
   }

   public static Level generateLevel() {
      return new Level(generateRooms(), new Vector<Vector2D>());
   }
}
