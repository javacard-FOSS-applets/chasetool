#*********************************************************************
# 
# 2002 ChAsE tool
# Néstor CATAÑO COLLAZOS
# INRIA, France
# 2004, route des Lucioles - B.P. 93
# 06902 Sophia-Antipolis Cedex(France)
#
# E-mail Nestor.Catano@sophia.inria.fr
# http://www-sop.inria.fr/lemme/verificard/modifSpec/index.html
#
#*********************************************************************
PACKAGES	= \
	assignable \

JARS	= \
	ChAsE.jar \

JARS_REFERRED_TO 	= \
		antlr.jar \
		jml-checker.jar \

MAIN_CLASS	=	CheckModClause
MAIN_PACKAGE	=	assignable
MAIN_JAR	=	ChAsE.jar


include $(CHASE_HOME)/make/Makefile
