
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
package lupos.engine.operators.stream;

import java.util.LinkedList;

import lupos.datastructures.items.TimestampedTriple;
import lupos.datastructures.items.Triple;
import lupos.engine.operators.messages.Message;
import lupos.engine.operators.messages.StartOfEvaluationMessage;
import lupos.misc.debug.DebugStep;
public class WindowDuration extends Window {

	private final int duration;
	private LinkedList<TimestampedTriple> tripleList;

	/**
	 * <p>Constructor for WindowDuration.</p>
	 *
	 * @param duration a int.
	 */
	public WindowDuration(final int duration) {
		this.duration = duration;
	}

	/** {@inheritDoc} */
	@Override
	public Message preProcessMessage(final StartOfEvaluationMessage message) {
		this.tripleList = new LinkedList<TimestampedTriple>();
		return message;
	}

	/** {@inheritDoc} */
	@Override
	public void consume(final Triple triple) {
		final TimestampedTriple timestampedTriple = (TimestampedTriple) triple;
		final long now = timestampedTriple.getTimestamp();
		int index = 0;
		for (final TimestampedTriple t : this.tripleList) {
			if (now - t.getTimestamp() >= this.duration) {
				this.deleteTriple(t);
				index++;
			} else
				break;
		}
		while (index > 0) {
			this.tripleList.remove(0);
			index--;
		}
		this.tripleList.add(timestampedTriple);
		super.consume(timestampedTriple);
	}
	
	/** {@inheritDoc} */
	@Override
	public void consumeDebug(final Triple triple, final DebugStep debugstep) {
		final TimestampedTriple timestampedTriple = (TimestampedTriple) triple;
		final long now = timestampedTriple.getTimestamp();
		int index = 0;
		for (final TimestampedTriple t : this.tripleList) {
			if (now - t.getTimestamp() >= this.duration) {
				this.deleteTripleDebug(t, debugstep);
				index++;
			} else
				break;
		}
		while (index > 0) {
			this.tripleList.remove(0);
			index--;
		}
		this.tripleList.add(timestampedTriple);
		super.consumeDebug(timestampedTriple, debugstep);
	}

	/** {@inheritDoc} */
	@Override
	public String toString() {
		return super.toString()+" " + this.duration;
	}
}
