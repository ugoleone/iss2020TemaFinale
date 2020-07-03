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
		 	
				var tabFree = "" 
				var tableToCheck = 1 
		return { //this:ActionBasciFsm
				state("s0") { //this:State
					action { //it:State
						println("[WAITER] waiter is starting..I'm HOME!")
						solve("consult('sysRules.pl')","") //set resVar	
						solve("consult('tearoomkb.pl')","") //set resVar	
						delay(1000) 
						discardMessages = false
					}
					 transition( edgeName="goto",targetState="reqHandler", cond=doswitch() )
				}	 
				state("reqHandler") { //this:State
					action { //it:State
						println("[WAITER] Waiting for a request!")
						 tableToCheck = 1  
					}
					 transition(edgeName="t00",targetState="checkTableState",cond=whenRequest("checkAvail"))
					transition(edgeName="t01",targetState="reachingClientToTakeOrder",cond=whenDispatch("readyToOrder"))
					transition(edgeName="t02",targetState="collectingDrink",cond=whenDispatch("ready"))
					transition(edgeName="t03",targetState="exitClient",cond=whenDispatch("exitReq"))
				}	 
				state("checkTableState") { //this:State
					action { //it:State
						println("[WAITER] Checking table state")
						if(  tableToCheck == 1  
						 ){request("tabStatus", "tabStatus(1)" ,"table1" )  
						}
						else
						 {request("tabStatus", "tabStatus(1)" ,"table2" )  
						 }
					}
					 transition(edgeName="t04",targetState="handleTabState",cond=whenReply("tabState"))
				}	 
				state("handleTabState") { //this:State
					action { //it:State
						if( checkMsgContent( Term.createTerm("tabState(X)"), Term.createTerm("tabState(STATUS)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								 tabFree = payloadArg(0)  
								if(  tabFree == "FreeClean" 
								 ){println("[WAITER] A table is free!")
								answer("checkAvail", "waitingTime", "waitingTime(0)"   )  
								}
								else
								 { tableToCheck = 2  
								 }
						}
					}
					 transition( edgeName="goto",targetState="reachEntranceDoor", cond=doswitchGuarded({ tabFree == "FreeClean"  
					}) )
					transition( edgeName="goto",targetState="checkTableState", cond=doswitchGuarded({! ( tabFree == "FreeClean"  
					) }) )
				}	 
				state("reachEntranceDoor") { //this:State
					action { //it:State
						solve("replaceRule(whatImDoing(_),whatImDoing(reach))","") //set resVar	
					}
					 transition( edgeName="goto",targetState="goingToEntranceDoor", cond=doswitch() )
				}	 
				state("reachingClientToTakeOrder") { //this:State
					action { //it:State
						println("[WAITER] I'm collecting the order from the client")
						solve("replaceRule(whatImDoing(_),whatImDoing(takingOrder))","") //set resVar	
					}
					 transition( edgeName="goto",targetState="goingToTable1", cond=doswitch() )
				}	 
				state("takingOrder") { //this:State
					action { //it:State
						request("take", "take(1)" ,"client" )  
					}
					 transition(edgeName="t05",targetState="clientReady",cond=whenReply("order"))
				}	 
				state("clientReady") { //this:State
					action { //it:State
						if( checkMsgContent( Term.createTerm("order(X)"), Term.createTerm("order(ORDER)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								forward("orderReq", "orderReq(${payloadArg(0)})" ,"barman" ) 
						}
						println("[WAITER] I'm transmitting the order to the barman")
					}
					 transition( edgeName="goto",targetState="returnToHome", cond=doswitch() )
				}	 
				state("collectingDrink") { //this:State
					action { //it:State
						solve("replaceRule(whatImDoing(_),whatImDoing(collectingDrink))","") //set resVar	
					}
					 transition( edgeName="goto",targetState="goingToBarman", cond=doswitch() )
				}	 
				state("servingDrinkToClient") { //this:State
					action { //it:State
						println("[WAITER] Serving the drink")
						forward("serveDrink", "serveDrink(tea)" ,"client" ) 
					}
					 transition( edgeName="goto",targetState="returnToHome", cond=doswitch() )
				}	 
				state("exitClient") { //this:State
					action { //it:State
						println("[WAITER] Client requested to exit! Proceeding to the payment")
						solve("replaceRule(whatImDoing(_),whatImDoing(exitClient))","") //set resVar	
					}
					 transition( edgeName="goto",targetState="goingToTable1", cond=doswitch() )
				}	 
				state("payment") { //this:State
					action { //it:State
						println("[WAITER] Collecting the money!")
						request("collect", "collect(1)" ,"client" )  
					}
					 transition(edgeName="t06",targetState="handlePayment",cond=whenReply("payment"))
				}	 
				state("handlePayment") { //this:State
					action { //it:State
						if( checkMsgContent( Term.createTerm("payment(X)"), Term.createTerm("payment(MONEY)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								 println("[WAITER] " + payloadArg(0) + " EUR collected! Have a nice day!")  
						}
						solve("replaceRule(whatImDoing(_),whatImDoing(convoyExit))","") //set resVar	
					}
					 transition( edgeName="goto",targetState="goingToExitDoor", cond=doswitch() )
				}	 
				state("goingToEntranceDoor") { //this:State
					action { //it:State
						request("moveTo", "moveTo(entrancedoor)" ,"planner" )  
					}
					 transition(edgeName="t17",targetState="handleAtCell",cond=whenReply("atcell"))
				}	 
				state("goingToExitDoor") { //this:State
					action { //it:State
						request("moveTo", "moveTo(exitdoor)" ,"planner" )  
					}
					 transition(edgeName="t18",targetState="handleAtCell",cond=whenReply("atcell"))
				}	 
				state("goingToBarman") { //this:State
					action { //it:State
						request("moveTo", "moveTo(barman)" ,"planner" )  
					}
					 transition(edgeName="t19",targetState="handleAtCell",cond=whenReply("atcell"))
				}	 
				state("goingToTable1") { //this:State
					action { //it:State
						request("moveTo", "moveTo(teatable1)" ,"planner" )  
					}
					 transition(edgeName="t110",targetState="handleAtCell",cond=whenReply("atcell"))
				}	 
				state("goingToTable2") { //this:State
					action { //it:State
						request("moveTo", "moveTo(teatable2)" ,"planner" )  
					}
					 transition(edgeName="t111",targetState="handleAtCell",cond=whenReply("atcell"))
				}	 
				state("returnToHome") { //this:State
					action { //it:State
						solve("replaceRule(whatImDoing(_),whatImDoing(returnHome))","") //set resVar	
						request("moveTo", "moveTo(home)" ,"planner" )  
					}
					 transition(edgeName="t112",targetState="handleAtCell",cond=whenReply("atcell"))
				}	 
				state("handleAtCell") { //this:State
					action { //it:State
						solve("whatImDoing(Z)","") //set resVar	
						 val WhatImDoing = getCurSol("Z").toString()  
						if( checkMsgContent( Term.createTerm("atcell(X,Y)"), Term.createTerm("atcell(X,Y)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								if(  WhatImDoing == "reach"  
								 ){delay(5000) 
								solve("replaceRule(whatImDoing(_),whatImDoing(convoyTable))","") //set resVar	
								forward("convoyTable", "convoyTable(A)" ,"waiter" ) 
								}
								if(  WhatImDoing == "convoyTable"  
								 ){delay(5000) 
								solve("replaceRule(whatImDoing(_),whatImDoing(returnHome))","") //set resVar	
								forward("returnHome", "returnHome(A)" ,"waiter" ) 
								}
								if(  WhatImDoing == "returnHome"  
								 ){delay(5000) 
								solve("replaceRule(whatImDoing(_),whatImDoing(nothing))","") //set resVar	
								forward("listenRequests", "listenRequests(A)" ,"waiter" ) 
								}
								if(  WhatImDoing == "takingOrder" 
								 ){delay(5000) 
								solve("replaceRule(whatImDoing(_),whatImDoing(take))","") //set resVar	
								forward("readyToTakeOrder", "readyToTakeOrder(A)" ,"waiter" ) 
								}
								if(  WhatImDoing == "collectingDrink" 
								 ){delay(5000) 
								solve("replaceRule(whatImDoing(_),whatImDoing(bringingDrinkToClient))","") //set resVar	
								forward("goTable1", "goTable1(A)" ,"waiter" ) 
								}
								if(  WhatImDoing == "bringingDrinkToClient"  
								 ){delay(5000) 
								solve("replaceRule(whatImDoing(_),whatImDoing(servingDrinkToClient))","") //set resVar	
								forward("serveDrink", "serveDrink(A)" ,"waiter" ) 
								}
								if(  WhatImDoing == "exitClient"  
								 ){delay(5000) 
								forward("pay", "pay(A)" ,"waiter" ) 
								}
								if(  WhatImDoing == "convoyExit"  
								 ){delay(5000) 
								solve("replaceRule(whatImDoing(_),whatImDoing(returnHome))","") //set resVar	
								forward("returnHome", "returnHome(A)" ,"waiter" ) 
								}
						}
					}
					 transition(edgeName="t113",targetState="goingToTable1",cond=whenDispatch("convoyTable"))
					transition(edgeName="t114",targetState="returnToHome",cond=whenDispatch("returnHome"))
					transition(edgeName="t115",targetState="takingOrder",cond=whenDispatch("readyToTakeOrder"))
					transition(edgeName="t116",targetState="servingDrinkToClient",cond=whenDispatch("serveDrink"))
					transition(edgeName="t117",targetState="reqHandler",cond=whenDispatch("listenRequests"))
					transition(edgeName="t118",targetState="goingToTable1",cond=whenDispatch("goTable1"))
					transition(edgeName="t119",targetState="payment",cond=whenDispatch("pay"))
				}	 
			}
		}
}
