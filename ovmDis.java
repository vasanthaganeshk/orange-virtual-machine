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

import java.util.*;
import java.io.*;
import javassist.bytecode.*;
import javassist.*;

class ovmDis{
    public static void main(String[] args) throws Throwable{
	String fname = args[0];
	BufferedInputStream fin =
	    new BufferedInputStream(new FileInputStream(fname));

	ClassFile cf = new ClassFile(new DataInputStream(fin));	
	MethodInfo minfo = cf.getMethod("main");
	CodeAttribute ca = minfo.getCodeAttribute();

	CodeIterator i = ca.iterator();
	while(i.hasNext()){
	    int index = i.next();
	    int op = i.byteAt(index);
	    String cod = Mnemonic.OPCODE[op];
	    System.out.println(cod);
	    if(cod == "sipush"){
		System.out.println(i.s16bitAt(index+1));
	    }
	}
	fin.close();
    }
}
