/* Generated by AN DISI Unibo */ 
package it.unibo.smartbell

import it.unibo.kactor.*
import alice.tuprolog.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
	
class Smartbell ( name: String, scope: CoroutineScope  ) : ActorBasicFsm( name, scope ){

	override fun getInitialState() : String{
		return "s0"
	}
	@kotlinx.coroutines.ObsoleteCoroutinesApi
	@kotlinx.coroutines.ExperimentalCoroutinesApi			
	override fun getBody() : (ActorBasicFsm.() -> Unit){
		  
				var temperature     = 37.5
				var clientID        = 0
		return { //this:ActionBasciFsm
				state("s0") { //this:State
					action { //it:State
						discardMessages = false
					}
					 transition( edgeName="goto",targetState="waitingForClient", cond=doswitch() )
				}	 
				state("waitingForClient") { //this:State
					action { //it:State
						println("[SMARTBELL] Waiting for a new client...")
					}
					 transition(edgeName="t00",targetState="checkTemp",cond=whenRequest("notify"))
				}	 
				state("checkTemp") { //this:State
					action { //it:State
						println("[SMARTBELL] Checking your temperature...")
						 
						 			val randomNumber = Math.random()
						 			if  (randomNumber >= 0.7)
						 				temperature = 39.0
						 			else
						 				temperature = 37.0		
					}
					 transition( edgeName="goto",targetState="badTemp", cond=doswitchGuarded({ temperature > 37.5  
					}) )
					transition( edgeName="goto",targetState="goodTemp", cond=doswitchGuarded({! ( temperature > 37.5  
					) }) )
				}	 
				state("badTemp") { //this:State
					action { //it:State
						println("[SMARTBELL] You should go to the hospital! ")
						answer("notify", "tempResult", "tempResult(no)"   )  
					}
					 transition( edgeName="goto",targetState="waitingForClient", cond=doswitch() )
				}	 
				state("goodTemp") { //this:State
					action { //it:State
						println("[SMARTBELL] Your temperature is ok ")
						 clientID++  
						answer("notify", "tempResult", "tempResult(yes)"   )  
					}
					 transition( edgeName="goto",targetState="waiterInfo", cond=doswitch() )
				}	 
				state("waiterInfo") { //this:State
					action { //it:State
						println("[SMARTBELL] Checking table situation with the waiter... ")
						request("checkAvail", "checkAvail(clientID)" ,"waiter" )  
					}
					 transition(edgeName="t11",targetState="informClient",cond=whenReply("waitingTime"))
				}	 
				state("informClient") { //this:State
					action { //it:State
						if( checkMsgContent( Term.createTerm("waitingTime(X)"), Term.createTerm("waitingTime(X)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								if(  payloadArg(0).toDouble() == 0.0 	 
								 ){println("[SMARTBELL] A waiter is coming... ")
								forward("accept", "accept(enter)" ,"client" ) 
								}
								else
								 {println("[SMARTBELL] You have to wait... ")
								 forward("inform", "inform(${payloadArg(0)})" ,"client" ) 
								 }
						}
					}
					 transition( edgeName="goto",targetState="waitingForClient", cond=doswitch() )
				}	 
			}
		}
}
