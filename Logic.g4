grammar Logic;

program: expression ('->' expression)? ';';

expression: orExpression;

orExpression: andExpression ('OR' andExpression)*;

andExpression: notExpression ('AND' notExpression)*;

notExpression: 'NOT' atom | atom;

atom: ID;

ID: [a-zA-Z]+;

WS: [ \t\r\n]+ -> skip;
