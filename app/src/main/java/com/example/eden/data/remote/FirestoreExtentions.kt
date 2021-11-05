package com.example.eden.data.remote

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
