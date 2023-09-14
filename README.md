# Projeto da Disciplina de Compiladores - 2023.1

- [Objetivo](#objetivo)
- [Comandos para Compilar](#comandos-para-compilar)
- [Como Usar](#como-usar)

## Objetivo
Criar um DSL para analisar expressões lógicas e transformá-la em código Java.

## Comandos para Compilar
1. Compilar a descrição da linguagem fonte:

```
java -jar antlr.jar Logic.g4 -o gen
```
O comando acima executa o gerador ANTLR que converte a descrição da gramática (Logic.g4) em
programas Java (Analisadores léxicos e sintáticos).


2. Compilar programas em Java:


```
javac -cp antlr.jar:gen *.java gen/*.java -d gen
```
O comando acima executa o compilador Java. O arquivo antlr.jar, que contem as bibliotecas
runtime utilizadas pelo código gerado pelo antlr são adicionadas ao CLASSPATH.


3. Executar o programa:

```
java -cp antlr.jar:gen LogicMain
```

O comando acima executa a classe LogicMain do compilador. Para a classe poder ser executada é necessário também incluir os arquivos do runtime do antlr.jar.

## Como usar
1. Criar um codespaces do projeto

2. Dentro do codespaces, abrir o arquivo "exemplo.logic" e escrever a(s) expressão(ões) lógica(s).

3. Executar o programa (escreva 'java -cp antlr.jar:gen LogicMain' no terminal)

4. A(s) expressão(ões) lógica(s) escolhida(s) será(ão) transformada(s) em código Java e pode(m) ser visualizada(s) no terminal.

# Desenvolvedores
<table>
  <tr>
    <td align="center">
      <a href="https://github.com/Vinizik">
        <img src="colaboradores/vinicius-martins.jpg" width="100px;"/><br>
        <sub>
          <b>Vinícius Martins</b>
        </sub>
      </a>
    </td>
    <td align="center">
      <a href="https://github.com/JoaoKern">
        <img src="colaboradores/joao-kern.jpg" width="100px;"/><br>
        <sub>
          <b>João Kern</b>
        </sub>
      </a>
    </td>
    <td align="center">
      <a href="https://github.com/GabrielCaminha">
        <img src="colaboradores/gabriel-caminha.jpg" width="100px;"/><br>
        <sub>
          <b>Gabriel Caminha</b>
        </sub>
      </a>
    </td>
    <td align="center">
      <a href="https://github.com/rodrigopascaretta">
        <img src="colaboradores/rodrigo-pascaretta.jpg" width="100px;"/><br>
        <sub>
          <b>Rodrigo Pascaretta</b>
        </sub>
      </a>
    </td>
    <td align="center">
      <a href="https://github.com/arthurbf2">
        <img src="colaboradores/arthur-falcao.jpg" width="100px;"/><br>
        <sub>
          <b>Arthur Falcão</b>
        </sub>
      </a>
    </td>
  </tr>
</table>
