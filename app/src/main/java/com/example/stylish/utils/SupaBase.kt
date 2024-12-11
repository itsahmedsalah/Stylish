package com.example.stylish.utils

import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.realtime.Realtime
import io.github.jan.supabase.storage.Storage

object SupaBase {

    val supaBaseUrl = "https://ganwmjesjsawpqznnpcs.supabase.co"
    val supaBaseKey =
        "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6ImdhbndtamVzanNhd3Bxem5ucGNzIiwicm9sZSI6ImFub24iLCJpYXQiOjE3MzEzNDg2MDksImV4cCI6MjA0NjkyNDYwOX0.o6mU8QkWVNptWbM5bEcxVUNnaGXUVk2uq4E-rBjTuKY"
    val supabase = createSupabaseClient(
        supaBaseUrl,
        supaBaseKey
    ) {
        install(Postgrest)
        install(Storage)
        install(Auth)
        install(Realtime)
    }
}