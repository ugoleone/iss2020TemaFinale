/* Generated by AN DISI Unibo */ 
package it.unibo.client

import it.unibo.kactor.*
import alice.tuprolog.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
	
class Client ( name: String, scope: CoroutineScope  ) : ActorBasicFsm( name, scope ){

	override fun getInitialState() : String{
		return "init"
	}
	@kotlinx.coroutines.ObsoleteCoroutinesApi
	@kotlinx.coroutines.ExperimentalCoroutinesApi			
	override fun getBody() : (ActorBasicFsm.() -> Unit){
		 var tempResult = "no"	 
		return { //this:ActionBasciFsm
				state("init") { //this:State
					action { //it:State
						println("[CLIENT] Client STARTED")
						discardMessages = false
					}
					 transition( edgeName="goto",targetState="askToEnter", cond=doswitch() )
				}	 
				state("askToEnter") { //this:State
					action { //it:State
						println("[CLIENT] Knock Knock, I'm here")
						request("notify", "notify" ,"smartbell" )  
					}
					 transition(edgeName="t00",targetState="handleReply",cond=whenReply("tempResult"))
				}	 
				state("handleReply") { //this:State
					action { //it:State
						if( checkMsgContent( Term.createTerm("tempResult(X)"), Term.createTerm("tempResult(RES)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								 tempResult = payloadArg(0)  
						}
					}
					 transition( edgeName="goto",targetState="waitToEnter", cond=doswitchGuarded({ tempResult == "yes"  
					}) )
					transition( edgeName="goto",targetState="exit", cond=doswitchGuarded({! ( tempResult == "yes"  
					) }) )
				}	 
				state("waitToEnter") { //this:State
					action { //it:State
					}
					 transition(edgeName="t11",targetState="wait",cond=whenDispatch("inform"))
					transition(edgeName="t12",targetState="enter",cond=whenDispatch("accept"))
				}	 
				state("wait") { //this:State
					action { //it:State
						println("[CLIENT] I have to wait")
						if( checkMsgContent( Term.createTerm("inform(X)"), Term.createTerm("inform(TIME)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								 var Time = payloadArg(0).toLong()  
								delay(Time)
						}
					}
					 transition( edgeName="goto",targetState="enter", cond=doswitch() )
				}	 
				state("enter") { //this:State
					action { //it:State
						println("[CLIENT] Choosing a drink")
						delay(1000) 
						forward("readyToOrder", "readyToOrder(1)" ,"waiter" ) 
					}
					 transition(edgeName="t03",targetState="makeOrder",cond=whenRequest("take"))
				}	 
				state("makeOrder") { //this:State
					action { //it:State
						println("[CLIENT] A Na-tea-li please")
						answer("take", "order", "order(tea)"   )  
					}
					 transition(edgeName="t04",targetState="drink",cond=whenDispatch("serveDrink"))
				}	 
				state("drink") { //this:State
					action { //it:State
						println("[CLIENT] *sip sip* Delicious tea")
						delay(10000) 
					}
					 transition( edgeName="goto",targetState="askToPay", cond=doswitch() )
				}	 
				state("askToPay") { //this:State
					action { //it:State
						println("[CLIENT] I want to pay")
						forward("exitReq", "exitReq(1)" ,"waiter" ) 
					}
					 transition(edgeName="t05",targetState="pay",cond=whenRequest("collect"))
				}	 
				state("pay") { //this:State
					action { //it:State
						println("[CLIENT] Paying")
						answer("collect", "payment", "payment(10)"   )  
					}
					 transition( edgeName="goto",targetState="exit", cond=doswitch() )
				}	 
				state("exit") { //this:State
					action { //it:State
						println("[CLIENT] Client EXIT Byeeee!")
						terminate(1)
					}
				}	 
			}
		}
}
