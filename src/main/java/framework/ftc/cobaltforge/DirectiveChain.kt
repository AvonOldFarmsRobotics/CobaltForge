package framework.ftc.cobaltforge

/**
 * Support for chaining directives
 * Created by Dummyc0m on 4/12/16.
 */
class DirectiveChain internal constructor(private val forge: CobaltForge, private var curr: AbstractDirective?) {

    fun then(directive: AbstractDirective): DirectiveChain {
        forge.addHandlerStore(directive)
        curr!!.next = directive
        curr = directive
        return this
    }
}
