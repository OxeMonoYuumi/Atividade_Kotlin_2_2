# Estoque API - CRUD

Backend desenvolvido para gerenciamento de produtos e estoque utilizando Kotlin, Ktor e Supabase.

---

# Tecnologias Utilizadas

* Kotlin
* Ktor
* Supabase
* PostgreSQL
* Kotlin Serialization

---

# Como Executar o Projeto

1. Execute o projeto:

```bash
./gradlew run
```

2. Servidor disponível em:

```text
http://localhost:8080
```

---

# Rotas da API

## Produtos

* GET /api/products
* POST /api/products
* PUT /api/products/{id}
* DELETE /api/products/{id}

---

## Estoque

* GET /api/stock
* POST /api/stock
* PUT /api/stock/{id}
* DELETE /api/stock/{id}
* GET /api/stock/summary
