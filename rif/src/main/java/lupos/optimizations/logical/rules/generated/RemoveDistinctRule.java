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
package lupos.optimizations.logical.rules.generated;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import lupos.optimizations.logical.rules.generated.runtime.Rule;
import lupos.engine.operators.BasicOperator;
import lupos.engine.operators.OperatorIDTuple;
public class RemoveDistinctRule extends Rule {

    private lupos.engine.operators.BasicOperator[] o2 = null;
    private lupos.engine.operators.singleinput.modifiers.distinct.Distinct d = null;
    private lupos.engine.operators.BasicOperator o1 = null;
    private int _dim_0 = -1;

    private boolean _checkPrivate0(BasicOperator _op) {
        if(!(_op instanceof lupos.engine.operators.BasicOperator)) {
            return false;
        }

        this.o1 = (lupos.engine.operators.BasicOperator) _op;

        List<OperatorIDTuple> _succedingOperators_1_0 = _op.getSucceedingOperators();


        for(OperatorIDTuple _sucOpIDTup_1_0 : _succedingOperators_1_0) {
            if(_sucOpIDTup_1_0.getOperator().getPrecedingOperators().size() != 1) {
                break;
            }

            if(_sucOpIDTup_1_0.getOperator().getClass() != lupos.engine.operators.singleinput.modifiers.distinct.Distinct.class) {
                continue;
            }

            this.d = (lupos.engine.operators.singleinput.modifiers.distinct.Distinct) _sucOpIDTup_1_0.getOperator();

            List<OperatorIDTuple> _succedingOperators_2_0 = _sucOpIDTup_1_0.getOperator().getSucceedingOperators();


            this._dim_0 = -1;
            this.o2 = new lupos.engine.operators.BasicOperator[_succedingOperators_2_0.size()];

            for(OperatorIDTuple _sucOpIDTup_2_0 : _succedingOperators_2_0) {
                this._dim_0 += 1;

                if(!this._checkPrivate1(_sucOpIDTup_2_0.getOperator())) {
                    return false;
                }
            }

            return true;
        }

        return false;
    }

    private boolean _checkPrivate1(BasicOperator _op) {
        if(!(_op instanceof lupos.engine.operators.BasicOperator)) {
            return false;
        }

        this.o2[this._dim_0] = (lupos.engine.operators.BasicOperator) _op;

        return true;
    }


    /**
     * <p>Constructor for RemoveDistinctRule.</p>
     */
    public RemoveDistinctRule() {
        this.startOpClass = lupos.engine.operators.BasicOperator.class;
        this.ruleName = "Remove Distinct";
    }

    /** {@inheritDoc} */
    protected boolean check(BasicOperator _op) {
        boolean _result = this._checkPrivate0(_op);

        if(_result) {
            // additional check method code...
            return !(this.d.getSucceedingOperators().get(0).getOperator() instanceof lupos.engine.operators.singleinput.Result);
        }

        return _result;
    }

    /** {@inheritDoc} */
    protected void replace(HashMap<Class<?>, HashSet<BasicOperator>> _startNodes) {
        // remove obsolete connections...
        this.o1.removeSucceedingOperator(this.d);
        this.d.removePrecedingOperator(this.o1);
        int[] _label_a = null;

        int _label_a_count = 0;
        _label_a = new int[this.o2.length];

        for(lupos.engine.operators.BasicOperator _child : this.o2) {
            _label_a[_label_a_count] = this.d.getOperatorIDTuple(_child).getId();
            _label_a_count += 1;

            this.d.removeSucceedingOperator(_child);
            _child.removePrecedingOperator(this.d);
        }


        // add new operators...


        // add new connections...
        _label_a_count = 0;

        for(lupos.engine.operators.BasicOperator _child : this.o2) {
            this.o1.addSucceedingOperator(new OperatorIDTuple(_child, _label_a[_label_a_count]));
            _child.addPrecedingOperator(this.o1);

            _label_a_count += 1;
        }



        // delete unreachable operators...
        this.deleteOperatorWithoutParentsRecursive(this.d, _startNodes);


        // additional replace method code...

    }
}
