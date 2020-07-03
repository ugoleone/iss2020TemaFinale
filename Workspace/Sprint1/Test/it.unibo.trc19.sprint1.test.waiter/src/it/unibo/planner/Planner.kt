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
		return { //this:ActionBasciFsm
				state("s0") { //this:State
					action { //it:State
						itunibo.planner.plannerUtil.initAI(  )
						itunibo.planner.plannerUtil.loadRoomMap( "teaRoomExplored"  )
						discardMessages = false
					}
					 transition(edgeName="t019",targetState="walk",cond=whenRequest("movetoCell"))
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
					 transition(edgeName="t020",targetState="execTheMove",cond=whenDispatch("doMove"))
					transition(edgeName="t021",targetState="walk",cond=whenRequestGuarded("movetoCell",{ CurrentPlannedMove.length == 0  
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
					 transition( edgeName="goto",targetState="execPlannedMoves", cond=doswitchGuarded({ payloadArg(0) != "w"  
					}) )
					transition( edgeName="goto",targetState="waitStepDone", cond=doswitchGuarded({! ( payloadArg(0) != "w"  
					) }) )
				}	 
				state("waitStepDone") { //this:State
					action { //it:State
					}
					 transition(edgeName="t122",targetState="execPlannedMoves",cond=whenReply("stepdone"))
				}	 
			}
		}
}
