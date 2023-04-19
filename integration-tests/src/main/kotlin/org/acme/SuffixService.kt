package org.acme

import jakarta.enterprise.context.ApplicationScoped

@ApplicationScoped
class SuffixService {

    fun getSuffix() = "suffix"

}