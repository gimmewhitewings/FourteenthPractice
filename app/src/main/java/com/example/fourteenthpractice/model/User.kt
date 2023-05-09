package com.example.fourteenthpractice.model

data class User(
    var name: String = "",
    var age: Int = 0
) {
    override fun toString(): String {
        return "Имя: $name Возраст: $age"
    }
}