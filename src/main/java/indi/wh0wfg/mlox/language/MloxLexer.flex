package indi.wh0wfg.mlox.language;

import com.intellij.lexer.FlexLexer;
import com.intellij.psi.tree.IElementType;

import indi.wh0wfg.mlox.language.psi.MloxTokenTypes;

%%

%{
  public MloxLexer() {
    this((java.io.Reader)null);
  }
%}

%public
%class MloxLexer
%implements FlexLexer
%function advance
%type IElementType
%unicode

EOL=\R
WHITE_SPACE=\s+

WHITE_SPACE=[ \t\n\x0B\f\r]+
COMMENT="//".*
NUMBER=[0-9]+(\.[0-9]+)?
STRING=\"([^\"\\]|\\.)*\"
IDENTIFIER=[_a-zA-Z][_a-zA-Z0-9]*

%%
<YYINITIAL> {
  {WHITE_SPACE}       { return MloxTokenTypes.WHITE_SPACE; }

  "("                 { return MloxTokenTypes.LEFT_PAREN; }
  ")"                 { return MloxTokenTypes.RIGHT_PAREN; }
  "["                 { return MloxTokenTypes.LEFT_SQUARE; }
  "]"                 { return MloxTokenTypes.RIGHT_SQUARE; }
  "{"                 { return MloxTokenTypes.LEFT_BRACE; }
  "}"                 { return MloxTokenTypes.RIGHT_BRACE; }
  ","                 { return MloxTokenTypes.COMMA; }
  "."                 { return MloxTokenTypes.DOT; }
//  "++"                { return MloxTokenTypes.INCREMENT; }
//  "--"                { return MloxTokenTypes.DECREMENT; }
  "+="                { return MloxTokenTypes.PLUS_ASSIGN; }
  "-="                { return MloxTokenTypes.MINUS_ASSIGN; }
  "*="                { return MloxTokenTypes.MUL_ASSIGN; }
  "/="                { return MloxTokenTypes.DIV_ASSIGN; }
  "%="                { return MloxTokenTypes.MOD_ASSIGN; }
  "!="                { return MloxTokenTypes.NOT_EQUAL; }
  "=="                { return MloxTokenTypes.EQUAL; }
  ">="                { return MloxTokenTypes.GREAT_EQUAL; }
  "<="                { return MloxTokenTypes.LESS_EQUAL; }
  ":"                 { return MloxTokenTypes.COLON; }
  ";"                 { return MloxTokenTypes.EOL; }
  "/"                 { return MloxTokenTypes.SLASH; }
  "*"                 { return MloxTokenTypes.STAR; }
  "!"                 { return MloxTokenTypes.BANG; }
  "-"                 { return MloxTokenTypes.MINUS; }
  "+"                 { return MloxTokenTypes.PLUS; }
  "%"                 { return MloxTokenTypes.MOD; }
  "="                 { return MloxTokenTypes.ASSIGN; }
  ">"                 { return MloxTokenTypes.GREATER; }
  "<"                 { return MloxTokenTypes.LESS; }
  "?"                 { return MloxTokenTypes.QUESTION; }
  "|"                 { return MloxTokenTypes.PIPE; }
  "import"            { return MloxTokenTypes.IMPORT; }
  "and"               { return MloxTokenTypes.AND; }
  "class"             { return MloxTokenTypes.CLASS; }
  "else"              { return MloxTokenTypes.ELSE; }
  "false"             { return MloxTokenTypes.FALSE; }
  "fun"               { return MloxTokenTypes.FUN; }
  "lambda"            { return MloxTokenTypes.LAMBDA; }
  "foreach"           { return MloxTokenTypes.FOREACH; }
  "in"                { return MloxTokenTypes.IN; }
  "for"               { return MloxTokenTypes.FOR; }
  "switch"            { return MloxTokenTypes.SWITCH; }
  "case"              { return MloxTokenTypes.CASE; }
  "default"           { return MloxTokenTypes.DEFAULT; }
  "try"               { return MloxTokenTypes.TRY; }
  "catch"             { return MloxTokenTypes.CATCH; }
  "finally"           { return MloxTokenTypes.FINALLY; }
  "throw"             { return MloxTokenTypes.THROW; }
  "if"                { return MloxTokenTypes.IF; }
  "nil"               { return MloxTokenTypes.NIL; }
  "or"                { return MloxTokenTypes.OR; }
  "print"             { return MloxTokenTypes.PRINT; }
  "return"            { return MloxTokenTypes.RETURN; }
  "break"             { return MloxTokenTypes.BREAK; }
  "continue"          { return MloxTokenTypes.CONTINUE; }
  "super"             { return MloxTokenTypes.SUPER; }
  "this"              { return MloxTokenTypes.THIS; }
  "true"              { return MloxTokenTypes.TRUE; }
  "var"               { return MloxTokenTypes.VAR; }
  "do"                { return MloxTokenTypes.DO; }
  "while"             { return MloxTokenTypes.WHILE; }

  {WHITE_SPACE}       { return MloxTokenTypes.WHITE_SPACE; }
  {COMMENT}           { return MloxTokenTypes.COMMENT; }
  {NUMBER}            { return MloxTokenTypes.NUMBER; }
  {STRING}            { return MloxTokenTypes.STRING; }
  {IDENTIFIER}        { return MloxTokenTypes.IDENTIFIER; }

}

[^] { return MloxTokenTypes.BAD_CHARACTER; }
