JCC = javac
HEADER = -cp /usr/share/java/javassist.jar:. -Xlint

default: ovm.class
	$(JCC) $(HEADER) ovm.java
ovm.class: ovm.java
	$(JCC) $(HEADER) ovm.java

clean:
	rm *.class
	rm *~
