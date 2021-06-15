package absyn;

public abstract class VarDec extends Dec {
    int nestLevel; // either 0 for global or 1 for local
    int offset; // is the offset for the related stack frame for memory access
}