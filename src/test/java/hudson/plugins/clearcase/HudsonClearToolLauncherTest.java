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
package hudson.plugins.clearcase;

import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;
import hudson.FilePath;
import hudson.Launcher;
import hudson.Proc;
import hudson.model.BuildListener;
import hudson.model.TaskListener;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

public class HudsonClearToolLauncherTest extends AbstractWorkspaceTest {

    @Mock
    private BuildListener taskListener;
    @Mock
    private Launcher      launcher;
    @Mock
    private Proc          proc;

    @Before
    public void setUp() throws Exception {
        createWorkspace();
    }

    @After
    public void teardown() throws Exception {
        deleteWorkspace();
    }

    @Test
    public void testClearToolLauncherImplWithNullStreams() throws Exception {
        final PrintStream mockedStream = new PrintStream(new ByteArrayOutputStream());
        
        when(taskListener.getLogger()).thenReturn(mockedStream);

        ClearToolLauncher launcherImpl = new HudsonClearToolLauncherDummy("exec", "ccscm", taskListener, workspace, launcher);
        launcherImpl.run(new String[] { "a" }, null, null, null);
        
        verify(taskListener).getLogger();

        
    }

    @Test
    public void testClearToolLauncherImplWithOutput() throws Exception {
        final PrintStream mockedStream = new PrintStream(new ByteArrayOutputStream());
        
        when(taskListener.getLogger()).thenReturn(mockedStream);

        ClearToolLauncher launcherImpl = new HudsonClearToolLauncherDummy("exec", "ccscm", taskListener, workspace, launcher);
        launcherImpl.run(new String[] { "a" }, null, new ByteArrayOutputStream(), null);
        
        verify(taskListener).getLogger();
    }

    @Test(expected = IOException.class)
    public void testBadReturnCode() throws Exception {
        final PrintStream mockedStream = new PrintStream(new ByteArrayOutputStream());

        when(taskListener.getLogger()).thenReturn(mockedStream);
        when(proc.join()).thenReturn(Integer.valueOf(1));
        
        ClearToolLauncher launcherImpl = new HudsonClearToolLauncherDummy("exec", "ccscm", taskListener, workspace, launcher);
        launcherImpl.run(new String[] { "a", "b" }, null, new ByteArrayOutputStream(), null);
        
        verify(taskListener).getLogger();
        verify(taskListener).fatalError(anyString());
        verify(proc).join();
    }

    /**
     * Assert that the Hudson cleartool launcher adds the clear tool exectuable to the command array.
     */
    @Test
    public void assertClearToolExecutableIsSet() throws Exception {
        final PrintStream mockedStream = new PrintStream(new ByteArrayOutputStream());

        when(taskListener.getLogger()).thenReturn(mockedStream);

        ClearToolLauncher launcherImpl = new HudsonClearToolLauncherDummy("exec", "ccscm", taskListener, workspace, launcher);
        launcherImpl.run(new String[] { "command" }, null, null, null);
    }

    public class HudsonClearToolLauncherDummy extends HudsonClearToolLauncher {
        public HudsonClearToolLauncherDummy(String executable, String scmName, TaskListener listener, FilePath workspace, Launcher launcher) {
            super(executable, scmName, listener, workspace, launcher);
        }

        @Override
        public Proc getLaunchedProc(String[] cmdWithExec, String[] env, InputStream inputStream, OutputStream out, FilePath path) throws IOException {
            return proc;
        }
    }
}