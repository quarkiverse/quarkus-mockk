package org.acme

import jakarta.enterprise.context.ApplicationScoped

@ApplicationScoped
class MessageService {

    fun getMessage() = "message"

}