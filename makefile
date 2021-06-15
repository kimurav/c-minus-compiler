JAVA=java
JAVAC=javac
JFLEX=jflex
CLASSPATH=-classpath ./java_cup/java-cup-11b.jar:.
CLASSPATHRUNTIME=-classpath ./java_cup/java-cup-11b-runtime.jar:.
CUP=$(JAVA) $(CLASSPATH) java_cup.Main
#CUP=cup

all: Main.class

Main.class: absyn/*.java parser.java sym.java Lexer.java Scanner.java Main.java SemanticAnalyzer.java DefSym.java CodeGen.java

%.class: %.java
	$(JAVAC) $(CLASSPATH)  $^

Lexer.java: cminus.flex
	$(JFLEX) cminus.flex

parser.java: cminus.cup
	$(CUP) cminus.cup
run:
	java $(CLASSPATHRUNTIME) Main c-progs/sort.cm

run-test1:
	java $(CLASSPATHRUNTIME) Main test/1.cm -t

run-test2:
	java $(CLASSPATHRUNTIME) Main test/2.cm

run-test3:
	java $(CLASSPATHRUNTIME) Main test/3.cm

run-test4:
	java $(CLASSPATHRUNTIME) Main test/4.cm

run-test5:
	java $(CLASSPATHRUNTIME) Main test/5.cm

run-sym-1:
	java $(CLASSPATHRUNTIME) Main test/1.cm -s

run-sym-2:
	java $(CLASSPATHRUNTIME) Main test/2.cm -s

run-sym-3:
	java $(CLASSPATHRUNTIME) Main test/3.cm -s

run-sym-4:
	java $(CLASSPATHRUNTIME) Main test/4.cm -s

run-sym-5:
	java $(CLASSPATHRUNTIME) Main test/5.cm -s

run-sym-6:
	java $(CLASSPATHRUNTIME) Main test/6.cm -s

run-sym-7:
	java $(CLASSPATHRUNTIME) Main test/7.cm -s

run-gen-1:
	java $(CLASSPATHRUNTIME) Main test/1.cm -c
clean:
	rm -f parser.java Lexer.java sym.java *.class absyn/*.class *~
