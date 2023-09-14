grammar Logic;

// Regras de entrada
program: statement+;

// Reescrevendo a regra para declaração de circuito lógico com ponto e vírgula
statement: ID '=' expression ';';

// Expressões lógicas
expression: term #Ter
         | expression 'AND' term #And
         | expression 'OR' term #Or
         | expression 'XOR' term #Xor
         | 'NOT' term #Not
         ;

term: ID
    | '(' expression ')'
    ;

// Identificadores (nomes de variáveis)
ID: [A-Za-z]+;

// Ignorar espaços em branco e quebras de linha
WS: [ \t\n\r]+ -> skip;
