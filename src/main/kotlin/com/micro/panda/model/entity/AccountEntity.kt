package com.micro.panda.model.entity

import jakarta.persistence.*
import java.sql.Timestamp

@Entity
@Table(name = "accounts")
data class AccountEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    var name: String? = null,
    var account: String? = null,
    var password: String? = null,
    var link: String? = null,
    var description: String? = null,
    var updated: Timestamp? = null,

    @ManyToOne
    @JoinColumn(name = "user_id")
    var userEntity: UserEntity? = null,

    @ManyToOne
    @JoinColumn(name = "email_id")
    var emailEntity: MailEntity? = null,

    @ManyToOne
    @JoinColumn(name = "type_id")
    var typeEntity: TypeEntity? = null,

    ) : Comparable<AccountEntity> {
    override fun compareTo(other: AccountEntity): Int {
        return compareValuesBy(this, other, { it.name }, { it.password })
    }
}