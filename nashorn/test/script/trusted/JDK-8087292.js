/*
 * Copyright (c) 2015, Oracle and/or its affiliates. All rights reserved.
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

/**
 * JDK-8087292: nashorn should have a "fail-fast" option for scripting, analog to bash "set -e"
 *
 * @test
 * @option -scripting
 * @run
 */

function tryExec() {
    try {
        `java`
    } catch (e) {
        print(e);
    }

    // make sure we got non-zero ("failure") exit code!
    if ($EXIT == 0) {
        print("Error: expected $EXIT code to be non-zero");
    }
}

// no exception now!
tryExec();

// turn on error with non-zero exit code
$ENV.JJS_THROW_ON_EXIT = "1";
tryExec();

// no exception after this
$ENV.JJS_THROW_ON_EXIT = "0";
tryExec();
