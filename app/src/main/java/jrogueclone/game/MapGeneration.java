package jrogueclone.game;

import java.util.Vector;

import jrogueclone.Global;
import jrogueclone.entity.Player;

import java.awt.Rectangle;

public class MapGeneration {
	public static Vector<Room> generateRooms() {

		int roomCount = (int) ((Math.random() * 3) + 7);
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
	  // TODO: player should be global
      return new Level(generateRooms(), new Vector<Vector2D>(), new Player('@'));
   }
}
