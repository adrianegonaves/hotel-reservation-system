# hotel-reservation-system
# Sistema de Reservas de Hotel (Hotel Reservation System)

Este é um sistema de gestão de reservas de hotel simples, baseado na consola, desenvolvido em Java. O projeto demonstra a aplicação de princípios de Programação Orientada a Objetos (POO) e *Padrões de Design (Design Patterns)* para criar uma arquitetura modular e extensível.

## Funcionalidades

* *Reserva de Quartos:* Verificação de disponibilidade e reserva de quartos para hóspedes.
* *Gestão de Estadias:* Simulação de check-in e check-out.
* *Faturação:* Cálculo do custo total da estadia e geração de faturas.
* *Monitorização de Estado:* Atualização automática do estado do quarto (ex: limpeza/manutenção) após o check-out.

## Tecnologias Utilizadas

* *Java 17:* Linguagem de programação principal.
* *Apache Maven:* Gestão de dependências e automação de build.
* *JUnit 5:* Framework para testes unitários.
* *JaCoCo:* Plugin para verificação de cobertura de código (Code Coverage).

## Arquitetura e Design Patterns

O projeto utiliza vários padrões de design para garantir baixo acoplamento e alta coesão:

### 1. Strategy Pattern (Estratégia)
Utilizado no cálculo de preços. A interface PricingStrategy permite a implementação de diferentes regras de negócio para o cálculo da estadia (ex: StandardPricingStrategy), facilitando a adição de novas estratégias (como preços sazonais) sem alterar o serviço principal.

### 2. Observer Pattern (Observador)
Implementado para monitorizar o estado dos quartos. A interface RoomObserver permite que serviços como o HousekeepingService sejam notificados automaticamente quando o estado de um objeto Room é alterado (por exemplo, quando um hóspede faz check-out, o serviço de limpeza é notificado).

### 3. Camada de Serviços (Service Layer)
A lógica de negócio está encapsulada em serviços dedicados:
* ReservationService: Gere a criação de reservas e ocupação de quartos.
* BillingService: Responsável pela geração de faturas.
* PricingService: Calcula os valores a pagar.

## Como Executar o Projeto

### Pré-requisitos
* Java JDK 17 instalado.
* Maven instalado.

### Passos

1.  *Compilar o projeto:*
    bash
    mvn clean install
    

3.  *Executar a aplicação:*
    Pode executar a classe Main diretamente através do Maven:
    bash
    mvn exec:java -Dexec.mainClass="com.hotel.Main"
    

## Como Executar os Testes

O projeto inclui testes unitários configurados com JUnit 5. Para executar os testes e gerar o relatório de cobertura:

```bash
mvn test