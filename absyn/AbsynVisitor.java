package absyn;

public interface AbsynVisitor {
    public void visit( Type type, int level );
    public void visit( SimpleVar var, int level );
    public void visit( IndexVar var, int level );
    public void visit( NilExp exp, int level );
    public void visit( VarExp exp, int level );
    public void visit( IntExp exp, int level );
    public void visit( CallExp exp, int level );
    public void visit( OpExp exp, int level );
    public void visit( AssgnExp exp, int level );
    public void visit( IfExp exp, int level );
    public void visit( WhileExp exp, int level );
    public void visit( ReturnExp exp, int level );
    public void visit( CompoundExp exp, int level );
    public void visit( FuncDec dec, int level );
    public void visit( SimpleDec dec, int level );
    public void visit( ArrayDec dec, int level );
    public void visit(DecError dec, int level);
    public void visit( DecList list, int level );
    public void visit( VarDecList exp, int level );
    public void visit( ExpList exp, int level );
}
