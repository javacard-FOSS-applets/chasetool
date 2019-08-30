package assignable;

import antlr.collections.AST;
import antlr.ASTFactory;

import java.io.File ;
import java.io.FileInputStream ;
import java.io.IOException ;

import java.util.Vector;

import edu.iastate.cs.jml.checker.util.SuffixMatcher ;
import edu.iastate.cs.jml.checker.*;

public class CheckModClause{    
  private static ASTFactory astFactory = new ASTFactory();
  private static JmlParser setParser(File f){
    
    try{
      JmlParser parser = null ;
      JmlLexer lexer = null ;
      FileInputStream fs  = null ;    

      fs = new FileInputStream(f);
      lexer = new JmlLexer(fs);
      lexer.currentFile = f;
    	    
      // Create a parser that reads from the scanner
      parser = new JmlParser(lexer);
      parser.lexer = lexer;
      parser.currentFile = f;
      parser.errors = 0;
      parser.warnings = 0;
      parser.JML_reading_JML_file = SuffixMatcher.isJMLFileName(f.getName());
      lexer.JML_reading_JML_file = parser.JML_reading_JML_file;
	    
      return parser;
    }
    catch(IOException e){
      System.err.println(e.getMessage());
      return null;
    }
  }
    
  public static void  checkAssignable(String[] args){
    try{
      Vector v = new Vector();
      AST prg = null;

      System.out.println("Checking " +args[0] +" ...");

      for(int i=0;i<args.length;i++){
	File f = new File(args[i]);
	
	JmlParser parser = setParser(f) ;
	
	parser.compilation_unit();
	parser.errors += parser.lexer.errors;
		
	if (parser.errors > 0) {
	  System.err.print(f.getPath() + " had ");
	  System.err.print(parser.errors + " error(s) and ");
	  System.err.println(parser.warnings + " warning(s)\n");
	  System.exit(1);
	}
	v.addElement(parser.getAST());
      }//end for

      boolean b = assignable.Assignable._PROGRAM(v);
	    
      if(!b){
	System.out.println("Some problems are present");
	System.exit(1);
      }//end if
    }
    catch(Exception e){
      System.err.println(e.getMessage());
    }
  }
    
  private static void usage(){
    System.err.println("Usage: java CheckModClause file.java file-out");
  }
    
  public static void main(String[] args) {
    checkAssignable(args);
  }
    
}
