package com.sun.jna.platform.linux;

import com.sun.jna.Memory;
import com.sun.jna.Native;
import com.sun.jna.platform.linux.XAttr.size_t;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;

public class XAttrTest {
    private static final Logger log = LoggerFactory.getLogger(XAttrTest.class);

    private static final String TEST_STRING = "Žluťoučký kůň úpěl nebo tak něco.";

    @Test
    public void setxattr() throws IOException {
        File file = File.createTempFile("xattr", "test");
        file.deleteOnExit();
        log.info("Test file is {}", file.getAbsolutePath());

        final byte[] xattrValue = Native.toByteArray(TEST_STRING, "UTF-8");
        Memory xattrValueMem = new Memory(xattrValue.length);
        xattrValueMem.write(0, xattrValue, 0, xattrValue.length);
        int retval = XAttr.INSTANCE.setxattr(file.getAbsolutePath(), "user.testattr",
            xattrValueMem, new size_t(xattrValueMem.size()), 0);
        int errno = Native.getLastError();
        log.info("Return value of setxattr() is {}", retval);
        if (retval != 0) {
            log.info("Errno is {}", errno);
        }
    }
}