/* Generated by AN DISI Unibo */ 
package it.unibo.timersmanager

import it.unibo.kactor.*
import alice.tuprolog.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
	
class Timersmanager ( name: String, scope: CoroutineScope  ) : ActorBasicFsm( name, scope ){

	override fun getInitialState() : String{
		return "s0"
	}
	@kotlinx.coroutines.ObsoleteCoroutinesApi
	@kotlinx.coroutines.ExperimentalCoroutinesApi			
	override fun getBody() : (ActorBasicFsm.() -> Unit){
		 var timersDict =  mutableMapOf<String, kotlinx.coroutines.Job>()  
		return { //this:ActionBasciFsm
				state("s0") { //this:State
					action { //it:State
						discardMessages = false
					}
					 transition( edgeName="goto",targetState="waitingRequests", cond=doswitch() )
				}	 
				state("waitingRequests") { //this:State
					action { //it:State
						println("[TIMERS MANAGER] Waiting for new requests")
					}
					 transition(edgeName="t054",targetState="timer",cond=whenDispatch("startTimer"))
					transition(edgeName="t055",targetState="timer",cond=whenDispatch("cancelTimer"))
				}	 
				state("timer") { //this:State
					action { //it:State
						if( checkMsgContent( Term.createTerm("startTimer(ID,A,M,P,T)"), Term.createTerm("startTimer(ID,A,M,P,T)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								println("[TIMERS MANAGER] Starting timer")
								
												var ID = payloadArg(0)
												var Actor = payloadArg(1)
												var Message = payloadArg(2)
												var Payload = payloadArg(3)
												var Time = payloadArg(4).toLong()
												if(ID == "0") {
													ID = Math.random().toString()
												}
												timersDict.put(ID, scope.launch {
													delay(Time)
													forward("$Message", "$Message($Payload)" ,"$Actor" )		
												})
						}
						if( checkMsgContent( Term.createTerm("cancelTimer(ID)"), Term.createTerm("cancelTimer(ID)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								println("[TIMERS MANAGER] Cancelling timer")
								
												var ID = payloadArg(0) 
												timersDict.get(ID)?.cancel() 
						}
					}
					 transition( edgeName="goto",targetState="waitingRequests", cond=doswitch() )
				}	 
			}
		}
}
