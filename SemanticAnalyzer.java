import absyn.*;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Set;

public class SemanticAnalyzer implements AbsynVisitor {
    final static int SPACES = 4;
    public ArrayList<HashMap<String, DefSym>> symTable;
    static int scope;
    static String currentFunction;
    static String currentFunctionCall;
    static boolean isArgument;
    static int argumentCounter;
    static boolean isParameter;
    public SemanticAnalyzer() {
        this.scope = 0;
        this.symTable = new ArrayList<HashMap<String, DefSym>>();
        HashMap global = new HashMap<String, DefSym>();
        this.symTable.add(0 ,new HashMap<String, DefSym>());
        currentFunction = "";
        currentFunctionCall = "";
        isArgument = false;
        isParameter = false;
        argumentCounter = 0;

    }

    public void printSymbolTable() {
        for(int i = this.symTable.size() -1; i >= 0; i--) {
            System.out.println(i + " " + symTable.get(i).keySet());
        }
    }

    public boolean existsInScope(String varName, int scope) {
        HashMap<String, DefSym> codeBlock = this.symTable.get(scope);
        if(codeBlock.containsKey(varName)) {
            return true;
        } else {
            return false;
        }
    }


    public static void errPrint(int row, int col, String msg) {
        System.err.println("ERROR Line:" + row + " Col:" + col + " " + msg);
    }

    public Type lookUpType(String name) {
        for(HashMap<String, DefSym> h: this.symTable) {
            if(h.containsKey(name)) {
                return h.get(name).type;
            }
        }
        return new Type(Type.ERROR);
    }

    private void indent( int level ) {
        for( int i = 0; i < level * SPACES; i++ ) System.out.print( " " );
    }

    public void visit( Type type, int level ) {

    }
    public void visit( SimpleVar var, int level ) {
        boolean exists = false;
        DefSym sym = null;
        // Go through each scope and search for the var name
        for(HashMap<String, DefSym> h: this.symTable) {
            Set<String> keys = h.keySet();
            if(keys.contains(var.name)) {
                exists = true;
                sym = h.get(var.name);
            }
        }
        if(!exists) {
            errPrint(var.row, var.col, "Undefined reference to " + var.name);
        } else if(isArgument) {
            DefSym functionSymbol = this.symTable.get(this.symTable.size() - 1).get(currentFunctionCall);
            if(!functionSymbol.parameters.get(argumentCounter).equals(sym.type)) {
                errPrint(var.row, var.col, "Error: argument type does not match the parameter type");
            }
            argumentCounter++;
        }
        
    }
    public void visit( IndexVar var, int level ){
        boolean exists = false;
        DefSym varSym = null;
        for(HashMap<String, DefSym> h: this.symTable) {
            Set<String> keys = h.keySet();
            if(keys.contains(var.name)) {
                exists = true;
                varSym = h.get(var.name);
            }
        }
        if(!exists) {
            errPrint(var.row, var.col, "Undefined reference to array " + var.name);
            return;
        }
        if(var.index != null) {
            if(var.index instanceof IntExp) {
                IntExp indexNum = (IntExp) var.index;
                if(indexNum.val < 0) {
                    errPrint(var.row, var.col, "Error: invalid negative index for array");
                } 
            } else if( var.index instanceof VarExp ) {
                VarExp varIndex = (VarExp) var.index;
                if(varIndex.variable instanceof SimpleVar) {
                    SimpleVar arrayIndex = (SimpleVar) varIndex.variable;
                    Type varType = lookUpType(arrayIndex.name);
                    if(varType.type != Type.INT) {
                        errPrint(var.row, var.col, "Error: index for array is not of type int");
                        return;
                    }
                } else {
                    errPrint(var.row, var.col, "Error: unexpected index for array should be an int");
                    return;
                }
            } else {
                errPrint(var.row, var.col, "Error: unexpected expression for index");
            }
        } else {
            errPrint(var.row, var.col, "Error: Missing index for array");
            return;
        }

    }
    public void visit( NilExp exp, int level ){

    }
    public void visit( VarExp exp, int level ){
        exp.variable.accept(this, level);
    }
    public void visit( IntExp exp, int level ){

    }
    public void visit( CallExp exp, int level ){
        currentFunctionCall = exp.func;
        // check the global hash map at index 0 if it contains the key
        if(exp.func.equals("output") || exp.func.equals("input")) {
            return;
        }
        if(!exp.func.equals("input")) {
            if(exp.args != null) {
                isArgument = true;
                exp.args.accept(this, level);
                isArgument = false;
                argumentCounter = 0;
            }
        } else if( !this.symTable.get(this.symTable.size() - 1).containsKey(exp.func)) {
            errPrint(exp.row, exp.col, "Undefined reference to function " + exp.func);
        }
    }
    public void visit( OpExp exp, int level ){
        exp.left.accept(this, level);
        exp.right.accept(this, level);
        // check the left side
        if(exp.left instanceof VarExp) {
            // if left hand side is a variable check the type for int
            VarExp lhsVar = (VarExp)exp.left;
            if(lhsVar.variable instanceof SimpleVar) {
                SimpleVar var = (SimpleVar) lhsVar.variable;
                Type t = lookUpType(var.name);
                if(t.type != Type.INT) {
                    errPrint(exp.row, exp.col, "Error: left side of operator is not of type int");
                }
            }
        } else if(exp.left instanceof CallExp) { 
            CallExp funcCall = (CallExp)exp.left;
            Type t = lookUpType(funcCall.func);
            if(t.type != Type.INT) {
                errPrint(exp.row, exp.col, "Error: Function in expression does not return int");
            }
        } 

        if(exp.right instanceof VarExp) {
            VarExp rhsVar = (VarExp)exp.right;
            if(rhsVar.variable instanceof SimpleVar) {
                SimpleVar var = (SimpleVar) rhsVar.variable;
                Type t = lookUpType(var.name);
                if(t.type != Type.INT) {
                    errPrint(exp.row, exp.col, "Error: right side of operator is not of type int");
                }
            }
        } else if(exp.right instanceof CallExp) {
            CallExp funcCall = (CallExp)exp.right;
            Type t = lookUpType(funcCall.func);
            if(t.type != Type.INT) {
                errPrint(exp.row, exp.col, "Error: Function in right hand side of expression does not return int");
            }
        }
    }
    public void visit( AssgnExp exp, int level ){
        exp.left.accept(this, level);
        exp.right.accept(this, level);
        // Gets the type of the left hand side
        Type lhsType = new Type(Type.ERROR);
        if( exp.left instanceof SimpleVar) {
            SimpleVar lhs = (SimpleVar) exp.left;
            lhsType.type = lookUpType(lhs.name).type;
        } else {
            IndexVar lhs = (IndexVar) exp.left;
            lhsType.type = lookUpType(lhs.name).type;
        }
        if(lhsType.type == Type.ERROR) {
            errPrint(exp.row, exp.col, "Undefined assignment reference" );
        }  
        // Get the type of the right hand side
        // Right hand side can be either OpExp, VarExp, or CallExp
        if(exp.right instanceof VarExp) {
            VarExp v = (VarExp) exp.right;
            // Check if the var is SimpleVar or IndexVar
            if(v.variable instanceof SimpleVar) {
                Type rhsType = lookUpType(((SimpleVar)v.variable).name);
                if(rhsType.type != lhsType.type) {
                    errPrint(v.row, v.col, "Mismatched types. Expected " + lhsType.toString() + " have " + rhsType.toString());
                }
            } else if(v.variable instanceof IndexVar) {
                Type rhsType = lookUpType(((IndexVar)v.variable).name);
                if(lhsType.type != rhsType.type) {
                    errPrint(v.row, v.col, "Mismatched types. Expected " + lhsType.toString() + " have " + rhsType.toString());
                }
            }
        } else if(exp.right instanceof CallExp) {
            // Checking return type of the function call on right hand side.
            CallExp call = (CallExp) exp.right;
            if((call.func.equals("input")) || (call.func.equals("output"))) {
                return;
            }
            Type rhsType = lookUpType(call.func);
            if(lhsType.type != rhsType.type) {
                errPrint(call.row, call.col, "Mismatched types for function call. Expected " + lhsType.toString() + " have " + rhsType.toString());
            }
        } else if(exp.right instanceof OpExp) {
            OpExp opExp = (OpExp) exp.right;
            if(!opExp.isArithmatic()) {
                errPrint(opExp.row, opExp.col, "Invalid expression for assignmen, not arithmetic");
            }
        }
    }
    public void visit( IfExp exp, int level ){
        indent(level);
        level++;
        System.out.println("Entering new if scope");
        this.symTable.add(0, new HashMap<String, DefSym>());
        exp.testBlock.accept(this, level);
        exp.thenBlock.accept(this, level);
        indent(level-1);
        System.out.println("Exiting if block");
        if(exp.elseBlock != null) {
            indent(level-1);
            System.out.println("Entering new else block");
            exp.elseBlock.accept(this, level);
            indent(level-1);
            System.out.println("Exiting else block");
        }
        this.symTable.remove(0);
        
    }
    public void visit( WhileExp exp, int level ){
        indent(level);
        level++;
        System.out.println("Entering new while block");
        this.symTable.add(0, new HashMap<String, DefSym>());
        exp.test.accept(this, level);
        exp.body.accept(this, level);
        indent(level-1);
        this.symTable.remove(0);
        System.out.println("Leaving while scope");
    }
    public void visit( ReturnExp exp, int level ){
        boolean isEmptyReturn = true;
        if(!(exp.exp instanceof NilExp)) {
            exp.exp.accept(this, level);
            isEmptyReturn = false;
        }
       DefSym currentFuncSym = null;
       for(HashMap<String, DefSym> h: this.symTable) {
           if(h.containsKey(this.currentFunction)) {
               currentFuncSym = h.get(currentFunction);
           }
       }
       if(currentFuncSym == null) {
           errPrint(exp.row, exp.col, "Function not found for return statement");
           return;
       }
       if(currentFuncSym.type.type == Type.VOID) {
           if(!isEmptyReturn) {
               errPrint(exp.row, exp.col, "Return type is not void");
           }
       } else if(currentFuncSym.type.type == Type.INT) {
            if(isEmptyReturn) {
               errPrint(exp.row, exp.col, "Return type is not int");
           }
       } 
    }
    public void visit( CompoundExp exp, int level ){
        if(exp.decs != null) {
            exp.decs.accept(this, level);
        }
        if(exp.exps != null) {
            exp.exps.accept(this, level);
        }
    }
    public void visit( FuncDec dec, int level ){
        indent(level);
        level++;
        System.out.println("Entering the scope for function " + dec.funcName);
        this.currentFunction = dec.funcName;
        DefSym func = new DefSym(dec.funcName, dec.type, level);
        symTable.get(symTable.size() - 1).put(func.name,func);
        this.symTable.add(0, new HashMap<String, DefSym>());
        if(dec.params != null) {
            isParameter = true;
            dec.params.accept(this, level);
            isParameter = false;
        }
        dec.body.accept(this, level);
        level--;
        indent(level);
        System.out.println("Leaving the scope for function " + dec.funcName);
        this.symTable.remove(0);
        
        
    }
    public void visit( SimpleDec dec, int level ){
        indent(level);
        if(this.existsInScope(dec.name, 0)) {
            errPrint(dec.row, dec.col, "Error variable already exists in local scope");
            return;
        } else if(this.existsInScope(dec.name, this.symTable.size() - 1)) {
            errPrint(dec.row, dec.col, "Error: redecleration of variable in global scope");
            return;
        }
        DefSym sym = new DefSym(dec.name, dec.type, level);
        this.symTable.get(0).put(dec.name, sym);
        System.out.println(sym.toString());
        if(isParameter) {
            HashMap<String, DefSym> globalScope = this.symTable.get(this.symTable.size() - 1);
            DefSym func = globalScope.get(currentFunction);
            func.parameters.add(sym.type);
        }
        
    }
    public void visit( ArrayDec dec, int level ){
        indent(level);
        if(dec.size != null) {
            if( !(dec.size instanceof IntExp) ) {
                errPrint(dec.row, dec.col, "Array index is not integer value");
                return;
            }
        } else if(dec.size == null) {
            errPrint(dec.row, dec.col, "Error: improper decleration of array: missing size");
            return;
        }
        if(this.existsInScope(dec.name, 0)) {
            errPrint(dec.row, dec.col, "Error variable already exists in local scope");
            return;
        } else if(this.existsInScope(dec.name, this.symTable.size() - 1)) {
            errPrint(dec.row, dec.col, "Error: redecleration of variable in global scope");
            return;
        }
        DefSym sym = new DefSym(dec.name, dec.type, dec.size.val, level);
        this.symTable.get(0).put(sym.name, sym);
        System.out.println(sym.toString());
        if(isParameter) {
            HashMap<String, DefSym> globalScope = this.symTable.get(this.symTable.size() - 1);
            DefSym func = globalScope.get(currentFunction);
            func.parameters.add(sym.type);
        }
    }
    public void visit(DecError dec, int level){

    }
    public void visit( DecList list, int level ){
        while(list != null) {
            list.head.accept(this, level);
            list = list.tail;
        }
    }
    public void visit( VarDecList varList, int level ){
        while(varList != null) {
            if(varList.head != null) {
                varList.head.accept(this, level);
            }
            varList = varList.tail;
        }
    }
    public void visit( ExpList expList, int level ){
        while(expList != null ) {
            if(expList.head != null) {
                expList.head.accept(this, level);
            }
            expList = expList.tail;
        }
    }
}