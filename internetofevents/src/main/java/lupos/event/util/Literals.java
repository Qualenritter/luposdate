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
package lupos.event.util;

import java.net.URISyntaxException;

import lupos.datastructures.items.literal.Literal;
import lupos.datastructures.items.literal.LiteralFactory;
import lupos.datastructures.items.literal.URILiteral;

/**
 * Helper class which contains constants for some common literals and methods to create literals.
 *
 * @author groppe
 * @version $Id: $Id
 */
public abstract class Literals {

	/**
	 * Literals from the rdf-namespace.
	 */
	public static class RDF {
		public static final Literal TYPE = createURI(Prefixes.RDF, "type");

		private RDF() {
		}
	}

	/**
	 * Literals from the xsd-namespace.
	 */
	public static class XSD {
		public static final URILiteral INT = createURI(Prefixes.XSD, "integer");
		public static final URILiteral LONG = createURI(Prefixes.XSD, "long");
		public static final URILiteral FLOAT = createURI(Prefixes.XSD, "float");
		public static final URILiteral DECIMAL = createURI(Prefixes.XSD, "decimal");
		public static final URILiteral DOUBLE = createURI(Prefixes.XSD, "double");
		public static final URILiteral String = createURI(Prefixes.XSD, "string");
		public static final URILiteral DURATION = createURI(Prefixes.XSD, "duration");
		public static final URILiteral TIME = createURI(Prefixes.XSD, "time");
		public static final URILiteral DATETIME = createURI(Prefixes.XSD, "dateTime");
		public static final URILiteral ANYURI = createURI(Prefixes.XSD, "anyURI");

		private XSD() {
		}
	}

	/**
	 * one anonymousliteral
	 */
	public static class AnonymousLiteral{
		public static final Literal ANONYMOUS = LiteralFactory.createAnonymousLiteral("_:a");

		private AnonymousLiteral() {
		}
	}

	/**
	 * Creates a URILiteral.
	 *
	 * @param uri a {@link java.lang.String} object.
	 * @return a {@link lupos.datastructures.items.literal.URILiteral} object.
	 */
	public static URILiteral createURI(final String uri) {
		return LiteralFactory.createURILiteralWithoutLazyLiteralWithoutException(uri);
	}

	/**
	 * Creates a URILiteral.
	 *
	 * @param namespace a {@link java.lang.String} object.
	 * @param str a {@link java.lang.String} object.
	 * @return a {@link lupos.datastructures.items.literal.URILiteral} object.
	 */
	public static URILiteral createURI(final String namespace, final String str) {
		final String uri = Utils.createURIString(namespace, str);
		return LiteralFactory.createURILiteralWithoutLazyLiteralWithoutException(uri);
	}

	/**
	 * <p>createTyped.</p>
	 *
	 * @param value a {@link java.lang.String} object.
	 * @param typeLiteral a {@link lupos.datastructures.items.literal.URILiteral} object.
	 * @return a {@link lupos.datastructures.items.literal.Literal} object.
	 * @throws java.net.URISyntaxException if any.
	 */
	public static Literal createTyped(final String value, final URILiteral typeLiteral) throws URISyntaxException {
		return LiteralFactory.createTypedLiteral("\"" + value + "\"", typeLiteral);
	}

	/**
	 * Create a Literal of type Duration, encoding the given duration
	 * values as described in
	 * http://www.w3schools.com/schema/schema_dtypes_date.asp.
	 *
	 * @param years
	 *            Number of years.
	 * @param months
	 *            Number of months.
	 * @param days
	 *            Number of days.
	 * @param hours
	 *            Number of hours.
	 * @param minutes
	 *            Number of minutes.
	 * @param seconds
	 *            Number of seconds.
	 * @return A new Literal of type Duration, containing the respective
	 *         encoding of the specified duration.
	 * @throws java.net.URISyntaxException if any.
	 */
	public static Literal createDurationLiteral(final int years,
			final int months, final int days, final int hours,
			final int minutes, final int seconds) throws URISyntaxException {
		String durationStr = "P";

		if (years > 0) {
			durationStr += years + "Y";
		}

		if (months > 0) {
			durationStr += months + "M";
		}

		if (days > 0) {
			durationStr += days + "D";
		}

		if (hours > 0 || minutes > 0 || seconds > 0) {
			durationStr += "T";

			if (hours > 0) {
				durationStr += hours + "H";
			}

			if (minutes > 0) {
				durationStr += minutes + "M";
			}

			if (seconds > 0) {
				durationStr += seconds + "S";
			}
		}

		if (durationStr.equals("P")) {
			durationStr += "T0S";
		}

		return createTyped(durationStr, Literals.XSD.DURATION);
	}
}
