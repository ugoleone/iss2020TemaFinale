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
						println("[RESOURCE MODEL] STARTING, I will be soon ready to listen to changes!")
						solve("consult('sysRules.pl')","") //set resVar	
						solve("consult('tearoomkb.pl')","") //set resVar	
						discardMessages = false
					}
					 transition( edgeName="goto",targetState="listenToChanges", cond=doswitch() )
				}	 
				state("listenToChanges") { //this:State
					action { //it:State
						println("[RESOURCE MODEL] LISTENING...")
					}
					 transition(edgeName="t00",targetState="informChanges",cond=whenDispatch("newClient"))
					transition(edgeName="t01",targetState="informChanges",cond=whenDispatch("taskUpdate"))
					transition(edgeName="t02",targetState="informChanges",cond=whenDispatch("ready"))
					transition(edgeName="t03",targetState="informChanges",cond=whenDispatch("orderReq"))
					transition(edgeName="t04",targetState="informChanges",cond=whenDispatch("tableCleaned"))
					transition(edgeName="t05",targetState="informChanges",cond=whenEvent("robotCurrentPosition"))
					transition(edgeName="t06",targetState="findLocation",cond=whenRequest("askWhere"))
				}	 
				state("informChanges") { //this:State
					action { //it:State
						
									var TeatableNumber = ""
									var Teatable = ""
						if( checkMsgContent( Term.createTerm("newClient(X)"), Term.createTerm("newClient(X)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								println("[RESOURCE MODEL] New Client")
								solve("newClient","") //set resVar	
								solve("teatable(X,available)","") //set resVar	
								if( currentSolution.isSuccess() ) {println("[RESOURCE MODEL] A table is free!")
								 
												TeatableNumber = getCurSol("X").toString()
												Teatable = "teatable"+TeatableNumber
								solve("replaceRule(teatable($TeatableNumber,_),teatable($TeatableNumber,busy))","") //set resVar	
								println("[RESOURCE MODEL] Waiter task updated: reachEntranceDoor")
								solve("replaceRule(waiter(_),waiter(reachEntranceDoor))","") //set resVar	
								emit("waitingTimeEvent", "waitingTimeEvent(0)" ) 
								emit("waiterTaskChangedEvent", "waiterTaskChangedEvent(reachEntranceDoor,$Teatable)" ) 
								}
								else
								{println("[RESOURCE MODEL] All tables is busy")
								emit("waitingTimeEvent", "waitingTimeEvent(5000)" ) 
								}
						}
						if( checkMsgContent( Term.createTerm("taskUpdate(X)"), Term.createTerm("taskUpdate(Task)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								 val Task = payloadArg(0) 
								println("[RESOURCE MODEL] Waiter task updated: $Task")
								solve("replaceRule(waiter(_),waiter($Task))","") //set resVar	
								if( Task == "servingDrinkToClient" 
								 ){solve("serveTea","") //set resVar	
								}
								if( Task == "convoyExit" 
								 ){solve("replaceRule(teatable(1,_),teatable(1,dirty))","") //set resVar	
								}
								if( Task == "returnHomeFromExit" 
								 ){solve("exitClient","") //set resVar	
								}
								if( Task == "athome" 
								 ){solve("teatable(TeatableNumber,dirty)","") //set resVar	
								if( currentSolution.isSuccess() ) { 
														TeatableNumber = getCurSol("TeatableNumber").toString()
														Teatable = "teatable" + TeatableNumber
								solve("replaceRule(waiter(_),waiter(sanitizing))","") //set resVar	
								emit("waiterTaskChangedEvent", "waiterTaskChangedEvent(sanitizing,$Teatable)" ) 
								}
								else
								{}
								}
						}
						if( checkMsgContent( Term.createTerm("orderReq(X)"), Term.createTerm("orderReq(T)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								 val T = payloadArg(0) 
								println("[RESOURCE MODEL] New order for teatable$T")
								solve("replaceRule(servicedesk(_),servicedesk(preparing($T)))","") //set resVar	
								emit("newOrderEvent", "newOrderEvent($T)" ) 
						}
						if( checkMsgContent( Term.createTerm("ready(X)"), Term.createTerm("ready(T)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								 val T = payloadArg(0) 
								println("[RESOURCE MODEL] Order is ready for teatable$T")
								solve("replaceRule(servicedesk(_),servicedesk(ready($T)))","") //set resVar	
								solve("replaceRule(waiter(_),waiter(collectingDrink))","") //set resVar	
								emit("waiterTaskChangedEvent", "waiterTaskChangedEvent(collectingDrink,$T)" ) 
						}
						if( checkMsgContent( Term.createTerm("robotCurrentPosition(X,Y)"), Term.createTerm("robotCurrentPosition(X,Y)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								 
												val X = payloadArg(0)
												val Y = payloadArg(1)
								solve("replaceRule(currentWaiterPos(_,_),currentWaiterPos($X,$Y))","") //set resVar	
						}
						if( checkMsgContent( Term.createTerm("tableCleaned(T)"), Term.createTerm("tableCleaned(T)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								
												Teatable = payloadArg(0)
								if( Teatable == "teatable1" 
								 ){TeatableNumber = "1" 
								}
								else
								 {TeatableNumber = "2" 
								 }
								solve("replaceRule(teatable($TeatableNumber,_),teatable($TeatableNumber,available))","") //set resVar	
						}
						solve("roomstate(waiter(S),currentWaiterPos(Y,X),stateOfTeatables(T1,T2),servicedesk(D),teaServed(TS),totalNumberOfClients(NC),clientsInTheRoom(CR))","") //set resVar	
						 	
									val S = getCurSol("S").toString()
									val X = getCurSol("X").toString()
									val Y = getCurSol("Y").toString()
									val T1 = getCurSol("T1").toString()
									val T2 = getCurSol("T2").toString()
									val D = getCurSol("D").toString()
									val TS = getCurSol("TS").toString()
									val NC = getCurSol("NC").toString()
									val CR = getCurSol("CR").toString()
									
									
									val JsonState : String = itunibo.formatter.formatterUtil.formatJson(S,X,Y,T1,T2,D,TS,NC,CR)
						updateResourceRep( JsonState  
						)
					}
					 transition( edgeName="goto",targetState="listenToChanges", cond=doswitch() )
				}	 
				state("findLocation") { //this:State
					action { //it:State
						if( checkMsgContent( Term.createTerm("askWhere(X)"), Term.createTerm("askWhere(X)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								 var Spot = payloadArg(0)  
								println("[RESOURCE MODEL] Find location $Spot")
								solve("pos($Spot,X,Y)","") //set resVar	
								 
											val X = getCurSol("X").toString() 
										  	val Y = getCurSol("Y").toString()
								answer("askWhere", "location", "location($X,$Y)"   )  
						}
					}
					 transition( edgeName="goto",targetState="listenToChanges", cond=doswitch() )
				}	 
			}
		}
}
