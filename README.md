# Gerenciamento API

Este projeto é uma API de gerenciamento de pessoas e seus endereços. Ele permite a criação, consulta, edição e exclusão de pessoas, bem como a adição, consulta, edição e exclusão de endereços associados a uma pessoa.

## Tecnologias Utilizadas
- Java 17
- Spring Boot
- H2 Database

- Configuração
Clone o repositório:

`git clone https://github.com/luckraw/gerenciamento-api.git`

Navegue até o diretório do projeto:

`cd gerenciamento-api`

- Configure o arquivo application.properties com as configurações necessárias, como as credenciais do banco de dados.

Execute o projeto:

`./mvnw spring-boot:run`

### Endpoints
- A seguir estão os endpoints disponíveis nesta API:

- Listar Pessoas
Retorna uma lista de todas as pessoas cadastradas.

```
Endpoint: /pessoas
Método: GET
Consultar Pessoa
Retorna os detalhes de uma pessoa específica com base no ID.
``` 
```
Endpoint: /pessoas/{id}
Método: GET
```
```
Criar Pessoa
Cria uma nova pessoa com base nos dados fornecidos.

Endpoint: /pessoas
Método: POST
```

- Corpo da Requisição:
```json
{
  "nome": "Nome da Pessoa",
  "dataNascimento": "yyyy-MM-dd",
  "enderecos": [
    {
      "logradouro": "Logradouro",
      "cep": "CEP",
      "numero": "Número",
      "cidade": "Cidade",
      "principal": true
    }
  ]
}
```
```
Editar Pessoa
Edita os dados de uma pessoa existente com base no ID.

Endpoint: /pessoas/{id}
Método: PUT
```
- Corpo da Requisição:
```json
{
  "nome": "Nome da Pessoa",
  "dataNascimento": "yyyy-MM-dd",
  "enderecos": [
    {
      "logradouro": "Logradouro",
      "cep": "CEP",
      "numero": "Número",
      "cidade": "Cidade",
      "principal": true
    }
  ]
}
```
```
Criar Endereço
Cria um novo endereço para uma pessoa existente com base no ID.

Endpoint: /pessoas/{id}/enderecos
Método: POST
```
- Corpo da Requisição:
```json
{
  "logradouro": "Logradouro",
  "cep": "CEP",
  "numero": "Número",
  "cidade": "Cidade",
  "principal": true
}
```
```
Listar Endereços
Retorna uma lista de todos os endereços associados a uma pessoa específica com base no ID.

Endpoint: /pessoas/{id}/enderecos
Método: GET
```
```
Consultar Endereço
Retorna os detalhes de um endereço específico associado a uma pessoa com base nos IDs fornecidos.

Endpoint: /pessoas/{id}/enderecos/{enderecoId}
Método: GET
```
```
Editar Endereço
Edita os dados de um endereço existente associado a uma pessoa com base nos IDs fornecidos.

Endpoint: /pessoas/{id}/enderecos/{enderecoId}
Método: PUT
```
- Corpo da Requisição:
```json
{
  "logradouro": "Novo Logradouro",
  "cep": "Novo CEP",
  "numero": "Novo Número",
  "cidade": "Nova Cidade",
  "principal": true
}
```
```
Definir Endereço Principal
Define um endereço como o endereço principal para uma pessoa específica com base nos IDs fornecidos.

Endpoint: /pessoas/{id}/enderecos/{enderecoId}/definirPrincipal
Método: PUT
```
