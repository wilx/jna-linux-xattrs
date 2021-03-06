package com.sun.jna.platform.linux;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.Collection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class XAttrUtilTest {
    private static final Logger log = LoggerFactory.getLogger(XAttrTest.class);

    private static final String TEST_STRING = "Žluťoučký kůň úpěl nebo tak něco.";
    private static final String TEST_STRING_2 = "Příliš žluťoučký kůň úpěl ďábelské ódy.";
    private static final String TEST_ATTRIBUTE = "user.test";
    private static final String TEST_ATTRIBUTE_FOO = TEST_ATTRIBUTE + ".foo";

    @Test
    public void setXAttr() throws IOException {
        File file = File.createTempFile("xattr", "test");
        file.deleteOnExit();
        log.info("Test file is {}", file.getAbsolutePath());

        XAttrUtil.setXAttr(file.getAbsolutePath(), TEST_ATTRIBUTE, TEST_STRING);
        XAttrUtil.setXAttr(file.getAbsolutePath(), TEST_ATTRIBUTE_FOO, TEST_STRING_2);

        String retrievedValue = XAttrUtil.getXAttr(file.getAbsolutePath(), TEST_ATTRIBUTE);
        assertEquals(TEST_STRING, retrievedValue);

        retrievedValue = XAttrUtil.getXAttr(file.getAbsolutePath(), TEST_ATTRIBUTE_FOO);
        assertEquals(TEST_STRING_2, retrievedValue);

        XAttrUtil.setXAttr(file.getAbsolutePath(), TEST_ATTRIBUTE, TEST_STRING_2);
        retrievedValue = XAttrUtil.lGetXAttr(file.getAbsolutePath(), TEST_ATTRIBUTE);
        assertEquals(TEST_STRING_2, retrievedValue);

        Collection<String> xattrs = XAttrUtil.listXAttr(file.getAbsolutePath());
        log.info("Extended attributes on {}: {}", file.getAbsolutePath(), xattrs);
        assertTrue(xattrs.contains(TEST_ATTRIBUTE));
        assertTrue(xattrs.contains(TEST_ATTRIBUTE_FOO));

        XAttrUtil.removeXAttr(file.getAbsolutePath(), TEST_ATTRIBUTE);
        xattrs = XAttrUtil.lListXAttr(file.getAbsolutePath());
        assertFalse(xattrs.contains(TEST_ATTRIBUTE));
        assertTrue(xattrs.contains(TEST_ATTRIBUTE_FOO));
    }
}