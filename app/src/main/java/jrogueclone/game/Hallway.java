package jrogueclone.game;

import java.util.Vector;
import jrogueclone.Global;

public class Hallway {
    public Hallway(Vector2D startPosition, Vector2D endPosition, char[][] roomMap) {
        int UP = 0;
        int DOWN = 1;
        int LEFT = 2;
        int RIGHT = 3;

        System.out.println("start: " + startPosition + " end: " + endPosition);

        for(int i = 0; i < Global.rows; i++) {
            System.out.print(i + (i <= 9 ? " " : ""));
            for(int j = 0; j < Global.columns; j++) {
                if((i == startPosition.getY() && j == startPosition.getX()) || (i == endPosition.getY() && j == endPosition.getX())) {
                    System.out.print('%');
                } else {
                    System.out.print(roomMap[j][i]);
                }
            }
            System.out.println();
        }

        System.out.println("creating hallway...");
        System.out.flush();

        
        
        if(startPosition.getY() > 0 && roomMap[startPosition.getX()][startPosition.getY() - 1] == ' ') {
            m_Waypoints.add(new Vector2D(startPosition.getX(), startPosition.getY() - 1));
        } else if(startPosition.getY() < Global.rows - 1 && roomMap[startPosition.getX()][startPosition.getY() + 1] == ' ') {
            m_Waypoints.add(new Vector2D(startPosition.getX(), startPosition.getY() + 1));
        } else if(startPosition.getX() > 0 && roomMap[startPosition.getX() - 1][startPosition.getY()] == ' ') {
            m_Waypoints.add(new Vector2D(startPosition.getX() - 1, startPosition.getY()));
        } else if(startPosition.getX() < Global.columns - 1 && roomMap[startPosition.getX() + 1][startPosition.getY()] == ' ') {
            m_Waypoints.add(new Vector2D(startPosition.getX() + 1, startPosition.getY()));
        }
        Vector2D finalWaypoint = new Vector2D(endPosition);
        if(endPosition.getY() > 0 && roomMap[endPosition.getX()][endPosition.getY() - 1] == ' ') {
            finalWaypoint = (new Vector2D(endPosition.getX(), endPosition.getY() - 1));
            System.out.println("end above");
        } else if(endPosition.getY() < Global.rows - 1 && roomMap[endPosition.getX()][endPosition.getY() + 1] == ' ') {
            finalWaypoint = (new Vector2D(endPosition.getX(), endPosition.getY() + 1));
            System.out.println("end below");
        } else if(endPosition.getX() > 0 && roomMap[endPosition.getX() - 1][endPosition.getY()] == ' ') {
            finalWaypoint = (new Vector2D(endPosition.getX() - 1, endPosition.getY()));
            System.out.println("end left");
        } else if(endPosition.getX() < Global.columns - 1 && roomMap[endPosition.getX() + 1][endPosition.getY()] == ' ') {
            finalWaypoint = (new Vector2D(endPosition.getX() + 1, endPosition.getY()));
            System.out.println("end right");
        }

        int xDirection = m_Waypoints.get(0).getX() < finalWaypoint.getX() ? RIGHT : LEFT;
        int yDirection = m_Waypoints.get(0).getY() < finalWaypoint.getY() ? DOWN : UP;

        System.out.println(m_Waypoints.get(0) + " to " + finalWaypoint);

        Vector2D cursorPos = new Vector2D(m_Waypoints.get(0));
        while(cursorPos.getX() != finalWaypoint.getX() || cursorPos.getY() != finalWaypoint.getY()) {
            boolean movedX = false;
            while(!(
                (cursorPos.getX() == finalWaypoint.getX()) ||
                (xDirection == LEFT && cursorPos.getX() == 0) ||
                (xDirection == RIGHT && cursorPos.getX() == Global.columns - 1) ||
                (roomMap[cursorPos.getX() + (xDirection == LEFT ? -1 : 1)][cursorPos.getY()] == '#')
            )) {
                cursorPos.setX(cursorPos.getX() + (xDirection == LEFT ? -1 : 1));
                movedX = true;
                System.out.println("loop x");
                System.out.flush();
            }
            if(movedX) {
                m_Waypoints.add(new Vector2D(cursorPos));
                System.out.println("waypointX: " + cursorPos);
            }
            
            boolean movedY = false;
            while(!(
                (cursorPos.getY() == finalWaypoint.getY()) ||
                (yDirection == UP && cursorPos.getY() == 0) ||
                (yDirection == DOWN && cursorPos.getY() == Global.rows - 1) ||
                (roomMap[cursorPos.getX()][cursorPos.getY() + (yDirection == DOWN ? 1 : -1)] == '#')
            )) {
                System.out.println("Loop y");
                System.out.flush();
                cursorPos.setY(cursorPos.getY() + (yDirection == UP ? -1 : 1));
                movedY = true;
            }
            if(movedY) {
                m_Waypoints.add(new Vector2D(cursorPos));
                System.out.println("waypointY: " + cursorPos);
            }

        }
        System.out.println("finished creating hallway");

        m_Waypoints.add(new Vector2D(finalWaypoint));
        m_tempEndPosition = new Vector2D(endPosition);

        // try {
        //     Thread.sleep(10000000);
        // } catch (InterruptedException e) {
        //     e.printStackTrace();
        // }
    }
    private Vector2D m_tempEndPosition = new Vector2D();

    public void draw() {
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
        System.out.println(m_Waypoints.size());
        System.out.println(m_Waypoints.lastElement());
        System.out.println(m_tempEndPosition);
    }

    Vector<Vector2D> m_Waypoints = new Vector<Vector2D>();
}
