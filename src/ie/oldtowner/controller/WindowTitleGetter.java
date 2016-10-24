package ie.oldtowner.controller;

import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.ptr.PointerByReference;

import static ie.oldtowner.controller.WindowTitleGetter.User32DLL.GetForegroundWindow;
import static ie.oldtowner.controller.WindowTitleGetter.User32DLL.GetWindowTextW;

/**
 * Created by walshejo on 24/10/2016.
 */
public class WindowTitleGetter {
    private static final int MAX_TITLE_LENGTH = 1024;
    char[] buffer;

    String currentWindow = "init";
    //String newWindow = null;

    public String getCurrentWindowTitle(){
        buffer = new char[MAX_TITLE_LENGTH * 2];
        GetWindowTextW(GetForegroundWindow(), buffer, MAX_TITLE_LENGTH);
        return Native.toString(buffer);
    }
    static class Psapi {
        static { Native.register("psapi"); }
        public static native int GetModuleBaseNameW(Pointer hProcess, Pointer hmodule, char[] lpBaseName, int size);
    }
/*
    static class Kernel32 {
        static { Native.register("kernel32"); }
        public static int PROCESS_QUERY_INFORMATION = 0x0400;
        public static int PROCESS_VM_READ = 0x0010;
        public static native int GetLastError();
        public static native Pointer OpenProcess(int dwDesiredAccess, boolean bInheritHandle, Pointer pointer);
    }*/

    static class User32DLL {
        static { Native.register("user32"); }
        public static native int GetWindowThreadProcessId(WinDef.HWND hWnd, PointerByReference pref);
        public static native WinDef.HWND GetForegroundWindow();
        public static native int GetWindowTextW(WinDef.HWND hWnd, char[] lpString, int nMaxCount);
    }
}
