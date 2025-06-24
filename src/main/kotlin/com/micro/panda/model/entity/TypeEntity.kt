package com.micro.panda.model.entity

import jakarta.persistence.*

@Entity
@Table(name = "types")
data class TypeEntity (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
    var type: String? = null,
)