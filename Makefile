################################################################################

PATH1 = /usr/share/java/javassist.jar
PATH2 = .
PATH3 = /usr/share/java/javatuples-1.2.jar

ACTUAL_PATH = $(PATH1):$(PATH2):$(PATH3)

WARNINGS = -Xlint

JCC = javac

################################################################################

ovm.class: ovm.java hello_world.class
	$(JCC) $(WARNINGS) -cp $(ACTUAL_PATH) ovm.java
	$(JCC) hello_world.java

hello_world.class: hello_world.java
	$(JCC) hello_world.java

################################################################################
# RUN THE VM ON hello_world.class
################################################################################

.PHONY: run
run:
	java -cp $(ACTUAL_PATH) ovm hello_world.class

################################################################################

.PHONY: clean
clean:
	\rm *.class *~

################################################################################
