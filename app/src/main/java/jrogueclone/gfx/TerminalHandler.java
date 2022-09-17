package jrogueclone.gfx;

import com.sun.jna.ptr.IntByReference;

import java.util.Arrays;

import jrogueclone.Global;
import jrogueclone.LibC;

public class TerminalHandler {
    public static final int STDIN_FILENO = 0;

    LibC.termios tios;
    LibC.termios initialTios;

    LibC.winsize terminalSize;

    public TerminalHandler() {
        tios = new LibC.termios();
        initialTios = new LibC.termios();
        Global.libc.tcgetattr(LibC.STDIN_FILENO, tios);
        Global.libc.tcgetattr(LibC.STDIN_FILENO, initialTios);
        tios.c_lflag &= ~(LibC.ICANON | LibC.ECHO | LibC.ECHONL);
        Global.libc.tcsetattr(LibC.STDIN_FILENO, LibC.TCSANOW, tios);

        for(int i = 0; i < Global.cols; i++) {
            for(int j = 0; j < Global.rows; j++) {
                renderData[i][j] = new RenderableCharacter(' ', 15, 0);
            }
        }

        LibC.winsize ws = new LibC.winsize();
        Global.libc.ioctl(LibC.STDIN_FILENO, LibC.TIOCGWINSZ, ws);
        terminalSize = ws;

        if(terminalSize.ws_col < Global.cols && terminalSize.ws_row < Global.rows) {
            disableAlternateScreen();
            restoreTios();
            System.err.println("Terminal too small! Must be at least (" + Global.cols + ", " + Global.rows + ")");
            System.exit(-1);
        }
    }

    public final LibC.winsize getTerminalSize() {
        return terminalSize;
    }

    public void restoreTios() {
        Global.libc.tcsetattr(LibC.STDIN_FILENO, LibC.TCSANOW, initialTios);
    }

    private boolean[] keyMap = new boolean[257];

    /**
     * Reads all of the keypresses from stdin and stores them. This should be called every frame.
     * To retrieve these keypresses use keyIsPressed(char key)
     * @see public void keyIsPressed(char key)
     */
    public void updateKeyPresses() {
        Arrays.fill(keyMap, false);

        IntByReference pLen = new IntByReference();
        pLen.setValue(0);

        int ioctlRet = Global.libc.ioctl(LibC.STDIN_FILENO, LibC.FIONREAD, pLen); // TODO: make a function that checks if we are on mac. If so, make the value LibC.TIOCOUTQ instead of LibC.FIONREAD
        if(ioctlRet < 0) {
            System.err.println("IOCTL error!");
            return;
        }
        if(pLen.getValue() <= 0) {
            return;
        }

        byte[] buf = new byte[pLen.getValue()];
        Global.libc.read(LibC.STDIN_FILENO, buf, pLen.getValue());

        for(byte b : buf) {
            if((int)b > 0 && (int)b < 256) {
                keyMap[b] = true;
            }
        }
    }
    private static final String CSI = "\033[";

    /**
     * Check if a key is pressed or not
     * @param c the key to check for
     * @return true if the key is pressed
     */
    public boolean keyIsPressed(int c) {
        if(c < 0 || c > 256) {
            System.out.println("keyIsPressed: c must be in the range [0, 256]");
            return false;
        }
        return keyMap[c];
    }

    /**
     * Uses an xterm standardized escape code to enable the alternate buffer. This means that the terminal
     * will switch to a black screen for us to render upon and once we exit, the user's original screen will be preserved.
     * To exit this, call disableAlternateScreen()
     * @see public void disableAlternateScreen()
     */
    public void initAlternateScreen() {
        System.out.print(CSI + "?1049h");
        internalMoveCursor(1, 1);
    }

    /**
     * Take the terminal out of the alternate screen buffer.
     * This will restore the user's previous screen.
     * @see public void initAlternateScreen()
     */
    public void disableAlternateScreen() {
        System.out.print(CSI + "?1049l");
    }

    // Note: 1, 1 is the top left corner of the screen
    private void internalMoveCursor(int row, int col) {
	    System.out.print(CSI + String.valueOf(row) + ";" + String.valueOf(col) + "H");
    }

    /**
     * Clears the screen
     */
    public void clear() {
        internalMoveCursor(1, 1);
        System.out.print(CSI + "2J");
    }

    private static class RenderableCharacter {
        public char character;
        public int fgColor;
        public int bgColor;


        public RenderableCharacter(char c, int fg, int bg) {
            character = c;
            this.fgColor = fg;
            this.bgColor = bg;
        }
    }

    RenderableCharacter[][] renderData = new RenderableCharacter[Global.cols][Global.rows];

    /**
     * Begin rendering. This will clear the renderbuffer
     */
    public void begin() {
        // clear render data
        for(int i = 0; i < Global.cols; i++) {
            for(int j = 0; j < Global.rows; j++) {
                renderData[i][j].character = ' ';
            }
        }
    }

    /**
     * Add a character to the renderbuffer. Be sure to call begin before calling this function and end after all rendering
     * is complete
     * @param col the column to insert the character at
     * @param row the row to insert the character at
     * @param c the character to insert
     * @see public void begin()
     * @see public void end()
     */
    public void putChar(int col, int row, char c) {
        renderData[col][row].character = c;
    }

    /**
     * Add a character to the renderbuffer. Be sure to call begin before calling this function and end after all rendering
     * is complete
     * @param col the column to insert the character at
     * @param row the row to insert the character at
     * @param c the character to insert
     * @param fg the color the character should be
     * @param bg the background color of the character
     */
    public void putChar(int col, int row, char c, int fg, int bg) {
        renderData[col][row].fgColor = fg;
        renderData[col][row].bgColor = bg;

        putChar(col, row, c);
    }

    private void setForegroundColor(int color) {
        System.out.print(CSI + "38;5;" + String.valueOf(color) + "m");
    }

    private void setBackgroundColor(int color) {
        System.out.print(CSI + "48;5;" + String.valueOf(color) + "m");
    }

    /**
     * Flush the renderBuffer to stdout
     */
    public void end() {
        int currentFg = 15;
        int currentBg = 0;
        for(int i = 0; i < Global.rows; i++) {
            for (int j = 0; j < Global.cols; j++) {
                if(renderData[j][i].fgColor != currentFg) {
                    currentFg = renderData[j][i].fgColor;
                    setForegroundColor(currentFg);
                }
                if(renderData[j][i].bgColor != currentBg) {
                    currentBg = renderData[j][i].bgColor;
                    setBackgroundColor(currentBg);
                }
                System.out.print(renderData[j][i].character);
            }
            System.out.println();
        }
        setBackgroundColor(0);
        setForegroundColor(15);
    }
}
