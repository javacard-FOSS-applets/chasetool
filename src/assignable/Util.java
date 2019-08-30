package assignable;

import antlr.collections.AST;
import antlr.collections.impl.ASTArray;
import antlr.ASTFactory;
import java.util.Vector;
import java.util.Iterator;
import edu.iastate.cs.jml.checker.JavaTokenTypes;

/**
 * This class implements some basic functionalities used by the
 * other classes of the package assignable
 */
public class Util{
    private static ASTFactory astFactory = new ASTFactory();
    
    /*
     * @param t <TT>AST</TT> tree corresponding to a java program.
     * @param methods_AST Return the set of method declarations of <TT>t</TT> as a <TT>java.lang.Vector</TT> of
     * AST tree elements.
    */
    static void getMethDecl(AST t, Vector methods_AST){
	for(; t != null; t = t.getNextSibling()) {
	    if(t.getType() == JavaTokenTypes.METH// ||
	       //t.getType() == JavaTokenTypes.CONSTRUCTOR
	       )
		methods_AST.addElement(astFactory.dupTree(t));
	    Util.getMethDecl(t.getFirstChild(), methods_AST);
	}
    }//end method    
    
    
    /*
     * @param t <TT>AST</TT> tree corresponding to a java program.
     * @param v_AST Return the set of class variable declarations of <TT>t</TT> as a <TT>Vector</TT>of
     * <TT>AST</TT> tree elements.
     */
    static void getClassVarDecl(AST t, Vector v_AST){
	for(; t != null; t = t.getNextSibling()){
	    if(t.getType() == JavaTokenTypes.METH) 
		continue; //don't consider meth declarations
	    if(t.getType() == JavaTokenTypes.VAR_DECL)
		Util.formatVarDecl(astFactory.dupTree(t),v_AST);
	    
	    getClassVarDecl(t.getFirstChild(),v_AST);
	}
    }//end method
    
    
    /*
     * @param t <TT>AST method tree without the root node.
     * @param v_AST Return the set of parameters of <TT>t</TT> as a <TT>Vector</TT>of
     * <TT>AST</TT> tree elements.
     */
    static void getMethPars(AST t, Vector v_AST){
	for(; t != null; t = t.getNextSibling()){
	    if(t.getType() == JavaTokenTypes.PARAM){
		if(t.getFirstChild().getNextSibling().getType() != JavaTokenTypes.DIMS)
		    v_AST.addElement(astFactory.dupTree(t));
		//:|-- [94]
		//:|  |--byte [35]
		//:|  |--#dims# [59]
		//:|  |  `--[ [186]
		//:|  `--src [113]
		else{
		    AST ast_par = astFactory.create(JavaTokenTypes.PARAM,""),
			ast_brc = astFactory.create(JavaTokenTypes.LBRACK,"["),
			ast_lbl = astFactory.dupTree(t.getFirstChild().getNextSibling().getNextSibling());
		    
			ast_brc.setFirstChild(astFactory.dupTree(t.getFirstChild()));
			ast_brc.setNextSibling(ast_lbl);
			ast_par.setFirstChild(ast_brc);
		    //:|  |-- [94]
		    //:|  |  |--[ [186]
		    //:|  |  |  `--byte [35]
		    //:|  |  `--src [113]
		    v_AST.addElement(ast_par);
		}
	    }
	    else if(t.getType() == JavaTokenTypes.COMMA){
		Util.getMethPars(astFactory.dupTree(t.getFirstChild()),v_AST);
		Util.getMethPars(t.getFirstChild().getNextSibling(),v_AST);
	    }
	}//end for
    }//end method
    
        
    /*
     * @param t A set of <TT>AST</TT> instruction with out the rout.
     * @param n Some instruction in <TT>t</TT>.
     * @return The n-th instruction of t if any, otherwise null.
     */
    static AST getMethodBody(AST t, int n){
	while(t.getType() != JavaTokenTypes.LCURLY){
	    //if 't' is abstract method
	    if(t.getType() == JavaTokenTypes.SEMI)
	    	return null;
	    t = t.getNextSibling();
	}//end while
	t = t.getNextSibling();
	
	for(int i=n; i>0; i--){
	    if(t.getType() == JavaTokenTypes.RCURLY)
		return null;
	    if(i==1){
		//when we find a try-catch expression, we have to do something
		//different because try{...}catch(...){...} is not stored as a tree
		//rather than a tree list
		if(t.getType() == JavaTokenTypes.LITERAL_try){
		    Vector vect_try = new Vector(),
			vect_fin = new Vector();
		    while(t.getType() != JavaTokenTypes.RCURLY){
			vect_try.addElement(astFactory.dupTree(t));
			t = t.getNextSibling();
		    }//end while
		    vect_try.addElement(astFactory.dupTree(t));
		    
		    t = t.getNextSibling();
		    //the catch construct can be stored whitout any modifications
		    if(t.getType() ==JavaTokenTypes.LITERAL_catch)
			vect_try.addElement(astFactory.dupTree(t));
		    
		    t = t.getNextSibling();
		    if(t.getType() == JavaTokenTypes.LITERAL_finally){
			while(t.getType() != JavaTokenTypes.RCURLY){
			    vect_fin.addElement(astFactory.dupTree(t));
			    t = t.getNextSibling();
			}//end while
			vect_fin.addElement(astFactory.dupTree(t));
		    }//end if
		    
		    ASTArray try_arr = new ASTArray(vect_try.size()+vect_fin.size());
		    for(int k=0;k<vect_try.size();k++)
			try_arr.add((AST)(vect_try.elementAt(k)));
		    //
		    for(int k=0;k<vect_fin.size();k++)
			try_arr.add((AST)(vect_fin.elementAt(k)));
		    
		    return astFactory.make(try_arr);
		}//end if LITERAL_try
		else
		    return astFactory.dupTree(t);
	    }//end if(i==1)
	    else
		//skip try...catch
		if(t.getType() == JavaTokenTypes.LITERAL_try){
		    while(t.getType() != JavaTokenTypes.RCURLY)
			t = t.getNextSibling();

		    t = t.getNextSibling();		    
		    t = t.getNextSibling();
		    
		    if(t.getType() == JavaTokenTypes.LITERAL_finally)
			while(t.getType() != JavaTokenTypes.RCURLY)
			    t = t.getNextSibling();
		    
		    t=t.getNextSibling();
		}//end if LITERAL_try
		else
		    t=t.getNextSibling();
	}//end for
	
	return null;
    }//end method

    
    
    /*
     * @param e An AST tree separed by </TT>token_sep</TT>
     * :|  |  |--.[114]
     * :|  |  |  |--.[114]
     * :|  |  |  |  |--.[114]
     * :|  |  |  |  |  |--x[113]
     * :|  |  |  |  |  `--y[113]
     * :|  |  |  |  `--z[113]
     * :|  |  |  `--i[113]
     * @param token_sep A seperation token.
     * @param v_exp A </TT>Vector</TT> of AST trees containing the leaf of </TT>e</TT> <...., x,y,z,i>.
     */
    static void getExpandParam(AST e, int token_sep, Vector v_exp){
	if(e != null)
	    if(e.getType() == token_sep){
		Util.getExpandParam(astFactory.dupTree(e.getFirstChild()),token_sep, v_exp);
		Util.getExpandParam(astFactory.dupTree(e.getFirstChild().getNextSibling()),token_sep,v_exp);
	    }
	    else{
		v_exp.addElement(astFactory.dupTree(e));
	    }//if else
    }//end method 
    
    
    //return ast_exp after extracting ast_fchild (if any)
    //
//    static AST getUnfold(AST ast_exp, AST ast_fchild){
// 	//expand parameters
// 	Vector v_exp = new Vector();
// 	Util.getExpandParam(astFactory.dupTree(ast_exp),JavaTokenTypes.DOT,v_exp);
// 	
// 	if(ast_fchild.equalsTree((AST)(v_exp.elementAt(0)))){
// 	    v_exp.remove(0); //remove ast_fchild
// 	    AST ast_new_fchild = (AST)(v_exp.remove(0));
// 	    
// 	    return Util.getFold(v_exp,astFactory.create(JavaTokenTypes.DOT,"."),ast_new_fchild);
    //	}
    //	else return ast_exp;
    //    }


    /*
     * @param v_exp A </TT>Vector</TT> of AST elements.
     * @param ast_root The root node.
     * @param ast_fchild The first child.
     * @return A <TT>AST</TT> tree consisting of </TT>ast_root</TT> as root, </TT>ast_fchild</TT> as first child
     * and the elements of <TT>v_exp</TT> as other nodes.
     */
    static AST foldAST(Vector v_exp, AST ast_root, AST ast_fchild){
	if(v_exp.size() > 0){
	    ASTArray exp_arr = new ASTArray(3);
	    exp_arr.add(ast_root);
	    exp_arr.add(ast_fchild);
	    exp_arr.add((AST)(v_exp.elementAt(0)));
	    
	    AST ast_elem = astFactory.make(exp_arr);
	    for(int i=1;i<v_exp.size();i++){
		ASTArray res_arr = new ASTArray(3);
		res_arr.add(astFactory.create(JavaTokenTypes.DOT,"."));
		res_arr.add(ast_elem);
		res_arr.add((AST)(v_exp.elementAt(i)));
		
		ast_elem = astFactory.make(res_arr);
	    }//end for
	    return ast_elem;
	}
	else return ast_fchild;
    }//end method
    
    
    /*
     * @param t a variable declaration
     * :|  |  |--#vardecl#
     * :|  |  |  |--short
     * :|  |  |  `--,
     * :|  |  |     |--s2
     * :|  |  |     `--s3
     * @param v_AST The elements of </TT>t</TT>
     * :|  |  |--#vardecl#
     * :|  |  |  |--short
     * :|  |  |  |--s2
     *    
     * :|  |  |--#vardecl#
     * :|  |  |  |--short
     * :|  |  |  |--s3
     */
    private static void formatVarDecl(AST t, Vector v_AST){
	AST tt = null;
	//:|  |--#vardecl# [105]
	//:|  |  |--int [35]
	//:|  |  |--#dims# [59]
	//:|  |  |  `--[ [186]
	//:|  |  |--y [113]
	//:|  |  |--= [142]
	//:|  |  `--new [165]
	//:|  |     |--int [35]
	//:|  |     `--#dim_exprs# [60]
	//:|  |        |--[ [186]
	//:|  |        `--4 [334]
	if(t.getFirstChild().getNextSibling().getType() == JavaTokenTypes.DIMS){
		tt = astFactory.create(JavaTokenTypes.VAR_DECL,"#vardecl#");
		AST ast_brc = astFactory.create(JavaTokenTypes.LBRACK,"["),
		    ast_rest = astFactory.dupList(t.getFirstChild().getNextSibling().getNextSibling());

		ast_brc.setFirstChild(astFactory.dupTree(t.getFirstChild()));
		ast_brc.setNextSibling(ast_rest);
		// tt ==
		//:|  |--#vardecl# [105]
		//:|  |  |--[ [186]
		//:|  |  |  `--int [35]
		//:|  |  |--y [113]
		//:|  |  |--= [142]
		//:|  |  `--new [165]
		//:|  |     |--int [35]
		//:|  |     `--#dim_exprs# [60]
		//:|  |        |--[ [186]
		//:|  |        `--4 [334]
		tt.setFirstChild(ast_brc);
	}
	else
	    tt = astFactory.dupTree(t);

	if(tt.getFirstChild().getNextSibling().getType() == JavaTokenTypes.COMMA) 
	    expandVarDecl(tt.getFirstChild().getNextSibling(),
			     tt.getFirstChild(),
			     v_AST);
	//t == Type var;  or t ==  Type var = val;
	else
	    v_AST.addElement(tt);
    }//end method
    
    
    /*
     * @param t A variable declaration
     * |  |--#vardecl# [105]
     * :|  |  |--int [35]
     * :|  |  `--, [136]
     * :|  |     |--x [113]
     * :|  |     |--= [142]
     * :|  |     |--8 [334]
     * :|  |     |--y [113]
     * :|  |     |--= [142]
     * :|  |     `--9 [334]
     * @param v_AST The expanded trees of </TT>t</TT>
     *  |  |--#vardecl# [105]
     * :|  |  |--int [35]
     * :|     |  |--x [113]
     * :|        |--= [142]
     * :|        |--8 [334]
     *              
     *  |  |--#vardecl# [105]
     * :|  |  |--int [35]
     * :|     |  |--y [113]
     * :|        |--= [142]
     * :|        |--9 [334]       
     */
    private static void expandVarDecl(AST t, AST expand_type, Vector v_AST){
	int i_times = -1 ;
	AST tt, ast_val, ast_assi, ast_id, ast_type, ast_ast;

	if(t.getFirstChild().getType() == JavaTokenTypes.COMMA){
	    i_times = 1;
	    tt = t.getFirstChild().getNextSibling();
	}else{  
	    i_times = 2;
	    tt = t.getFirstChild();
	}
	
	for(int i=0; i<i_times; i++){
	    if(tt.getNextSibling() != null &&
	       tt.getNextSibling().getType() == JavaTokenTypes.ASSIGN){		
		//value AST
		ast_val = astFactory.dupTree(tt.getNextSibling().getNextSibling());
		//assignment AST
		ast_assi = astFactory.create(JavaTokenTypes.ASSIGN,"=");
		ast_assi.setNextSibling(ast_val);
		
		//identifier AST
		ast_id = astFactory.create(JavaTokenTypes.IDENT,tt.getText());
		ast_id.setNextSibling(ast_assi);

		//next tree
		if(i_times == 2)
		    tt = tt.getNextSibling().getNextSibling().getNextSibling();
	    }else{
		//identifier AST
		ast_id = astFactory.create(JavaTokenTypes.IDENT,tt.getText());
		
		//next tree
		if(i_times == 2)
		    tt = tt.getNextSibling();
		    
	    }//end if else
	    
	    //type AST
	    ast_type = astFactory.dupTree(expand_type);
	    ast_type.setNextSibling(ast_id);
	    
	    //ast
	    ast_ast = astFactory.create(JavaTokenTypes.VAR_DECL,"#vardecl#");
	    ast_ast.setFirstChild(ast_type);
	    
	    //storing tree
	    v_AST.addElement(astFactory.dupTree(ast_ast));
	}//end for
	
	//expand the first child
	if(i_times == 1) 
	    expandVarDecl(t.getFirstChild(),expand_type,v_AST);	    
    }//end method

}//end class
