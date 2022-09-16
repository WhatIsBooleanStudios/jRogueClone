package jrogueclone.gfx;

import java.io.IOException;

import jrogueclone.Global;
import jrogueclone.LibC;

public class TerminalHandler {
    public static final int STDIN_FILENO = 0;

    LibC.termios tios;

    public TerminalHandler() {
        tios = new LibC.termios();
        Global.libc.tcgetattr(LibC.STDIN_FILENO, tios);
        tios.c_lflag &= ~(LibC.ICANON | LibC.ECHO | LibC.ECHONL);
        Global.libc.tcsetattr(LibC.STDIN_FILENO, LibC.TCSANOW, tios);
    }

    public int read() {
        return Global.libc.getchar();
    }
}
