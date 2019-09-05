package com.zack.proxyserver

import android.database.Cursor
import fi.iki.elonen.NanoHTTPD
import fi.iki.elonen.NanoHTTPD.*
import org.json.JSONArray
import org.json.JSONObject
import java.util.concurrent.Callable
import java.util.concurrent.Executors
import java.util.concurrent.Future


class MenuRepository(val cursor: Cursor, val apiService: ApiService) {

    fun fetchMenu(session: NanoHTTPD.IHTTPSession?): NanoHTTPD.Response {
        val response: String
        val dbResponse: String = fetchFromDb()
        if (!dbResponse.isEmpty()) {
            response = dbResponse
        } else {
            response = fetchFromWebServer()
        }
        return newFixedLengthResponse(Response.Status.OK, MIME_PLAINTEXT, response)
    }


    private fun fetchFromDb(): String {
        var id: String
        var name: String
        var price: Int
        var type: String
        val jsonArray = JSONArray()

        val executorService = Executors.newSingleThreadExecutor()
        val task = object : Callable<String> {
            @Throws(Exception::class)
            override fun call(): String {
                while (cursor.moveToNext()) {
                    val jsonObj = JSONObject()
                    id = cursor.getString(cursor.getColumnIndex("id"))
                    name = cursor.getString(cursor.getColumnIndex("name"))
                    price = cursor.getInt(cursor.getColumnIndex("price"))
                    type = cursor.getString(cursor.getColumnIndex("type"))
                    jsonObj.put("id", id)
                    jsonObj.put("name", name)
                    jsonObj.put("price", price)
                    jsonObj.put("type", type)
                    jsonArray.put(jsonObj)
                }
                return jsonArray.toString()
            }
        }
        val json: Future<String> = executorService.submit(task)
        return json.get()
    }


    private fun fetchFromWebServer(): String {
        return apiService.fetchMenu().execute().toString()
    }

}