JC = javac -d .

run:	all
	java main/NineMensMorris
	
all:
	$(JC) src/main/*.java

clean:
	rm main/*.class
	rm -d main	