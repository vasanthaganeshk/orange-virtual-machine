/*
Virtual machine for Java.
Copyright (C) 2016 Vasantha Ganesh K. <vasanthaganesh.k@tuta.io>,
Sarath Das K.V. <sarathdaskv96@gmail.com>

This file is part of orange-virtual-machine.

orange-virtual-machine is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

orange-virtual-machine is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with orange-virtual-machine.  If not, see <http://www.gnu.org/licenses/>.
*/

import java.util.stream.*;
import java.util.*;
import java.io.*;
import javassist.bytecode.*;
import javassist.*;
import org.javatuples.*;

class IllegalClassFile extends Exception{
  public IllegalClassFile(String message){
     super(message);
  }
}


class ovm{
    // static Stack callStack = new Stack();
    static Stack<Integer> dataStack = new Stack<Integer>();
    static Stack<Integer> blockStack = new Stack<Integer>();
    static Integer globalVar_0 = 0;
    static Integer globalVar_1 = 0;
    static Integer globalVar_2 = 0;
    static Integer globalVar_3 = 0;
    
    public static void runMethod(CodeIterator i, ConstPool cpl) throws Throwable{
	while(i.hasNext()){
	    int index = i.next();
	    int op = i.byteAt(index);
	    String cod = Mnemonic.OPCODE[op];

	    if(cod == "sipush"){
		dataStack.push(i.s16bitAt(index+1));
	    }
	    else if(cod == "istore_0"){
		globalVar_0 = dataStack.pop();
	    }
	    else if(cod == "istore_1"){
		globalVar_1 = dataStack.pop();
	    }
	    else if(cod == "istore_2"){
		globalVar_2 = dataStack.pop();
	    }
	    else if(cod == "istore_3"){
		globalVar_3 = dataStack.pop();
	    }
	    else if(cod == "getstatic"){
		dataStack.push(cpl.getFieldrefClass(i.s16bitAt(index+1)));
		Integer tmp = cpl.getFieldrefClass(i.s16bitAt(index+1));
		System.out.println(tmp);
		System.out.println(cpl.getClassInfo(tmp));
		System.out.println(cpl.getNameAndTypeName(tmp+1));
		System.out.println(cpl.getNameAndTypeDescriptor(tmp+1));
	    }
	    else if(cod == "iload_0"){
		dataStack.push(globalVar_0);
	    }
	    else if(cod == "iload_1"){
		dataStack.push(globalVar_1);
	    }
	    else if(cod == "iload_2"){
		dataStack.push(globalVar_2);
	    }
	    else if(cod == "iload_3"){
		dataStack.push(globalVar_3);
	    }
	    else if(cod == "iadd"){
		dataStack.push(dataStack.pop() + dataStack.pop());
	    }
	    else if(cod == "invokevirtual"){
		System.out.println(cod + " " + i.s16bitAt(index+1));
	    }
	    else if(cod == "return"){
		System.out.println(cod);
	    }
	    else{
		System.out.println("Unhandled Bytecode " + cod);
	    }
	}
    }
    
    public static void main(String[] args) throws Throwable{
	String fname = args[0];
	BufferedInputStream fin = new BufferedInputStream(new FileInputStream(fname));
	
	ClassFile cf = new ClassFile(new DataInputStream(fin));	
	ConstPool cpl = cf.getConstPool();
	List<MethodInfo> allMethods = cf.getMethods();
	List<String> mnames = allMethods.stream().map(x -> x.getName()).collect(Collectors.toList());

	if(mnames.contains("main")){
	    MethodInfo minfo = cf.getMethod("main");
	    CodeAttribute ca = minfo.getCodeAttribute();
	    CodeIterator i = ca.iterator();
	    runMethod(i, cpl);
	}
	else{
	    throw new IllegalClassFile("No main method found in " + fname);
	}
	fin.close();
    }
}
