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
/**
 *  Represents the entire RSS feed, containing (here: producing) several FeedMessages
 *
 * @author groppe
 * @version $Id: $Id
 */

package lupos.event.producer.rsssemantics;

import java.util.ArrayList;
import java.util.List;
public class Feed {

	final String title;
	final String link;
	final String description;
	final String language;
	final String copyright;
	final String pubDate;
	final List<FeedMessage> entries = new ArrayList<FeedMessage>();

	/**
	 * <p>Constructor for Feed.</p>
	 *
	 * @param title a {@link java.lang.String} object.
	 * @param link a {@link java.lang.String} object.
	 * @param description a {@link java.lang.String} object.
	 * @param language a {@link java.lang.String} object.
	 * @param copyright a {@link java.lang.String} object.
	 * @param pubDate a {@link java.lang.String} object.
	 */
	public Feed(String title, String link, String description, String language,
			String copyright, String pubDate) {
		this.title = title;
		this.link = link;
		this.description = description;
		this.language = language;
		this.copyright = copyright;
		this.pubDate = pubDate;
	}

	/**
	 * <p>getMessages.</p>
	 *
	 * @return all FeedMessages from feed as ArrayList
	 */
	public List<FeedMessage> getMessages() {
		return this.entries;
	}

	/**
	 * <p>getLastMessage.</p>
	 *
	 * @return FeedMessage from feed that was last added
	 */
	public FeedMessage getLastMessage() {
		return this.entries.get(this.entries.size() - 1);
	}

	/**
	 * <p>Getter for the field <code>title</code>.</p>
	 *
	 * @return title
	 */
	public String getTitle() {
		return this.title;
	}

	/**
	 * <p>Getter for the field <code>link</code>.</p>
	 *
	 * @return link
	 */
	public String getLink() {
		return this.link;
	}

	/**
	 * <p>Getter for the field <code>description</code>.</p>
	 *
	 * @return description
	 */
	public String getDescription() {
		return this.description;
	}

	/**
	 * <p>Getter for the field <code>language</code>.</p>
	 *
	 * @return language
	 */
	public String getLanguage() {
		return this.language;
	}

	/**
	 * <p>Getter for the field <code>copyright</code>.</p>
	 *
	 * @return copyright
	 */
	public String getCopyright() {
		return this.copyright;
	}

	/**
	 * <p>Getter for the field <code>pubDate</code>.</p>
	 *
	 * @return pubDate
	 */
	public String getPubDate() {
		return this.pubDate;
	}

	/**
	 * {@inheritDoc}
	 *
	 * Convert to String
	 */
	@Override
	public String toString() {
		return "Feed [copyright=" + this.copyright + ", description=" + this.description
				+ ", language=" + this.language + ", link=" + this.link + ", pubDate="
				+ this.pubDate + ", title=" + this.title + "]";
	}
}
