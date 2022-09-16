package jrogueclone;

import jrogueclone.gfx.TerminalHandler;
import com.sun.jna.Native;

public class Global {

    // Handles input and renders game view
    public static final LibC libc = (LibC) Native.loadLibrary("c", LibC.class);
    public static final TerminalHandler terminalHandler = new TerminalHandler();
}
