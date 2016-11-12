import java.io.*;
import javassist.bytecode.*;
import javassist.*;

class ovmDis{
    public static void main(String[] args) throws IOException, BadBytecode{
	BufferedInputStream fin =
	    new BufferedInputStream(new FileInputStream("hello_world.class"));
	ClassFile cf = new ClassFile(new DataInputStream(fin));
	MethodInfo minfo = cf.getMethod("main");
	CodeAttribute ca = minfo.getCodeAttribute();
	CodeIterator i = ca.iterator();
	while(i.hasNext()){
	    int index = i.next();
	    int op = i.byteAt(index);
	    System.out.println(Mnemonic.OPCODE[op]);
	}
	fin.close();
    }
}
