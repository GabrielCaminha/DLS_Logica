grammar Logic;

// Regras de entrada
program: statement+;

// Regra para declaração de circuito lógico
statement: ID '=' expression;

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
