# 🚢 Import Control (Full-Stack)

Sistema completo de gerenciamento de processos de importação via API REST e interface React.

## 📖 Sobre o Projeto

O **Import Control** é uma solução Full-Stack desenvolvida para modernizar o controle de processos de importação, substituindo o uso de planilhas manuais e descentralizadas. 
O objetivo principal da aplicação é garantir a integridade dos dados logísticos e financeiros, evitar a duplicidade de lançamentos e fornecer uma interface padronizada e segura para o cadastro e consulta de invoices, fornecedores, condições de pagamento e previsões de embarque.

---

## 🏆 Desafio: Padrões de Projeto (Design Patterns)

Este projeto foi apresentado como solução prática para o desafio de **Padrões de Projeto**. Optou-se por construir uma arquitetura real de **Comércio Exterior**, aplicando os seguintes *Design Patterns* e princípios no ecossistema Spring Boot:

* **DTO (Data Transfer Object):** Utilização de *Java Records* (`ProcessoDTO`, `CondicaoPagamentoDTO`) para criar objetos imutáveis que transportam apenas os dados necessários entre as camadas, protegendo as Entidades do banco de dados e resolvendo vulnerabilidades de *Over-posting*.
* **Facade (Fachada):** O controlador REST delega a execução para o `ProcessoService`, que age como uma Fachada. Ele oculta a complexidade de buscar usuários, converter Strings em Enums (`UnidadeMedida`, `StatusProcesso`) e orquestrar salvamentos secundários, entregando um método limpo `salvar(dados)`.
* **Singleton e Inversão de Controle (IoC):** O contêiner do Spring gerencia o ciclo de vida dos `@Service` e `@Repository` como Singletons. A injeção de dependências é feita via Construtor, garantindo baixo acoplamento e imutabilidade.
* **Smart/Upsert Pattern (Find-or-Create):** Implementado no `CondicaoPagamentoService` utilizando a avaliação *lazy* do `orElseGet()`. O sistema recebe um objeto complexo do Frontend e avalia em tempo real: se a condição (ex: "Net 30") já existe para aquele usuário, ele reaproveita o ID; se não, cria e vincula ao processo na mesma transação, mantendo o banco normalizado.

---

## 🚀 Principais Funcionalidades

* **Cadastro Inteligente:** Registro de processos com validação automática de duplicidade (impede o cadastro do mesmo nº de processo duas vezes).
* **Gestão Financeira Aninhada:** Criação e associação dinâmica de Condições de Pagamento no mesmo fluxo do processo logístico.
* **Tratamento de Erros Semântico:** Respostas HTTP claras (ex: `409 Conflict` para duplicidade, `400 Bad Request` para validações do Bean Validation) com mensagens limpas.
* **Segurança de Infraestrutura:** Configuração de credenciais via variáveis de ambiente (`.env`), protegendo senhas do banco.

## 🛠️ Tecnologias Utilizadas

**Backend:**
* Java 17 (LTS)
* Spring Boot 3.2+ (Web, Data JPA, Validation)
* PostgreSQL 15 (Banco de Dados)
* Docker (Containerização do Banco)
* Dotenv (Gestão de Variáveis de Ambiente)
* Maven

**Frontend (Client-side):**
* React.js com TypeScript
* Axios (Consumo da API com JWT Interceptors)
* React Hook Form + Zod (Validação de formulários complexos)

---

## ⚙️ Como Executar o Projeto (Backend)

Siga os passos abaixo para rodar a API em sua máquina local.

### 1. Pré-requisitos
Certifique-se de ter instalado: Java JDK 17, Maven, Docker Desktop e Git.

### 2. Clonar o Repositório
```bash
git clone [https://github.com/Klaudio0707/Import-Control---Sistema-de-Gest-o-de-Importa-o-.git](https://github.com/Klaudio0707/Import-Control---Sistema-de-Gest-o-de-Importa-o-.git)
cd import-control

3. Configurar Variáveis de Ambiente
Na raiz do projeto (junto ao arquivo pom.xml), crie um arquivo chamado .env e adicione o seguinte conteúdo:

Snippet de código
# Configurações do Banco de Dados
DB_URL=jdbc:postgresql://localhost:5432/importdb
DB_USER=postgres
DB_PASSWORD=admin

# Configuração da API
PORT=8080
Nota: O arquivo .env não é versionado pelo Git por motivos de segurança.

4. Subir o Banco de Dados (Docker)
Abra o terminal e execute o comando abaixo para criar e rodar o container do PostgreSQL:

Bash
docker run --name import-db \
  -e POSTGRES_PASSWORD=admin \
  -e POSTGRES_DB=importdb \
  -p 5432:5432 \
  -d postgres:15-alpine
5. Executar a Aplicação
Com o banco rodando, inicie a aplicação via terminal:

Bash
mvn spring-boot:run
O servidor iniciará em: http://localhost:8080

🔌 Documentação da API (Endpoints)
Abaixo estão os exemplos atualizados de requisições para testar no Postman ou Insomnia.

1. Criar Processo (POST)
URL: http://localhost:8080/processos
Body (JSON):

JSON
{
  "numeroProcesso": "IMP-2026-001",
  "identificadorInvoice": "INV-9988",
  "fornecedor": "Tech Imports Global",
  "produto": "Placas de Vídeo RTX",
  "quantidade": 50.0,
  "preco": 1200.50,
  "previsaoEmbarque": "2026-03-20",
  "unidadeMedida": "UN",
  "statusProcesso": "CRIADO",
  "statusPagamento": "PENDENTE",
  "usuarioId": 1,
  "condicaoPagamento": {
    "descricao": "Net 30",
    "diasPrazo": 30
  }
}
2. Listar Todos (GET)
URL: http://localhost:8080/processos

3. Buscar por ID (GET)
URL: http://localhost:8080/processos/{id}

4. Atualizar Processo (PUT)
URL: http://localhost:8080/processos/{id}
(Recebe o mesmo Payload do POST, com a possibilidade de omitir a condição para usar o ID existente).

5. Deletar Processo (DELETE)
URL: http://localhost:8080/processos/{id}

🤝 Contribuição
Contribuições são bem-vindas! Sinta-se à vontade para abrir issues ou enviar Pull Requests.

📝 Licença
Este projeto foi desenvolvido para fins de estudo, portfólio e resolução de desafios arquiteturais.
