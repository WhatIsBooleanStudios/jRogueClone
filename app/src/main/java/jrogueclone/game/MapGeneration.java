package jrogueclone.game;

import java.util.Vector;
import java.util.HashSet;
import java.util.HashMap;

import jrogueclone.Global;
import jrogueclone.entity.Player;

import java.awt.Rectangle;

public class MapGeneration {
	public static Vector<Room> generateRooms() {
		Vector<Room> rooms = new Vector<>();

		int minimumRoomHorizontalPadding = 3;
		int minimumRoomVerticalPadding = 1;
		int maximumRoomWidth = 15;
		int maximumRoomHeight = 7;
		int maximumRoomsHorizontal = ((Global.columns - minimumRoomHorizontalPadding)
				/ (minimumRoomHorizontalPadding + maximumRoomWidth));
		int maximumRoomsVertical = ((Global.rows - minimumRoomVerticalPadding)
				/ (minimumRoomVerticalPadding + maximumRoomHeight));

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
		rooms.remove((int) (Math.random() * rooms.size()));

		// move them around
		for (int it = 0; it < 3; it++)
			for (int i = 0; i < rooms.size(); i++) {
				int tgtHorizMovement = 10;
				int tgtHorizDirection = (int) (Math.random() * 2) == 1 ? 1 : -1;
				int tgtVertMovement = 10;
				int tgtVertDirection = (int) (Math.random() * 2) == 1 ? 1 : -1;

				while (Math.abs(tgtHorizMovement) > 0) {
					boolean collides = false;
					for (int j = 0; j < rooms.size(); j++) {
						if (j == i)
							continue;
						Rectangle curRectangle = new Rectangle(
								rooms.get(i).getRoomPosition().getX() - minimumRoomHorizontalPadding
										+ tgtHorizDirection,
								rooms.get(i).getRoomPosition().getY() - minimumRoomVerticalPadding,
								rooms.get(i).getRoomWidth() + minimumRoomHorizontalPadding * 2,
								rooms.get(i).getRoomHeight() + minimumRoomVerticalPadding * 2);
						Rectangle newRectangle = rooms.get(j).getRect();
						if (curRectangle.intersects(newRectangle) || newRectangle.intersects(curRectangle)
								|| (curRectangle.x + curRectangle.width > Global.columns) && tgtHorizDirection == 1
								|| (curRectangle.x < 0) && (tgtHorizDirection == -1)) {
							collides = true;
							break;
						}
					}

					if (collides) {
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
								rooms.get(i).getRoomPosition().getY() - minimumRoomVerticalPadding
										+ tgtVertDirection,
								rooms.get(i).getRoomWidth() + minimumRoomHorizontalPadding * 2,
								rooms.get(i).getRoomHeight() + minimumRoomVerticalPadding * 2);

						Rectangle newRectangle = rooms.get(j).getRect();
						if (curRectangle.intersects(newRectangle) || newRectangle.intersects(curRectangle)
								|| (curRectangle.y + curRectangle.height > Global.rows) && tgtVertDirection == 1
								|| (curRectangle.y < 0) && (tgtVertDirection == -1)) {
							collides = true;
							break;
						}
					}

					if (collides) {
						break;
					} else {
						Vector2D currentPosition = rooms.get(i).getRoomPosition();
						currentPosition.setY(currentPosition.getY() + tgtVertDirection);
						rooms.get(i).setRoomPosition(currentPosition);
						tgtVertMovement--;

					}

				}

			}
		for (Room room : rooms) {
			room.generateHallwayConnectionPoints();
		}

		return rooms;
	}

	private static Vector2D findNearestConnection(Vector2D position, Room parentRoom, Vector<Room> rooms,
			HashSet<Vector2D> usedPositions, HashMap<Room, HashSet<Room>> alreadyConnectedRooms) {
		Vector2D closest = new Vector2D(Integer.MAX_VALUE, Integer.MAX_VALUE);
		double closestDistance = Double.MAX_VALUE;
		Room closestRoom = null;
		for (Room room : rooms) {
			if (parentRoom.getRoomPosition().equals(room.getRoomPosition())) {
				continue;
			}
			for (Vector2D connectionPoint : room.getHallwayConnectionPoints()) {
				if (usedPositions.contains(connectionPoint))
					continue;
				alreadyConnectedRooms.putIfAbsent(parentRoom, new HashSet<>());
				if (alreadyConnectedRooms.get(parentRoom).contains(room))
					continue;
				double distance = Math.sqrt(
						Math.pow((connectionPoint.getX() - position.getX()), 2) +
								Math.pow((connectionPoint.getY() - position.getY()), 2));

				if (closestDistance > distance) {
					closestDistance = distance;
					closest = connectionPoint;
					closestRoom = room;
				}
			}
		}

		alreadyConnectedRooms.putIfAbsent(parentRoom, new HashSet<>());
		alreadyConnectedRooms.get(parentRoom).add(closestRoom);
		alreadyConnectedRooms.putIfAbsent(closestRoom, new HashSet<>());
		alreadyConnectedRooms.get(closestRoom).add(parentRoom);

		return closest;
	}

	private static Room findRoomInBottomLeftCorner(Vector<Room> rooms) {
		Vector<Room> sortedRooms = rooms;
		sortedRooms.sort((a, b) -> {
			Integer aPosX = (a.getRoomPosition().getX());
			Integer bPosX = (b.getRoomPosition().getY());
			Integer aPosY = (a.getRoomPosition().getY());
			Integer bPosY = (b.getRoomPosition().getY());
			int xComparison = aPosX.compareTo(bPosX);
			if (xComparison != 0) {
				return xComparison;
			} else {
				return bPosY.compareTo(aPosY);
			}
		});
		return sortedRooms.get(0);
	}

	private static Room findRoomInBottomRightCorner(Vector<Room> rooms) {
		Vector<Room> sortedRooms = rooms;
		sortedRooms.sort((a, b) -> {
			Integer aPosX = (a.getRoomPosition().getX());
			Integer bPosX = (b.getRoomPosition().getY());
			Integer aPosY = (a.getRoomPosition().getY());
			Integer bPosY = (b.getRoomPosition().getY());
			int xComparison = bPosX.compareTo(aPosX);
			if (xComparison != 0) {
				return xComparison;
			} else {
				return bPosY.compareTo(aPosY);
			}
		});
		return sortedRooms.get(0);
	}

	private static Vector<Hallway> generateHallways(Vector<Room> rooms) {
		char[][] roomMap = new char[Global.columns][Global.rows];
		for (int i = 0; i < Global.columns; i++) {
			for (int j = 0; j < Global.rows; j++) {
				roomMap[i][j] = ' ';
			}
		}
		for (Room room : rooms) {
			for (int i = room.getRoomPosition().getX(); i < room.getRoomPosition().getX() + room.getRoomWidth(); i++) {
				for (int j = room.getRoomPosition().getY(); j < room.getRoomPosition().getY()
						+ room.getRoomHeight(); j++) {
					roomMap[i][j] = '#';
				}
			}
		}
		Vector<Hallway> hallways = new Vector<>();
		HashSet<Vector2D> usedPositions = new HashSet<>();
		HashMap<Room, HashSet<Room>> alreadyConnectedRooms = new HashMap<>();
		Vector<Vector2D> mustConnectHallways = new Vector<>();
		for (Room room : rooms) {
			for (Vector2D startPosition : room.getHallwayConnectionPoints()) {
				if (usedPositions.contains(startPosition))
					continue;
				Vector2D connectionPoint = findNearestConnection(new Vector2D(startPosition), room, rooms,
						usedPositions, alreadyConnectedRooms);
				if (connectionPoint.equals(new Vector2D(Integer.MAX_VALUE, Integer.MAX_VALUE))) {
					mustConnectHallways.add(startPosition);
				} else {
					hallways.add(new Hallway(startPosition, connectionPoint, roomMap));
					usedPositions.add(startPosition);
					usedPositions.add(connectionPoint);
				}
			}
		}

		for (int i = 0; i < mustConnectHallways.size() - (mustConnectHallways.size() % 2 == 0 ? 0 : 1); i += 2) {
			hallways.add(new Hallway(mustConnectHallways.get(i), mustConnectHallways.get(i + 1), roomMap));
		}

		Room bottomLeft = findRoomInBottomLeftCorner(rooms);
		Room bottomRight = findRoomInBottomRightCorner(rooms);

		if (!alreadyConnectedRooms.get(bottomLeft).contains(bottomRight)) {
			bottomLeft.addExtraHallwayConnectionPoint();
			bottomRight.addExtraHallwayConnectionPoint();
			hallways.add(new Hallway(bottomLeft.getHallwayConnectionPoints().lastElement(),
					bottomRight.getHallwayConnectionPoints().lastElement(), roomMap));
		}
		return hallways;
	}

	public static Level generateLevel(Player player) {
		Vector<Room> rooms = generateRooms();
		if (player != null) {
			return new Level(rooms, generateHallways(rooms), player);
		} else
			return new Level(rooms, generateHallways(rooms), new Player('@'));

	}
}
