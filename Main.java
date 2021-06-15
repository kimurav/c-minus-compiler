import java.io.*;
import absyn.*;
   
class Main {
  public static boolean SHOW_TREE = false;
  public static boolean SHOW_SYMBOL = false;
  public static boolean SHOW_ASSEMBLY = false;

  static public void main(String argv[]) {
    for(int i = 0; i < argv.length; i++) {
      if(argv[i].equals("-s")) {
        SHOW_SYMBOL = true;
        break;
      }
      if(argv[i].equals("-t")) {
        SHOW_TREE = true;
        break;
      }
      if(argv[i].equals("-c")) {
        SHOW_ASSEMBLY = true;
      }
    }    
    /* Start the parser */
    try {
      parser p = new parser(new Lexer(new FileReader(argv[0])));
      Absyn result = (Absyn)(p.parse().value);
      if(SHOW_TREE) {
        ShowTreeVisitor visitor = new ShowTreeVisitor();
        result.accept(visitor, 0);
      } 
      if(SHOW_SYMBOL) {
        SemanticAnalyzer analyzer = new SemanticAnalyzer();
        result.accept(analyzer, 0);
      }
      if(SHOW_ASSEMBLY) {
        String fileName = argv[0];
        //fileName.replace(".cm", ".tm");

        PrintWriter w = null;
        String tmFile = replaceCMExt(fileName);
        try {
          w = new PrintWriter(new File(tmFile));
        } catch(FileNotFoundException e) {
          System.err.println("Could not find file " +fileName);
          System.exit(0);
        }
        CodeGen gen = new CodeGen(w);
        gen.generateCode(result);
      } 

      
    } catch (Exception e) {
      /* do cleanup here -- possibly rethrow e */
      e.printStackTrace();
    }
  }

  public static String replaceCMExt(String in) {
    StringBuilder sb = new StringBuilder();
    System.out.println(in);
    if(in.contains("/")) {
        int index = in.lastIndexOf("/");
        sb.append("./tm_progs/");
        sb.append(in.substring(index+1));
    }
    int index = sb.lastIndexOf(".");
    sb.replace(index, index+3, ".tm");
    return sb.toString();
  }

}


