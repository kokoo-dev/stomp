package com.kokoo.stomp.config

import java.security.Principal

class CustomPrincipal(
    private val name: String
): Principal {

    override fun getName(): String {
        return name
    }
}