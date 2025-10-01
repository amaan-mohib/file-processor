grammar FileQuery;

// Entry point
query
    : statement+ EOF
    ;

// Statement dispatcher
statement
    : selectStatement
    | setStatement
    | removeStatement
    | deleteStatement
    | insertStatement
    | filterStatement
    ;

target
    : identifier                         # TargetIdentifier
    | PATH '(' pathExpression ')'        # TargetPath
    ;

pathExpression
    : identifier ('.' identifier | '[' NUMBER ']')*
    ;

// Statements
selectStatement
    : SELECT columnList (WHERE comparison)? ';'?
    ;

setStatement
    : SET assignment (',' assignment)* ';'?
    ;

removeStatement
    : REMOVE target (',' target)* ';'?
    ;

deleteStatement
    : DELETE WHERE comparison ';'?
    ;

filterStatement
    : FILTER WHERE comparison ';'?
    ;

insertStatement
    : INSERT ('(' columnList ')')? VALUES '(' valueList ')' ';'?
    ;

// Functions
function
    : uppercase
    | lowercase
    | length
    ;
uppercase : UPPER '(' functionArgs ')' ;
lowercase : LOWER '(' functionArgs ')' ;
length    : LENGTH '(' functionArgs ')' ;

functionArgs
    : identifier                    # FunctionIdExpr
    | STRING                        # FunctionStrExpr
    | PATH '(' pathExpression ')'   # FunctionPathExpr
    ;

// Helpers
columnList
    : '*'
    | identifier (',' identifier)*
    ;

assignment
    : target '=' expression
    ;

valueList
    : value (',' value)*
    ;

comparison
    : conditionExpr comparator conditionExpr    # ComparisonExpr
    ;

common
    : value                             # ValueExpr
    | identifier                        # IdentifierExpr
    | PATH '(' pathExpression ')'       # PathExpr
    | function                          # FunctionExpr
    ;

conditionExpr
    : common
    ;

expression
    : common                            # CommonExpr
    | expression operator expression    # OpExpr
    | '(' expression ')'                # ParenExpr
    ;

value
    : STRING
    | NUMBER
    | 'true'
    | 'false'
    | 'null'
    | jsonValue
    ;

identifier
    : ID
    ;

jsonValue
    : '{' pair (',' pair)* '}'      # JsonPairExpr
    | '[' value (',' value)* ']'    # JsonValueExpr
    ;

pair
    : STRING ':' value
    ;

comparator
    : '>' | '<' | '>=' | '<=' | '==' | '!='
    ;

arithmetic
    : '+' | '-' | '*' | '/'
    ;

logical
    : AND | OR
    ;

operator
    : arithmetic | comparator | logical
    ;

// Lexer rules
SELECT : 'SELECT' | 'select';
SET    : 'SET' | 'set';
REMOVE : 'REMOVE' | 'remove';
DELETE : 'DELETE' | 'delete';
FILTER : 'FILTER' | 'filter';
INSERT : 'INSERT' | 'insert';
VALUES : 'VALUES' | 'values';
AND    : 'AND' | 'and';
OR     : 'OR' | 'or';
WHERE  : 'WHERE' | 'where';
PATH   : 'PATH' | 'path';
UPPER  : 'UPPER' | 'upper';
LOWER  : 'LOWER' | 'lower';
LENGTH : 'LENGTH' | 'length';

ID     : [a-zA-Z_][a-zA-Z0-9_]*;
NUMBER : [0-9]+ ('.' [0-9]+)?;
STRING : '"' (~["\\] | '\\' .)* '"';

WS     : [ \t\r\n]+ -> skip;
