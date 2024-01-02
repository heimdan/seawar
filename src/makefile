#
# define compiler and compiler flag variables
#

JFLAGS = -g
JC = javac
JVM= java 

.SUFFIXES: .java .class

.java.class:
	$(JC) $(JFLAGS) $*.java
#
# CLASSES is a macro consisting of 4 words (one for each java source file)
#

CLASSES = \
Bateau.java\
Game.java\
Player.java\
SeaWar.java

default: classes

classes: $(CLASSES:.java=.class)
