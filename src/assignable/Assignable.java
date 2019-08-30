package assignable ;

import java.util.Vector;
import java.util.Iterator;
import antlr.ASTFactory;
import antlr.Token ;//
import antlr.collections.AST ;
import antlr.collections.impl.ASTArray;
import edu.iastate.cs.jml.checker.JavaTokenTypes ;
import edu.iastate.cs.jml.checker.LineAST ;

import edu.iastate.cs.jml.checker.ASTtoStringConverter;

public class Assignable{
  private static ASTFactory astFactory = new ASTFactory();
  private static AST astPrgm;
  private static Vector contexts = new Vector();
    
    /**
     * @param t_contexts programs representing the contexts for the
     * program t_contexts[0]
     **/
  public static boolean _PROGRAM(Vector t_contexts)
    throws assignable.exception.ContextException{

    int contextsSize = t_contexts.size();
    for(int i=0; i<contextsSize; i++){
      AST elm = (AST)t_contexts.elementAt(i);
      Vector v_meths = new Vector();
      Util.getMethDecl(elm,v_meths);
      
      //add all method declarations
      int msSize = v_meths.size() ;
      for(int j=0; j<msSize; j++)
	contexts.addElement(new ASTContext(elm,(AST)(v_meths.elementAt(j))));
    }

    //finding methods declarations for the program 'astPrgm'
    astPrgm = (AST)t_contexts.elementAt(0);
    Vector methods_AST = new Vector();
    Util.getMethDecl(astFactory.dupList(astPrgm),methods_AST);
	
    //checking for each method declaration in the program 'astPrgm'
    int msSize = methods_AST.size();
    for(int i=0; i<msSize; i++){
      AST method_AST = (AST)(methods_AST.elementAt(i));

      //creating a ASTContext for method_AST
      ASTContext currentContext = new ASTContext(astPrgm,method_AST);
      
      System.out.println("[[METH1," +currentContext.getMethSig()+"]]");
      //System.out.println("Printing currentContext\n" +currentContext.toString());

      boolean b = _mod(method_AST.getFirstChild(),currentContext,1);
      if(!b) 
      	System.out.println(" Not Passed");
      else 
      	System.out.println(" Passed");
	    
    }//end for
    return true ;
  }//end method
    
    

  /**
   * @return (n-th element of 'S') mod 'currentContext'.getA()
   */
  public static boolean _mod(AST body_S, ASTContext currentContext, int n)
    throws assignable.exception.ContextException{
    
    AST method_instr = Util.getMethodBody(body_S,n);
	
    if(method_instr != null){
      if(isStatement(method_instr))
	return _modSTM(method_instr,currentContext) &&
	  _mod(body_S,currentContext,++n);
      else{
	boolean tempRes;
	//Special case when 'method_instr' is a variable declaration
	//T x = e modEXP Y;
	if(method_instr.getType() == JavaTokenTypes.VAR_DECL){
	  AST left_ast;
	  switch(method_instr.getFirstChild().getNextSibling().getType()){
	  case JavaTokenTypes.DIMS: //short [] y = null;
	    left_ast = astFactory.dupList(method_instr.getFirstChild().getNextSibling().getNextSibling());
	    if(left_ast.getNextSibling() != null){
	      tempRes = _modEXP(astFactory.dupTree(left_ast.getNextSibling().getNextSibling()),currentContext);//e modEXP Y
	      if(!tempRes)
		return tempRes;
	    }
	  default: //int x = 8; //short y [] = null;
	    left_ast = astFactory.dupList(method_instr.getFirstChild().getNextSibling());
	    if(left_ast.getNextSibling() != null)
	      if(left_ast.getNextSibling().getType() == JavaTokenTypes.DIMS){
		if(left_ast.getNextSibling().getNextSibling() != null){
		  tempRes = _modEXP(astFactory.dupTree(left_ast.getNextSibling().getNextSibling().getNextSibling()),currentContext);
		  if(!tempRes)
		    return tempRes;		  
		}
	      }
	      else{
		if(left_ast.getNextSibling()!= null){
		  tempRes = _modEXP(astFactory.dupTree(left_ast.getNextSibling().getNextSibling()),currentContext);
		  if(!tempRes)
		    return tempRes;
		}
	      }
	  }
	  
	  //add the variable to the context and \fields_of(\reach(var)) to modifies
	  currentContext.addMethVar(astFactory.dupTree(method_instr));
	  
	  AST ast_modifies = astFactory.create(JavaTokenTypes.ASSIGNABLE_KEYWORD,"modifies"),
	      ast_fields_of = astFactory.create(JavaTokenTypes.T_FIELDS_OF,"\\fields_of"),
	      ast_reach = astFactory.create(JavaTokenTypes.T_REACH,"\\reach");
	      
	  ast_reach.setFirstChild(astFactory.dupTree(method_instr.getFirstChild().getNextSibling()));
	  ast_fields_of.setFirstChild(ast_reach);
	  ast_modifies.setFirstChild(ast_fields_of);
	  
	  currentContext.addModifies(ast_modifies);
	  //
	  
	  tempRes = _mod(body_S,currentContext,++n);		
	  
	  //remove what was added previously
	  currentContext.removeMethVar();	  
	  currentContext.removeModifies();

	  return tempRes;
	}
	else return _modEXP(method_instr,currentContext) &&
	       _mod(body_S,currentContext,++n);
      }
    }
    else
      return true;
  }//end method
    
    
  /**
     * @return 'method_instr' modSTM 'currentContext'.getA()
     */
    public static boolean _modSTM(AST method_instr, ASTContext currentContext)
	throws assignable.exception.ContextException{
	AST C, E, I, U, S, T, F,
	    ast_tmp;

	switch(method_instr.getType()){
	    //try {S} catch(E) {T} finally {F}
	case JavaTokenTypes. LITERAL_try:
	    //finding the instructions S

	    S = astFactory.create(0,"ROOT");
	    method_instr = method_instr.getFirstChild();
	    
	    S.addChild(astFactory.dupTree(method_instr));//LCURLY
	    method_instr = method_instr.getNextSibling();
		
	    while(method_instr.getType() != JavaTokenTypes.RCURLY){
		S.addChild(astFactory.dupTree(method_instr));
		method_instr = method_instr.getNextSibling();
	    }
	    S.addChild(astFactory.dupTree(method_instr));//RCURLY
	    method_instr = method_instr.getNextSibling(); //catch sibling

	    ast_tmp = astFactory.dupList(method_instr.getFirstChild());

	    //:      |-- [94]
	    //:   |  |  |--Exception [113]
	    //:   |  |  `--e [113]
	    E = astFactory.dupTree(ast_tmp);
	
	    //finding the instructions T
	    T = astFactory.create(0,"ROOT");
	    ast_tmp = ast_tmp.getNextSibling();
	    
	    T.addChild(astFactory.dupTree(ast_tmp));//LCURLY
	    ast_tmp = ast_tmp.getNextSibling();
		
	    while(ast_tmp.getType() != JavaTokenTypes.RCURLY){
		T.addChild(astFactory.dupTree(ast_tmp));
		ast_tmp = ast_tmp.getNextSibling();
	    }
	    T.addChild(astFactory.dupTree(ast_tmp));//RCURLY
	    
	    method_instr = method_instr.getNextSibling();
	    if(method_instr != null){ 
		//finding the instructions F
		F = astFactory.create(0,"ROOT");
		method_instr = method_instr.getNextSibling();
	    
		F.addChild(astFactory.dupTree(method_instr));//LCURLY
		method_instr = method_instr.getNextSibling();
		
		while(method_instr.getType() != JavaTokenTypes.RCURLY){
		    F.addChild(astFactory.dupTree(method_instr));
		    method_instr = method_instr.getNextSibling();
		}
		F.addChild(astFactory.dupTree(method_instr));//RCURLY
		return _TRY_CATCH_FINALLY(S.getFirstChild(),E,T.getFirstChild(),F.getFirstChild(),currentContext);
	    }
	    else
		return _TRY_CATCH(S.getFirstChild(),E,T.getFirstChild(),currentContext);
	    
	    //if C then {S} {else T}
	case JavaTokenTypes.LITERAL_if:
	    //finding the guard C
	    C = astFactory.dupTree(method_instr.getFirstChild());
	    
	    //finding the instructions S
	    S = astFactory.create(0,"ROOT");
	    method_instr = method_instr.getFirstChild().getNextSibling();
	    
	    if(method_instr.getType() == JavaTokenTypes.LCURLY){
		S.addChild(astFactory.dupTree(method_instr));
		method_instr = method_instr.getNextSibling();
		
		while(method_instr.getType() != JavaTokenTypes.RCURLY){
		    S.addChild(astFactory.dupTree(method_instr));
		    method_instr = method_instr.getNextSibling();
		}
	    } 
	    else{
		S.addChild(astFactory.create(JavaTokenTypes.LCURLY,"{"));
		S.addChild(astFactory.dupTree(method_instr));
	    }
	    
	    S.addChild(astFactory.create(JavaTokenTypes.RCURLY,"}"));
	    
	    //finding the instructions T
	    T = astFactory.create(0,"ROOT");
	    method_instr = method_instr.getNextSibling();
	    
	    if(method_instr != null){
		if(method_instr.getType() == JavaTokenTypes.LCURLY){
		    T.addChild(astFactory.dupTree(method_instr));
		    method_instr = method_instr.getNextSibling();
		    
		    while(method_instr.getType() != JavaTokenTypes.RCURLY){
			T.addChild(astFactory.dupTree(method_instr));
			method_instr = method_instr.getNextSibling();
		    }
		}
		else{
		    T.addChild(astFactory.create(JavaTokenTypes.LCURLY,"{"));
		    T.addChild(astFactory.dupTree(method_instr));
		}
	    
		T.addChild(astFactory.create(JavaTokenTypes.RCURLY,"}"));
	    }
	    
	    if(T.getFirstChild() != null)
		return _IF_THEN_ELSE(C,S.getFirstChild(),T.getFirstChild(),currentContext) ;
	    else
		return _IF_THEN(C,S.getFirstChild(),currentContext) ;
	    
	    //while C {S}
	case JavaTokenTypes.LITERAL_while:
	    //finding the guard C
	    method_instr = method_instr.getFirstChild().getNextSibling().getNextSibling();
	    C = astFactory.dupTree(method_instr);

	    //finding the instructions S
	    S = astFactory.create(0,"ROOT");
	    method_instr = method_instr.getFirstChild().getNextSibling();
	    
	    if(method_instr.getType() == JavaTokenTypes.LCURLY){
		S.addChild(astFactory.dupTree(method_instr));
		method_instr = method_instr.getNextSibling();
		
		while(method_instr.getType() != JavaTokenTypes.RCURLY){
		    S.addChild(astFactory.dupTree(method_instr));
		    method_instr = method_instr.getNextSibling();
		}
	    } 
	    else{
		S.addChild(astFactory.create(JavaTokenTypes.LCURLY,"{"));
		S.addChild(astFactory.dupTree(method_instr));
	    }
	    
	    S.addChild(astFactory.create(JavaTokenTypes.RCURLY,"}"));	    
	    return _WHILE(C,S.getFirstChild(),currentContext);
	    
	    //for(I;C;U){S}
	case JavaTokenTypes.LITERAL_for:
	    I = method_instr.getFirstChild().getFirstChild();
	    C = astFactory.dupTree(method_instr.getFirstChild().getNextSibling().getFirstChild());
	    U = astFactory.dupTree(method_instr.getFirstChild().getNextSibling().getNextSibling().getFirstChild());
	    
	    //finding the instructions S
	    S = astFactory.create(0,"ROOT");
	    method_instr = method_instr.getFirstChild().getNextSibling().getNextSibling().getNextSibling().getNextSibling().getNextSibling();
	    if(method_instr.getType() == JavaTokenTypes.LCURLY){
		S.addChild(astFactory.dupTree(method_instr));
		method_instr = method_instr.getNextSibling();
		
		while(method_instr.getType() != JavaTokenTypes.RCURLY){
		    S.addChild(astFactory.dupTree(method_instr));
		    method_instr = method_instr.getNextSibling();
		}
	    } 
	    else{
		S.addChild(astFactory.create(JavaTokenTypes.LCURLY,"{"));
		S.addChild(astFactory.dupTree(method_instr));	    
	    }
	    
	    S.addChild(astFactory.create(JavaTokenTypes.RCURLY,"}"));
	    
	    return _FOR(I,C,U,S.getFirstChild(),currentContext);
	    
	    //return
	    //return E
	case JavaTokenTypes.LITERAL_return:
	    method_instr = method_instr.getFirstChild() ;
	    if(method_instr == null)
		return _RETURN(currentContext) ;
	    else
		return _RETURN_EXP(astFactory.dupTree(method_instr),currentContext);
	    
	    //break
	    //break lbl;
	case JavaTokenTypes.LITERAL_break:
	    method_instr = method_instr.getFirstChild() ;
	    if(method_instr == null)
		return _BREAK(currentContext) ;
	    else
		return _BREAK_LABEL(astFactory.dupTree(method_instr),currentContext);
	    
	    //throws E
	case JavaTokenTypes.LITERAL_throw:
	    method_instr = method_instr.getFirstChild();
	    return _THROWS_EXP(astFactory.dupTree(method_instr),currentContext);
	    
	    //continue
	case JavaTokenTypes.LITERAL_continue:
	    return _CONTINUE(currentContext);
	default:
	    return false ;
	}
    }

    //VAR-DECL
    //VAR-DECL-ASS
    //ASSIGNMENT
    //PREF-PLUS
    //PREF-MINUS
    //POSTF-PLUS
    //POSTF-MINUS
    //BINARY
    //CAST
    //CONDITIONAL
    //UNARY
    //INSTANCE
    //EXP-TO-PE

    /**
     * @return 'e' modEXP 'currentContext'.getA().
     */
    public static boolean _modEXP(AST e, ASTContext currentContext)
	throws assignable.exception.ContextException{	
	boolean tempRes = true;
	
	switch(e.getType()){
////	    //VAR-DECL
////	    //VAR-DECL-ASS
////	case JavaTokenTypes.VAR_DECL:
////	    AST left_ast = astFactory.dupList(e.getFirstChild().getNextSibling());
////	    
////	    //T x = e modEXP Y;
////	    if(left_ast.getNextSibling()!= null){		    
////		tempRes = _in_PRIME(astFactory.dupTree(left_ast), currentContext,Y) && //x in' Y
////		    _modEXP(astFactory.dupTree(left_ast.getNextSibling().getNextSibling()),currentContext,Y);//e modEXP Y
////	    }
////	    break;
	    
	    //ASSG
	case JavaTokenTypes.ASSIGN: // =
	case JavaTokenTypes.ADDITIVE_ASSIGNMENT_OP: // += -=
	case JavaTokenTypes.MULTIPLICATIVE_ASSIGNMENT_OP: // *= /= %=
	case JavaTokenTypes.BITWISE_ASSIGNMENT_OP: //&=
	  tempRes = _in_PRIME(astFactory.dupTree(e.getFirstChild()),currentContext) & //e1 \in' Y
	    _modPE(astFactory.dupTree(e.getFirstChild()),currentContext) & //e1 modPE Y
	    _modEXP(astFactory.dupTree(e.getFirstChild().getNextSibling()),currentContext); //e2 modEXP Y
	    break;
	    
	    //PREF-PLUS
	    //PREF-MINUS
	    //POSTF-PLUS
	    //POSTF-MINUS
	case JavaTokenTypes.POST_INCREMENT_OP: // e++, e--
	case JavaTokenTypes.PRE_INCREMENT_OP: // --e, ++e
	    tempRes = _in_PRIME(astFactory.dupTree(e.getFirstChild()),currentContext) && //e \in' Y
		_modPE(astFactory.dupTree(e.getFirstChild()),currentContext); //e modPE Y
	    break;
	    
	    //BINARY
	case JavaTokenTypes.RELATIONAL_OP: // <, >, <=, >=,
	case JavaTokenTypes.EQUALITY_OP: // ==, !=
	case JavaTokenTypes.LOGICAL_OP: // ||, &&
	case JavaTokenTypes.ADDITIVE_OP: // +, -
	case JavaTokenTypes.MULTIPLICATIVE_OP: // %, *, /
	case JavaTokenTypes.BITWISE_OP: //&
	    tempRes = _modEXP(astFactory.dupTree(e.getFirstChild()),currentContext) && //e1 modPE Y
		_modEXP(astFactory.dupTree(e.getFirstChild().getNextSibling()),currentContext); //e2 modEXP Y
	    break;

	//CAST
	case JavaTokenTypes.CAST:
	    tempRes = _modEXP(astFactory.dupTree(e.getFirstChild().getNextSibling()),currentContext) ; //e2 modEXP Y
	    break;

	//CONDITIONAL
	case JavaTokenTypes.QUESTION:
	    tempRes = _modEXP(astFactory.dupTree(e.getFirstChild()),currentContext) && //e1 modEXP Y
		_modEXP(astFactory.dupTree(e.getFirstChild().getNextSibling()),currentContext) && //e2 modEXP Y
		_modEXP(astFactory.dupTree(e.getFirstChild().getNextSibling().getNextSibling()),currentContext); //e3 modEXP Y
	    break;

	    //UNARY
	case JavaTokenTypes.UNARY_NUMERIC_OP: // -e, +e, ~e
	case JavaTokenTypes.LNOT: // !e
	    tempRes = _modEXP(astFactory.dupTree(e.getFirstChild()),currentContext); //e modEXP Y
	    break;

	//INSTANCE
	case JavaTokenTypes.LITERAL_instanceof:
	    tempRes = _modEXP(astFactory.dupTree(e.getFirstChild()),currentContext) ; //e modEXP Y
	    break ;

	//EXP-TO-PE
	default:
	    tempRes = _modPE(astFactory.dupTree(e),currentContext) ; //e modPE Y	    
	}
	
	if(!tempRes){
	    String s = edu.iastate.cs.jml.checker.ASTtoStringConverter.convert(e);
	    System.out.println("Warning: expression\n" +s +"may contain a problem");
	}
	return tempRes ;
    }//end method
    
            
    /**
     * @return 'try {S} catch(E) {T}' modSTM 'currentContext'.getA().
     */
    public static boolean _TRY_CATCH(AST S, AST E, AST T, ASTContext currentContext)
	throws assignable.exception.ContextException{
	
	return _mod(S,currentContext,1) &&
	    _mod(T,currentContext,1);
    }//end method

    
    /**
     * @return 'try {S} catch(E) {T} finally {F}' modSTM 'currentContext'.getA().
     */
    public static boolean _TRY_CATCH_FINALLY(AST S, AST E, AST T, AST F, ASTContext currentContext)
	throws assignable.exception.ContextException{

	return _TRY_CATCH(S,E,T,currentContext) &&
	    _mod(F,currentContext,1);
    }//end method
    
    
    /**
     * @return 'if(C){S}' modSTM 'currentContext'.getA().
     */
    public static boolean _IF_THEN(AST C, AST S, ASTContext currentContext)
	throws assignable.exception.ContextException{
	
	return _modEXP(C,currentContext) &&
	    _mod(S,currentContext,1);
    }//end method    
    
        
    /**
     * @return 'if(C){S}else{T}' modSTM 'currentContext'.getA().
     */
    public static boolean _IF_THEN_ELSE(AST C, AST S, AST T, ASTContext currentContext)
	throws assignable.exception.ContextException{
	
	return _IF_THEN(C,S,currentContext) &&
	    _mod(T,currentContext,1);
    }//end method
    
    /**
     * @return 'while(C){S}' modSTM 'currentContext'.getA().
     */
    public static boolean _WHILE(AST C, AST S, ASTContext currentContext)
	throws assignable.exception.ContextException{

	return _modEXP(C,currentContext) &&
	    _mod(S,currentContext,1);
    }//end method

    
    /**
     * @return 'for(I;C;U){S}' modSTM 'currentContext'.getA().
     */    
    public static boolean _FOR(AST I, AST C, AST U, AST S, ASTContext currentContext)
	throws assignable.exception.ContextException{
	
	AST F = astFactory.create(0,"ROOT");
	F.addChild(I);
	F.addChild(C);
	F.addChild(U);
	F.addChild(S);
	
	return _mod(F.getFirstChild(),currentContext,1);
    }//end method
    

    /**
     * @return 'break L' modSTM 'currentContext'.getA().
     */   
    public static boolean _BREAK_LABEL(AST L, ASTContext currentContext){
	return true ;
    }//end method


    /**
     * @return 'break' modSTM 'currentContext'.getA().
     */   
    public static boolean _BREAK(ASTContext currentContext){
	return true ;
    }//end method
    

    /**
     * @return 'continue L' modSTM 'currentContext'.getA().
     */   
    public static boolean _CONTINUE_LABEL(AST L, ASTContext currentContext){
	return true ;
    }//end method
    

    /**
     * @return 'continue' modSTM 'currentContext'.getA().
     */
    public static boolean _CONTINUE(ASTContext currentContext){
	return true ;
    }//end method
    
   
    /**
     * @return 'return' modSTM 'currentContext'.getA().
     */
    public static boolean _RETURN(ASTContext currentContext){
	return true ;
    }//end method
    
    
    /**
     * @return 'return E' modSTM 'currentContext'.getA().
     */
    public static boolean _RETURN_EXP(AST E, ASTContext currentContext)
	throws assignable.exception.ContextException{
	return _modEXP(E,currentContext) ; //e modEXP
    }//end method
        
    
    /**
     * @return 'throw E' modSTM 'currentContext'.getA().
     */
    public static boolean _THROWS_EXP(AST E, ASTContext currentContext)
	throws assignable.exception.ContextException{
	return _modEXP(E,currentContext) ; //e modEXP Y
    }//end method
    
        
    /**
     * @param e = e1,...,en.
     * @return 'e1' modEXP 'currentContext'.getA() & ... &  'en' modEXP 'currentContext'.getA().
     */
    public static boolean _modEXP_LIST(AST e, ASTContext currentContext)
	throws assignable.exception.ContextException{
    	boolean tempRes ;
    	if(e != null){
    	    AST expTemp = astFactory.dupTree(e) ;
    	    // qn,....,q1
    	    while(expTemp.getType() == JavaTokenTypes.COMMA){
    		tempRes = _modEXP(expTemp.getFirstChild().getNextSibling(),currentContext) ; //qn
    		if(!tempRes)  return false ;
    		expTemp = expTemp.getFirstChild() ;
    	    }	    
    	    return _modEXP(expTemp,currentContext) ; //q1 
    	}
    	return true ;	
    }//end method
    
    //EXP-PRM
    //PE-TO-PS
    //EXP-METH
    //PE-TO-PE
    /**
     * @return 'e' modPE 'currentContext'.getA().
     */
    public static boolean _modPE(AST e, ASTContext currentContext)
	throws assignable.exception.ContextException{
	boolean tempRes ;
	switch(e.getType()){
	case JavaTokenTypes.LPAREN:
	    //e = e1 . m(o)
	    if(e.getFirstChild().getType() == JavaTokenTypes.DOT){
		//METH-INV
		tempRes = _modifies(astFactory.dupTree(e),currentContext) &&//[e1.m(o).modifies][o\q] << Y
		    _modPE(astFactory.dupTree(e.getFirstChild().getFirstChild()),currentContext) && //e1 modPE Y
		    _modEXP_LIST(astFactory.dupTree(e.getFirstChild().getNextSibling()),currentContext); //q_ modEXP_LIST Y
	    }
	    //e = m(o)
	    else {
		//PE-TO-PRM
		tempRes = _modPRM(astFactory.dupTree(e),currentContext) ; //e modPRM Y
	    }	    
	    return tempRes;
	case JavaTokenTypes.LBRACK:
	    //e = e1 . a[e3]
	    if(e.getFirstChild().getType() == JavaTokenTypes.DOT){
		//apply 'a[e3] modPS Y' here, because it's not simple passing down a[e3]
		tempRes = _modEXP(astFactory.dupTree(e.getFirstChild().getNextSibling()),currentContext) && //a[e3] modPS Y
		    _modPE(astFactory.dupTree(e.getFirstChild().getFirstChild()),currentContext); //e1 modPE Y
	    }
	    //e = a[e3]
	    else{
		//apply 'a[e3] modPS Y' here, because it's not simple passing down a[e3]
		tempRes = _modEXP(astFactory.dupTree(e.getFirstChild()),currentContext);
	    }
	    return tempRes;
	case JavaTokenTypes.DOT:
	    //e = e1 . e2
	    //PE-TO-PS
	    tempRes = _modPE(astFactory.dupTree(e.getFirstChild()),currentContext) && //e1 modPE Y
		_modPS(astFactory.dupTree(e.getFirstChild().getNextSibling()),currentContext) ; //e2 modPS Y
	    return tempRes;
	default:
	    //PE-TO-PRM
	    tempRes = _modPRM(astFactory.dupTree(e),currentContext) ; //e modPRM Y
	    return tempRes;
	}//end switch
    }//end method
    

    //IDENT-FIELD
    //ARRAY-FIELD
    //???INTERV-ARRAY
    //???TIMES-ARRAY
    //STATIC
    //CONST
    //SUPER
    //THIS
    //METH
    //NEW-EXP
    /**
     * @return 'e' modPRM 'currentContext'.getA().
     */
    public static boolean _modPRM(AST e, ASTContext currentContext)
	throws assignable.exception.ContextException{
	boolean tempRes ;
	
	switch(e.getType()){
	case JavaTokenTypes.IDENT : //IDENT-FIELD 
	case JavaTokenTypes.LITERAL_static : //STATIC
	case JavaTokenTypes.NUM_INT : //CONST
	case JavaTokenTypes.NUM_FLOAT : //CONST
	case JavaTokenTypes.STRING_LITERAL : //CONST
	case JavaTokenTypes.LITERAL_true : //CONST
	case JavaTokenTypes.LITERAL_false : //CONST
	case JavaTokenTypes.LITERAL_super : //SUPER
	case JavaTokenTypes.LITERAL_this : //THIS
	case JavaTokenTypes.LITERAL_null: //NULL
	    return true ;
	case JavaTokenTypes.LBRACK: // ARRAY-FLD a[e]
	    tempRes = _modEXP(astFactory.dupTree(e.getFirstChild().getNextSibling()),
			      currentContext);
	    return tempRes; 
	case JavaTokenTypes.LPAREN: //METH
	    tempRes =_modifies(astFactory.dupTree(e),currentContext) && //[m(o).modifies][o\q] << Y
		_modEXP_LIST(astFactory.dupTree(e.getFirstChild().getNextSibling()),currentContext) ; //q_ modEXP_ Y
	    return tempRes;
	case JavaTokenTypes. LITERAL_new: //NEW-EXP//NEW-ARRAY
	    //e_ modEXP_ Y
	    tempRes = _modEXP_LIST(astFactory.dupTree(e.getFirstChild().getNextSibling()),currentContext) ;
	    return tempRes;
	default: //error
	    return false;
	}
    }
    
    //IDENT-FLD
    //ARRAY-FLD
    //THIS
    //SUPER
    /**
     * @return 'e' modPS 'currentContext'.getA().
     */
    public static boolean _modPS(AST e, ASTContext currentContext){	
	switch(e.getType()){
	case JavaTokenTypes.IDENT : //IDENT-FIELD
	case JavaTokenTypes.LITERAL_this : //THIS
	case JavaTokenTypes.LITERAL_super: //SUPER
	    return true;
	    
	    //this case is applied in the modPE rules.
	    /*case JavaTokenTypes.LBRACK: // IN-ARR-FLD a[e]
	    tempRes = _modEXP(astFactory.dupTree(e.getFirstChild().getNextSibling()),
			      contexts);
			      return tempRes; */
	default:
	    return false;
	}
    }
    
    
    /**
     * @param 'e' = e1.m(q) || 'e' = m(q).
     * @return [e1.m(o).modifies][o\q][this\e1] << Y.
     */
    public static boolean _modifies(AST e, ASTContext currentContext)
	throws assignable.exception.ContextException{
	
	// e = e1.m(q)
	if(e.getFirstChild().getType() == JavaTokenTypes.DOT){	    
	    //getting the modifies declaration of the method 'e'
	    //v == [e1.m(o).modifies][o\q][this\e1] 
	    
	    Vector v = currentContext.getAssignable(astFactory.dupTree(e.getFirstChild().getFirstChild()),//e1
						  e.getFirstChild().getFirstChild().getNextSibling().getText(),//m
						  astFactory.dupTree(e.getFirstChild().getNextSibling()),//q
						  contexts);
	    //v<<Y ?
	    int v_size = v.size();
	    for(int i=0;i<v_size;i++){
		boolean tempRes = Assignable._in_PRIME(astFactory.dupTree(((AST)(v.elementAt(i))).getFirstChild()),
						       currentContext
						       );
		if(tempRes==false)
		    return false;
	    }//end for
	    return true ;
	}
	//e = m(q);
	else{
	    //getting the modifies declaration of the method 'currentContext'
	    //v == [e1.m(o).modifies][o\q][this\e1] 
	    Vector v = currentContext.getAssignable(null,//e1
						  e.getFirstChild().getText(),//m
						  e.getFirstChild().getNextSibling(),//q
						  contexts
						  );
	    //v<<Y ?
	    int v_size = v.size();
	    for(int i=0;i<v_size;i++){
		boolean tempRes = Assignable._in_PRIME(astFactory.dupTree(((AST)(v.elementAt(i))).getFirstChild()),
						       currentContext
						       );
		if(tempRes==false)
		    return false;
	    }//end for
	    return true ;
	}//end if ... else
    }//end method
    
    
    /**
     * @return 'e' \in' 'currentContext'.getA().
     */
    public static boolean _in_PRIME(AST e, ASTContext currentContext)
	throws assignable.exception.ContextException{	

	//calculating Y
	Vector Y = currentContext.getA();
	Iterator iter = Y.iterator();
	
	//formating 'e'
	AST ast_e = currentContext.formatExp(e);
	
	//e = e.a[e1]
	//e = a[e1]
	//e = e.x
	while(iter.hasNext()){
	    AST astTemp = astFactory.dupList((AST)(iter.next()));
	    //IN-VAR
	    //IN-ARR
	    //IN-EXP
	    //IN-EXP-ARR
	    if(astTemp.equalsTree(ast_e))
		return true;
	    
	    if(astTemp.getType() == JavaTokenTypes.LBRACK){
		if(
		   astTemp.getFirstChild().getNextSibling().getType() == JavaTokenTypes.STAR_ELEMS &&
		   astTemp.getFirstChild().equalsTree(ast_e.getFirstChild())
		   )
		    return true;
	    }
	    else if(astTemp.getType() == JavaTokenTypes.T_FIELDS_OF){ //astTemp == \fields_of(ast_exp)
		AST ast_exp = astFactory.dupTree(astTemp.getFirstChild());
	
		//IN-FLD-VAR
		//IN-FLD-ARR
		//IN-FLD-EXP-VAR
		//IN-FLD-EXP-ARR
		Vector v_fields = new Vector();
		
		currentContext.fieldsOfExp(ast_exp,contexts,v_fields);
		for(int i=0;i<v_fields.size();i++){
		    AST ast_elm = (AST)(v_fields.elementAt(i));
		    if((ast_elm.getType()==JavaTokenTypes.LBRACK)){
			astFactory.dupTree(ast_e.getFirstChild()).equalsTree(astFactory.dupTree(ast_elm.getFirstChild()));
			if(ast_elm.getFirstChild().getNextSibling().getType() == JavaTokenTypes.STAR_ELEMS)
			    return true;
		    }
		    if(ast_elm.equalsTree(ast_e))
			return true;
		}//end for
	    }
	}//end while
	return false;
    }//end method    
    
    
    /**
     * @return isStatement(instruc)?true:false.
     */
    static boolean isStatement(AST instr){
	switch(instr.getType()){
	case JavaTokenTypes.LITERAL_if:
	case JavaTokenTypes.LITERAL_while:
	case JavaTokenTypes.LITERAL_break:
	case JavaTokenTypes.LITERAL_continue:
	case JavaTokenTypes.LITERAL_return:
	case JavaTokenTypes.LITERAL_throw:
	case JavaTokenTypes.SEMI:
	case JavaTokenTypes.LITERAL_try:
	case JavaTokenTypes.LITERAL_for:
	    return true;
	}//end switch
	return false;
    }//end method
    
}//end class
