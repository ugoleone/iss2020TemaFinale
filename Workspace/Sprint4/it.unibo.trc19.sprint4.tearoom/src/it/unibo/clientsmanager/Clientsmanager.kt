/* Generated by AN DISI Unibo */ 
package it.unibo.clientsmanager

import it.unibo.kactor.*
import alice.tuprolog.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
	
class Clientsmanager ( name: String, scope: CoroutineScope  ) : ActorBasicFsm( name, scope ){

	override fun getInitialState() : String{
		return "init"
	}
	@kotlinx.coroutines.ObsoleteCoroutinesApi
	@kotlinx.coroutines.ExperimentalCoroutinesApi			
	override fun getBody() : (ActorBasicFsm.() -> Unit){
		 
				var tempResult = ""	
				var ID = ""
				var Temperature = 0.0
		return { //this:ActionBasciFsm
				state("init") { //this:State
					action { //it:State
						println("[CLIENT MANAGER] Client Manager STARTED")
						discardMessages = false
					}
					 transition( edgeName="goto",targetState="waitingMsg", cond=doswitch() )
				}	 
				state("waitingMsg") { //this:State
					action { //it:State
					}
					 transition(edgeName="t044",targetState="newClientState",cond=whenEvent("newClient"))
					transition(edgeName="t045",targetState="retrieveClientState",cond=whenEvent("nextState"))
					transition(edgeName="t046",targetState="handleAlarm",cond=whenDispatch("alarm"))
				}	 
				state("handleAlarm") { //this:State
					action { //it:State
						if( checkMsgContent( Term.createTerm("alarm(ID)"), Term.createTerm("alarm(ID)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								 var ID = payloadArg(0)  
								request("clientStateReq", "clientStateReq($ID)" ,"resourcemodel" )  
						}
					}
					 transition(edgeName="t047",targetState="stayOrLeave",cond=whenReply("clientStateRep"))
				}	 
				state("stayOrLeave") { //this:State
					action { //it:State
						if( checkMsgContent( Term.createTerm("clientStateRep(ID,STATE)"), Term.createTerm("clientStateRep(ID,CurrentState)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								 
												var ID = payloadArg(0)
												var CurrentState = payloadArg(1) 
												if(CurrentState == "waiting"){
													println("[CLIENT $ID] Bye!")
								forward("updateClientState", "updateClientState($ID,withdraw)" ,"resourcemodel" ) 
								
												}
						}
					}
					 transition( edgeName="goto",targetState="waitingMsg", cond=doswitch() )
				}	 
				state("newClientState") { //this:State
					action { //it:State
						println("[CLIENT MANAGER] A new client is here!")
						 if(Math.random() < 0.95) {
									Temperature = 36.0
								} else {
									Temperature = 39.0
								} 
						request("notify", "notify($Temperature)" ,"smartbell" )  
					}
					 transition(edgeName="t048",targetState="handleReply",cond=whenReply("tempResult"))
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
					 transition(edgeName="t149",targetState="wait",cond=whenDispatch("inform"))
					transition(edgeName="t150",targetState="enter",cond=whenDispatch("accept"))
				}	 
				state("wait") { //this:State
					action { //it:State
						println("[POSSIBLE CLIENT] I have to wait")
						if( checkMsgContent( Term.createTerm("inform(ID,X)"), Term.createTerm("inform(ID,TIME)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								 var Time = payloadArg(1).toLong() 
												ID = payloadArg(0)
								forward("startTimer", "startTimer(0,clientsmanager,alarm,$ID,$Time)" ,"timersmanager" ) 
						}
					}
					 transition( edgeName="goto",targetState="waitingMsg", cond=doswitch() )
				}	 
				state("enter") { //this:State
					action { //it:State
						if( checkMsgContent( Term.createTerm("accept(ID)"), Term.createTerm("accept(ID)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								 
												ID = payloadArg(0)
						}
					}
					 transition( edgeName="goto",targetState="waitingMsg", cond=doswitch() )
				}	 
				state("retrieveClientState") { //this:State
					action { //it:State
						if( checkMsgContent( Term.createTerm("nextState(ID)"), Term.createTerm("nextState(ID)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								 var ID = payloadArg(0)  
								request("clientStateReq", "clientStateReq($ID)" ,"resourcemodel" )  
						}
					}
					 transition(edgeName="t051",targetState="progressClientState",cond=whenReply("clientStateRep"))
				}	 
				state("progressClientState") { //this:State
					action { //it:State
						if( checkMsgContent( Term.createTerm("clientStateRep(ID,STATE)"), Term.createTerm("clientStateRep(ID,CurrentState)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								 
												var ID = payloadArg(0)
												var CurrentState = payloadArg(1) 
												var NextState = ""
												when(CurrentState) {
													"entering" -> {
														NextState = "ordering"
														println("[CLIENT $ID] I want to order!")
													}
													"ordering" -> {
								forward("order", "order($ID,tea)" ,"waiter" ) 
								
														NextState = "waitingtea"
														println("[CLIENT $ID] A Na-tea-li please!")
													}
													"waitingtea" -> {
														NextState = "drinking"
														println("[CLIENT $ID] Delicious tea!")
													}
													"drinking" -> {
														NextState = "paying"
														println("[CLIENT $ID] I want to pay!")
													}
													"paying" -> {
								forward("payment", "payment($ID,10)" ,"waiter" ) 
								
														NextState = "exiting"
														println("[CLIENT $ID] Exiting!")
													}
													"exiting" -> {
														NextState = "gone"
														println("[CLIENT $ID] Bye!")
													}
													"waiting" -> {
														NextState = "gone"
														println("[CLIENT $ID] Bye!")
													}
												}
								forward("updateClientState", "updateClientState($ID,$NextState)" ,"resourcemodel" ) 
						}
					}
					 transition( edgeName="goto",targetState="waitingMsg", cond=doswitch() )
				}	 
				state("makeOrder") { //this:State
					action { //it:State
					}
					 transition(edgeName="t052",targetState="progressClientState",cond=whenEvent("nextState"))
				}	 
				state("pay") { //this:State
					action { //it:State
					}
					 transition(edgeName="t053",targetState="progressClientState",cond=whenEvent("nextState"))
				}	 
				state("exit") { //this:State
					action { //it:State
						println("[POSSIBLE CLIENT] Maybe next time. Bye!")
					}
					 transition( edgeName="goto",targetState="waitingMsg", cond=doswitch() )
				}	 
			}
		}
}
