package com.fatec.merge_skills_kmp.routes

import com.fatec.merge_skills_kmp.domain.models.Stock
import com.fatec.merge_skills_kmp.domain.models.StockInsert
import com.fatec.merge_skills_kmp.domain.models.Summary
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

fun Route.stockRoutes(supabase: SupabaseClient){
    route("/api/stock"){
        get {
            try {
                val stocks = supabase.postgrest["stocks"].select().decodeList<Stock>()
                call.respond(stocks)
            } catch (e: Exception) {
                call.respond(HttpStatusCode.InternalServerError, mapOf("error" to e.message))
            }
        }
        post {
            try {
                val stock = call.receive<StockInsert>()
                val result = supabase.postgrest["stocks"].insert(stock){
                    select()
                }.decodeSingle<Stock>()
                call.respond(HttpStatusCode.Created, result)
            } catch (e: Exception) {
                call.respond(HttpStatusCode.BadRequest, mapOf("error" to "Falha ao adicionar item ao estoque: ${e.message}"))
            }
        }
        get("/{id}") {
            val id = call.parameters["id"] ?: return@get call.respond(HttpStatusCode.BadRequest, "Id inválido")
            try {
                val item = supabase.postgrest["stocks"].select {
                    filter { eq("id", id) }
                }.decodeSingleOrNull<Stock>()

                if (item != null) call.respond(item) else call.respond(HttpStatusCode.NotFound, "Item não foi encontrado")
            } catch (e: Exception) {
                call.respond(HttpStatusCode.InternalServerError, mapOf("error" to (e.message ?: "Erro ao buscar item")))
            }
        }
        put("/{id}") {
            val id = call.parameters["id"] ?: return@put call.respond(HttpStatusCode.BadRequest, "Id inválido")
            try {
                val updatedItem = call.receive<Stock>()
                supabase.postgrest["stocks"].update(updatedItem) {
                    filter { eq("id", id) }
                }
                call.respond(HttpStatusCode.OK, updatedItem)
            } catch (e: Exception) {
                call.respond(HttpStatusCode.BadRequest, mapOf("error" to (e.message ?: "Erro ao atualizar item")))
            }
        }
        delete("/{id}") {
            val id = call.parameters["id"] ?: return@delete call.respond(HttpStatusCode.BadRequest, "Id inválido")
            try {
                supabase.postgrest["stocks"].delete {
                    filter { eq("id", id) }
                }
                call.respond(HttpStatusCode.NoContent)
            } catch (e: Exception) {
                call.respond(HttpStatusCode.InternalServerError, mapOf("error" to (e.message ?: "Erro ao deletar item")))
            }
        }
        get("/summary") {
            try {
                // Lê diretamente da View que criamos no Supabase
                val summary = supabase.postgrest["stocks"].select().decodeList<Summary>()
                call.respond(summary)
            } catch (e: Exception) {
                call.respond(HttpStatusCode.InternalServerError, mapOf("error" to (e.message ?: "Erro no resumo")))
            }
        }
    }
}