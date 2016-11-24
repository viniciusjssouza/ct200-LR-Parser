## CT-200 - Trabalho 2 - Parsers LR
---
**Equipe**
- Ederson Monteiro de Oliveira Donde
- Filipe Spuri Ribeiro Silva
- Vinicius Jose Silveira de Souza
     
---    
Projeto contendo um exemplo simples de um parser LR(1). 

O parser implementado reconhece a linguagem:
```
 L = (a|b)(,(a|b))+ 
```

A gramática livre de contexto implementada é:

```
(1) LIST -> LIST ',' ELEMENT
(2) LIST -> ELEMENT
(3) ELEMENT -> 'a'
(4) ELEMENT -> 'b'
```

Para executar a aplicação, basta executar o jar com a seguinte linha de comando:

```
java -jar LRParserExample.jar
```
Em seguida, forneça a string de entrada para ser avaliada pelo parser como, por exemplo:
```
a,b,b
```
O programa exibirá os símbolos encontrados durante o processo de parsing, indicando ao final se a string fornecida é aceita pela linguagem ou não:
```
a,b,b
--> Element found:a
--> List element found!
--> Element found:b
--> List element found!
--> Element found:b
--> List element found!
Input ACCEPTED!

```
Para terminar o aplicativo, basta fornecer uma entrada vazia.
