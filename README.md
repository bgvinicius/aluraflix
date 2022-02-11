# aluraflix [![codecov](https://codecov.io/gh/bgvinicius/aluraflix/branch/main/graph/badge.svg?token=YN2CQF8NTZ)](https://codecov.io/gh/bgvinicius/aluraflix)


Projeto desenvolvido para disciplina de Técnicas de Programação 2 da Universidade Federal do Ceará.

O projeto consiste em uma API desenvolvida em spring boot a partir do backlog presente [aqui](https://www.alura.com.br/challenges/back-end/semana-01-api-rest).

O projeto possui fluxo de integração contínua configurado, para tal, utiliza o [codecov.io](https://www.codecov.io/) para armazenamento de coverage dos testes.

Os testes são implementados usando JUnit 5 e Mockito para realizar mocks das dependências. A injeção de dependências é facilitada pelo próprio spring.

---

# Executando testes

Para executar o projeto localmente, primeiramente, é necessário ter o Java 17 instalado em sua máquina

No linux, isso pode ser feito com o comando 

`sudo apt-get install openjdk-17-jdk`

Uma vez que o java estiver instalado, você na raiz do projeto você pode executar o comando `./mvnw test` para executar os testes da aplicação.

--- 

# Executando API

Para executar a aplicação, você precisa ter o postgres em sua máquina. É possível usar o docker para executá-lo em sua máquina, para isso, você pode [instalar o docker por aqui](https://docs.docker.com/engine/install/ubuntu/).

Ao ter o docker instalado, basta executar o comando para iniciar o postgres em seu computador:

`docker run -p 5432:5432 --name some-postgres2 -e POSTGRES_PASSWORD=password -e POSTGRES_DB=aluraflix -d postgres`

Por fim, execute a aplicação com o comando:

`./mvnw spring-boot:run`

Isso iniciará a API localmente na porta 8080.

Você pode ver a documentação da API [nesse link](https://www.postman.com/acessocidadaoce/workspace/aluraflix/collection/17513664-c0b5a8bd-c77a-4bff-89ba-ef47517dc2cb?ctx=documentation).

Caso deseje executar algum endpoint, lembre-se de colocar o environment para dev.

---