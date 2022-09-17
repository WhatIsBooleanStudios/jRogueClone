package jrogueclone;

import jrogueclone.gfx.TerminalHandler;
import com.sun.jna.Native;

public class Global {

    // Loads the native c library for use by the terminal handler
    public static final LibC libc = (LibC) Native.loadLibrary("c", LibC.class);
    // Handles input and renders game view
    public static final TerminalHandler terminalHandler = new TerminalHandler();

    public static final int rows = 20;
    public static final int cols = 60;
}
