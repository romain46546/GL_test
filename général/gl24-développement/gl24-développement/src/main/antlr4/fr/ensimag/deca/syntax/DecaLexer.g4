lexer grammar DecaLexer;

options {
   language=Java;
   // Tell ANTLR to make the generated lexer class extend the
   // the named class, which is where any supporting code and
   // variables will be placed.
   superClass = AbstractDecaLexer;
}

@members {
    /**
     * Raise an error for invalid characters.
     */
    private void invalidChar() {
        throw new DecaRecognitionException(this, _input) {
            private static final long serialVersionUID = 1L;
            @Override
            public String getMessage() {
                return "Caractère \"" + getText() + "\" non autorisé";
            }
        };
    }

    /**
     * Raise an error for unterminated strings.
     */
    private void unterminatedString() {
        throw new DecaRecognitionException(this, _input) {
            private static final long serialVersionUID = 1L;
            @Override
            public String getMessage() {
                return "String literal not terminated";
            }
        };
    }
}

// Deca lexer rules.
OBRACE      : '{';
CBRACE      : '}';
SEMI        : ';';
COMMA       : ',';
EQUALS      : '=';
PRINT       : 'print';
PRINTLN     : 'println';
PRINTX      : 'printx';
PRINTLNX    : 'printlnx';
WHILE       : 'while';
RETURN      : 'return';
IF          : 'if';
ELSE        : 'else';
OR          : '||';
AND         : '&&';
EQEQ        : '==';
NEQ         : '!=';
LEQ         : '<=';
GEQ         : '>=';
GT          : '>';
LT          : '<';
INSTANCEOF  : 'instanceof';
PLUS        : '+';
MINUS       : '-';
TIMES       : '*';
SLASH       : '/';
PERCENT     : '%';
EXCLAM      : '!';
DOT         : '.';
OPARENT     : '(';
CPARENT     : ')';
READINT     : 'readInt';
READFLOAT   : 'readFloat';
NEW         : 'new';
TRUE        : 'true';
FALSE       : 'false';
THIS        : 'this';
NULL        : 'null';
CLASS       : 'class';
EXTENDS     : 'extends';
PROTECTED   : 'protected';
ASM         : 'asm';
KW_INT      : 'int';
KW_FLOAT    : 'float';
KW_BOOLEAN  : 'boolean';

STRING      : '"' ( '\\' . | ~["\\\r\n] )* '"';
MULTI_LINE_STRING : '"' ( '\\' . | ~["\\] )* '"';
UNTERMINATED_STRING : '"' ( '\\' . | ~["\\\r\n] )* ( '\r'? '\n' | EOF ) { unterminatedString(); };
FLOAT       : DIGIT+ '.' DIGIT+ EXP? | DIGIT+ EXP;
fragment EXP: ('e'|'E') ('+'|'-')? DIGIT+;
INT         : DIGIT+;
IDENT       : LETTER (LETTER | DIGIT | '_')*;

COMMENT     : '/*' .*? '*/' -> skip;
LINE_COMMENT: '//' ~[\r\n]* -> skip;
WS          : [ \t\r\n\f]+ -> skip;

INCLUDE
    : '#include' [ \t]* '"' FILENAME '"' { doInclude(getText()); } -> skip
    ;

fragment FILENAME
    : [a-zA-Z0-9._-]+
    ;


INVALID_CHAR : . { invalidChar(); };

fragment LETTER : [a-zA-Z_$];
fragment DIGIT  : [0-9];
