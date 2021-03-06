/* Generated by AN DISI Unibo */ 
package it.unibo.waiter

import it.unibo.kactor.*
import alice.tuprolog.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
	
class Waiter ( name: String, scope: CoroutineScope  ) : ActorBasicFsm( name, scope ){

	override fun getInitialState() : String{
		return "s0"
	}
	@kotlinx.coroutines.ObsoleteCoroutinesApi
	@kotlinx.coroutines.ExperimentalCoroutinesApi			
	override fun getBody() : (ActorBasicFsm.() -> Unit){
		
				var stateTable1="stateTable1"
				var stateTable2="stateTable2"
		return { //this:ActionBasciFsm
				state("s0") { //this:State
					action { //it:State
						println("I'm WAITER")
					}
					 transition( edgeName="goto",targetState="reqHandler", cond=doswitch() )
				}	 
				state("reqHandler") { //this:State
					action { //it:State
						println("[WAITER] I'm HOME, waiting for a request!")
						 tableToCheck = 1  
					}
				}	 
			}
		}
}
