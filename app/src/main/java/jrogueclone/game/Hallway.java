package jrogueclone.game;

import java.util.Vector;
import jrogueclone.Global;

public class Hallway {
    public Hallway(Vector2D startPosition, Vector2D endPosition, char[][] roomMap) {
        int UP = 0;
        int DOWN = 1;
        int LEFT = 2;
        int RIGHT = 3;

        // Calculate the actual start point of the hallway. This is going to be one away
        // from the given start point in order to make it stick out from the room itself
        if(startPosition.getY() > 0 && roomMap[startPosition.getX()][startPosition.getY() - 1] == ' ') {
            m_Waypoints.add(new Vector2D(startPosition.getX(), startPosition.getY() - 1));
        } else if(startPosition.getY() < Global.rows - 1 && roomMap[startPosition.getX()][startPosition.getY() + 1] == ' ') {
            m_Waypoints.add(new Vector2D(startPosition.getX(), startPosition.getY() + 1));
        } else if(startPosition.getX() > 0 && roomMap[startPosition.getX() - 1][startPosition.getY()] == ' ') {
            m_Waypoints.add(new Vector2D(startPosition.getX() - 1, startPosition.getY()));
        } else if(startPosition.getX() < Global.columns - 1 && roomMap[startPosition.getX() + 1][startPosition.getY()] == ' ') {
            m_Waypoints.add(new Vector2D(startPosition.getX() + 1, startPosition.getY()));
        }

        // Calculate the actual end point of the hallway using the same method as above
        Vector2D finalWaypoint = new Vector2D(endPosition);
        if(endPosition.getY() > 0 && roomMap[endPosition.getX()][endPosition.getY() - 1] == ' ') {
            finalWaypoint = (new Vector2D(endPosition.getX(), endPosition.getY() - 1));
        } else if(endPosition.getY() < Global.rows - 1 && roomMap[endPosition.getX()][endPosition.getY() + 1] == ' ') {
            finalWaypoint = (new Vector2D(endPosition.getX(), endPosition.getY() + 1));
        } else if(endPosition.getX() > 0 && roomMap[endPosition.getX() - 1][endPosition.getY()] == ' ') {
            finalWaypoint = (new Vector2D(endPosition.getX() - 1, endPosition.getY()));
        } else if(endPosition.getX() < Global.columns - 1 && roomMap[endPosition.getX() + 1][endPosition.getY()] == ' ') {
            finalWaypoint = (new Vector2D(endPosition.getX() + 1, endPosition.getY()));
        }

        // Calculate which direction we need to travel in to get to the end
        int xDirection = m_Waypoints.get(0).getX() < finalWaypoint.getX() ? RIGHT : LEFT;
        int yDirection = m_Waypoints.get(0).getY() < finalWaypoint.getY() ? DOWN : UP;

        Vector2D cursorPos = new Vector2D(m_Waypoints.get(0));
        while(cursorPos.getX() != finalWaypoint.getX() || cursorPos.getY() != finalWaypoint.getY()) {
            // Move until the x value is equal to that of the waypoint or until we hit a wall
            boolean movedX = false;
            while(!(
                (cursorPos.getX() == finalWaypoint.getX()) ||
                (xDirection == LEFT && cursorPos.getX() == 0) ||
                (xDirection == RIGHT && cursorPos.getX() == Global.columns - 1) ||
                (roomMap[cursorPos.getX() + (xDirection == LEFT ? -1 : 1)][cursorPos.getY()] == '#')
            )) {
                cursorPos.setX(cursorPos.getX() + (xDirection == LEFT ? -1 : 1));
                movedX = true;
            }
            if(movedX) {
                m_Waypoints.add(new Vector2D(cursorPos));
            }

            // Handle the case where our x value equals the end point except our y value does not and
            // There is a wall in our way from going up or down. This means that we need to deviate from the
            // target x value until we can move up or down. Afterwards, we recalculate the x and y direction
            // that we need to move
            if(!cursorPos.equals(finalWaypoint)) {
                boolean adjustX = false;
                while(
                    (cursorPos.getY() != Global.rows - 1 && yDirection == DOWN && roomMap[cursorPos.getX()][cursorPos.getY() + 1] == '#') ||
                    (cursorPos.getY() > 0 && yDirection == UP && roomMap[cursorPos.getX()][cursorPos.getY() - 1] == '#')) {
                    cursorPos.setX(cursorPos.getX() + (xDirection == LEFT ? -1 : 1));
                    adjustX = true;
                }
                if(adjustX) {
                    m_Waypoints.add(new Vector2D(cursorPos));
                    //continue;
                }
            }
            xDirection = m_Waypoints.lastElement().getX() < finalWaypoint.getX() ? RIGHT : LEFT;
            yDirection = m_Waypoints.lastElement().getY() < finalWaypoint.getY() ? DOWN : UP;
            
            boolean movedY = false;
            while(!(
                (yDirection == DOWN && cursorPos.getY() == Global.rows - 1) ||
                (yDirection == UP && cursorPos.getY() == 0) ||
                (cursorPos.getY() == finalWaypoint.getY()) ||
                (yDirection == UP && cursorPos.getY() == 0) ||
                (yDirection == DOWN && cursorPos.getY() == Global.rows - 1) ||
                (roomMap[cursorPos.getX()][cursorPos.getY() + (yDirection == DOWN ? 1 : -1)] == '#')
            )) {
                cursorPos.setY(cursorPos.getY() + (yDirection == UP ? -1 : 1));
                movedY = true;
            }
            if(movedY) {
                m_Waypoints.add(new Vector2D(cursorPos));
            }

            // Handle the case where our y value equals the end point except our x value does not and
            // There is a wall in our way from going left or right. This means that we need to deviate from the
            // target y value until we can move right or left. Afterwards, we recalculate the x and y direction
            // that we need to move
            if(!cursorPos.equals(finalWaypoint)) {
                boolean adjustY = false;
                if(cursorPos.getY() >= Global.rows - 1) {
                    yDirection = UP;
                    cursorPos.setY(Global.rows - 1);
                } else if(cursorPos.getY() < 0) {
                    yDirection = DOWN;
                    cursorPos.setY(0);
                }
                while(
                    (roomMap[cursorPos.getX() + 1][cursorPos.getY()] == '#') ||
                    (roomMap[cursorPos.getX() - 1][cursorPos.getY()] == '#')) {
                    if(cursorPos.getY() >= Global.rows - 1) {
                        yDirection = UP;
                        cursorPos.setY(Global.rows - 1);
                    } else if(cursorPos.getY() < 0) {
                        yDirection = DOWN;
                        cursorPos.setY(0);
                    }
                    cursorPos.setY(cursorPos.getY() + (yDirection == UP ? -1 : 1));

                    adjustY = true;
                }
                if(adjustY) {
                    m_Waypoints.add(new Vector2D(cursorPos));
                }
            }

        }

        m_Waypoints.add(new Vector2D(finalWaypoint));

    }

    public void draw() {
        if(m_Waypoints.size() < 1) {
            return;
        }
        Vector2D cursorPos = new Vector2D(m_Waypoints.get(0));
        for(int i = 0; i < m_Waypoints.size() - 1; i++) {
            while(!cursorPos.equals(m_Waypoints.get(i + 1))) {
                Global.terminalHandler.putChar(cursorPos.getX(), cursorPos.getY(), '#', 255, 233, true, this);
                if(cursorPos.getX() < m_Waypoints.get(i + 1).getX()) {
                    cursorPos.setX(cursorPos.getX() + 1);
                } else if(cursorPos.getX() > m_Waypoints.get(i + 1).getX()) {
                    cursorPos.setX(cursorPos.getX() - 1);
                } else if(cursorPos.getY() > m_Waypoints.get(i + 1).getY()) {
                    cursorPos.setY(cursorPos.getY() - 1);
                } else if(cursorPos.getY() < m_Waypoints.get(i + 1).getY()) {
                    cursorPos.setY(cursorPos.getY() + 1);
                }
            }
        }
        for (Vector2D wp : m_Waypoints) {
            Global.terminalHandler.putChar(wp.getX(), wp.getY(), '*', 255, 233, true, this);
        }
    }

    Vector<Vector2D> m_Waypoints = new Vector<>();
}
