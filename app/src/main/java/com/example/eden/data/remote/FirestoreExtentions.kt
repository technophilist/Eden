package com.example.eden.data.remote

import com.example.eden.data.domain.NotificationInfo
import com.example.eden.data.domain.PetInfo
import com.google.firebase.firestore.DocumentSnapshot


fun DocumentSnapshot.toPetInfo() = PetInfo(
    id = get("id").toString(),
    name = get("name").toString(),
    description = get("description").toString(),
    type = get("type").toString(),
    breed = get("breed").toString(),
    gender = get("gender").toString(),
    imageResource = get("imageUrl").toString(),
    age = get("age").toString(),
    color = get("color").toString(),
    weight = get("weight").toString()
)

fun DocumentSnapshot.toNotificationInfo() = NotificationInfo(
    id = get("id").toString(),
    type = getNotificationTypeForString(get("type").toString()),
    header = get("header").toString(),
    content = get("content").toString(),
    urlString = get("urlString").toString()
)

private fun getNotificationTypeForString(string: String): NotificationInfo.NotificationType =
    when (string.lowercase()) {
        "orders" -> NotificationInfo.NotificationType.ORDERS
        "appointments" -> NotificationInfo.NotificationType.APPOINTMENTS
        "ngo" -> NotificationInfo.NotificationType.NGO
        else -> throw IllegalArgumentException("Unexpected value for field name 'type' found in document")
    }