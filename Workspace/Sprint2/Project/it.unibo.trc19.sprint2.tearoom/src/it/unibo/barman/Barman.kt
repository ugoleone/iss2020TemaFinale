/* Generated by AN DISI Unibo */ 
package it.unibo.barman

import it.unibo.kactor.*
import alice.tuprolog.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
	
class Barman ( name: String, scope: CoroutineScope  ) : ActorBasicFsm( name, scope ){

	override fun getInitialState() : String{
		return "s0"
	}
	@kotlinx.coroutines.ObsoleteCoroutinesApi
	@kotlinx.coroutines.ExperimentalCoroutinesApi			
	override fun getBody() : (ActorBasicFsm.() -> Unit){
		var Table = "" 
		return { //this:ActionBasciFsm
				state("s0") { //this:State
					action { //it:State
						discardMessages = false
					}
					 transition( edgeName="goto",targetState="waitingForOrder", cond=doswitch() )
				}	 
				state("waitingForOrder") { //this:State
					action { //it:State
						println("[BARMAN] Waiting for a new order...")
					}
					 transition(edgeName="t028",targetState="makeTea",cond=whenEvent("newOrderEvent"))
				}	 
				state("makeTea") { //this:State
					action { //it:State
						if( checkMsgContent( Term.createTerm("newOrderEvent(X)"), Term.createTerm("newOrderEvent(T)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								Table = payloadArg(0) 
								println("[BARMAN] Making some delicious tea...")
								delay(30000) 
						}
					}
					 transition( edgeName="goto",targetState="teaReady", cond=doswitch() )
				}	 
				state("teaReady") { //this:State
					action { //it:State
						println("[BARMAN] The tea is ready!")
						forward("ready", "ready($Table)" ,"resourcemodel" ) 
					}
					 transition( edgeName="goto",targetState="waitingForOrder", cond=doswitch() )
				}	 
			}
		}
}
