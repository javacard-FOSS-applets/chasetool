package assignable.exception;

import antlr.collections.AST ;

public class ContextException extends java.lang.Exception{
    
    public ContextException(String str_meth, AST ast_mess){	
	super("Exception: "
	      +str_meth +"\n"
	      +edu.iastate.cs.jml.checker.ASTtoStringConverter.convert(ast_mess)
	      );
    }//end method
	
}
