/* Generated by AN DISI Unibo */ 
package it.unibo.ctxtable1
import it.unibo.kactor.QakContext
import it.unibo.kactor.sysUtil
import kotlinx.coroutines.runBlocking

fun main() = runBlocking {
	QakContext.createContexts(
	        "localhost", this, "table1.pl", "sysRules.pl"
	)
}

