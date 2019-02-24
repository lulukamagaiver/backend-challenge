# Desafio de recrutamento de Invillia

[! [Build Status] (https://travis-ci.org/shelsonjava/invillia.svg?branch=master)] (https://travis-ci.org/shelsonjava/invillia)

! [Invillia Logo] (https://invillia.com/public/assets/img/logo-invillia.svg)
[Invillia] (https: // https: //www.invillia.com/) - A transformação começa aqui.

A empresa ACME está migrando seu sistema monolítico para uma arquitetura de microsserviço e você é responsável por criar seu MVP (produto mínimo viável).
https://en.wikipedia.org/wiki/Minimum_viable_product

Seu desafio é:
Crie um aplicativo com os recursos descritos abaixo, se você acha que os requisitos não são detalhados o suficiente, deixe um comentário (português ou inglês) e prossiga da melhor forma possível.

Você pode escolher quantos recursos julgar necessários para o MVP, NÃO É necessário criar todos os recursos, recomendamos enfaticamente que se concentre na qualidade em vez da quantidade. Você será avaliado pela qualidade de sua solução.

Se você acha que algo é realmente necessário, mas não tem tempo suficiente para implementar, explique, pelo menos, como você o implementaria.

## Tarefas

Sua tarefa é desenvolver um (ou mais, sinta-se livre) serviço (s) RESTful para:
* Crie uma ** Loja **
* Atualize uma informação da ** Loja **
* Recupere uma ** Loja ** por parâmetros
* Crie um ** Pedido ** com itens
* Crie um ** Pagamento ** para um ** Pedido **
* Recupere um ** Pedido ** por parâmetros
* Reembolso ** Pedido ** ou qualquer ** Item de pedido **

Bifurque este repositório e envie seu código com commits parciais.

## Regras do negócio

* A ** Loja ** é composta por nome e endereço
* Um ** Pedido ** é composto por endereço, data de confirmação e status
* Um ** Item de Pedido ** é composto por descrição, preço unitário e quantidade.
* A ** Pagamento ** é composto por status, número de cartão de crédito e data de pagamento
* Um ** Pedido ** só deve ser reembolsado até dez dias após a confirmação e o pagamento é concluído.

## Requisitos não Funcionais

Seu (s) serviço (s) deve (m) ser resiliente (s), tolerante a falhas, responsivo (s). Você deve prepará-lo para ser altamente escalável quanto possível.

O processo deve ser o mais próximo possível de "tempo real", equilibrando suas escolhas para alcançar o esperado
escalabilidade.

## É bom ter recursos (descrever ou implementar):
* Processamento assíncrono OK
* Base de dados - H2 ok
* Docker - Não implementado por falta de tempo
* AWS
* Segurança - Implementado na regra de negocio
* Swagger - Implementado
* Código Limpo - OK