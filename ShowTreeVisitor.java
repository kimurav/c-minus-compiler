import absyn.*;

public class ShowTreeVisitor implements AbsynVisitor {
    final static int SPACES = 4;

    private void indent( int level ) {
        for( int i = 0; i < level * SPACES; i++ ) System.out.print( " " );
    }

    public void visit( Type t, int level ) {
        indent(level);
        if(t.type == Type.INT) {
            System.out.println("Type: int");
        } else {
            System.out.println("Type: void");
        }
        level++;
    }
    public void visit( SimpleVar var, int level ){
        indent(level);
        System.out.println("SimpleVar: " + var.name);
        level++;
    }
    public void visit( IndexVar var, int level ){
        indent(level);
        System.out.println("IndexVar: " + var.name);
        level++;
        var.index.accept(this, level);
    }
    public void visit( NilExp exp, int level ){
    }
    public void visit( VarExp exp, int level ){
        exp.variable.accept(this, level);
    }
    public void visit( IntExp exp, int level ){
        indent(level);
        System.out.println("IntExp: " + exp.val);
    }
    public void visit( CallExp exp, int level ){
        indent(level);
        System.out.println("CallExp: " + exp.func);
        level++;
        if(exp.args != null) {
            exp.args.accept(this, level);
        }
    }
    public void visit( OpExp exp, int level ){
        indent(level);
        System.out.print("OpExp: ");
        switch(exp.op){
            case OpExp.PLUS:
                System.out.println("+");
                break;
            case OpExp.MINUS:
                System.out.println("-");
                break;
            case OpExp.TIMES:
                System.out.println("*");
                break;
            case OpExp.DIV:
                System.out.println("/");
                break;
            case OpExp.EQ:
                System.out.println("==");
                break;
            case OpExp.NEQ:
                System.out.println("!=");
                break;
            case OpExp.LT:
                System.out.println("<");
                break;
            case OpExp.LTE:
                System.out.println("<=");
                break;
            case OpExp.GT:
                System.out.println(">");
                break;
            case OpExp.GTE:
                System.out.println(">=");
                break;
        }
        level++;
        if(exp.left != null) {
            exp.left.accept(this, level);
        }
        if(exp.right != null) {
            exp.right.accept(this, level);
        }

    }
    public void visit( AssgnExp exp, int level ){
        indent(level);
        System.out.println("AssgnExp: =");
        level++;
        exp.left.accept(this, level);
        exp.right.accept(this, level);
    }
    public void visit( IfExp exp, int level ){
        indent(level);
        System.out.println("IfExp:");
        level++;
        exp.testBlock.accept(this, level);
        exp.thenBlock.accept(this, level);
        if(exp.elseBlock != null) {
            exp.elseBlock.accept(this, level);
        }

    }
    public void visit( WhileExp exp, int level ){
        indent(level);
        System.out.println("WhileEx: while");
        level++;
        exp.test.accept(this,level);
        exp.body.accept(this, level);  
    }
    public void visit( ReturnExp retExp, int level ){
        indent(level);
        System.out.println("ReturnExp: ");
        level++;
        retExp.exp.accept(this, level);
    }
    public void visit( CompoundExp exp, int level ){
        indent(level);
        System.out.println("CompoundExp:");
        level++;
        if(exp.decs != null) {
            exp.decs.accept(this, level);
        }
        if(exp.exps != null) {
            exp.exps.accept(this, level);
        }
    }
    public void visit( FuncDec dec, int level ){
        indent(level);
        System.out.print("FuncDec: ");
        if(dec.type.type == Type.INT) {
            System.out.print("int ");
        } else if (dec.type.type == Type.VOID) {
            System.out.print("void ");
        }
        System.out.println(dec.funcName);
        
        if(dec.params != null) {
            dec.params.accept(this, level);
        }
        level++;
        if(dec.body != null) {
            dec.body.accept(this,level);
        }
        
    }
    public void visit( SimpleDec dec, int level ){
        indent(level);
        System.out.print("SimpleDec: ");
        if(dec.type.type == Type.INT) {
            System.out.print("int");
        } else {
            System.out.print("void");
        }
        System.out.println(" " + dec.name);
        level++;
    }
    public void visit( ArrayDec dec, int level ){
        indent(level);
        System.out.println("ArrayDec: "+dec.name);
        level++;
        dec.type.accept(this, level);
        dec.size.accept(this, level);
    }

    public void visit(DecError dec, int level) {
        indent(level);
        System.out.println("DecError:");
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