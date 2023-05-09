package com.example.fourteenthpractice.model

data class User(
    var name: String,
    var age: Int
) {
    override fun toString(): String {
        return "Имя: $name Возраст: $age"
    }
}