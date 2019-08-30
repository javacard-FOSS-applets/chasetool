package com.gemplus.pacap.purse;

public class AccessControlTable extends Object{
    private com.gemplus.pacap.purse.AccessControl[] data = null;    
    
    public AccessControlTable(byte[] initArray) {
	super();
	data = new AccessControl[(byte)(initArray.length / 2)];
	for(byte j = 0;j < data.length;j++) {
	    data[j] = new AccessControl(initArray[(byte)(j*2)]);
	    data[j].setCondition(initArray[(byte)(j*2+1)]);            
	}
    }
        
    /*@ 
      modifies \nothing;
    */
    public AccessControl getAccessControl(byte id) {
	for(byte i = 0;i < data.length;i++) {
	    if(data[i].getMethode() == id) {
		return data[i];
	    }
	}
	data[3].getMethode();
	return null;
    }

    /*@
      modifies \nothing ;
    */ 
    public boolean verify(byte id, com.gemplus.pacap.purse.AccessCondition c) throws AccessConditionException {
	com.gemplus.pacap.purse.AccessControl ac = getAccessControl(id);
	return (ac == null ? false : ac.verify(c));
    }

    public void setAccessControl(byte id, byte ac) {
    }
    
}

