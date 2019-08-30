package assignable;

import java.util.Vector;
import java.util.Iterator;

import antlr.CommonAST;
import antlr.ASTFactory;
import antlr.collections.AST;
import antlr.collections.impl.ASTArray;
import edu.iastate.cs.jml.checker.JavaTokenTypes;
import edu.iastate.cs.jml.checker.ASTtoStringConverter;

/**
 * This class represents a context of java instruction. For a
 * instructions, A Java context is formed of the assignable clauses of
 * the method where the instruction appears, parameters and local
 * variables.
 */
public class ASTContext extends CommonAST{
    private static ASTFactory astFactory = new ASTFactory();
    private AST 
	pack_sig_ast, clas_sig_ast, exte_sig_ast,
	impl_sig_ast, meth_sig_ast, meth_return_type;
    private Vector
	class_var_tree = new Vector(),
	meth_var_tree = new Vector(),
	par_tree = new Vector(),
	modifies_tree = new Vector();
    
    /**
     * @param t a <TT>AST</TT> program.
     * @param method_ast a method <TT>AST</TT> tree occuring in <TT>t</TT>.
     */ 
    public ASTContext(AST t, AST method_ast){
	
	InitializePackSig(astFactory.dupList(t));
	InitializeClassSig(astFactory.dupList(t));
	InitializeExtendsSig(astFactory.dupList(t));
	InitializeImplementsSig(astFactory.dupList(t));
	InitializeMethReturnSig(astFactory.dupTree(method_ast));
	InitializeMethSig(astFactory.dupTree(method_ast));
		
	//values by default
	if(pack_sig_ast== null) 
	    pack_sig_ast = astFactory.create(JavaTokenTypes.LITERAL_package,"package");
	if(exte_sig_ast == null)
	    exte_sig_ast = astFactory.create(JavaTokenTypes.LITERAL_extends,"extends");
	if(impl_sig_ast == null)
	    impl_sig_ast = astFactory.create(JavaTokenTypes.LITERAL_implements,"implements");
		
	//finding out class variable declarations.
	//That's stored in class_var_decl_ast
	Util.getClassVarDecl(astFactory.dupList(t),class_var_tree);
	
	//finding out method variable declarations.
	//That's stored in meth_var_tree
	//Util.getMethVarDecl(astFactory.dupList(method_ast.getFirstChild()),meth_var_tree);
	
	//finding out method parameters.
	//That's stored in par_tree
	Util.getMethPars(astFactory.dupList(method_ast.getFirstChild()),par_tree);
	
	//finding out modifies declarations
	//That's stored in modifies_tree
	this.getModif(astFactory.dupTree(method_ast));
	
    }//end method
    
    
    /**
     * Set <TT>pack_sig_ast</TT> to package signature of <TT>t</TT>.
     *
     * @param t A <TT>AST</TT> java program.
     */
    private void InitializePackSig(AST t){
	for(; t != null; t = t.getNextSibling()){
	    if(t.getType() == JavaTokenTypes.LITERAL_package){
		this.pack_sig_ast = astFactory.dupTree(t);
		break; //break for
	    }
	}//end for
    }//end method


    /**
     * Set <TT>exte_sig_ast</TT> to the extends signature of <TT>t</TT>.
     *
     * @param t A <TT>AST</TT> java program.
     */
    private void InitializeExtendsSig(AST t){
	for(; t != null; t = t.getNextSibling()){
	    if(t.getType() == JavaTokenTypes.LITERAL_extends){
		this.exte_sig_ast =astFactory.create();
		this.exte_sig_ast.setText("extends");
		this.exte_sig_ast.setType(JavaTokenTypes.LITERAL_extends);
		this.exte_sig_ast.setFirstChild(astFactory.dupTree(t.getNextSibling()));
		break; //break for
	    }
	    else{
		InitializeExtendsSig(t.getFirstChild());
		if(this.exte_sig_ast!= null)
		    break;
	    }
	}//end for
    }//end of method
    
    
    /**
     * Set <TT>impl_sig_ast</TT> the implements signature of <TT>t</TT>.
     *
     * @param t A <TT>AST</TT> java program
     */
    private void InitializeImplementsSig(AST t){
	for(; t != null; t = t.getNextSibling()){
	    if(t.getType() == JavaTokenTypes.LITERAL_implements){
		this.impl_sig_ast = astFactory.create();
		this.impl_sig_ast.setText("implements");
		this.impl_sig_ast.setType(JavaTokenTypes.LITERAL_implements);
		this.impl_sig_ast.setFirstChild(astFactory.dupTree(t.getFirstChild()));
	    }
	    else{
		InitializeImplementsSig(t.getFirstChild());
		if(this.impl_sig_ast != null)
		    break;//break for
	    }
	}//end for
    }//end of method
    
    
    /**
     * Set <TT>clas_sig_ast</TT> to the class signature of <TT>t</TT>
     *
     * @param t A <TT>AST</TT> java program
     */
    private void InitializeClassSig(AST t){
	for(; t != null; t = t.getNextSibling()) 
	    if(t.getType() == JavaTokenTypes.LITERAL_class ||
	       t.getType() == JavaTokenTypes.LITERAL_interface){
		this.clas_sig_ast = astFactory.create(t);
		this.clas_sig_ast.setFirstChild(astFactory.dupTree(t.getFirstChild()));
		break;
	    }
    }//end of method

    /**
     * Set <TT>meth_sig_ast</TT> to method signature of <TT>t</TT>.
     *
     * @param t An <TT>AST</TT> method tree.
     */
    private void InitializeMethSig(AST t){
	this.meth_sig_ast = astFactory.create(JavaTokenTypes.METH,"#meth#");
	if(t.getFirstChild().getNextSibling().getType() != JavaTokenTypes.DIMS)
	    this.meth_sig_ast.setFirstChild(astFactory.dupTree(t.getFirstChild().getNextSibling()));
	else
	    this.meth_sig_ast.setFirstChild(astFactory.dupTree(t.getFirstChild().getNextSibling().getNextSibling()));
    
    }//end method
    
    
    /**
     * :|  |--#meth# [92]
     * <br>:|  |  |--byte [35]
     * <br>:|  |  |--#dims# [59]
     * <br>:|  |  |  `--[ [186]
     * <br>:|  |  |--m [113]
     * <br>:|  |  `--; [112]
     * <br>Set 'meth_return_type' to the return type of 't'
     * <br>:|  `--[ [186]
     * <br>:|  |  |--byte [35]
     *
     * @param t An <TT>AST</TT> method tree.
     */
    private void InitializeMethReturnSig(AST t){
	if(t.getFirstChild().getNextSibling().getType() != JavaTokenTypes.DIMS)
	    this.meth_return_type = astFactory.dupTree(t.getFirstChild());
	//:|  [ [186]
	//:|  |`--byte [35]
	else{
	    AST ast_brc = astFactory.create(JavaTokenTypes.LBRACK,"[");
	    ast_brc.setFirstChild(astFactory.dupTree(t.getFirstChild()));
	    this.meth_return_type = ast_brc;
	}//end if else	
    }//end method
    
    
    /*
     * @returns the set of variable locations that <TT>this</TT> context can modify.
     */
    Vector getA(){
	Vector y_vector = new Vector();

	Vector v_meth_var = this.getMethVarVector();
	for(int i=0; i<v_meth_var.size();i++){
	    AST t = (AST)(v_meth_var.elementAt(i));
	    y_vector.addElement(t.getFirstChild().getNextSibling());
	}//end for
	
	Vector v_par = this.getParVector();
	for(int i=0;i<v_par.size();i++){
	    AST t = (AST)(v_par.elementAt(i));
	    y_vector.addElement(t.getFirstChild().getNextSibling());
	}//end for
	
	Vector v_modifies = this.getModifiesVector();
	for(int i=0;i<v_modifies.size();i++){
	    AST t = ((AST)(v_modifies.elementAt(i))).getFirstChild();
	    y_vector.addElement(t);
	}//end for
	return y_vector;
    }//end method
    
    
    /**
     * @param var A variable tree.
     * @param b When true the method searchs in assignable clause,
     * parameters and method variable; otherwise the method searchs
     * just in the assignable clause.
     * @param contexts All available contexts.
     * @return The AST tree class of <TT>var</TT> after searching in
     * <TT>contexts</TT>.
     * @exception assignable.exception.ContextException Whether
     * <TT>var</TT> is not found in <TT>contexts</TT>.
     */
    AST getClassFromContexts(AST var, boolean b, Vector contexts) 
	throws assignable.exception.ContextException{	
	
	AST ast_temp = this.getClassFromContext(var,b);
	//if var is found in this
	if(ast_temp != null) 
	    return ast_temp;
	
	//'var' is not found in the current class, so it's
	//necessary to search in the superclass.       
	//... getting the 'superclass' context
	ASTContext ast_context = this.getSuperContextFromClass(contexts);
	
	//searh in all superclasses
	while(ast_context != null){
	    ast_temp = ast_context.getClassFromContext(var,false);
	    if(ast_temp != null) 
		return ast_temp;
	    ast_context = ast_context.getSuperContextFromClass(contexts);
	} //end while

	throw new assignable.exception.ContextException("Not found code for identifier",var);
    }//end of the method
    
    
    /**
     * @param var a variable tree.
     * @param b When true the method searchs in assignable clause, parameters and method
     * variable; otherwise the method searchs just in the assignable clause.
     * @param contexts All available contexts.
     * @return The AST tree class of <TT>var</TT> after searching in <TT>this</TT>.
     */
    private AST getClassFromContext(AST var, boolean b){
	//if(var = (TYPE)E) --> return TYPE
	if(var.getType() == JavaTokenTypes.CAST)
	    return astFactory.dupTree(var.getFirstChild());
	
	//if(var = 'this') --> return C(this)
	if(var.getType() == JavaTokenTypes.LITERAL_this)
	    return this.getClassQualified();
	
	//if var is constant 'int', 'float' or 'string' ...
	if(var.getType() == JavaTokenTypes.NUM_INT)
	    return (astFactory.create(JavaTokenTypes.JAVA_BUILTIN_TYPE,"int"));	
	else if(var.getType() == JavaTokenTypes.NUM_FLOAT)
	    return (astFactory.create(JavaTokenTypes.JAVA_BUILTIN_TYPE,"float"));
	else if(var.getType() == JavaTokenTypes.STRING_LITERAL)
	    return (astFactory.create(JavaTokenTypes.JAVA_BUILTIN_TYPE,"String"));
	else if(var.getType() == JavaTokenTypes.LITERAL_true)
	    return (astFactory.create(JavaTokenTypes.JAVA_BUILTIN_TYPE,"boolean"));
	else if(var.getType() == JavaTokenTypes.LITERAL_false)
	    return (astFactory.create(JavaTokenTypes.JAVA_BUILTIN_TYPE,"boolean"));	

	//looking for 'var' in this
	if(b){
	    //search in the method variables
	    int v_size = this.meth_var_tree.size();
	    for(int i=0; i<v_size; i++){
		AST t = (AST)(this.meth_var_tree.elementAt(i));
		 
		AST ast_tmp = t.getFirstChild().getNextSibling();
		if(ast_tmp.equalsTree(var))
		    return astFactory.dupTree(t.getFirstChild());
	    }//end for
	    
	    //search in the parameters
	    v_size = this.par_tree.size();
	    for(int i=0; i<v_size; i++){
		AST t = (AST)(this.par_tree.elementAt(i));
		
		AST ast_tmp = t.getFirstChild().getNextSibling();
		
		if(ast_tmp.equalsTree(var))
		    return astFactory.dupTree(t.getFirstChild());
	    }//end for
	}//end if(b)

	//search in the class variables
	int v_size = this.class_var_tree.size();
	for(int i=0; i<v_size; i++){
	    AST t = (AST)(this.class_var_tree.elementAt(i));
	
	    AST ast_tmp = t.getFirstChild().getNextSibling();
	    
	    if(ast_tmp.equalsTree(var))
		return astFactory.dupTree(t.getFirstChild());
	}//end for

	return null;
    }//end method
    
    
    /**
     * @param qualif_class Name of qualified class.
     * @param contexts All available contexts.
     * @return The context corresponding with <TT>qualif_class</TT> after searching in <TT>contexts</TT>.
     * @throw assignable.exception.ContextException Whether <TT>qualif_class</TT> is not found in <TT>contexts</TT>.
     */
    private static ASTContext getContextFromClass(AST qualif_class, Vector contexts) 
	throws assignable.exception.ContextException{
	
	if(qualif_class.getText().equals("int") ||
	   qualif_class.getText().equals("byte") || 
	   qualif_class.getText().equals("short") || 
	   qualif_class.getText().equals("String") || 
	   qualif_class.getText().equals("Integer") || 
	   qualif_class.getText().equals("Float") || 
	   qualif_class.getText().equals("Double") || 
	   qualif_class.getText().equals("String")
	   )
	    return null; 
	
	//search the class qualif_class in 'contexts'.
	Iterator iter = contexts.iterator();
	while(iter.hasNext()){
	    ASTContext ast_context = (ASTContext)(iter.next()); 
	    //if ast_context is the context of the class
	    //return it
	    if(ast_context.getClassQualified().equalsTree(qualif_class))
		return ast_context;
	}//end of while

	throw new assignable.exception.ContextException("Not found code for identifier", qualif_class);
    }//end method


    /**
     * @param contexts All available contexts.
     * @return The context corresponding with the super class of
     * <TT>this</TT> after searching in <TT>contexts</TT>, if any;
     * <TT>null</TT> otherwise.
     */
    private ASTContext getSuperContextFromClass(Vector contexts){
	AST ast_temp = this.getSuperClassQualified();
	
	//search the superclass context in 'contexts'.
	Iterator iter = contexts.iterator();
	while(iter.hasNext()){
	    ASTContext ast_context = (ASTContext)(iter.next()); 
	    //if ast_context is the context of the superclass then return it
	    if(ast_context.getClassQualified().equalsTree(ast_temp))
		return ast_context;
	}//end of while

	return null;
    }//end method


    /**
     * @return The absolute name of the class of <TT>this</TT>.
     **/
    private AST getClassQualified(){
	AST ast_tmp;
	if(this.getPackTree() != null){	
	    AST ast_child;

	    ast_tmp = astFactory.create();
	    ast_tmp.setType(JavaTokenTypes.DOT);
	    ast_tmp.setText(".");

	    ast_child = this.getPackTree();
	    ast_child.setNextSibling(this.getClassTree());

	    ast_tmp.setFirstChild(ast_child);
	}
	else 
	    ast_tmp = this.getClassTree();

	return ast_tmp;
    }//end method
    

    //This method must have another implementation
    /**
     * @return The absolute name of the class of super of <TT>this</TT>.
     */
    AST getSuperClassQualified(){
	return astFactory.dupTree(this.getExtendsTree());
    }
    

    /**
     * @return pack_sig_ast.
     */
    AST getPackTree(){
	return astFactory.dupTree(this.pack_sig_ast.getFirstChild());
    }//end method

    
    /**
     * @return meth_return_type.
     */
    AST getMethReturnTree(){
	return astFactory.dupTree(this.meth_return_type);
    }//end method
    

    /**
     * @return clas_sig_ast.
     */
    AST getClassTree(){
	return astFactory.dupTree(this.clas_sig_ast.getFirstChild());
    }//end method
    

    /**
     * @return exte_sig_ast.
     */
    AST getExtendsTree(){
	return astFactory.dupTree(this.exte_sig_ast.getFirstChild());
    }//end method
    

    /**
     * @return The method signature of <TT>this</TT>.
     */
    String getMethSig(){
	return this.meth_sig_ast.getFirstChild().getText();
    }//end method
    
    
    /**
     * @return modifies_tree.
     */
    Vector getModifiesVector(){
	Vector v = new Vector();
	for(int i=0; i<modifies_tree.size(); i++)
	    v.addElement(astFactory.dupTree((AST)(modifies_tree.elementAt(i))));
	return v;
	//return (Vector)(modifies_tree.clone());
    }//end method
    

    /**
     * @return par_tree.
     */
    Vector getParVector(){
	Vector v = new Vector();
	for(int i=0; i<par_tree.size(); i++)
	    v.addElement(astFactory.dupTree((AST)(par_tree.elementAt(i))));
	return v;
	//return (Vector)(par_tree.clone()); it doesnt' work !!
    }//end method
    
   
    /**
     * @return meth_var_tree.
     */
    Vector getMethVarVector(){
	Vector v = new Vector();
	for(int i=0; i<meth_var_tree.size(); i++)
	    v.addElement(astFactory.dupTree((AST)(meth_var_tree.elementAt(i))));
	return v;
	//return (Vector)(meth_var_tree.clone()); it doesnt' work !!
    }//end method
    
   
    /**
     * @return class_var_tree.
     */
    Vector getClassVarVector(){
	Vector v = new Vector();
	for(int i=0; i<class_var_tree.size(); i++)
	    v.addElement(astFactory.dupTree((AST)(class_var_tree.elementAt(i))));
	return v;
	//return (Vector)(class_var_tree.clone()); it doesnt' work !!
    }//end method
    
   
    /**
     * Add <T>var</TT> to <T>meth_var_tree</TT>.
     *
     * @param var An AST tree variable.
     */
    public void addMethVar(AST var){
	meth_var_tree.addElement(var);
    }   
       
    /**
     * Remove the last element of <TT>meth_var_tree</TT>.
     *
     */
    public void removeMethVar(){
	meth_var_tree.removeElementAt(meth_var_tree.size()-1);
    }

    /**
     * Add <T>elm</TT> to <T>modifies_tree</TT>.
     * @param elm An AST tree variable.
     */
    public void addModifies(AST elm) {
	modifies_tree.addElement(elm);
    }
    
    /**
     * Remove the last element of <TT>modifies_tree</TT>.
     *
     */
    public void removeModifies(){
	modifies_tree.removeElementAt(modifies_tree.size()-1);
    }
    
    /**
     * @param var an AST tree variable.
     * @return <TT>true</TT> if <TT>var</TT> is class variable, <TT>false</TT> otherwise.
     */
    boolean isClassVar(AST var){
	for(int i=0;i<this.class_var_tree.size();i++){
	    AST t = (AST)(this.class_var_tree.elementAt(i));

	    AST ast_tmp = t.getFirstChild().getNextSibling();
	    
	    if(ast_tmp.equalsTree(var))
		return true;
	}//end for
	return false;
    }//end method
    
        
    /**
     * @param var An AST tree variable.
     * @return true if <TT>var</TT> is parameter, <TT>false</TT> otherwise.
     */
    boolean isParameter(AST var){
	for(int i=0;i<this.par_tree.size();i++){
	    AST t = (AST)(this.par_tree.elementAt(i)),
		ast_tmp = t.getFirstChild().getNextSibling();
	    if(ast_tmp.equalsTree(var))
		return true;
	}//end for
	return false;
    }//end method
    
      
    /**
     * @param var An <TT>AST</TT> tree variable.
     * @return true if <TT>var</TT> is ethod variable, otherwise <TT>false</TT>.
     */
    boolean isMethVar(AST var){
	for(int i=0;i<this.meth_var_tree.size();i++){
	    AST t = (AST)(this.meth_var_tree.elementAt(i));

	    AST ast_tmp = t.getFirstChild().getNextSibling();

	    if(ast_tmp.equalsTree(var))
		return true;
	}//end for
	return false;
    }//end method
    

    /**
     * @param e_1 An AST class tree.
     * @param e_2 An AST class tree.
     * @param contexts All available contexts.
     * @return true if <TT>e_1</TT> is a superclass of <TT>e_2</TT>; otherwise <TT>false</TT>.
     */
    static boolean isSuperClass(AST e_1, AST e_2, Vector contexts)
	throws assignable.exception.ContextException{

	ASTContext e_2_cxt = ASTContext.getContextFromClass(e_2,contexts);
	AST ast_super =  e_2_cxt.getSuperClassQualified();
	
	while(ast_super != null){
	    if(ast_super.equalsTree(e_1))
		return true;
	    e_2_cxt = ASTContext.getContextFromClass(ast_super,contexts);
	    ast_super = e_2_cxt.getSuperClassQualified();
	}//end while
	return false;
    }//end method
    
    
    /**
     * <TT>precondition</TT> e = e1.e2...en, n>=0 and ei != method call and
     * <br><TT>postcondition</TT> <TT>v_reach</TT> = reach(e).
     *
     * @param e an <TT>AST</TT> expression tree.
     * @param contexts All available contexts.
     */
    void _reach(AST e, Vector contexts, Vector v_reach) 
	throws assignable.exception.ContextException{

	// 'e' belongs to reach(e)
	v_reach.addElement(astFactory.dupTree(e));	
 	//fields_of(e) belongs to reach(e)
	this.fieldsOfExp(astFactory.dupTree(e),contexts,v_reach);
	
	//finding the other elements
	int new_size = v_reach.size(),
	    old_size = 0;
	while(new_size > old_size){
	    for(int i=0;i<new_size;i++){
		//ast_elem = e.i
		AST ast_elem = (AST)(v_reach.elementAt(i));
		this.fieldsOfExp(ast_elem,contexts,v_reach);
	    }//for
	    old_size = new_size;
	    new_size = v_reach.size();
	}//while 
    }//end method
    

    /**
     * <TT>postcondition</TT> <TT>v_fields</TT>= fields_of(e) <TT>and</TT>
     * <TT>v_fields</TT> does not contain repeated elements.
     *
     * @param e An AST expression tree.
     * @param contexts All available contexts.
     */
    void fieldsOfExp(AST e, Vector contexts, Vector v_fields)
	throws assignable.exception.ContextException{
	//special case when e is an array
	// add:
	//:`--[[186]
	//:   |--e[113]
	//:   `--*[104]
	if(e.getType() == JavaTokenTypes.LBRACK){
	    AST ast_lbr = astFactory.create(JavaTokenTypes.LBRACK,"["),
		ast_elm = astFactory.dupTree(e),
		ast_astx = astFactory.create(JavaTokenTypes.STAR_ELEMS,"*");
	    
	    ast_elm.setNextSibling(ast_astx);
	    ast_lbr.setFirstChild(ast_elm);
	    v_fields.addElement(ast_lbr);
	    return;
	}
	else if(e.getType() == JavaTokenTypes.T_REACH){
	    //v_reach = \reach(*)
	    this._reach(e.getFirstChild(),contexts,v_fields);
	    int f_size = v_fields.size();
	    //i=1 -> it discard the first \reach
	    for(int i=1;i<f_size;i++)
		this.getFieldsOfExp((AST)(v_fields.elementAt(i)),contexts,v_fields);
	    return;
	}
	else
	    this.getFieldsOfExp(e,contexts,v_fields);
    }//end of method
    

    /**
     * <TT>postcondition</TT> <TT>v_fields</TT> contains the fields of <TT>e</TT>.
     *
     * @param e An AST expression tree.
     * @param contexts All available contexts.
     * @exception assignable.exception.ContextException If the class of <TT>e</TT> is not infered from
     * <TT>contexts</TT>.
     */
    private void getFieldsOfExp(AST e, Vector contexts, Vector v_fields) 
	throws assignable.exception.ContextException{
	AST ast_qualif_class = this.getClassFromExp(e,contexts);
	
	//getting the context of ast_qualif_class
	ASTContext ast_context = ASTContext.getContextFromClass(ast_qualif_class, contexts);
	if(ast_context == null)
	    return; //it's primitive data type
	Vector v_var =	ast_context.getClassVarVector();
	int v_size = v_var.size();
	for(int i=0;i<v_size;i++){
	    AST t = (AST)(v_var.elementAt(i));

	    //e.x belgongs to fields_of(e)
	    AST ast_dot = astFactory.create(JavaTokenTypes.DOT,"."),
		ast_obj = astFactory.dupTree(e);

	    ast_obj.setNextSibling(astFactory.dupTree(t.getFirstChild().getNextSibling()));
	    ast_dot.setFirstChild(ast_obj);

	    //discard duplicated elements
	    boolean found = false;
	    for(int k=0; !found && k<v_fields.size();k++){
		AST ast_elm = ((AST)(v_fields.elementAt(k)));
		if(ast_elm.equalsTree(ast_dot))
		    found = true;
	    }//end for
	    if(!found)
		v_fields.addElement(ast_dot);
	}//end for
    }//en method
    
    
    /**
     * @param e An AST expression tree
     *<br>:|  |     |--. [114]
     *<br>:|  |     |  |--. [114]
     *<br>:|  |     |  |  |--. [114]
     *<br>:|  |     |  |  |  |--e1 [113]
     *<br>:|  |     |  |  |  `--e2 [113]
     *<br>:|  |     |  |  `--e3 [113]
     *<br>:|  |     |  `--m [113]
     *<br>:|  |     `--, [136]
     *<br>:|  |        |--, [136]
     *<br>:|  |        |  |--par1 [113]
     *<br>:|  |        |  `--par2 [113]
     *<br>:|  |        `--par3 [113]
     * @param contexts All available contexts.
     * @return The class of <TT>e</TT>.
     * @exception assignable.exception.ContextException If the class of <TT>e</TT> is not infered from
     * <TT>contexts</TT>.
     */
    AST getClassFromExp(AST e, Vector contexts) 
	throws assignable.exception.ContextException{
	AST ast_qualif_class, ast_temp;
	ASTContext ast_context = null;	

	//boolean isArray = false;

	// When the expression is a method
	// e = e1.e2.....en.m(q1,...,qk)
	if(e.getType() == JavaTokenTypes.LPAREN){
	    String str_m;
	    AST ast_pars = e.getFirstChild().getNextSibling();

	    if(e.getFirstChild().getType() == JavaTokenTypes.DOT){
		str_m = e.getFirstChild().getFirstChild().getNextSibling().getText();
		ast_qualif_class = this.getClassFromExp(astFactory.dupTree(e.getFirstChild().getFirstChild()),contexts);
	    }
	    else{
		str_m = e.getFirstChild().getText();
		ast_qualif_class = null;
	    }
	    
	    Vector v_pars = new Vector();
	    //expand parameters
	    Util.getExpandParam(ast_pars,
				      JavaTokenTypes.COMMA,
				      v_pars);
	    //
	    Vector v_pars_clas = new Vector();
	    for(int i=0;i<v_pars.size();i++){
		AST ast_clas = this.getClassFromExp((AST)(v_pars.elementAt(i)),contexts);
		v_pars_clas.addElement(ast_clas);
	    }
	    int size_of_pars = v_pars_clas.size();	    
	    //search in the contexts from root of the classes
	    ast_context = this;
	    while(ast_qualif_class != null){	    
		for(int i=0; i<contexts.size(); i++){
		    ASTContext t = (ASTContext)(contexts.elementAt(i));
		    if(t.getClassQualified().equalsTree(ast_qualif_class)){
			if(t.getMethSig().equals(str_m)){
			    Vector v_o = t.getParVector();
			    int v_o_size = v_o.size();
			    
			    if(size_of_pars == v_o_size){
				boolean b = true;
				for(int k=0;b && k<size_of_pars;k++){
				    AST ast_v_q = (AST)(v_pars_clas.elementAt(k)),
					ast_v_o = astFactory.dupTree(((AST)(v_o.elementAt(k))).getFirstChild());
				    if(!ast_v_q.equalsTree(ast_v_o))
					b = false;
				}
				//if it's the method I'am looking for
				if(b)
				    return t.getMethReturnTree();
			    }//end if ==
			}
		    }//end if
		}//end for
		
		ast_context = ast_context.getSuperContextFromClass(contexts);
		if(ast_context != null)
		    ast_qualif_class = ast_context.getClassQualified();
		else
		    ast_qualif_class = null;	
	    }//end while

	    throw new assignable.exception.ContextException("Not found code for identifier",e);
	}//end if(isMethod)
	//:|  |  |--[ [catch]
	//:|  |  |  |--data [113]
	//:|  |  |  `--newIndex [113]
	else if(e.getType() == JavaTokenTypes.LBRACK){
	    ast_qualif_class = this.getClassFromExp(astFactory.dupTree(e.getFirstChild()),contexts); //e = data
	    return astFactory.dupTree(ast_qualif_class.getFirstChild());
	}	
	else if(e.getType() == JavaTokenTypes.DOT){
	    //if 'e' is itself a CLASS
	    try{
		ast_qualif_class = astFactory.dupTree(e);
		ast_context = ASTContext.getContextFromClass(ast_qualif_class,contexts);
		//if(ast_context != null)
		return ast_qualif_class;
	    } catch(assignable.exception.ContextException msg){;}
		
	    //if 'e' = C1.C2.....Cn.x 
	    try{
		ast_temp = astFactory.dupTree(e.getFirstChild());
		ast_context = ASTContext.getContextFromClass(ast_temp,contexts);
		//if(ast_context != null){
		ast_qualif_class = ast_context.getClassFromContexts(astFactory.dupTree(e.getFirstChild().getNextSibling()), 
								    true,
								    contexts);
		return ast_qualif_class;
	    } catch(assignable.exception.ContextException msg){;}
	}//end if e = e1.e2

	//storing each ei in v_exp_ast
	Vector v_exp_ast = new Vector();
	Util.getExpandParam(e,
				  JavaTokenTypes.DOT,
				  v_exp_ast);//astFactory.dupTree(e)
	
	Iterator iter = v_exp_ast.iterator();
	//ast_temp = e1
	ast_temp = (AST)(iter.next());
	//ast_qualif_class = C(e1)
	ast_qualif_class = this.getClassFromContexts(ast_temp,true,contexts);
	
	if(iter.hasNext()){
	    //I have the class, but acutally I need the context
	    while(iter.hasNext()){
		//boolean found = false;
		ast_context = ASTContext.getContextFromClass(ast_qualif_class,contexts);
		//ast_temp == ei
		ast_temp = (AST)(iter.next());
		//ast_qualif_class == class of ei
		ast_qualif_class = ast_context.getClassFromContexts(ast_temp,false,contexts);//C(ei)
	    }//end while 
	}//end if	
	return ast_qualif_class;
    }//end method
    
    
    // e = e1.m(q).
    /**
     * @param str_m An method name.
     * @param ast_q The parameters of <TT>str_m</TT>.
     * @param contexts All available contexts.
     * @return if e1 != null [e1.m(o).modifies][o\q][this\e1] like <modifies exp1, modifies exp2,...>
     *         otherwise [m(o).modifies][o\q] like <modifies exp1, modifies exp2,...>
     * @exception assignable.exception.ContextException If the class of <TT>e</TT> is not infered from
     * <TT>contexts</TT>.
     */
    Vector getAssignable(AST ast_e1, String str_m, AST ast_q, Vector contexts) 
	throws assignable.exception.ContextException{

	AST qualif_class_e1;
	if(ast_e1 == null)
	   qualif_class_e1 = this.getClassQualified();
	else
	    qualif_class_e1 = this.getClassFromExp(ast_e1,contexts);
	
	Vector v_q_par = new Vector();
	Util.getExpandParam(ast_q,
				  JavaTokenTypes.COMMA,
				  v_q_par);//expand parameters
	
	Vector v_q_class = new Vector();
	for(int i=0;i<v_q_par.size();i++){
	    AST ast_clas = this.getClassFromExp((AST)(v_q_par.elementAt(i)),contexts);
	    v_q_class.addElement(ast_clas);
	}//end for
	int v_q_size = v_q_class.size();

	//search in the contexts from root of the classes
	ASTContext ast_context = this;
	while(qualif_class_e1 != null){	    
	    for(int i=0; i<contexts.size(); i++){
		ASTContext t = (ASTContext)(contexts.elementAt(i));
		if(t.getClassQualified().equalsTree(qualif_class_e1) ||
		   ASTContext.isSuperClass(t.getClassQualified(),qualif_class_e1,contexts)
		   //||t.getClassQualified() IS A SUPERCLASS of qualif_class_e1
		   ){
		    if(t.getMethSig().equals(str_m)){
			Vector v_o = t.getParVector();
			int v_o_size = v_o.size();
			
			if(v_q_size == v_o_size){
			    boolean b = true;
			    for(int k=0;b && k<v_q_size;k++){
				AST ast_v_q = (AST)(v_q_class.elementAt(k)),
				    ast_v_o = astFactory.dupTree(((AST)(v_o.elementAt(k))).getFirstChild());
				if(!ast_v_q.equalsTree(ast_v_o))
				    b = false;
			    }
			    //if it's the method I'am looking for
			    if(b){
				Vector mod_vec = t.getModifiesVector();
				int mod_vec_size = mod_vec.size();
				for(int k=0;k<mod_vec_size;k++){
				    t.replaceObyQandTHISbyE1((AST)(mod_vec.elementAt(k)), //this method
							     v_q_par, //modifies
							     ast_e1);//the first parameter
				}//end for
				return mod_vec;
			    }//end if(b)
			}//end if ==
		    }
		}//end if
	    }//end for

	    ast_context = ast_context.getSuperContextFromClass(contexts);
	    if(ast_context != null)
		qualif_class_e1 = ast_context.getClassQualified();
	    else
		qualif_class_e1 = null;	
	}//end while
	
	throw new assignable.exception.ContextException("Not found code for identifier", ast_e1);
    }//end of the method
    
    
    /**
     * <TT>precondition</TT> method(this) = m(q)
     * <br><TT>postcondition</TT> t=\old(t)[par_tree\q][this\e_1].
     *
     * @param t A modifies tree.
     * @param q Actual parameters.
     * @param e_1 An <TT>AST</TT> expression tree.
     */
    private void replaceObyQandTHISbyE1(AST t, Vector q, AST e_1){
	//replace O by Q
	for(int i=0;i<this.par_tree.size();i++){
	    AST tt = (AST)(this.par_tree.elementAt(i));
	    
	    AST o_temp = tt.getFirstChild().getNextSibling(),
		q_temp = (AST)(q.elementAt(i));
	    
	    ASTContext.help_replaceObyQ(t,
					o_temp.getText(),
					q_temp.getText());
	}//end for
	
	//replace this by e1
	help_replaceTHISbyE1(t,
			     e_1);	
    }//end method
    
    
    /**
     * <TT>postcondition</TT> t = \old(t)[o\q].
     *
     * @param t A modifies tree.
     * @param o Formal parameter.
     * @param q Actual parameter.
     */
    private static void help_replaceObyQ(AST t, String o, String q){
	for(;t!=null;t=t.getNextSibling()){
	    if(t.getText().equals(o))
		t.setText(q);
	    ASTContext.help_replaceObyQ(t.getFirstChild(),o,q); //NO astFactory.dupTree(t)
	}//end for
    }//end method
    

    /**
     * <TT>postcondition</TT> t = \old(t)[this\e_1].
     *
     * @param t A modifies tree.
     * @param e_1 An AST expression tree
     */
    private static void help_replaceTHISbyE1(AST t, AST e_1){
	for(;t!=null;t=t.getNextSibling()){
	    if(e_1 != null)
		if(t.getType() == JavaTokenTypes.DOT)
		    if(t.getFirstChild().getType() == JavaTokenTypes.LITERAL_this){
			AST ast_left = astFactory.dupTree(e_1),
			    ast_right = astFactory.dupTree(t.getFirstChild().getNextSibling());
			
			ast_left.setNextSibling(ast_right);
			t.setFirstChild(ast_left);
		    }//end if
	    ASTContext.help_replaceTHISbyE1(t.getFirstChild(),e_1); //NO astFactory.dupTree(t)
	}//end for
    }//end method

    
    /**
     * @param e An <TT>AST</TT> expression tree <TT>e1.e2</TT>.
     * @param contexts All available contexts.
     * @return ClassOf(e1).e2
     * @exception assignable.exception.ContextException If the class of <TT>e</TT> is not infered from
     * <TT>contexts</TT>.
     */
    AST replaceEbyClassOfE(AST e, Vector contexts) 
	throws assignable.exception.ContextException{
	//e = e1.e2
	if(e.getType() == JavaTokenTypes.DOT){
	    AST ast_e1 = astFactory.dupTree(e.getFirstChild());
	    AST ast_e2 = astFactory.dupTree(e.getFirstChild().getNextSibling());
	    
	    AST ast_e1_cls = this.getClassFromExp(ast_e1,contexts);
	    
	    AST ast_dot = astFactory.create(JavaTokenTypes.DOT,".");
	    ast_e1_cls.setNextSibling(ast_e2);
	    ast_dot.setFirstChild(ast_e1_cls);
	    return ast_dot;
	}//end if
	return e;
    }    
    

    /**
     * <TT>postcondition</TT> <TT>v_AST</TT>= vector of modifies declarations of <TT>t</TT>.
     *
     * @param t A <TT>AST</TT> method tree.
     */
    void getModif(AST t){
	for(; t != null; t = t.getNextSibling()) {
	    if(t.getType() == JavaTokenTypes.ASSIGNABLE_KEYWORD)
		this.formatModif(astFactory.dupList(t.getFirstChild()));            
	    //if(t.getType() == JavaTokenTypes.ASGNABLE_SEQ){
            //  this._modif_format(astFactory.dupList(t.getFirstChild().getFirstChild()));
            //}
	    this.getModif(astFactory.dupList(t.getFirstChild()));
	}//end for
    }//end method
    
    
    /**
     * @param e An AST expression tree.
     * @return If(!isParameter(e) && !isMethodVariable(e) && !isClassVariable(e)) then <TT>this.e</TT> else <TT>e</TT>.
     */
    AST formatExp(AST e){	
	//special case when e = a[e1] -->
	//                  e = this.a[e1]
      if(e.getType() == JavaTokenTypes.LBRACK &&
	 !this.isParameter(astFactory.dupTree(e.getFirstChild())) &&
	 !this.isMethVar(astFactory.dupTree(e.getFirstChild())) &&
	 this.isClassVar(astFactory.dupTree(e.getFirstChild()))
	 ){ 
	    //:|  |  `--[ [186]
	    //:|  |     |--. [114]
	    //:|  |     |  |--x [113]
	    //:|  |     |  `--data [113]
	    //:|  |     `--* [104]
	    AST ast_dot = astFactory.create(JavaTokenTypes.DOT,"."),
		ast_this = astFactory.create(JavaTokenTypes.LITERAL_this,"this");
	    
	    ast_this.setNextSibling(astFactory.dupTree(e.getFirstChild()));
	    
	    ast_dot.setFirstChild(ast_this);
	    ast_dot.setNextSibling(e.getFirstChild().getNextSibling());
	    
	    AST ast_brack = astFactory.create(JavaTokenTypes.LBRACK);
	    ast_brack.setFirstChild(ast_dot);
	    return ast_brack;
	}
	//special case when e = x -->
	//                  e = this.x
	else if(e.getType() == JavaTokenTypes.IDENT &&
		!this.isParameter(e) &&
		!this.isMethVar(e) &&
		this.isClassVar(e)){
	    AST ast_dot = astFactory.create(JavaTokenTypes.DOT,".");
	    
	    AST ast_this = astFactory.create(JavaTokenTypes.LITERAL_this,"this");
	    ast_this.setNextSibling(astFactory.dupTree(e));
	    ast_dot.setFirstChild(ast_this);
	    
	    return ast_dot;	    
	}
	//special case when e = 
	// :-- .
	// |   |-- x
	// |    -- y
	//  -- z
	// --> 
	// :-- .
	// |   |-- .
	// |   |   |-- this
	// |   |    -- x
	// |    -- y
	//  -- z
	else if(e.getType() == JavaTokenTypes.DOT){
	    Vector v_exp = new Vector();
	    //expand parameters
	    Util.getExpandParam(astFactory.dupList(e),
				      JavaTokenTypes.DOT,
				      v_exp);
	    AST ast_head = (AST)(v_exp.elementAt(0));	    
	    //:|  |  |--[ [186]
	    //:|  |  |  |--data [113]
	    //:|  |  |  `--newIndex [113]
	    if(ast_head.getType() == JavaTokenTypes.LBRACK)
		ast_head = astFactory.dupTree(ast_head.getFirstChild());
	    if(ast_head.getType() != JavaTokenTypes.LITERAL_this &&
	       !this.isParameter(ast_head) &&
	       !this.isMethVar(ast_head) &&
	       this.isClassVar(ast_head)
	       )
		return Util.foldAST(v_exp,astFactory.create(JavaTokenTypes.DOT,"."),astFactory.create(JavaTokenTypes.LITERAL_this,"this"));
	}//end if
	return e;
    }
    
    
    /**
     * <TT>postcondition</TT> <modifies x,modifies y, modifies z> in <TT>modifies_tree</TT>.
     *
     * @param t A modifies declaration modifies x,y,z;.
     $ @param t.
     */
    void formatModif(AST t){
	AST ast_modif = astFactory.create(JavaTokenTypes.ASSIGNABLE_KEYWORD,"modifies");	
	
	//format _modif_format(e1, e2,...en) --> format(e1), format(e2), ... format(en)
	if(t.getType() == JavaTokenTypes.COMMA){
	    this.formatModif(astFactory.dupTree(t.getFirstChild()));
	    this.formatModif(astFactory.dupTree(t.getFirstChild().getNextSibling()));
	}
	else if(t.getType() == JavaTokenTypes.T_EVERYTHING){
	    AST ast_this = astFactory.create(JavaTokenTypes.LITERAL_this,"this"),
		ast_reach = astFactory.create(JavaTokenTypes.T_REACH,"\\reach"),
		ast_field = astFactory.create(JavaTokenTypes.T_FIELDS_OF,"\\fields_of");
	    ast_reach.setFirstChild(ast_this);
	    ast_field.setFirstChild(ast_reach);
	    ast_modif.setFirstChild(ast_field);
	    this.modifies_tree.addElement(ast_modif);
	}
	else if(t.getType() == JavaTokenTypes.T_NOTHING)
	    return;
	else if(t.getType() == JavaTokenTypes.T_FIELDS_OF){
	    AST ast_e,
	    	ast_field = astFactory.create(JavaTokenTypes.T_FIELDS_OF,"\\fields_of");
	    
	    if(t.getFirstChild().getType() == JavaTokenTypes.T_REACH){
	    	AST ast_reach = astFactory.create(JavaTokenTypes.T_REACH,"\\reach");
	    	
		ast_e = formatExp(t.getFirstChild().getFirstChild());
	    	ast_reach.setFirstChild(ast_e);
	    	ast_field.setFirstChild(ast_reach);
	    	ast_modif.setFirstChild(ast_field);
	    	this.modifies_tree.addElement(ast_modif);
	    }
	    else{
		ast_e = formatExp(t.getFirstChild());
		ast_field.setFirstChild(ast_e);
		ast_modif.setFirstChild(ast_field);
		this.modifies_tree.addElement(ast_modif);
	    }
	}
	else{
	    AST ast_e = formatExp(t);
	    
	    ast_modif.setFirstChild(ast_e);
	    this.modifies_tree.addElement(astFactory.dupTree(ast_modif));
	}
    }//end method
    
    
    /**
     * @return The String representation of <TT>this</TT>.
     */
    public String toString(){
	String str1 = "Class variables:\n";
	for(int i=0;i<this.class_var_tree.size();i++){
	    str1 += ASTtoStringConverter.convert((AST)(this.class_var_tree.elementAt(i)));
	}
	
	String str2 = "Method variables:\n";
	for(int i=0;i<this.meth_var_tree.size();i++){
	    str2 +=  ASTtoStringConverter.convert((AST)(this.meth_var_tree.elementAt(i)));
	}
	
	String str3 = "Parameters variables:\n";
	for(int i=0;i<this.par_tree.size();i++){
	    str3 += ASTtoStringConverter.convert((AST)(this.par_tree.elementAt(i)));
	}

	String str4 = "Assignable locations:\n";
	for(int i=0;i<this.modifies_tree.size();i++){
	    str4 += ASTtoStringConverter.convert((AST)(this.modifies_tree.elementAt(i)));
	}			     

	String str5 = "package \n";
	str5 += ASTtoStringConverter.convert(pack_sig_ast);

	String str6 = "class \n";
	str6 += ASTtoStringConverter.convert(clas_sig_ast);

	String str7 = "extends \n";
	str7 += ASTtoStringConverter.convert(exte_sig_ast);

	String str8 = "implements \n";
	str8 += ASTtoStringConverter.convert(impl_sig_ast);

	String str9 = "return \n";
	str9 += ASTtoStringConverter.convert(meth_return_type);

	String str10 = "method \n";
	str10 += ASTtoStringConverter.convert(meth_sig_ast);

	return   str5 +
	    str6 +
	    str7 +
	    str8 +
	    str9 +
	    str10 +
	    str4 +		 
	    str1 +
	    str2 +
	    str3 ;
    }
}//end class
