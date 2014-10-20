JC = javac -d .

.java.class:
	$(JC) $*.class

run:	all
	java main/NineMensMorris
	
all:
	$(JC) src/main/*.java

clean:
	rm main/*.class
	rm -d main	