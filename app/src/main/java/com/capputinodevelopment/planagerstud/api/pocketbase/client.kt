package com.capputinodevelopment.planagerstud.api.pocketbase

import io.github.agrevster.pocketbaseKotlin.PocketbaseClient
import io.ktor.http.URLProtocol

fun getClient(): PocketbaseClient {
    return PocketbaseClient({
        protocol = URLProtocol.HTTPS
        host = "lstdb.tbwebtech.de"
        port = 443
    })
}