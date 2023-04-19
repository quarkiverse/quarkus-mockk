package org.acme.resource

import org.acme.MessageService
import org.acme.SuffixService
import jakarta.ws.rs.GET
import jakarta.ws.rs.Path

@Path("/message")
class RestRessource(val messageService: MessageService, val suffixService: SuffixService) {

    @GET
    fun message() = "${messageService.getMessage()} ${suffixService.getSuffix()}"

}