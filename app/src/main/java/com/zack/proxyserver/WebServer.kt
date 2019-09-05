package com.zack.proxyserver

import android.content.Context
import fi.iki.elonen.NanoHTTPD

class WebServer(val menuRepository: MenuRepository) : NanoHTTPD(8974) {
    override fun serve(session: IHTTPSession?): Response {
        val default = newFixedLengthResponse(Response.Status.OK, MIME_PLAINTEXT, "Hi zack")
        default.addHeader("Access-Control-Allow-Origin", "*");
        default.addHeader("Access-Control-Max-Age", "3628800");
        default.addHeader("Access-Control-Allow-Methods", "GET, POST, PUT, OPTIONS");
        default.addHeader("Access-Control-Allow-Headers", "X-Requested-With");
        default.addHeader("Access-Control-Allow-Headers", "Authorization");
        when (session!!.method.name) {
            "GET" -> return menuRepository.fetchMenu(session)
            else -> return default
        }
    }
}