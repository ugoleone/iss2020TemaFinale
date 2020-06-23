/* Generated by AN DISI Unibo */ 
package it.unibo.table2

import it.unibo.kactor.*
import alice.tuprolog.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
	
class Table2 ( name: String, scope: CoroutineScope  ) : ActorBasicFsm( name, scope ){

	override fun getInitialState() : String{
		return "s0"
	}
	@kotlinx.coroutines.ObsoleteCoroutinesApi
	@kotlinx.coroutines.ExperimentalCoroutinesApi			
	override fun getBody() : (ActorBasicFsm.() -> Unit){
		return { //this:ActionBasciFsm
				state("s0") { //this:State
					action { //it:State
						println("table2 is starting..")
						var state = "free and clean" 
					}
					 transition( edgeName="goto",targetState="handleRequests", cond=doswitch() )
				}	 
				state("handleRequests") { //this:State
					action { //it:State
						answer("tabStatus", "tabState", "tabState(state)"   )  
					}
				}	 
			}
		}
}
