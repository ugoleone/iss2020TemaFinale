/* Generated by AN DISI Unibo */ 
package it.unibo.planner

import it.unibo.kactor.*
import alice.tuprolog.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
	
class Planner ( name: String, scope: CoroutineScope  ) : ActorBasicFsm( name, scope ){

	override fun getInitialState() : String{
		return "s0"
	}
	@kotlinx.coroutines.ObsoleteCoroutinesApi
	@kotlinx.coroutines.ExperimentalCoroutinesApi			
	override fun getBody() : (ActorBasicFsm.() -> Unit){
		
			var XT = "0"
			var YT = "0"
			var CurrentPlannedMove = ""
			var StepTime    	   = 100L
			var Configured = false
			var SingleMove = false
		return { //this:ActionBasciFsm
				state("s0") { //this:State
					action { //it:State
						if(  !Configured  
						 ){ Configured = true  
						itunibo.planner.plannerUtil.initAI(  )
						itunibo.planner.plannerUtil.loadRoomMap( "teaRoomExplored"  )
						discardMessages = false
						}
						 SingleMove = false  
					}
					 transition(edgeName="t022",targetState="walk",cond=whenRequest("movetoCell"))
					transition(edgeName="t023",targetState="execSingleMove",cond=whenDispatch("doMove"))
				}	 
				state("execSingleMove") { //this:State
					action { //it:State
						 SingleMove = true  
						if( checkMsgContent( Term.createTerm("doMove(V)"), Term.createTerm("doMove(M)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								if(  payloadArg(0) == "w"  
								 ){request("step", "step(370)" ,"basicrobot" )  
								}
								else
								 {forward("cmd", "cmd(${payloadArg(0)})" ,"basicrobot" ) 
								 delay(500) 
								 }
						}
						 
						  			val move = payloadArg(0)
						  			itunibo.planner.plannerUtil.updateMap(move,"")
					}
					 transition( edgeName="goto",targetState="updateCurrentWaiterPosDir", cond=doswitchGuarded({ payloadArg(0) != "w"  
					}) )
					transition( edgeName="goto",targetState="waitStepDoneFail", cond=doswitchGuarded({! ( payloadArg(0) != "w"  
					) }) )
				}	 
				state("walk") { //this:State
					action { //it:State
						if( checkMsgContent( Term.createTerm("movetoCell(X,Y)"), Term.createTerm("movetoCell(X,Y)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								 XT = payloadArg(0)
											   YT = payloadArg(1)			  
								println("[PLANNER] MOVING to ($XT,$YT)")
								itunibo.planner.plannerUtil.planForGoal( "$XT", "$YT"  )
						}
					}
					 transition( edgeName="goto",targetState="execPlannedMoves", cond=doswitch() )
				}	 
				state("execPlannedMoves") { //this:State
					action { //it:State
						  CurrentPlannedMove = itunibo.planner.plannerUtil.getNextPlannedMove()  
						if(  CurrentPlannedMove.length > 0  
						 ){forward("doMove", "doMove($CurrentPlannedMove)" ,"planner" ) 
						}
						else
						 {println("[PLANNER] POINT ($XT,$YT) REACHED")
						 answer("movetoCell", "atcell", "atcell($XT,$YT)"   )  
						 }
					}
					 transition(edgeName="t024",targetState="execTheMove",cond=whenDispatch("doMove"))
					transition(edgeName="t025",targetState="walk",cond=whenRequestGuarded("movetoCell",{ CurrentPlannedMove.length == 0  
					}))
				}	 
				state("execTheMove") { //this:State
					action { //it:State
						if( checkMsgContent( Term.createTerm("doMove(V)"), Term.createTerm("doMove(M)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								if(  payloadArg(0) == "w"  
								 ){request("step", "step(370)" ,"basicrobot" )  
								}
								else
								 {forward("cmd", "cmd(${payloadArg(0)})" ,"basicrobot" ) 
								 delay(500) 
								 }
						}
						 
						  			val move = payloadArg(0)
						  			itunibo.planner.plannerUtil.updateMap(move,"")
					}
					 transition( edgeName="goto",targetState="updateCurrentWaiterPosDir", cond=doswitchGuarded({ payloadArg(0) != "w"  
					}) )
					transition( edgeName="goto",targetState="waitStepDoneFail", cond=doswitchGuarded({! ( payloadArg(0) != "w"  
					) }) )
				}	 
				state("waitStepDoneFail") { //this:State
					action { //it:State
					}
					 transition(edgeName="t126",targetState="updateCurrentWaiterPosDir",cond=whenReply("stepdone"))
					transition(edgeName="t127",targetState="updateCurrentWaiterPosDir",cond=whenReply("stepfail"))
				}	 
				state("updateCurrentWaiterPosDir") { //this:State
					action { //it:State
						 
						     		val X = itunibo.planner.plannerUtil.getPosX()
						     		val Y = itunibo.planner.plannerUtil.getPosY()
						     		val Direction = itunibo.planner.plannerUtil.getDirection()
						forward("waiterCurrentPositionDirection", "waiterCurrentPositionDirection($X,$Y,$Direction)" ,"resourcemodel" ) 
					}
					 transition( edgeName="goto",targetState="execPlannedMoves", cond=doswitchGuarded({ !SingleMove  
					}) )
					transition( edgeName="goto",targetState="s0", cond=doswitchGuarded({! ( !SingleMove  
					) }) )
				}	 
			}
		}
}