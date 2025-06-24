package com.micro.panda.model.entity

import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "users")
class UserEntity (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
    var uuid: UUID? = null
){
}