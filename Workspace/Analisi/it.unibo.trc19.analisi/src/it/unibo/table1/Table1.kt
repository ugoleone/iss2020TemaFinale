/* Generated by AN DISI Unibo */ 
package it.unibo.table1

import it.unibo.kactor.*
import alice.tuprolog.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
	
class Table1 ( name: String, scope: CoroutineScope  ) : ActorBasicFsm( name, scope ){

	override fun getInitialState() : String{
		return "s0"
	}
	@kotlinx.coroutines.ObsoleteCoroutinesApi
	@kotlinx.coroutines.ExperimentalCoroutinesApi			
	override fun getBody() : (ActorBasicFsm.() -> Unit){
		 var state = "free and clean"  
		return { //this:ActionBasciFsm
				state("s0") { //this:State
					action { //it:State
						println("[TABLE1] table1 is starting..")
					}
					 transition(edgeName="t018",targetState="handleRequests",cond=whenRequest("tabStatus"))
				}	 
				state("handleRequests") { //this:State
					action { //it:State
						answer("tabStatus", "tabState", "tabState(FreeClean)"   )  
					}
				}	 
			}
		}
}
