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
		 
				var WhatImDoing = "athome" 
				var TeatableNumber = ""
				var Teatable = ""
				var Misc = ""
				var StartCleaning = 0L
				var CleaningTime = 0L
				var RemainingTime = 0L
				var ActiveTimer = 0L
				var ReturningHome = false
				var TeatableToClean = ""
				var TeatableNumberToClean = ""
				var WasCleaning = false
		return { //this:ActionBasciFsm
				state("s0") { //this:State
					action { //it:State
						println("[WAITER] waiter is starting..I'm HOME!")
						discardMessages = false
					}
					 transition( edgeName="goto",targetState="reqHandler", cond=doswitch() )
				}	 
				state("reqHandler") { //this:State
					action { //it:State
						println("[WAITER] Waiting for a request!")
					}
					 transition(edgeName="t014",targetState="handleChange",cond=whenEvent("waiterTaskChangedEvent"))
					transition(edgeName="t015",targetState="movementHelper",cond=whenDispatch("moveTo"))
					transition(edgeName="t016",targetState="returnHomeState",cond=whenDispatch("returnHome"))
				}	 
				state("handleChange") { //this:State
					action { //it:State
						if( checkMsgContent( Term.createTerm("waiterTaskChangedEvent(X,T)"), Term.createTerm("waiterTaskChangedEvent(T,X)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								if(  ReturningHome  
								 ){forward("stopTask", "stopTask(0)" ,"planner" ) 
								}
								else
								 { ReturningHome = false  
								 }
								
								 			if(WasCleaning) {
												CleaningTime = System.currentTimeMillis() - StartCleaning
												if(CleaningTime < RemainingTime){
													//non ho finito di pulire il tavolo
													RemainingTime = RemainingTime - CleaningTime
													WasCleaning = false
								forward("cancelTimer", "cancelTimer($ActiveTimer)" ,"timersmanager" ) 
								forward("cleaningInterrupted", "cleaningInterrupted($RemainingTime,$TeatableNumberToClean)" ,"resourcemodel" ) 
								
												}
											}
								 
											WhatImDoing = payloadArg(0)
											Misc = payloadArg(1)
											if(Misc == "1" || Misc == "2") {
												TeatableNumber = Misc
												Teatable = "teatable" + TeatableNumber
											}
											if(WhatImDoing == "sanitizing") {
												TeatableNumberToClean = Misc.split('s')[1]
												TeatableToClean = "teatable" + TeatableNumberToClean
												RemainingTime = Misc.split('s')[2].toLong()
											}
								println("[WAITER] New task: $WhatImDoing")
								
											when(WhatImDoing) {
												"reachEntranceDoor" -> { 
								forward("moveTo", "moveTo(entrancedoor)" ,"waiter" ) 
								}
												"convoyTable" -> { 
								forward("moveTo", "moveTo($Teatable)" ,"waiter" ) 
								}
												"takingOrder" -> { 
								forward("moveTo", "moveTo($Teatable)" ,"waiter" ) 
								}
												"collectingDrink" -> { 
								forward("moveTo", "moveTo(barman)" ,"waiter" ) 
								}
												"bringingDrinkToClient" -> { 
								forward("moveTo", "moveTo($Teatable)" ,"waiter" ) 
								}
												"collectingPayment" -> { 
								forward("moveTo", "moveTo($Teatable)" ,"waiter" ) 
								}
												"convoyExit" -> { 
								forward("moveTo", "moveTo(exitdoor)" ,"waiter" ) 
								}
												"returnHome" -> { 
								forward("returnHome", "returnHome(home)" ,"waiter" ) 
								}	
												"sanitizing" -> { 
								forward("moveTo", "moveTo($TeatableToClean)" ,"waiter" ) 
								}
									
									
												"stepAhead" -> { 
								forward("doMove", "doMove(w)" ,"planner" ) 
								}
												"turnLeft" -> { 
								forward("doMove", "doMove(l)" ,"planner" ) 
								}
												"turnRight" -> { 
								forward("doMove", "doMove(r)" ,"planner" ) 
								}
													
												"forceExit" -> { 
								forward("moveTo", "moveTo($Teatable)" ,"waiter" ) 
								}
								 			} 
						}
					}
					 transition( edgeName="goto",targetState="reqHandler", cond=doswitch() )
				}	 
				state("takingOrder") { //this:State
					action { //it:State
						if( checkMsgContent( Term.createTerm("order(ID,X)"), Term.createTerm("order(ID,O)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								forward("cancelTimer", "cancelTimer($ActiveTimer)" ,"timersmanager" ) 
								 var ID = payloadArg(0) 
												var O = payloadArg(1)
								forward("orderReq", "orderReq($ID,$O)" ,"resourcemodel" ) 
								println("[WAITER] I'm transmitting the order to the barman")
								forward("taskDone", "taskDone($WhatImDoing,0)" ,"resourcemodel" ) 
						}
					}
					 transition( edgeName="goto",targetState="reqHandler", cond=doswitch() )
				}	 
				state("servingDrinkToClient") { //this:State
					action { //it:State
						println("[WAITER] Serving the drink")
						forward("taskDone", "taskDone($WhatImDoing,0)" ,"resourcemodel" ) 
					}
					 transition( edgeName="goto",targetState="reqHandler", cond=doswitch() )
				}	 
				state("handlePayment") { //this:State
					action { //it:State
						if( checkMsgContent( Term.createTerm("payment(ID,X)"), Term.createTerm("payment(ID,MONEY)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								forward("cancelTimer", "cancelTimer($ActiveTimer)" ,"timersmanager" ) 
								 println("[WAITER] " + payloadArg(0) + " EUR collected! Have a nice day!")  
								forward("taskDone", "taskDone($WhatImDoing,${payloadArg(0)})" ,"resourcemodel" ) 
						}
					}
					 transition( edgeName="goto",targetState="reqHandler", cond=doswitch() )
				}	 
				state("cleaningTable") { //this:State
					action { //it:State
						if( checkMsgContent( Term.createTerm("cleanTable(T)"), Term.createTerm("cleanTable(T)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								TeatableNumberToClean = payloadArg(0) 
								println("[WAITER] Cleaning $TeatableToClean!")
								
												StartCleaning = System.currentTimeMillis()
												ActiveTimer = StartCleaning
												WasCleaning = true
								forward("startTimer", "startTimer($ActiveTimer,waiter,alarm,$TeatableNumberToClean,$RemainingTime)" ,"timersmanager" ) 
						}
					}
					 transition(edgeName="t017",targetState="endCleaning",cond=whenDispatch("alarm"))
					transition(edgeName="t018",targetState="handleChange",cond=whenEvent("waiterTaskChangedEvent"))
				}	 
				state("endCleaning") { //this:State
					action { //it:State
						if( checkMsgContent( Term.createTerm("alarm(ID)"), Term.createTerm("alarm(P)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								TeatableNumberToClean = payloadArg(0) 
								forward("taskDone", "taskDone(sanitizing,$TeatableNumberToClean)" ,"resourcemodel" ) 
						}
					}
					 transition( edgeName="goto",targetState="reqHandler", cond=doswitch() )
				}	 
				state("handleAlarm") { //this:State
					action { //it:State
						println("[WAITER] Client timeout!")
						if( checkMsgContent( Term.createTerm("alarm(ID)"), Term.createTerm("alarm(P)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								TeatableNumber = payloadArg(0) 
								forward("timeoutClient", "timeoutClient($WhatImDoing,$TeatableNumber)" ,"resourcemodel" ) 
						}
					}
					 transition( edgeName="goto",targetState="reqHandler", cond=doswitch() )
				}	 
				state("returnHomeState") { //this:State
					action { //it:State
						if( checkMsgContent( Term.createTerm("returnHome(X)"), Term.createTerm("returnHome(L)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								val Location = payloadArg(0) 
								request("askWhere", "askWhere($Location)" ,"resourcemodel" )  
						}
						if( checkMsgContent( Term.createTerm("location(X,Y)"), Term.createTerm("location(X,Y)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								 
												val XT = payloadArg(0)
											   	val YT = payloadArg(1)			  
								request("movetoCell", "movetoCell($XT,$YT)" ,"planner" )  
								 ReturningHome = true  
						}
					}
					 transition(edgeName="t119",targetState="handleChange",cond=whenEvent("waiterTaskChangedEvent"))
					transition(edgeName="t120",targetState="returnHomeState",cond=whenReply("location"))
					transition(edgeName="t121",targetState="handleAtCell",cond=whenReply("atcell"))
				}	 
				state("movementHelper") { //this:State
					action { //it:State
						if( checkMsgContent( Term.createTerm("moveTo(X)"), Term.createTerm("moveTo(L)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								val Location = payloadArg(0) 
								request("askWhere", "askWhere($Location)" ,"resourcemodel" )  
						}
						if( checkMsgContent( Term.createTerm("location(X,Y)"), Term.createTerm("location(X,Y)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								 
												val XT = payloadArg(0)
											   	val YT = payloadArg(1)			  
								request("movetoCell", "movetoCell($XT,$YT)" ,"planner" )  
						}
					}
					 transition(edgeName="t122",targetState="movementHelper",cond=whenReply("location"))
					transition(edgeName="t123",targetState="handleAtCell",cond=whenReply("atcell"))
				}	 
				state("handleAtCell") { //this:State
					action { //it:State
						if( checkMsgContent( Term.createTerm("atcell(X,Y)"), Term.createTerm("atcell(X,Y)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								 
												var Col = payloadArg(0).toInt()
												var Row = payloadArg(1).toInt()
								 when(WhatImDoing) {
												"convoyTable" -> {
								if( (Col==2 && Row==2) || (Col==4 && Row==2) 
								 ){forward("unlockClient", "unlockClient($TeatableNumber)" ,"resourcemodel" ) 
								forward("listenRequests", "listenRequests(1)" ,"waiter" ) 
								forward("taskDone", "taskDone($WhatImDoing,$TeatableNumber)" ,"resourcemodel" ) 
								}
								 } 
												"takingOrder" ->  {
								if( (Col==2 && Row==2) || (Col==4 && Row==2) 
								 ){ ActiveTimer = System.currentTimeMillis()  
								forward("startTimer", "startTimer($ActiveTimer,waiter,alarm,$TeatableNumber,10000)" ,"timersmanager" ) 
								forward("unlockClient", "unlockClient($TeatableNumber)" ,"resourcemodel" ) 
								}
								 }
												"bringingDrinkToClient" ->  {
								if( (Col==2 && Row==2) || (Col==4 && Row==2) 
								 ){forward("listenRequests", "listenRequests(1)" ,"waiter" ) 
								forward("taskDone", "taskDone($WhatImDoing,$TeatableNumber)" ,"resourcemodel" ) 
								}
								 }
												"collectingPayment" ->  {
								if( (Col==2 && Row==2) || (Col==4 && Row==2) 
								 ){ ActiveTimer = System.currentTimeMillis()  
								forward("startTimer", "startTimer($ActiveTimer,waiter,alarm,$TeatableNumber,10000)" ,"timersmanager" ) 
								forward("unlockClient", "unlockClient($TeatableNumber)" ,"resourcemodel" ) 
								}
								 } 
												"sanitizing" ->  {
								if( (Col==2 && Row==2) || (Col==4 && Row==2) 
								 ){forward("cleanTable", "cleanTable($TeatableNumberToClean)" ,"waiter" ) 
								}
								}
												"forceExit" -> { 
								forward("listenRequests", "listenRequests(1)" ,"waiter" ) 
								forward("taskDone", "taskDone($WhatImDoing,$TeatableNumber)" ,"resourcemodel" ) 
								}
												
												"returnHome" -> {
												ReturningHome = false  
								forward("listenRequests", "listenRequests(1)" ,"waiter" ) 
								forward("taskDone", "taskDone($WhatImDoing,$Misc)" ,"resourcemodel" ) 
								}
												else -> {
								forward("listenRequests", "listenRequests(1)" ,"waiter" ) 
								forward("taskDone", "taskDone($WhatImDoing,$Misc)" ,"resourcemodel" ) 
								}
											} 
						}
					}
					 transition(edgeName="t124",targetState="movementHelper",cond=whenDispatch("moveTo"))
					transition(edgeName="t125",targetState="takingOrder",cond=whenDispatch("order"))
					transition(edgeName="t126",targetState="handlePayment",cond=whenDispatch("payment"))
					transition(edgeName="t127",targetState="cleaningTable",cond=whenDispatch("cleanTable"))
					transition(edgeName="t128",targetState="reqHandler",cond=whenDispatch("listenRequests"))
					transition(edgeName="t129",targetState="handleAlarm",cond=whenDispatch("alarm"))
					transition(edgeName="t130",targetState="handleAtCell",cond=whenReply("atcell"))
				}	 
			}
		}
}
