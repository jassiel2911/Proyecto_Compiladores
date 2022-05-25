%{
    #include <stdio.h>
%}

entero [0-9]
reales ([0-9]*\.[0-9]*)
letra [A-Za-z]
id (_|{letra})(_|{letra}|{entero})*
op ("+")|("-")
parentesis ("(")|(")")

%option noyywrap
%option yylineno

%%
i-(i+i) {printf("\n%s palabra valida",yytext);}
{entero}* {printf("Cadena valida %s",yytext);}
{reales}* {printf("Cadena valida %s",yytext);}
{letra}* {printf("Cadena valida %s",yytext);}
{op}* {printf("Cadena valida %s",yytext);}
{parentesis}* {printf("Cadena valida %s",yytext);}
{id}* {printf("Identificador encontrado %s",yytext);}
.* {printf("Cadena no valida %s",yytext);}
%%

int main(int argc, char *argv[]){
    if(argc > 1){
        yyin = fopen(argv[1], "r");
    }
    else{
        printf("No se puede abrir el archivo\n");
    }
    yylex();
}