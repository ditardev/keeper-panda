package com.micro.panda.controller

import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("\${server.endpoint.main}")
class PandaController {
}