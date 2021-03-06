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
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package hudson.plugins.clearcase.history;

import hudson.scm.ChangeLogSet;

import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * @author hlyh
 */
public interface HistoryAction {

    /**
     * Returns if the repository has any changes since the specified time
     * 
     * @param time check for changes since this time
     * @param viewPath The view path name (local path in the workspace)
     * @param viewTag The view tag (unique identifier on CC Server)
     * @param branchNames the branch names
     * @param viewPaths optional vob paths
     * @return true, if the ClearCase repository has changes; false, otherwise.
     */
    public boolean hasChanges(Date time, String viewPath, String viewTag, String[] branchNames, String[] viewPaths) throws IOException, InterruptedException;

    /**
     * Returns if the repository has any changes since the specified time
     * 
     * @param time check for changes since this time
     * @param viewPath The view path name (local path in the workspace)
     * @param viewTag The view tag (unique identifier on CC Server)
     * @param branchNames the branch names
     * @param viewPaths optional vob paths
     * @return List of changes
     */
    public List<ChangeLogSet.Entry> getChanges(Date time, String viewPath, String viewTag, String[] branchNames, String[] viewPaths) throws IOException,
            InterruptedException;

}
