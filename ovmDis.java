/*
Java disassembler for Java.
Copyright (C) 2016 Vasantha Ganesh K. <vasanthaganesh.k@tuta.io>.

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

class IllegalClassFile extends Exception{
  public IllegalClassFile(String message){
     super(message);
  }
}

class ovmDis{
    public static void runMethod(CodeIterator i, ConstPool cpl) throws Throwable {
	while(i.hasNext()){
	    int index = i.next();
	    int op = i.byteAt(index);
	    String cod = Mnemonic.OPCODE[op];
	    if(cod == "sipush"){
		System.out.println(cod + " " + i.s16bitAt(index+1));
	    }
	    else if(cod == "istore_1"){
		System.out.println(cod);
	    }
	    else if(cod == "istore_2"){
		System.out.println(cod);
	    }	    
	    else if(cod == "getstatic"){
		System.out.println(cod + " " + ((i.byteAt(index+1)<<8) + i.byteAt(index+2)));
	    }
	    else if(cod == "iload_1"){
		System.out.println(cod);
	    }
	    else if(cod == "iload_2"){
		System.out.println(cod);
	    }
	    else if(cod == "iadd"){
		System.out.println(cod);
	    }
	    else if(cod == "invokevirtual"){
		System.out.println(cod + " " + ((i.byteAt(index+1)<<8) + i.byteAt(index+2)));
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
