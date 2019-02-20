package com.iamchiwon.utils

import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.google.gson.JsonParser

fun JsonElement.toKotlinyJson(): KotlinyJson {
    return KotlinyJson(this)
}

class KotlinyJson(private val jsonElement: JsonElement?) {

    companion object {
        fun parse(json: String): KotlinyJson {
            val element = JsonParser().parse(json).asJsonObject
            return KotlinyJson(element)
        }
    }

    operator fun get(key: String): KotlinyJson {
        if (jsonElement != null) {
            if (jsonElement.isJsonObject && jsonElement.asJsonObject.has(key)) {
                return jsonElement.asJsonObject.get(key).toKotlinyJson()
            }
        }
        return KotlinyJson(null)
    }

    fun obj(): JsonObject? {
        return jsonElement?.asJsonObject
    }

    fun element(): JsonElement {
        if (jsonElement != null) {
            return jsonElement
        }
        return JsonParser().parse("{}").asJsonObject
    }

    fun has(key: String): Boolean {
        if (jsonElement != null) {
            return jsonElement.isJsonObject && jsonElement.asJsonObject.has(key)
        }
        return false
    }

    fun asArray(): List<KotlinyJson> {
        if (jsonElement != null) {
            if (jsonElement.isJsonArray) return jsonElement.asJsonArray.map { it.toKotlinyJson() }
        }
        return emptyList()
    }

    fun asString(): String? {
        try {
            return jsonElement?.asString
        } catch (e: Exception) {
            return null
        }
    }

    fun asStringValue(): String {
        return asString() ?: ""
    }

    fun asBool(): Boolean? {
        try {
            return jsonElement?.asBoolean
        } catch (e: Exception) {
            return null
        }
    }

    fun asBoolValue(): Boolean {
        return asBool() ?: false
    }

    fun asInt(): Int? {
        try {
            return jsonElement?.asInt
        } catch (e: Exception) {
            return null
        }
    }

    fun asIntValue(): Int {
        return asInt() ?: 0
    }

    fun asFloat(): Float? {
        try {
            return jsonElement?.asFloat
        } catch (e: Exception) {
            return null
        }
    }

    fun asFloatValue(): Float {
        return asFloat() ?: 0f
    }

}

