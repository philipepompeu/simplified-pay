# Simplified-Pay

## Objetivo do Projeto
O **Simplified-Pay** é uma plataforma de pagamentos simplificada. Nela, é possível depositar e realizar transferências de dinheiro entre usuários. Temos dois tipos de usuários: **comuns** e **lojistas**. Ambos possuem uma carteira com dinheiro e podem realizar transferências, porém, **lojistas apenas recebem dinheiro e não podem enviar pagamentos**.

## Requisitos Atendidos e Implementação
### Endpoints Implementados
- **`POST /users`** → Permite criar usuários. 
  - Retorna **200** em caso de sucesso.
  - Retorna **422** caso alguma validação falhe (exemplo: CPF ou e-mail já cadastrado).
- **`POST /transfer`** → Permite transferir dinheiro entre usuários.
  - Retorna **200** em caso de sucesso.
  - Retorna **422** caso validações falhem (exemplo: saldo insuficiente ou tentativa de transferência por lojistas).

## Requisitos não implementados

- Antes de finalizar a transferência, deve-se consultar um serviço autorizador externo, use este mock
  [https://util.devi.tools/api/v2/authorize](https://util.devi.tools/api/v2/authorize) para simular o serviço
  utilizando o verbo `GET`;

### Persistência de Dados
Os endpoints foram criados utilizando **Spring Boot** e **Spring Data JPA** para persistência dos dados no banco de dados.

### Notificações Assíncronas e Padrão Observer
- Criamos a entidade **`Notification`**, que registra notificações de transferências recebidas.
- A **classe `NotificationProcessorService`** processa essas notificações periodicamente, mudando seu status.
- Implementamos o **padrão Observer no `UserService`**, permitindo que notificações sejam criadas dinamicamente sem acoplamento direto entre os serviços.

### Arquitetura
O projeto segue uma **estrutura de pastas baseada na Arquitetura Limpa**, garantindo organização, separação de responsabilidades e facilidade de manutenção.

## Tecnologias Utilizadas
- **Java 17**
- **Spring Boot** (Spring Web, Spring Data JPA, Spring Scheduling)
- **Banco de Dados Relacional (H2)**
- **ModelMapper** para conversão de DTOs
- **Padrões de Design: Observer**

## Considerações Finais
O Simplified-Pay foi desenvolvido para demonstrar boas práticas de arquitetura e engenharia de software, garantindo modularidade, testabilidade e escalabilidade. O uso do **padrão Observer** desacopla a criação de notificações, enquanto a **execução assíncrona** das notificações evita impacto no tempo de resposta das transferências. Além disso, a adoção da **Arquitetura Limpa** facilita futuras manutenções e extensões do sistema.

