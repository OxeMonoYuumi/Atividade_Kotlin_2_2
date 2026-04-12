package com.fatec.merge_skills_kmp.routes

import com.fatec.merge_skills_kmp.domain.models.QuestionAttempt
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.postgrest
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.call
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import io.ktor.server.routing.route

fun Route.questionAttemptRoutes(supabase: SupabaseClient) {
    route("/api/questionAttempt") {
        get {
            try {
                val questionsAttempt = supabase.postgrest["question_attempts"].select().decodeList<QuestionAttempt>()
                call.respond(questionsAttempt)
            } catch (e: Exception) {
                call.respond(HttpStatusCode.InternalServerError, mapOf("error" to e.message))
            }
        }
    }
}