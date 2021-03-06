/**
 * DoubleFunction.java
 * © MINETUR, Government of Spain
 * This program is part of LocalGIS
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 2 of the License, or (at your option) any later version.
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package com.geopista.util.expression;

import java.util.Stack;

import org.nfunk.jep.ParseException;
import org.nfunk.jep.function.PostfixMathCommand;

/**
 * TODO Documentación
 * @author juacas
 *
 */
public class DoubleFunction extends PostfixMathCommand
{

	/**
	 * 
	 */
	public DoubleFunction() {
		super();
		this.numberOfParameters=1;
		}
	public void run(Stack inStack) throws ParseException
	{
		// check the stack
		checkStack(inStack);
		// get the parameter from the stack
		Object param = inStack.pop();
		// check whether the argument is of the right type
		if (param instanceof String) {
		// calculate the result
		Double r = new Double((String)param);
		// push the result on the inStack
		inStack.push(r); 
		}
		else
		if (param instanceof Integer)
		{
			Double r = new Double(((Integer)param).doubleValue());
			// push the result on the inStack
			inStack.push(r);
		}
		else 
		if (param instanceof Double)
		   	{ //identity
		   	inStack.push(param);
		   	}else {
		      throw new ParseException("Invalid parameter type");
		   }
		
	}

}
