/* Name: Noah Dunn
 * Course: CSE465
 * Date: 1/30/2020
 * Professor: Dr. Zmuda
 * Assignment: Z+- Interpreter
 * 
 * This is a helper class that handles a large amount of the
 * Variable operations
 */


public class Variable {
	/*
	 *  We need to know the name of the variable, if its initialized or not, the type of the Variable
	 *  and what value it holds
	 * 
	 */
	private String varName;
	private boolean initState;
	private String varType;
	private String val;

	public Variable(String varName, boolean initState, String varType, String val) throws Exception {
		//  The user cannot use a reserved word as the name or the value, unless it's True or False
		if (Main.reservedWords.contains(varName.toUpperCase())
				|| (Main.reservedWords.contains(val.toUpperCase())) && !(val.equals("TRUE") || val.equals("FALSE")))  {
			throw new Exception();
		}
		this.varName = varName;
		this.initState = initState;
		this.varType = varType;
		this.val = val;
		//  If the user is attempting to make a declaration and the type doesn't match the value, we want to throw an error
		if(initState == true) {
			if (!typeValMatches(varType, val)) {
				throw new Exception();
			}
		}

	} 
	//  A method to handle all operations between two Variables 
	public boolean operate(Variable rightVariable, String operation) {
		//  If we want to declare
		if(operation.equals("=")){
			//  We check type matching between the variables, and if it's good we go ahead with the operation
			if(typeValMatches(this.varType, rightVariable.getVal())) {
				this.val = rightVariable.val;
				this.initState = true;
				return true;
			}
			return false;
		}
		else {
			// If we want to conduct +=, *=, -= operations
			if(typeValMatches(this.varType, rightVariable.getVal())) {
				//  We cannot execute these operations if the variable doesn't have a value
				if(this.initState == false) {
					return false;
				}
				//  We cannot use these operations if the variable is a boolean
				else if(this.varType.equals("BOOL")) {
					return false;
				}
				//  We can only do strings with the += operation
				else if(this.varType.equals("STRING")) {
					if(operation.equals("+=")) {
						//  When merging strings, we do not want to merge their leading and trailing quotation marks
						//  I.E   "hello" + "world" -> "helloworld"
						this.val = this.val.substring(0, this.val.length() - 1);
						this.val += rightVariable.val.substring(1, rightVariable.val.length());
						return true;
					}
					return false;
				}
				//  If the values are Ints, we need to cast them, add them, and return them to string form
				else if(this.varType.equals("INT")) {
					if(operation.equals("+=")) {
						this.val = "" + (Integer.parseInt(this.val) + Integer.parseInt(rightVariable.val));
						return true;
					}
					else if(operation.equals("-=")) {
						this.val = "" + (Integer.parseInt(this.val) - Integer.parseInt(rightVariable.val));
						return true;
					}
					else if(operation.equals("*=")) {
						this.val = "" + (Integer.parseInt(this.val) * Integer.parseInt(rightVariable.val));
						return true;
					}
					else {
						return false;
					}
				}
				else {
					return false;
				}
			}
			return false;
		}
	}

	public boolean typeValMatches(String varType, String val) {
		//  We support adding booleans to ints, but ints cannot contain decimals and must be parsable
		if (varType.equals("INT")) {
			if (val.contains(".")) {
				return false;
			}
			else if (val.equals("TRUE")) {
				this.val = "1";
				return true;
			} else if (val.equals("FALSE")) {
				this.val = "0";
				return true;
			}
			try {
				Integer.parseInt(val);
				return true;
			} catch (NumberFormatException e) {
				return false;
			}
		//  Booleans can be TRUE or FALSE but no other values (Case insensitive)
		} else if (varType.equals("BOOL")) {
			if(val.equalsIgnoreCase("TRUE") || val.equalsIgnoreCase("FALSE")) {
				return true;
			}
			else {
				return false;
			}
		//  All string values need to begin and end with quotation marks
		} else if (varType.equals("STRING")) {
			if(val.startsWith("\"") && val.endsWith("\"")) {
				return true;
			}
			else {
				return false;
			}
		} else {
			return false;
		}
	}
	//  All the setters and getters for the private variables
	public String getVarName() {
		return varName;
	}

	public void setVarName(String varName) {
		this.varName = varName;
	}

	public boolean getInitState() {
		return initState;
	}

	public void setInitState(boolean initState) {
		this.initState = initState;
	}

	public String getVarType() {
		return varType;
	}

	public void setVarType(String varType) {
		this.varType = varType;
	}

	public String getVal() {
		return val;
	}

	public void setVal(String val) {
		this.val = val;
	}

}
