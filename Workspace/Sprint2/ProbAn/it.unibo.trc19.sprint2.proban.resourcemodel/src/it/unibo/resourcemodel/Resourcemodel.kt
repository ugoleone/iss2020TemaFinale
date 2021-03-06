/* Generated by AN DISI Unibo */ 
package it.unibo.resourcemodel

import it.unibo.kactor.*
import alice.tuprolog.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
	
class Resourcemodel ( name: String, scope: CoroutineScope  ) : ActorBasicFsm( name, scope ){

	override fun getInitialState() : String{
		return "s0"
	}
	@kotlinx.coroutines.ObsoleteCoroutinesApi
	@kotlinx.coroutines.ExperimentalCoroutinesApi			
	override fun getBody() : (ActorBasicFsm.() -> Unit){
		return { //this:ActionBasciFsm
				state("s0") { //this:State
					action { //it:State
						println("STARTING, I will be soon ready to listen to changes!")
						solve("consult('tearoomkb.pl')","") //set resVar	
						delay(200) 
					}
					 transition( edgeName="goto",targetState="listenToChanges", cond=doswitch() )
				}	 
				state("listenToChanges") { //this:State
					action { //it:State
						println("LISTENING...")
					}
					 transition(edgeName="t00",targetState="informChanges",cond=whenDispatch("modelChange"))
				}	 
				state("informChanges") { //this:State
					action { //it:State
						println("MODEL has changed...")
						solve("modelChange","") //set resVar	
						emit("modelChanged", "modelChange("change")" ) 
					}
					 transition( edgeName="goto",targetState="listenToChanges", cond=doswitch() )
				}	 
			}
		}
}
