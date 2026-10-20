package com.capputinodevelopment.planagerstud.api

import com.capputinodevelopment.planagerstud.database.Database.db
import com.capputinodevelopment.planagerstud.database.Lesson
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive

suspend fun parseLesson(element: JsonElement) {
    var lesson = Lesson()
    val attrs = element.jsonObject["attributes"]!!
    val recurrence = attrs.jsonObject["recurrence"]!!

    lesson.id = element.jsonObject["id"]!!.jsonPrimitive.content
    lesson.title = attrs.jsonObject["title"]!!.jsonPrimitive.content
    lesson.start = attrs.jsonObject["start"]!!.jsonPrimitive.content
    lesson.end = attrs.jsonObject["end"]!!.jsonPrimitive.content
    lesson.weekDay = attrs.jsonObject["weekday"]!!.jsonPrimitive.content.toInt()

    lesson.freq = recurrence.jsonObject["FREQ"]!!.jsonPrimitive.content
    lesson.interval = recurrence.jsonObject["INTERVAL"]!!.jsonPrimitive.content.toInt()
    lesson.from = recurrence.jsonObject["DTSTART"]!!.jsonPrimitive.content
    lesson.until = recurrence.jsonObject["UNTIL"]!!.jsonPrimitive.content
    lesson.exDates = recurrence.jsonObject["EXDATES"]!!.jsonArray.joinToString(",")

    lesson.location = attrs.jsonObject["locations"]!!.jsonArray[0].jsonPrimitive.content.split(" - ")[1]

    db.lessonsDAO().insertAll(lesson)
}