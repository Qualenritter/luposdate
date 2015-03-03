
/**
 * Copyright (c) 2007-2015, Institute of Information Systems (Sven Groppe and contributors of LUPOSDATE), University of Luebeck
 *
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification, are permitted provided that the
 * following conditions are met:
 *
 * 	- Redistributions of source code must retain the above copyright notice, this list of conditions and the following
 * 	  disclaimer.
 * 	- Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the
 * 	  following disclaimer in the documentation and/or other materials provided with the distribution.
 * 	- Neither the name of the University of Luebeck nor the names of its contributors may be used to endorse or promote
 * 	  products derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES,
 * INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT,
 * INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE
 * GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY
 * OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 * @author groppe
 * @version $Id: $Id
 */
package lupos.datastructures.buffermanager;

import java.io.IOException;
import java.io.InputStream;
public class ContinousPagesInputStream extends InputStream {

	protected final PageManager pageManager;

	protected byte[] currentPage;
	protected int index;
	protected int currentPageNumber;

	/**
	 * <p>Constructor for ContinousPagesInputStream.</p>
	 *
	 * @param pagenumber a int.
	 * @param pageManager a {@link lupos.datastructures.buffermanager.PageManager} object.
	 * @throws java.io.IOException if any.
	 */
	public ContinousPagesInputStream(final int pagenumber, final PageManager pageManager) throws IOException {
		this(pagenumber, pageManager, 0);
	}


	/**
	 * <p>Constructor for ContinousPagesInputStream.</p>
	 *
	 * @param pagenumber a int.
	 * @param pageManager a {@link lupos.datastructures.buffermanager.PageManager} object.
	 * @param index a int.
	 * @throws java.io.IOException if any.
	 */
	public ContinousPagesInputStream(final int pagenumber, final PageManager pageManager, final int index) throws IOException {
		this.currentPageNumber = pagenumber;
		this.pageManager = pageManager;
		this.currentPage = pageManager.getPage(pagenumber);
		this.index = index;
	}

	/** {@inheritDoc} */
	@Override
	public int read() throws IOException {
		if (this.index >= this.currentPage.length) {
			this.currentPageNumber++;
			this.currentPage = this.pageManager.getPage(this.currentPageNumber);
			this.index = 0;
		}
		return (0xFF & this.currentPage[this.index++]);
	}


	/**
	 * <p>Getter for the field <code>index</code>.</p>
	 *
	 * @return a int.
	 */
	public int getIndex() {
		return this.index;
	}

	/**
	 * <p>Getter for the field <code>currentPageNumber</code>.</p>
	 *
	 * @return a int.
	 */
	public int getCurrentPageNumber() {
		return this.currentPageNumber;
	}
}
