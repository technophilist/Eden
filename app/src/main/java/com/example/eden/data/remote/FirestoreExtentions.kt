package com.example.eden.data.remote

import com.example.eden.data.domain.NotificationInfo
import com.example.eden.data.domain.PetInfo
import com.google.firebase.firestore.DocumentSnapshot

/**
 * Used to convert an instance of [DocumentSnapshot] to an instance of
 * [PetInfo],
 */
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

/**
 * Used to convert an instance of [DocumentSnapshot] to an instance of
 * [NotificationInfo].
 */
fun DocumentSnapshot.toNotificationInfo() = NotificationInfo(
    id = get("id").toString(),
    type = getNotificationTypeForString(get("type").toString()),
    header = get("header").toString(),
    content = get("content").toString(),
    urlString = get("urlString").toString()
)

/**
 * Helper method used to get an [NotificationInfo.NotificationType]
 * enum associated with the [string].
 * @throws IllegalArgumentException when an unexpected [string] is
 * passed as an argument.
 */
private fun getNotificationTypeForString(string: String): NotificationInfo.NotificationType =
    when (string.lowercase()) {
        "orders" -> NotificationInfo.NotificationType.ORDERS
        "appointments" -> NotificationInfo.NotificationType.APPOINTMENTS
        "ngo" -> NotificationInfo.NotificationType.NGO
        else -> throw IllegalArgumentException("Unexpected value for field name 'type' found in document")
    }