package com.fatec.merge_skills_kmp.routes

import com.fatec.merge_skills_kmp.domain.models.Product
import com.fatec.merge_skills_kmp.domain.models.ProductInsert
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.postgrest
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.call
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.put
import io.ktor.server.routing.delete
import io.ktor.server.routing.route

fun Route.productRoutes(supabase: SupabaseClient){
    route("/api/products"){
        get {
            try {
                val products = supabase.postgrest["products"].select().decodeList<Product>()
                call.respond(products)
            } catch (e: Exception) {
                call.respond(HttpStatusCode.InternalServerError, mapOf("error" to e.message))
            }
        }
        post {
            try {
                val product = call.receive<ProductInsert>()
                val result = supabase.postgrest["products"].insert(product){
                    select()
                }.decodeSingle<Product>()
                call.respond(HttpStatusCode.Created, result)
            } catch (e: Exception) {
                call.respond(HttpStatusCode.BadRequest, mapOf("error" to "Falha ao criar produto: ${e.message}"))
            }
        }
        get("/{id}") {
            val id = call.parameters["id"] ?: return@get call.respond(HttpStatusCode.BadRequest, "Id inválido")
            try {
                val product = supabase.postgrest["products"].select {
                    filter { eq("id", id) }
                }.decodeSingleOrNull<Product>()

                if (product != null) call.respond(product) else call.respond(HttpStatusCode.NotFound, "Produto não foi encontrado")
            } catch (e: Exception) {
                call.respond(HttpStatusCode.InternalServerError, mapOf("error" to (e.message ?: "Erro ao buscar produto")))
            }
        }
        put("/{id}") {
            val id = call.parameters["id"] ?: return@put call.respond(HttpStatusCode.BadRequest, "Id inválido")
            try {
                val updatedProduct = call.receive<Product>()
                supabase.postgrest["products"].update(updatedProduct) {
                    filter { eq("id", id) }
                }
                call.respond(HttpStatusCode.OK, updatedProduct)
            } catch (e: Exception) {
                call.respond(HttpStatusCode.BadRequest, mapOf("error" to (e.message ?: "Erro ao atualizar produto")))
            }
        }
        delete("/{id}") {
            val id = call.parameters["id"] ?: return@delete call.respond(HttpStatusCode.BadRequest, "Id inválido")
            try {
                supabase.postgrest["products"].delete {
                    filter { eq("id", id) }
                }
                call.respond(HttpStatusCode.NoContent)
            } catch (e: Exception) {
                call.respond(HttpStatusCode.InternalServerError, mapOf("error" to (e.message ?: "Erro ao deletar produto")))
            }
        }
    }
}