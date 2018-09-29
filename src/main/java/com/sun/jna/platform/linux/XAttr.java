package com.sun.jna.platform.linux;

import com.sun.jna.IntegerType;
import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.Pointer;

public interface XAttr extends Library {
    XAttr INSTANCE = Native.loadLibrary(null, XAttr.class);

    class size_t extends IntegerType {
        public static final size_t ZERO = new size_t();

        private static final long serialVersionUID = 1L;

        public size_t() { this(0); }
        public size_t(long value) { super(Native.SIZE_T_SIZE, value, true); }
    }

    class ssize_t extends IntegerType {
        public static final ssize_t ZERO = new ssize_t();

        private static final long serialVersionUID = 1L;

        public ssize_t() {
            this(0);
        }

        public ssize_t(long value) {
            super(Native.SIZE_T_SIZE, value, false);
        }
    }

    int XATTR_CREATE = 1;
    int XATTR_REPLACE = 2;

    int EPERM = 1;
    int E2BIG = 7;
    int EEXIST = 17;
    int ENOSPC = 28;
    int ERANGE = 34;
    int ENODATA = 61;
    int ENOATTR = ENODATA;
    int ENOTSUP = 95;
    int EDQUOT = 122;

    int setxattr(String path, String name, Pointer value, size_t size, int flags);
    int lsetxattr(String path, String name, Pointer value, size_t size, int flags);
    int fsetxattr(int fd, String name, Pointer value, size_t size, int flags);

    ssize_t getxattr(String path, String name, Pointer value, size_t size);
    ssize_t lgetxattr(String path, String name, Pointer value, size_t size);
    ssize_t fgetxattr(int fd, String name, Pointer value, size_t size);

    ssize_t listxattr(String path, Pointer list, size_t size);
    ssize_t llistxattr(String path, Pointer list, size_t size);
    ssize_t flistxattr(int fd, Pointer list, size_t size);

    int removexattr(String path, String name);
    int lremovexattr(String path, String name);
    int fremovexattr(int fd, String name);
}
