package org.acme.resource

import org.acme.MessageService
import org.acme.SuffixService
import javax.ws.rs.GET
import javax.ws.rs.Path

@Path("/message")
class RestRessource(val messageService: MessageService, val suffixService: SuffixService) {

    @GET
    fun message() = "${messageService.getMessage()} ${suffixService.getSuffix()}"

}