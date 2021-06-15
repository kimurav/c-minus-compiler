
   
import java_cup.runtime.*;
      
%%
   
/* -----------------Options and Declarations Section----------------- */
   
/* 
   The name of the class JFlex will create will be Lexer.
   Will write the code to the file Lexer.java. 
*/
%class Lexer

%eofval{
  return null;
%eofval};

/*
  The current line number can be accessed with the variable yyline
  and the current column number with the variable yycolumn.
*/
%line
%column
    
/* 
   Will switch to a CUP compatibility mode to interface with a CUP
   generated parser.
*/
%cup
   
/*
  Declarations
   
  Code between %{ and %}, both of which must be at the beginning of a
  line, will be copied letter to letter into the lexer class source.
  Here you declare member variables and functions that are used inside
  scanner actions.  
*/
%{   
    /* To create a new java_cup.runtime.Symbol with information about
       the current token, the token will have no value in this
       case. */
    private Symbol symbol(int type) {
        return new Symbol(type, yyline, yycolumn);
    }
    
    /* Also creates a new java_cup.runtime.Symbol with information
       about the current token, but this object has a value. */
    private Symbol symbol(int type, Object value) {
        return new Symbol(type, yyline, yycolumn, value);
    }
%}
   

/*
  Macro Declarations
  
  These declarations are regular expressions that will be used latter
  in the Lexical Rules Section.  
*/
   
/* A line terminator is a \r (carriage return), \n (line feed), or
   \r\n. */
LineTerminator = \r|\n|\r\n
   
/* White space is a line terminator, space, tab, or form feed. */
WhiteSpace     = {LineTerminator} | [\s]+
   
/* A literal integer is is a number beginning with a number between
   one and nine followed by zero or more numbers between zero and nine
   or just a zero.  */
digit = [0-9]
number = {digit}+
   
/* A identifier integer is a word beginning a letter between A and
   Z, a and z, or an underscore followed by zero or more letters
   between A and Z, a and z, zero and nine, or an underscore. */
letter = [a-zA-Z][a-zA-Z0-9]*
identifier = {letter}+
   
%%
/* ------------------------Lexical Rules Section---------------------- */
   
/*
   This section contains regular expressions and actions, i.e. Java
   code, that will be executed when the scanner matches the associated
   regular expression. */
   
"if"               { return symbol(sym.IF, yytext());}
"else"             { return symbol(sym.ELSE,yytext()); }
"int"              { return symbol(sym.INT, yytext()); }
"return"           {return symbol(sym.RETURN,yytext());}
"void"             {return symbol(sym.VOID,yytext());}
"while"            {return symbol(sym.WHILE,yytext());}
"+"                { return symbol(sym.PLUS,yytext()); }
"-"                { return symbol(sym.MINUS,yytext()); }
"*"                { return symbol(sym.TIMES, yytext()); }
"/"                { return symbol(sym.SLASH,yytext()); }
"<"                { return symbol(sym.LT,yytext()); }
"<="               {return symbol(sym.LTE,yytext());}
">"                { return symbol(sym.GT, yytext()); }
">="               {return symbol(sym.GTE, yytext());}
"=="               {return symbol(sym.EQ, yytext());}
"!="               {return symbol(sym.NEQ, yytext());}
"="                { return symbol(sym.ASGN, yytext()); }
";"                { return symbol(sym.SEMI, yytext()); }
","                {return symbol(sym.COMMA, yytext());}
"("                { return symbol(sym.OPRA, yytext()); }
")"                {return symbol(sym.CPRA, yytext());}
"["                 {return symbol(sym.OSQPRA, yytext());}
"]"                 {return symbol(sym.CSQPRA, yytext());}
"{"                 {return symbol(sym.OBRAC, yytext());}
"}"                 {return symbol(sym.CBRAC, yytext());}
{number}           { return symbol(sym.NUM, yytext());}
{identifier}       { return symbol(sym.ID, yytext()); }
{WhiteSpace}+      { /* skip whitespace */ }   
"/*"[^*/]*"*/"      {}
.                  { return symbol(sym.ERROR); }
