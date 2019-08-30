#***************************************************************************
#
#  Section 1: Directories.
#
#***************************************************************************

DOC_DIR          = $(CHASE_HOME)/docs
MAKE_DIR         = $(CHASE_HOME)/make
JAR_DIR          = $(CHASE_HOME)/jars
SOURCE_DIR       = $(CHASE_HOME)/src
JAVA_BIN         = $(JAVA_HOME)/bin

PURSE_SOURCE	=	\
	HeureException.purse \
	Heure.purse \
	DateException.purse \
	Date.purse \
	PartnerID.purse \
	SalerID.purse \
	Currencies.purse \
	ExchangeSession.purse \
	ExchangeRecord.purse \
	AccessConditionException.purse \
	AccessCondition.purse \
	AccessControl.purse \
	AccessControlTable.purse \
	AllowedLoyaltyException.purse \
	AllowedLoyalty.purse \
	LoyaltiesTableException.purse \
	LoyaltiesTable.purse \

UTILS_SOURCE	=	\
	Annee.utils \
	Mois.utils \
	Jour.utils \
	PacapException.utils \
	DecimalException.utils \
	Decimal.utils \

PACAPINTERFACES_SOURCE	=	\
	TransactionInterface.pacapinterfaces \
	PurseLoyaltyInterface.pacapinterfaces \
	LoyaltyPurseInterface.pacapinterfaces \
	LoyaltyLoyaltyInterface.pacapinterfaces \

JAVACARD_SOURCE	=	 \
	Util.javacard \
	AID.javacard \

TEST_SOURCE	=	 \
	A.test \
	B.test \

#***************************************************************************
#
#  Section 2. Tools and options.
#
#***************************************************************************

JAVA     = java
JAVAC    = javac
JAVAH    = javah
MAKE     = make
DELETE   = rm -f
PRINT    = @echo

ifdef IS_UNIX
SEP = :
else
SEP = ;
endif

EMPTY            =
SPACE            = $(EMPTY) $(EMPTY)

LOCAL_JARTMP     = $(patsubst %,$(JAR_DIR)/%,$(JARS))
LOCAL_JARLIST    = $(subst $(SPACE),$(SEP),$(LOCAL_JARTMP))

OTHER_JARTMP     = $(patsubst %,$(JAR_DIR)/%,$(JARS_REFERRED_TO))
OTHER_JARLIST    = $(subst $(SPACE),$(SEP),$(OTHER_JARTMP))

JRE              = $(JAVA_HOME)/jre/lib/rt.jar

CLASSPATH        = $(JRE)$(SEP)$(LOCAL_JARLIST)$(SEP)$(OTHER_JARLIST)


JAVA_OPTIONS     = -classpath $(CLASSPATH) 


PACKAGE_LOC     = $(subst .,/,$(PACKAGE))
CUR_PACK_LOC    = $(subst .,/,$(CUR_PACK))
PACKAGE_DIR     = $(DESTINATION)/$(PACKAGE_LOC)
JAVA_FILES      = $(filter  %.java,$(SOURCE))
CLASS_FILES     = $(JAVA_FILES:%.java=$(PACKAGE_DIR)/%.class)

# Make a list of all packages involved
ifdef PACKAGE
PACKAGE_LIST    = $(subst .,/,$(PACKAGE))
MAIN_CLASS      = $(MAIN)
MAIN_PACKAGE    = $(PACKAGE)
else
PACKAGE_LIST    = $(subst .,/,$(PACKAGES)) $(subst .,/,$(NODOC_PACKAGES))
endif


#***************************************************************************
#
#  Section 3. Rules and dependencies.
#
#  This section defines the exact rules for creating a target file from
#  a (set of) source file(s). The rules can be quite complex and the
#  makefile syntax is not extreamly readable. A quick crash course:
#
#  target : depends
#    rule
#
#  target  - the parameter given to make: What to build
#  depends - file or other targets target depends on
#  rule    - how to create target
#  $(VAR)  - environment variable or variable defined above
#  $@      - Current target
#  $*      - Current target without extension
#  $<      - Current dependency
#
#***************************************************************************

# Make a list of all packages involved
ifdef PACKAGE
PACKAGE_LIST    = $(subst .,/,$(PACKAGE))
MAIN_CLASS      = $(MAIN)
MAIN_PACKAGE    = $(PACKAGE)
else
PACKAGE_LIST    = $(subst .,/,$(PACKAGES)) $(subst .,/,$(NODOC_PACKAGES))
endif

#Rule 10. Apply ChAsE to one file of package purse.
%.PURSE:
	@$(JAVA_BIN)/$(JAVA) $(JAVA_OPTIONS) \
	$(MAIN_PACKAGE).$(MAIN_CLASS) \
	$(SOURCE_DIR)/$(CUR_PACK_LOC)/$*.java \
	$(patsubst %.purse, %.java, $(patsubst %,$(SOURCE_DIR)/$(CUR_PACK_LOC)/%,$(PURSE_SOURCE))) \
	$(patsubst %.utils, %.java, $(patsubst %,$(SOURCE_DIR)/utils/%,$(UTILS_SOURCE))) \
	$(patsubst %.pacapinterfaces, %.java, $(patsubst %,$(SOURCE_DIR)/pacapinterfaces/%,$(PACAPINTERFACES_SOURCE))) \
	$(patsubst %.javacard, %.java, $(patsubst %,$(SOURCE_DIR)/javacard/framework/%,$(JAVACARD_SOURCE))) \

#Rule 11. It's called by Rule12. It Invokes Rule10
%.purse: 
	$(MAKE) -k -f $(SOURCE_DIR)/purse/Makefile $(subst .purse,.PURSE,$@)

#Rule 12. Apply ChAsE to the files of package purse
purse:	$(PURSE_SOURCE)
	$(PRINT) purse Done.

#Rule 13. Apply ChAsE to one file of package utils.
%.UTILS:
	@$(JAVA_BIN)/$(JAVA) $(JAVA_OPTIONS) \
	$(MAIN_PACKAGE).$(MAIN_CLASS) \
	$(SOURCE_DIR)/$(CUR_PACK_LOC)/$*.java \
	$(patsubst %.purse, %.java, $(patsubst %,$(SOURCE_DIR)/purse/%,$(PURSE_SOURCE))) \
	$(patsubst %.utils, %.java, $(patsubst %,$(SOURCE_DIR)/$(CUR_PACK_LOC)/%,$(UTILS_SOURCE))) \
	$(patsubst %.pacapinterfaces, %.java, $(patsubst %,$(SOURCE_DIR)/pacapinterfaces/%,$(PACAPINTERFACES_SOURCE))) \
	$(patsubst %.javacard, %.java, $(patsubst %,$(SOURCE_DIR)/javacard/framework/%,$(JAVACARD_SOURCE))) \

#Rule 14. It's called by Rule15. It Invokes Rule13
%.utils: 
	$(MAKE) -k -f $(SOURCE_DIR)/utils/Makefile $(subst .utils,.UTILS,$@)

#Rule 15. Apply ChAsE to the files of package utils
utils: $(UTILS_SOURCE)
	$(PRINT) utils Done.

#Rule 16. Apply ChAsE to one file of package utils.
%.PACAPINTERFACES:
	@$(JAVA_BIN)/$(JAVA) $(JAVA_OPTIONS) \
	$(MAIN_PACKAGE).$(MAIN_CLASS) \
	$(SOURCE_DIR)/$(CUR_PACK_LOC)/$*.java \
	$(patsubst %.purse, %.java, $(patsubst %,$(SOURCE_DIR)/purse/%,$(PURSE_SOURCE))) \
	$(patsubst %.utils, %.java, $(patsubst %,$(SOURCE_DIR)/utils/%,$(UTILS_SOURCE))) \
	$(patsubst %.pacapinterfaces, %.java, $(patsubst %,$(SOURCE_DIR)/$(CUR_PACK_LOC)/%,$(PACAPINTERFACES_SOURCE))) \
	$(patsubst %.javacard, %.java, $(patsubst %,$(SOURCE_DIR)/javacard/framework/%,$(JAVACARD_SOURCE))) \

#Rule 17. It's called by Rule18. It Invokes Rule16.
%.pacapinterfaces: 
	$(MAKE) -k -f $(SOURCE_DIR)/pacapinterfaces/Makefile $(subst .pacapinterfaces,.PACAPINTERFACES,$@)


#Rule 18. Apply ChAsE to the files of package pacapintefaces.
pacapinterfaces: $(PACAPINTERFACES_SOURCE)
	$(PRINT) pacapinterfaces Done.

#Rule 19. Apply ChAsE to one file of package javacard.framework.
%.JAVACARD:
	@$(JAVA_BIN)/$(JAVA) $(JAVA_OPTIONS) \
	$(MAIN_PACKAGE).$(MAIN_CLASS) \
	$(SOURCE_DIR)/$(CUR_PACK_LOC)/$*.java \
	$(patsubst %.purse, %.java, $(patsubst %,$(SOURCE_DIR)/purse/%,$(PURSE_SOURCE))) \
	$(patsubst %.utils, %.java, $(patsubst %,$(SOURCE_DIR)/utils/%,$(UTILS_SOURCE))) \
	$(patsubst %.pacapinterfaces, %.java, $(patsubst %,$(SOURCE_DIR)/pacapinterfaces/%,$(PACAPINTERFACES_SOURCE))) \
	$(patsubst %.javacard, %.java, $(patsubst %,$(SOURCE_DIR)/$(CUR_PACK_LOC)/%,$(JAVACARD_SOURCE))) \

#Rule 20. It's called by Rule21. It Invokes Rule19
%.javacard: 
	$(MAKE) -k -f $(SOURCE_DIR)/javacard/framework/Makefile $(subst .javacard,.JAVACARD,$@)

#Rule 21. Apply ChAsE to the files of package javacard.framework.
javacard: $(JAVACARD_SOURCE)
	$(PRINT) javacard Done.

#Rule 22. Apply ChAsE to all packages of application purse.
allpurse:
	$(MAKE) -k -f $(SOURCE_DIR)/purse/Makefile purse
	$(MAKE) -k -f $(SOURCE_DIR)/utils/Makefile utils
	$(MAKE) -k -f $(SOURCE_DIR)/pacapinterfaces/Makefile pacapinterfaces
	$(MAKE) -k -f $(SOURCE_DIR)/javacard/framework/Makefile javacard
	$(PRINT) allpurse Done.


%.TEST:
	$(JAVA_BIN)/$(JAVA) $(JAVA_OPTIONS) \
	$(MAIN_PACKAGE).$(MAIN_CLASS) \
	$(SOURCE_DIR)/$(CUR_PACK_LOC)/$*.java \
	$(patsubst %.test, %.java, $(patsubst %,$(SOURCE_DIR)/$(CUR_PACK_LOC)/%,$(TEST_SOURCE)))

%.test:
	$(MAKE) -k -f $(SOURCE_DIR)/test/Makefile $(subst .test,.TEST,$@)

test:	$(TEST_SOURCE)
	$(PRINT) test Done.
