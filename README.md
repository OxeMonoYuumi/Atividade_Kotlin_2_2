# Merge Skills KMP

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

1. Configure o arquivo `local.properties`:

```properties
SUPABASE_URL=[SuaURL](https://lkklktbwfrxckcspdlfs.supabase.co)
SUPABASE_KEY=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6Imxra2xrdGJ3ZnJ4Y2tjc3BkbGZzIiwicm9sZSI6InNlcnZpY2Vfcm9sZSIsImlhdCI6MTc3ODk4NzQ3NCwiZXhwIjoyMDk0NTYzNDc0fQ.Kj67qocJ7JMe4iQpAkWOr9hWY529etd9qQTsEUaEuew
```

2. Execute o projeto:

```bash
./gradlew run
```

3. Servidor disponível em:

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
