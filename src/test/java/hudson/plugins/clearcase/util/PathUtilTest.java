/**
 * The MIT License
 *
 * Copyright (c) 2007-2009, Sun Microsystems, Inc., Kohsuke Kawaguchi, Erik Ramfelt,
 *                          Henrik Lynggaard, Peter Liljenberg, Andrew Bayer
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package hudson.plugins.clearcase.util;

import hudson.FilePath;
import hudson.Launcher;
import hudson.Proc;
import hudson.remoting.Channel;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

import junit.framework.Assert;

import org.junit.Test;

public class PathUtilTest {
    @Test
    public void testWindows() throws Exception {
        Launcher launcher = new MyLauncher(false);
        String converted = PathUtil.convertPathForOS("C/abc",
                                                     launcher);
        Assert.assertEquals("C\\abc", converted);
        String converted2 = PathUtil.convertPathForOS(
                                                      "\nPeter\n", launcher);
        Assert.assertEquals("\r\nPeter\r\n", converted2);
        String converted3 = PathUtil.convertPathForOS(
                                                      "C\\abc", launcher);
        Assert.assertEquals("C\\abc", converted3);
        String converted4 = PathUtil.convertPathForOS(
                                                      "\r\nPeter\r\n", launcher);
        Assert.assertEquals("\r\nPeter\r\n", converted4);
        String converted5 = PathUtil.convertPathForOS(
                                                      "\nPeter\n", launcher);
        Assert.assertEquals("\r\nPeter\r\n", converted5);
        
        
    }
    
    @Test
    public void testUnix() throws Exception {
        Launcher launcher = new MyLauncher(true);
        String converted = PathUtil.convertPathForOS("C\\abc",
                                                     launcher);
        Assert.assertEquals("C/abc", converted);
        String converted2 = PathUtil.convertPathForOS(
                                                      "\r\nPeter\r\n", launcher);
        Assert.assertEquals("\nPeter\n", converted2);
        String converted3 = PathUtil.convertPathForOS("C/abc",
                                                      launcher);
        Assert.assertEquals("C/abc", converted3);
        String converted4 = PathUtil.convertPathForOS(
                                                      "\nPeter\n", launcher);
        Assert.assertEquals("\nPeter\n", converted4);
    }
    
    private static class MyLauncher extends Launcher {
        
        public MyLauncher(boolean unix) {
            super(null, null);
            this.unix = unix;
        }
        
        private boolean unix;
        
        @Override
        public void kill(Map<String, String> arg0) throws IOException,
                                                          InterruptedException {
            // TODO Auto-generated method stub
            
        }

        @Override
        public Channel launchChannel(String[] arg0, OutputStream arg1,
                                     FilePath arg2, Map<String, String> arg3) throws IOException,
                                                                                     InterruptedException {
            // TODO Auto-generated method stub
            return null;
        }
        
        @Override
        public Proc launch(ProcStarter starter) throws IOException {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean isUnix() {
            return this.unix;
        }
    }
}
