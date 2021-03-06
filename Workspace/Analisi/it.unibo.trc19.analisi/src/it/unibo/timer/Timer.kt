/* Generated by AN DISI Unibo */ 
package it.unibo.timer

import it.unibo.kactor.*
import alice.tuprolog.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
	
class Timer ( name: String, scope: CoroutineScope  ) : ActorBasicFsm( name, scope ){

	override fun getInitialState() : String{
		return "s0"
	}
	@kotlinx.coroutines.ObsoleteCoroutinesApi
	@kotlinx.coroutines.ExperimentalCoroutinesApi			
	override fun getBody() : (ActorBasicFsm.() -> Unit){
		return { //this:ActionBasciFsm
				state("s0") { //this:State
					action { //it:State
						println("[TIMER] timer is starting..")
					}
					 transition( edgeName="goto",targetState="waitingToStart", cond=doswitch() )
				}	 
				state("waitingToStart") { //this:State
					action { //it:State
					}
					 transition(edgeName="t08",targetState="watchDog",cond=whenDispatch("startTimer"))
				}	 
				state("watchDog") { //this:State
					action { //it:State
						stateTimer = TimerActor("timer_watchDog", 
							scope, context!!, "local_tout_timer_watchDog", 1000.toLong() )
					}
					 transition(edgeName="t09",targetState="exitMsg",cond=whenTimeout("local_tout_timer_watchDog"))   
				}	 
				state("exitMsg") { //this:State
					action { //it:State
						forward("endTime", "endTime(1)" ,"waiter" ) 
					}
				}	 
			}
		}
}
