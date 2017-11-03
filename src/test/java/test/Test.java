package test;
public class Test {
	
	String s;

	public Test(String s) {
		this.s = s;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		 Test f = new Test("f");
         changeReference(f); // It won't change the reference!
         System.out.println("f : " + f);
         modifyReference(f); // It will modify the object that the reference variable "f" refers to!
         System.out.println("toto : " +f);
    }
    public static void changeReference(Test a)
    {
         Test b = new Test("b");
         a = b;
         System.out.println("changeReference: "  + a);
    }
    public static void modifyReference(Test c)
    {
         c.setAttribute("c");
         System.out.println(c);
    }
    
    public void setAttribute(String s) { this.s = s;}
    
    @Override
	public String toString() { return ""+s; }

}
