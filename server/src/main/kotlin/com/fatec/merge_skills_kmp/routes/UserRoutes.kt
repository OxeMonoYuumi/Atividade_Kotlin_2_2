package com.fatec.merge_skills_kmp.routes

import com.fatec.merge_skills_kmp.domain.models.User
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.postgrest
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.call
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import io.ktor.server.routing.route

fun Route.userRoutes(supabase: SupabaseClient) {
    route("/api/user") {
        get {
            try {
                val users = supabase.postgrest["users"].select().decodeList<User>()
                call.respond(users)
            } catch (e: Exception) {
                call.respond(HttpStatusCode.InternalServerError, mapOf("error" to e.message))
            }
        }
    }
}