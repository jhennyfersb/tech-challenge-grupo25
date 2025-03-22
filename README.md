# Documentação Fase 2 - Tech Challenge

## Índice
- [Documentação Fase 2 - Tech Challenge](#documentação-fase-2---tech-challenge)
  - [Índice](#índice)
  - [Introdução](#introdução)
  - [Estrutura do Projeto](#estrutura-do-projeto)
    - [Camadas do Projeto](#camadas-do-projeto)
    - [Estrutura de Pastas](#estrutura-de-pastas)
  - [Domínios](#domínios)
  - [Funcionalidades Implementadas](#funcionalidades-implementadas)
  - [Qualidade do Código](#qualidade-do-código)
  - [Collections para Teste](#collections-para-teste)
  - [Repositório de Código](#repositório-de-código)

---

## Introdução
Este documento tem como objetivo fornecer uma visão detalhada do projeto **Tech Challenge**, que visa desenvolver um sistema de gestão para restaurantes. O projeto é dividido em várias fases, sendo a **Fase 2** o foco desta documentação. Nesta fase, expandimos o sistema incluindo a gestão dos tipos de usuários, cadastro de restaurantes e cardápios. Além disso, são implementadas práticas de desenvolvimento e estruturação de código limpo, além de requisitos técnicos para garantir alta qualidade e organização.

---

## Estrutura do Projeto
A estrutura do projeto foi organizada de forma a garantir modularidade, escalabilidade e manutenção, seguindo os princípios da **Clean Architecture**. A divisão em camadas permite uma separação clara de responsabilidades, facilitando a implementação de novas funcionalidades e a realização de testes.

### Camadas do Projeto
O projeto está dividido em camadas que refletem as responsabilidades específicas de cada parte do sistema. As principais camadas são:

- **Domain (Domínio)**  
  - Contém as principais entidades do sistema, como Usuário, Endereço, Restaurante e Item Menu.
  - Representa as regras de negócio e os objetos centrais do sistema.
  - Não possui dependências externas, garantindo que o núcleo do sistema seja independente de frameworks ou tecnologias específicas.

- **Application (Aplicação)**  
  - Implementa os casos de uso do sistema, como "Cadastrar usuário", "Criar Restaurante" e "Adicionar Item ao Menu".
  - Coordena as interações entre as camadas de domínio e infraestrutura.
  - Contém serviços e interfaces que definem as operações necessárias para executar as funcionalidades do sistema.

- **Infrastructure (Infraestrutura)**  
  - Responsável pela comunicação com sistemas externos, como bancos de dados (SQL ou NoSQL) e APIs.
  - Implementa repositórios concretos para persistência de dados e integração com frameworks como Spring Boot.
  - Contém configurações específicas para o ambiente de execução, como conexões com o Docker.

- **Presentation (Interface)**  
  - Define os endpoints da API RESTful para interação com o sistema.
  - Utiliza controladores (controllers) para receber requisições HTTP, processá-las e retornar respostas adequadas.
  - Garante que os dados sejam formatados corretamente para consumo por clientes externos.

- **Configuration (Configuração)**  
  - Contém arquivos de configuração do sistema, como `application.properties` ou `application.yml`.
  - Define parâmetros de conexão com o banco de dados, configurações de segurança e outras propriedades essenciais para a execução do projeto.

- **Testes**  
  - Inclui testes unitários e de integração para garantir a qualidade do código.

### Estrutura de Pastas
```
src/
├── main/
│ ├── java/com/techchallenge/
│ │ ├── domain/
│ │ │ ├── entities/ # Entidades principais
│ │ │ ├── repositories/ # Interfaces de repositório
│ │ │ └── services/ # Serviços de domínio (regras de negócio)
│ │ ├── application/
│ │ │ ├── usecases/ # Casos de uso
│ │ │ └── dtos/ # DTOs para transferência de dados
│ │ ├── infrastructure/
│ │ │ ├── persistence/ # Implementação de repositórios
│ │ │ ├── config/ # Configurações do sistema
│ │ │ └── security/ # Configurações de segurança
│ │ ├── presentation/
│ │ │ ├── controllers/ # Controladores REST
│ │ │ └── exceptions/ # Tratamento de exceções
│ │ └── RedeApplication.java # Classe principal do projeto
│ └── resources/
│ ├── application.properties # Configurações gerais
└── test/
├── java/com/techchallenge/ # Testes unitários e de integração
```

---

## Domínios
- **Usuário**  
  Este domínio lida com a gestão dos diferentes tipos de usuários no sistema, incluindo "Dono de Restaurante" e "Cliente".  
  - Cada usuário poderá ter vários endereços cadastrados para “entrega”.  
  - Optamos por usar as “roles” dentro de um ENUM e não com gestão em um banco de dados. A lógica por trás dessa decisão está atrelada ao fato de que se precisarmos cadastrar novas “roles”, será necessário refatorar o código para validar novas permissões, mantendo nosso código mais fechado.

- **Endereço**  
  Este domínio se concentra na gestão de endereços relacionados aos usuários.  
  - O endereço do restaurante está atrelado ao mesmo. A entidade Endereço está classificada em N para 1 com usuário, mas para restaurante é 1 para 1, sendo esse salvo junto à entidade de Restaurante.

- **Restaurante**  
  Este domínio abrange o cadastro completo de restaurantes, incluindo informações essenciais para sua operação.

- **Item do Menu**  
  Este domínio trata do cadastro dos itens vendidos nos restaurantes, incluindo detalhes. Não foi implementado sistema de armazenamento das fotos do item.

---

## Funcionalidades Implementadas
Nesta fase do projeto, foram desenvolvidas as seguintes funcionalidades principais:

1. **Cadastro de Tipo de Usuário**  
   - Implementação de um CRUD para gerenciar os tipos de usuários ("Dono de Restaurante" e "Cliente").  
   - Associação dos tipos de usuários a perfis existentes no sistema, com estratégias específicas dependendo do banco de dados.

2. **Cadastro de Restaurante**  
   - Desenvolvimento de um CRUD completo para o cadastro de restaurantes.  
   - Inclusão de informações como nome, endereço, tipo de cozinha, horário de funcionamento e dono do restaurante (associado a um usuário existente).

3. **Cadastro de Itens do Cardápio**  
   - Criação de um CRUD para gerenciar os itens vendidos nos restaurantes.  
   - Detalhamento dos itens com campos como nome, descrição, preço, disponibilidade para consumo apenas no local e caminho de armazenamento da foto do prato (sem upload real de imagens, apenas salvando o caminho).

---

## Qualidade do Código
- **Uso Adequado das Práticas de Desenvolvimento do Spring Boot**  
  - Utilização correta de anotações como `@RestController`, `@Service`, `@Repository` e `@Entity` para organizar as responsabilidades do código.  
  - Implementação de injeção de dependências (`@Autowired`) para promover o desacoplamento entre as camadas.  
  - Configuração adequada do arquivo `application.properties` para gerenciar variáveis de ambiente, conexão com o banco de dados e outras configurações.

- **Organização e Estruturação do Código**  
  - **Separação de Responsabilidades**: Divisão clara entre as camadas do sistema, como Domain, Application e Infrastructure, seguindo os princípios da Clean Architecture.  
  - **Nomenclatura Clara**: Uso de nomes descritivos para classes, métodos e variáveis, evitando ambiguidades.  
  - **Modularização**: Agrupamento lógico de classes relacionadas em pacotes específicos (ex.: controllers, services, repositories).

---

## Collections para Teste
- [Collection Postman - Array de Sabores](https://documenter.getpostman.com/view/35154103/2sAYdmjnHr)

## Repositório de Código
- [GitHub - Array de Sabores](https://github.com/jhennyfersb/tech-challenge-grupo25)
