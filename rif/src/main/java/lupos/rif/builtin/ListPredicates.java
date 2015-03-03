
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
package lupos.rif.builtin;

import lupos.datastructures.items.Item;
import lupos.datastructures.items.Variable;
import lupos.datastructures.items.literal.Literal;
import lupos.misc.Tuple;
import lupos.rif.IExpression;
import lupos.rif.datatypes.ListLiteral;
import lupos.rif.model.RuleList;

@Namespace(value = "http://www.w3.org/2007/rif-builtin-predicate#")
public class ListPredicates {

	/**
	 * <p>is_list.</p>
	 *
	 * @param arg a {@link lupos.rif.builtin.Argument} object.
	 * @return a {@link lupos.rif.builtin.BooleanLiteral} object.
	 */
	@Builtin(Name = "is-list")
	public static BooleanLiteral is_list(final Argument arg) {
		if (arg.arguments.size() == 1
				&& (arg.arguments.get(0) instanceof RuleList || arg.arguments.get(0) instanceof ListLiteral)) {
			return BooleanLiteral.TRUE;
		} else {
			return BooleanLiteral.FALSE;
		}
	}

	/**
	 * <p>list_contains.</p>
	 *
	 * @param arg a {@link lupos.rif.builtin.Argument} object.
	 * @return a {@link java.lang.Object} object.
	 */
	@Builtin(Name = "list-contains", Bindable = true)
	public static Object list_contains(final Argument arg) {
		if (arg.arguments.size() == 2) {

			final Item arg0 = arg.arguments.get(0);
			if(arg0 instanceof RuleList) {
				final RuleList list1 = (RuleList) arg.arguments.get(0);

				if(arg.arguments.get(1) instanceof RuleList){
					final RuleList list2 = (RuleList) arg.arguments.get(1);
					for (final IExpression expr : list1.getItems()) {
						if (expr.equals(list2)) {
							return BooleanLiteral.TRUE;
						}
					}
					return BooleanLiteral.FALSE;
				} else if(arg.arguments.get(1) instanceof Variable) {
					return new Tuple<Variable, RuleList>((Variable)arg.arguments.get(1), list1);
				}
			} else if(arg0 instanceof ListLiteral) {
				final Item arg1 = arg.arguments.get(1);
				if(arg1 instanceof Literal){
					if(((ListLiteral)arg0).getEntries().contains(arg1)){
						return BooleanLiteral.TRUE;
					}
					return BooleanLiteral.FALSE;
				} else if(arg1 instanceof ListLiteral){
					final ListLiteral list2 = (ListLiteral) arg1;
					// in general the following should be better encoded in a recursive function!
					if(((ListLiteral)arg0).getEntries().containsAll(list2.getEntries())){
						return BooleanLiteral.TRUE;
					}
					return BooleanLiteral.FALSE;
				} else if(arg.arguments.get(1) instanceof Variable) {
					return new Tuple<Variable, ListLiteral>((Variable)arg.arguments.get(1), (ListLiteral)arg0);
				}
			} else if(arg0 instanceof Literal){
				System.err.println("Warning: The external list-contains is currently not iterable on lists!");
				System.err.println("Use instead following predicate:");
				System.err.println("Forall ?member ?headOfList ?rest(pred:membersOfList(?headOfList ?x) :- Or(?headOfList[rdf:first->?x] And(?headOfList[rdf:rest->?rest] pred:membersOfList(?rest ?x))))");
			}
		}
		System.err.println("Wrong usage of the external list-contains, assuming FALSE");
		return BooleanLiteral.FALSE;
	}

}
