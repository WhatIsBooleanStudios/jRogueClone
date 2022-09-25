package jrogueclone.gfx;

import com.sun.jna.ptr.IntByReference;

import java.util.Arrays;

import jrogueclone.Global;
import jrogueclone.LibC;

public class TerminalHandler {
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

        for (int i = 0; i < Global.columns; i++) {
            for (int j = 0; j < Global.rows; j++) {
                renderData[i][j] = new RenderableCharacter(' ', 15, 0, false, null);
            }
        }

        LibC.winsize ws = new LibC.winsize();
        Global.libc.ioctl(LibC.STDIN_FILENO, LibC.TIOCGWINSZ, ws);
        terminalSize = ws;

        if (terminalSize.ws_col < Global.columns && terminalSize.ws_row < Global.rows) {
            restoreState();
            System.err.println("Terminal too small! Must be at least (" + Global.columns + ", " + Global.rows + ")");
            System.exit(-1);
        }
    }

    /**
     * Get the terminal size
     * 
     * @return the size of the terminal
     */
    public final LibC.winsize getTerminalSize() {
        return terminalSize;
    }

    /**
     * Restore the low-level termios state that the user had before we messed with
     * it
     */
    public void restoreTios() {
        Global.libc.tcsetattr(LibC.STDIN_FILENO, LibC.TCSANOW, initialTios);
    }

    private final boolean[] keyMap = new boolean[257];

    /**
     * Reads all the keypresses from stdin and stores them. This should be called
     * every frame.
     * To retrieve these keypresses use keyIsPressed(char key)
     * 
     * @see public void keyIsPressed(char key)
     */
    public void updateKeyPresses() {
        Arrays.fill(keyMap, false);

        IntByReference pLen = new IntByReference();
        pLen.setValue(0);

        int ioctlRet = Global.libc.ioctl(LibC.STDIN_FILENO, LibC.FIONREAD, pLen);
        if (ioctlRet < 0) {
            System.err.println("IOCTL error!");
            return;
        }
        if (pLen.getValue() <= 0) {
            return;
        }

        byte[] buf = new byte[pLen.getValue()];
        Global.libc.read(LibC.STDIN_FILENO, buf, pLen.getValue());

        for (byte b : buf) {
            if ((int) b > 0) {
                keyMap[b] = true;
            }
        }
    }

    public static final String CSI = "\033[";

    /**
     * Check if a key is pressed or not
     * 
     * @param c the key to check for
     * @return true if the key is pressed
     */
    public boolean keyIsPressed(int c) {
        if (c < 0 || c > 256) {
            System.out.println("keyIsPressed: c must be in the range [0, 256]");
            return false;
        }
        return keyMap[c];
    }

    /**
     * Uses an xterm standardized escape code to enable the alternate buffer. This
     * means that the terminal
     * will switch to a black screen for us to render upon and once we exit, the
     * user's original screen will be preserved.
     * To exit this, call disableAlternateScreen()
     * 
     * @see public void disableAlternateScreen()
     */
    public void initAlternateScreen() {
        System.out.print(CSI + "?1049h");
        internalMoveCursor(1, 1);
    }

    /**
     * Take the terminal out of the alternate screen buffer.
     * This will restore the user's previous screen.
     * 
     * @see public void initAlternateScreen()
     */
    public void disableAlternateScreen() {
        System.out.print(CSI + "?1049l");
    }

    // Note: 1, 1 is the top left corner of the screen
    private void internalMoveCursor(int row, int col) {
        System.out.print(CSI + row + ";" + col + "H");
    }

    /**
     * Hides the terminal text cursor
     */
    public void hideCursor() {
        System.out.print(CSI + "?25l");
    }

    /**
     * Shows the terminal text cursor
     */
    public void showCursor() {
        System.out.print(CSI + "?25h");
    }

    /**
     * Set the cursor to the top left corner
     */
    public void resetCursor() {
        internalMoveCursor(1, 1);
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
        public boolean bold;
        public Object userData;

        public RenderableCharacter(char c, int fg, int bg, boolean bold, Object userData) {
            character = c;
            this.fgColor = fg;
            this.bgColor = bg;
            this.bold = bold;
            this.userData = userData;
        }
    }

    RenderableCharacter[][] renderData = new RenderableCharacter[Global.columns][Global.rows];

    /**
     * Begin rendering. This will clear the renderbuffer
     */
    public void begin() {
        // clear render data
        for (int i = 0; i < Global.columns; i++) {
            for (int j = 0; j < Global.rows; j++) {
                renderData[i][j].character = ' ';
                renderData[i][j].fgColor = 232;
                renderData[i][j].bgColor = 232;
                renderData[i][j].bold = false;
                renderData[i][j].userData = null;
            }
        }
    }

    /**
     * Add a character to the renderbuffer. Be sure to call begin before calling
     * this function and end after all rendering
     * is complete
     * 
     * @param col the column to insert the character at
     * @param row the row to insert the character at
     * @param c   the character to insert
     * @see public void begin()
     * @see public void end()
     */
    public void putChar(int col, int row, char c) {
        putChar(col, row, c, 255, 232, false);
    }

    /**
     * Add a character to the renderbuffer. Be sure to call begin before calling
     * this function and end after all rendering
     * is complete
     * 
     * @param col the column to insert the character at
     * @param row the row to insert the character at
     * @param c   the character to insert
     * @param fg  the color the character should be
     * @param bg  the background color of the character
     */
    public void putChar(int col, int row, char c, int fg, int bg, boolean bold) {
        putChar(col, row, c, fg, bg, bold, null);
    }

    /**
     * Add a character to the renderbuffer. Be sure to call begin before calling
     * this function and end after all rendering
     * is complete
     * 
     * @param col      the column to insert the character at
     * @param row      the row to insert the character at
     * @param c        the character to insert
     * @param fg       the color the character should be
     * @param bg       the background color of the character
     * @param userData user specified data that can be retrieved from the
     *                 renderbuffer by the user
     */
    public void putChar(int col, int row, char c, int fg, int bg, boolean bold, Object userData) {
        renderData[col][row].userData = userData;
        renderData[col][row].fgColor = fg;
        renderData[col][row].bgColor = bg;
        renderData[col][row].bold = bold;
        renderData[col][row].character = c;
    }

    private void setForegroundColor(int color) {
        System.out.print(CSI + "38;5;" + color + "m");
    }

    private void setBackgroundColor(int color) {
        System.out.print(CSI + "48;5;" + color + "m");
    }

    private void enableBold() {
        System.out.print(CSI + "1m");
    }

    private void disableBold() {
        System.out.print(CSI + "22m");
    }

    /**
     * Get the current character at the given position in the renderbuffer
     * 
     * @param col the column of the target character
     * @param row the row of the target character
     * @return the char that occupies the given position
     */
    public int getCurrentCharacterAt(int col, int row) {
        return renderData[col][row].character;
    }

    /**
     * Get the forground color at the given position in the renderbuffer
     * 
     * @param col the column of the target character
     * @param row the row of the target character
     * @return the forground color of the character that occupies the given position
     */
    public int getForgroundColorAt(int col, int row) {
        return renderData[col][row].fgColor;
    }

    /**
     * Get the background color at the given position in the renderbuffer
     * 
     * @param col the column of the target character
     * @param row the row of the target character
     * @return the background color of the character that occupies the given
     *         position
     */
    public int getBackgroundColorAt(int col, int row) {
        return renderData[col][row].bgColor;
    }

    /**
     * Get the user specified data at the given position in the renderbuffer
     * 
     * @param col the column of the target character
     * @param row the row of the target character
     * @return the user data at the given position
     */
    public Object getUserDataAt(int col, int row) {
        return renderData[col][row].userData;
    }

    /**
     * Flush the renderBuffer to stdout
     */
    public void end() {
        int currentFg = Integer.MAX_VALUE;
        int currentBg = Integer.MAX_VALUE;
        boolean boldState = false;
        for (int i = 0; i < Global.rows; i++) {
            for (int j = 0; j < Global.columns; j++) {
                if (renderData[j][i].fgColor != currentFg) {
                    currentFg = renderData[j][i].fgColor;
                    setForegroundColor(currentFg);
                    System.out.flush();
                }
                if (renderData[j][i].bgColor != currentBg) {
                    currentBg = renderData[j][i].bgColor;
                    setBackgroundColor(currentBg);
                    System.out.flush();
                }
                if (renderData[j][i].bold != boldState) {
                    boldState = renderData[j][i].bold;
                    if (boldState) {
                        enableBold();
                    } else {
                        disableBold();
                    }
                }
                System.out.print(renderData[j][i].character);
            }
            System.out.print('\n');
        }
        System.out.flush();
        resetTextAttributes();
    }

    /**
     * Reset terminal state for our users to use after this program is done messing
     * with it
     */
    public void restoreState() {
        disableAlternateScreen();
        restoreTios();
        resetTextAttributes();
        showCursor();
    }

    /**
     * Set text attributes (color, bold) back to normal
     */
    public void resetTextAttributes() {
        System.out.print(CSI + "0m");
    }
}
