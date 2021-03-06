/*
 * Copyright (c) 2003, 2015, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Oracle, 500 Oracle Parkway, Redwood Shores, CA 94065 USA
 * or visit www.oracle.com if you need additional information or have any
 * questions.
 */

/*
 * @test
 * @bug 4638723 8015882
 * @summary Test to ensure that the refactored version of the standard
 * doclet still works with Taglets that implement the 1.4.0 interface.
 * @author jamieh
 * @library ../lib
 * @modules jdk.javadoc
 * @build JavadocTester ToDoTaglet UnderlineTaglet Check
 * @run main TestLegacyTaglet
 */

public class TestLegacyTaglet extends JavadocTester {

    public static void main(String... args) throws Exception {
        TestLegacyTaglet tester = new TestLegacyTaglet();
        tester.runTests();
    }

    @Test
    void test() {
        javadoc("-d", "out",
                "-sourcepath", testSrc,
                "-tagletpath", System.getProperty("test.classes", "."),
                "-taglet", "ToDoTaglet",
                "-taglet", "Check",
                "-taglet", "UnderlineTaglet",
                testSrc("C.java"));
        checkExit(Exit.OK);
        checkOutput("C.html", true,
                "This is an <u>underline</u>",
                "<DT><B>To Do:</B><DD><table summary=\"Summary\" cellpadding=2 cellspacing=0><tr>" +
                "<td bgcolor=\"yellow\">Finish this class.</td></tr></table></DD>",
                "<DT><B>To Do:</B><DD><table summary=\"Summary\" cellpadding=2 cellspacing=0><tr>" +
                "<td bgcolor=\"yellow\">Tag in Method.</td></tr></table></DD>");
        checkOutput(Output.STDERR, false,
                "NullPointerException");
    }
}
