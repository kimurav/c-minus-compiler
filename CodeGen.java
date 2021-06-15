import absyn.*;
import java.io.*;
import java.util.*;


class CodeGen implements AbsynVisitor {

    public ArrayList<HashMap<String, DefSym>> symTable;
    int emitLoc;
    int highEmitLoc;
    PrintWriter writer;

    int pc;
    int gp;
    int fp;
    int ac;
    int ac1;

    public CodeGen(PrintWriter writer) {
        this.symTable = new ArrayList<HashMap<String, DefSym>>();
        this.emitLoc = 0;
        this.highEmitLoc = 0;
        this.writer = writer;
        this.pc = 7;
        this.gp = 6;
        this.fp = 5;
        this.ac = 0;
        this.ac1 = 1;
    }

    void generateCode(Absyn tree) {
        // prelude
        emitComment("Starting standard prelude");
        emitRM("LD", gp, 0, 0, "load gp with maxaddr");
        emitRM("LDA", fp, 0, gp, "copy gp to fp");
        emitRM("ST", 0, 0, 0, "clear content at loc 0");
        emitComment("Jump around i/o code here");
        // generate io routines
        int ioLoc = emitSkip(1);
        //input routine
        emitComment("Standard input function code:");
        emitRM("ST", 0, -1, fp, "store return");
        emitRO("IN", 0, 0, 0, "input");
        emitRM("LD", 7, -1, fp, "return to caller");
        

        //output routine
        emitComment("Standard output function code:");
        emitRM("ST", 0, -1, fp, "store return");
        emitRM("LD", 0, -2, fp, "load output level");
        emitRO("OUT", 0, 0, 0, "output");
        emitRM("LD", 7, -1, fp, "return to caller");
        int outLoc = emitSkip(0);
        emitBackup(ioLoc);
        emitRM_Abs("LDA", pc, outLoc, "jump around i/o code");
        emitRestore();
        /* jump around io routines */

        // gnerate finale
    }
    /* 
        * Standard prelude:
    0:     LD  6,0(0) 	load gp with maxaddress
    1:    LDA  5,0(6) 	copy to gp to fp
    2:     ST  0,0(0) 	clear location 0
    * Jump around i/o routines here
    * code for input routine
    4:     ST  0,-1(5) 	store return
    5:     IN  0,0,0 	input
    6:     LD  7,-1(5) 	return to caller
    * code for output routine
    7:     ST  0,-1(5) 	store return
    8:     LD  0,-2(5) 	load output value
    9:    OUT  0,0,0 	output
    10:     LD  7,-1(5) 	return to caller
    3:    LDA  7,7(7) 	jump around i/o code
    * End of standard prelude.
    */
    public void genPrelude() {
       
    }

    public void genInputCode() {
        
    }

    public void genOutputCode() {
     
    }

    public void visit( Type type, int level ){
    
    }
    public void visit( SimpleVar var, int level ){
    
    }
    public void visit( IndexVar var, int level ){
    
    }
    public void visit( NilExp exp, int level ){
    
    }
    public void visit( VarExp exp, int level ){
    
    }
    public void visit( IntExp exp, int level ){
    
    }
    public void visit( CallExp exp, int level ){
    
    }
    public void visit( OpExp exp, int level ){
    
    }
    public void visit( AssgnExp exp, int level ){
    
    }
    public void visit( IfExp exp, int level ){
    
    }
    public void visit( WhileExp exp, int level ){
    
    }
    public void visit( ReturnExp exp, int level ){
    
    }
    public void visit( CompoundExp exp, int level ){
    
    }
    public void visit( FuncDec dec, int level ){
    
    }
    public void visit( SimpleDec dec, int level ){
    
    }
    public void visit( ArrayDec dec, int level ){
    
    }
    public void visit(DecError dec, int level){
    
    }
    public void visit( DecList list, int level ){
    
    }
    public void visit( VarDecList exp, int level ){
    
    }
    public void visit( ExpList exp, int level ){
    
    }

    void emitRO(String op, int r, int s, int t, String comment) {
        writer.println(emitLoc + ":     " + op + "  " + r + "," + s + "," + t + "\t" + comment);
        emitLoc++;
        writer.flush();
        if(highEmitLoc < emitLoc) {
            highEmitLoc = emitLoc;
        }
    }

    void emitRM(String op, int r, int d, int s, String comment) {
        writer.println(emitLoc + ":     " + op + "  " + r + "," + d +"(" + s +")" + "\t" + comment);
        emitLoc++;
        writer.flush();
        if(highEmitLoc < emitLoc) {
            highEmitLoc = emitLoc;
        }
    }

    int emitSkip(int dist) {
        int i = emitLoc;
        emitLoc += dist;
        if(highEmitLoc < emitLoc) {
            highEmitLoc = emitLoc;
        }
        return i;
    }

    void emitBackup(int loc) {
        if(loc > highEmitLoc) {
            emitComment("BUG in emitbackup");
        }
        emitLoc = loc;
    }

    void emitRestore() {
        emitLoc = highEmitLoc;
    }

    void emitRM_Abs(String op, int r, int a, String c) {
        writer.println(emitLoc + ":     " + op + "  " + r + "," + (a - (emitLoc + 1)) + "(" + pc + ")" + "\t" + c);
        writer.flush();
        emitLoc++;
        if(highEmitLoc < emitLoc) {
            highEmitLoc = emitLoc;
        }
    }

    void emitComment(String s) {
        writer.println("* "+s);
        writer.flush();
    }
}