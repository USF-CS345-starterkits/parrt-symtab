/** A TypeScript-inspired grammar for demonstrating symbol table management */
grammar TypeScript;

@header {
import org.antlr.symtab.*;
}

program returns [Scope scope] : sourceElement* EOF ;

// TODO: Fill in everything else
