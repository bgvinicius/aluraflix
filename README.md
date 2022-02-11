# aluraflix [![codecov](https://codecov.io/gh/bgvinicius/aluraflix/branch/main/graph/badge.svg?token=YN2CQF8NTZ)](https://codecov.io/gh/bgvinicius/aluraflix)


Projeto desenvolvido para disciplina de Técnicas de Programação 2 da Universidade Federal do Ceará.

O projeto consiste em uma API desenvolvida em spring boot a partir do backlog presente [aqui](https://www.alura.com.br/challenges/back-end/semana-01-api-rest).

O projeto possui fluxo de integração contínua configurado, com execução de testes, coverage e análise estática de código com o [CheckStyle](https://checkstyle.sourceforge.io/) e [reviewdog](https://github.com/reviewdog/reviewdog). Para armazenamento de coverage dos testes, foi utilizado o [codecov.io](https://www.codecov.io/).

Além disso, o projeto possui deploy automatizado no Heroku a partir de commits na branch `main`.

Os testes são implementados usando JUnit 5 e Mockito para realizar mocks das dependências. A injeção de dependências é facilitada pelo próprio spring.

---

# Executando testes

Para executar o projeto localmente, primeiramente, é necessário ter o Java 17 instalado em sua máquina

No linux, isso pode ser feito com o comando 

`sudo apt-get install openjdk-17-jdk`

Uma vez que o java estiver instalado, você na raiz do projeto você pode executar o comando `./mvnw test` para executar os testes da aplicação.

--- 

# Executando a API

Para executar a aplicação, você precisa ter o postgres em sua máquina. É possível usar o docker para executá-lo em sua máquina, para isso, você pode [instalar o docker por aqui](https://docs.docker.com/engine/install/ubuntu/).

Ao ter o docker instalado, basta executar o comando para iniciar o postgres em seu computador:

`docker run -p 5432:5432 --name some-postgres2 -e POSTGRES_PASSWORD=password -e POSTGRES_DB=aluraflix -d postgres`

Por fim, execute a aplicação com o comando:

`./mvnw spring-boot:run`

Isso iniciará a API localmente na porta 8080.

Você pode ver a documentação da API [nesse link](https://www.postman.com/aerospace-geoscientist-56099919/workspace/aluraflix/overview).

Clique aqui [![Run in Postman](https://run.pstmn.io/button.svg)](https://god.gw.postman.com/run-collection/19549763-461452f4-ae7a-42da-bc9f-ea5ed38754f3?action=collection%2Ffork&collection-url=entityId%3D19549763-461452f4-ae7a-42da-bc9f-ea5ed38754f3%26entityType%3Dcollection%26workspaceId%3D2a5a8837-be26-4ae7-af2a-1f05ed235836) para executar requisições na API disponibilizada no Heroku.

---

# Deploy

A aplicação possui deploy automatizado no heroku sempre que surgir algum commit novo na branch main, é possível acessar a API através do seguinte link:

`https://aluraflix-tecnicasprog2.herokuapp.com/`
