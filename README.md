
# Transaction Authorizer

Autorizador de transações de cartão de crédito.

## Features implementadas

- **L1. Autorizador simples (Done)**
- **L2. Autorizador com fallback (Done)**
- **L3.Dependente do comerciante (Done)**
- **L4. Questão aberta (Done)**
## Stack utilizada

- **Kotlin**
- **Spring Boot**
- **Postgres**
- **Redis**
- **Open API**
- **Gradle**
- **Docker e Docker Compose**
- **Junit**
- **Mockito**

## Solução Lock de transações

### Redis com Locks Distribuídos

Para ambientes distribuídos, onde múltiplas instâncias da aplicação estão processando transações, optei por usar o Redis para implementar locks distribuídos.

#### Como funciona:
Um lock temporário é colocado em uma conta antes de processar a transação, garantindo que apenas uma instância da aplicação possa processar transações para a mesma conta ao mesmo tempo.
O lock é removido quando o processamento é concluído.

#### Prós
- Desempenho rápido: Redis é extremamente rápido, sendo uma solução eficiente para operações de lock devido ao seu armazenamento em memória.
- Distribuído: Permite que o lock seja compartilhado entre múltiplos servidores ou instâncias de aplicação.
- Expiração automática: O Redis permite definir um TTL (time-to-live) para o lock, garantindo que ele seja liberado automaticamente se algo der errado.
- Simplicidade: Implementar locking com Redis usando bibliotecas como Redisson é simples e direto.

#### Contras
- Persistência limitada: Como Redis é uma solução em memória, em caso de falhas no Redis, o lock pode ser perdido, levando a possíveis conflitos de transação.
- Custo de manutenção: Adicionar Redis ao seu stack traz mais complexidade em termos de configuração e manutenção de infraestrutura.
- Tolerância a falhas: Se não configurado corretamente (por exemplo, sem usar a estratégia correta de locking distribuído como Redlock), pode haver problemas de concorrência.
- Dependência externa: A disponibilidade do Redis se torna crítica para o funcionamento da aplicação. Se o Redis cair, o sistema de locks falha.

#### Implementação

```kotlin
lock = redissonClient.getLock("account:${transactionDTO.account}")
``` 
obtém um lock exclusivo para a conta com base no ID da conta. Isso significa que se duas transações tentarem acessar a mesma conta ao mesmo tempo, a segunda terá que esperar até que a primeira transação libere o lock.

```kotlin
lock.lock()
``` 
garante que o código a seguir seja executado exclusivamente por uma única instância da aplicação para essa conta específica.

```kotlin
finally { lock.unlock() }
```
garante que o lock será sempre liberado, independentemente de o processamento da transação ter sido bem-sucedido ou falhado.
## Executando a aplicação

Siga os passos abaixo para configurar e executar o projeto localmente:

### Pré-requisitos
Certifique-se de ter os seguintes componentes instalados:

- **Docker Compose**: Utilizado para criar o ambiente da API, do banco de dados PostgreSQL e Redis.
- **Gradle**: A ferramenta de build usada no projeto.

### Passos para rodar o projeto

#### Clone o repositório:

Clone o repositório para sua máquina local usando o seguinte comando:

```bash
git clone https://github.com/Jefferson-Euclides/authorization-challenge.git
```

#### Rodar o Docker Compose:

O projeto utiliza PostgreSQL como banco de dados e Redis para o sistema de locking distribuído. Para levantar esses serviços, execute:

```bash
docker-compose up -d
```
Isso iniciará os containers do PostgreSQL, do Redis e da aplicação em segundo plano.

A aplicação estará disponível na porta 8080.

#### Acessar a documentação Swagger:

Para explorar os endpoints da API e suas respectivas descrições, você pode acessar a documentação gerada automaticamente pelo Swagger:

URL da documentação Swagger: http://localhost:8080/api/swagger-ui.html

#### Testar a aplicação:

Para rodar os testes automatizados da aplicação, execute:

```bash
./gradlew test
```

#### Finalizando:

Para parar a aplicação e os containers Docker, execute o seguinte comando:

```bash
docker-compose down
``` 
