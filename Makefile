
JCC = javac
HEADER = -cp /usr/share/java/javassist.jar:. -Xlint

default: hello_world.class ovmDis.class
	$(JCC) $(HEADER) ovmDis.java
ovmDis.class: ovmDis.java
	$(JCC) $(HEADER) ovmDis.java

clean:
	rm *.class
	rm *~
