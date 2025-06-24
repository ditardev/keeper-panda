package com.micro.panda.model.entity

import jakarta.persistence.*
import java.sql.Timestamp

@Entity
@Table(name = "accounts")
class AccountEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @ManyToOne
    @JoinColumn(name = "user_id")
    var user: UserEntity? = null,

    var name: String? = null,
    var account: String? = null,
    var password: String? = null,
    var link: String? = null,
    var description: String? = null,
    var mail: String? = null,
    var type: String? = null,
    var updated: Timestamp? = null,

    ) {
}