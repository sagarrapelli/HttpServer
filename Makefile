# define a makefile variable for the java compiler
JCC = javac

# typing 'make' will invoke the first target entry in the makefile 
# (the default one in this case)
#
default: Server.class

# this target entry builds the Server class
# the Server.class file is dependent on the Server.java file
# and the rule associated with this entry gives the command to create it
#
Server.class: Server.java
	$(JCC) Server.java

# To start over from scratch, type 'make clean'.  
# Removes all .class files, so that the next make rebuilds them
#
clean: 
	$(RM) *.class