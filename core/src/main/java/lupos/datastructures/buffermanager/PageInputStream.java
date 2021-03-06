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
 */
package lupos.datastructures.buffermanager;

import java.io.IOException;
public class PageInputStream extends ContinousPagesInputStream {

	/** Constant <code>DEFAULTSTARTINDEX=6</code> */
	public final static int DEFAULTSTARTINDEX = 6;

	protected int maxOnThisPage;

	/**
	 * <p>Constructor for PageInputStream.</p>
	 *
	 * @param pagenumber a int.
	 * @param pageManager a {@link lupos.datastructures.buffermanager.PageManager} object.
	 * @throws java.io.IOException if any.
	 */
	public PageInputStream(final int pagenumber, final PageManager pageManager) throws IOException {
		this(pagenumber, pageManager, PageInputStream.DEFAULTSTARTINDEX);
	}


	/**
	 * <p>Constructor for PageInputStream.</p>
	 *
	 * @param pagenumber a int.
	 * @param pageManager a {@link lupos.datastructures.buffermanager.PageManager} object.
	 * @param index a int.
	 * @throws java.io.IOException if any.
	 */
	public PageInputStream(final int pagenumber, final PageManager pageManager, final int index) throws IOException {
		super(pagenumber, pageManager, index);
		this.setMaxOnThisPage();
	}

	private final void setMaxOnThisPage() {
		this.maxOnThisPage = ((0xFF & this.currentPage[4]) << 8) | (0xFF & this.currentPage[5]);
	}

	/** {@inheritDoc} */
	@Override
	public int read() throws IOException {
		if (this.index >= this.maxOnThisPage) {
			final int nextPage = (((0xFF & this.currentPage[0]) << 8 | (0xFF & this.currentPage[1])) << 8 | (0xFF & this.currentPage[2])) << 8 | (0xFF & this.currentPage[3]);
			if (nextPage == 0) {
				return -1;
			}
			this.currentPageNumber = nextPage;
			this.currentPage = this.pageManager.getPage(nextPage);
			this.index = PageInputStream.DEFAULTSTARTINDEX;
			this.setMaxOnThisPage();
		}
		return (0xFF & this.currentPage[this.index++]);
	}
}
