grammar SetCommand;

setStatement
    : 'SET' target '=' expression ';'?
    ;

target
    : identifier                         # TargetIdentifier
    | 'PATH' '(' pathExpression ')'            # TargetPath
    ;

expression
    : expression '+' expression          # AddExpr
    | expression '-' expression          # SubExpr
    | expression '*' expression          # MulExpr
    | expression '/' expression          # DivExpr
    | expression '&&' expression         # AndExpr
    | expression '||' expression         # OrExpr
    | expression '==' expression         # EqExpr
    | expression '!=' expression         # NeqExpr
    | expression '>' expression          # GtExpr
    | expression '<' expression          # LtExpr
    | expression '>=' expression         # GteExpr
    | expression '<=' expression         # LteExpr
    | '(' expression ')'                 # ParenExpr
    | jsonValue                          # JsonValueExpr
    | 'PATH' '(' pathExpression ')'      # PathExpr
    | identifier                         # IdentifierExpr
    | literal                            # LiteralExpr
    ;

pathExpression
    : identifier ('.' identifier | '[' NUMBER ']')*
    ;

jsonValue
    : '{' pair (',' pair)* '}'             # JsonObject
    | '[' expression (',' expression)* ']' # JsonArray
    ;

pair
    : STRING ':' expression
    ;

literal
    : STRING
    | NUMBER
    | 'true'
    | 'false'
    | 'null'
    ;

identifier
    : ID
    ;

STRING : '"' (~["\\] | '\\' .)* '"';
NUMBER : '-'? [0-9]+ ('.' [0-9]+)?;
ID     : [a-zA-Z_][a-zA-Z0-9_]*;
WS     : [ \t\r\n]+ -> skip;
