package com.micro.panda.model.entity

import jakarta.persistence.*

@Entity
@Table(name = "mails")
class MailEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
    var mail: String? = null
) {

}