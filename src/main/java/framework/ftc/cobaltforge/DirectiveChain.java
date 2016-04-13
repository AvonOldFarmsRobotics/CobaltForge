package framework.ftc.cobaltforge;

/**
 * Support for chaining directives
 * Created by Dummyc0m on 4/12/16.
 */
public final class DirectiveChain {
    private final CobaltForge forge;
    private AbstractDirective curr;

    DirectiveChain(CobaltForge forge, AbstractDirective curr) {
        this.forge = forge;
        this.curr = curr;
    }

    public final DirectiveChain then(AbstractDirective directive) {
        forge.addHandlerStore(directive);
        curr.setNext(directive);
        curr = directive;
        return this;
    }
}
