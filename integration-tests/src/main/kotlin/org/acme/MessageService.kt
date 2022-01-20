package org.acme

import javax.enterprise.context.ApplicationScoped

@ApplicationScoped
class MessageService {

    fun getMessage() = "message"

}