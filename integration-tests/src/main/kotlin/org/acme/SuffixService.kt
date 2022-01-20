package org.acme

import javax.enterprise.context.ApplicationScoped

@ApplicationScoped
class SuffixService {

    fun getSuffix() = "suffix"

}