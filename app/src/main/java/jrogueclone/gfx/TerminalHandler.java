package jrogueclone.gfx;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import com.sun.jna.NativeLong;
import com.sun.jna.ptr.IntByReference;

import java.util.Arrays;

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

    private boolean[] keyMap = new boolean[257];
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
                keyMap[(int)b] = true;
            }
        }
    }
    public boolean keyIsPressed(int c) {
        if(c < 0 || c > 256) {
            System.out.println("keyIsPressed: c must be in the range [0, 256]");
            return false;
        }
        // System.out.println("query char: " + c);
        return keyMap[c];
    }

    public void initAlternateScreen() {
        System.out.print((char)27 + " [ " + " 1049 " + " h");
    }

    public void disableAlternateScreen() {
        System.out.print((char)27 + " [" + " 1049 " + " l");
    }
}
