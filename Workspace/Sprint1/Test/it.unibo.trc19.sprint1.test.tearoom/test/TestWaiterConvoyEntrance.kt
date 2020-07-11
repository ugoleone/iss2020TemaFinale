package test

import org.junit.Before
import org.junit.After
import org.junit.Test
import org.junit.Assert.assertTrue
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.delay
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import it.unibo.kactor.ActorBasic
import it.unibo.kactor.MsgUtil
import it.unibo.kactor.MqttUtils
import itunibo.planner.model.RoomMap
import it.unibo.kactor.ApplMessage
import itunibo.planner.plannerUtil
import org.junit.Assert
 

class TestWaiterConvoyEntrance {
var waiter             : ActorBasic? = null
var client             : ActorBasic? = null
var smartbell             : ActorBasic? = null
val initDelayTime     = 1000L   // 

@kotlinx.coroutines.ObsoleteCoroutinesApi
@kotlinx.coroutines.ExperimentalCoroutinesApi
	@Before
	fun systemSetUp() {
//		println("%%%  boudaryTest prepare the result map expected ")
//  		println( mapRoomKotlin.mapUtil.refMapForTesting )
   		//activate the application: SEE boundaryTest
   		kotlin.concurrent.thread(start = true) {
			it.unibo.ctxtearoom.main()  //WARNING: elininate the autostart
		}
	}

	@After
	fun terminate() {
		println("%%%  TestWaiter terminate ")
	}
	
	


@kotlinx.coroutines.ObsoleteCoroutinesApi
@kotlinx.coroutines.ExperimentalCoroutinesApi
@Test
	fun testWaiterConvoyEntrance(){
	 	runBlocking{
 			while( waiter == null /*|| client==null || smartbell==null*/){
				println("testWaiterMap wait for robot ... ")
				delay(initDelayTime)  //time for robot to start
//				client = it.unibo.kactor.sysUtil.getActor("client")
				waiter = it.unibo.kactor.sysUtil.getActor("waiter")
//				smartbell = it.unibo.kactor.sysUtil.getActor("smartbell")
 			}
			//REACH
			
			MsgUtil.sendMsg(MsgUtil.buildRequest("client","checkAvail","checkAvail(0)","waiter"),waiter!!)
			
			delay(7500L)
			
			var mapReach: String?="|1, 1, 1, 1, 1, 1, 1, X, " + "\n"+
								 "|1, 1, 1, 1, 1, 1, 1, X, " + "\n"+
								 "|1, 1, 1, 1, 1, 1, 1, X, " + "\n"+
								 "|1, 1, X, 1, X, 1, 1, X, " + "\n"+
								 "|1, r, 1, 1, 1, 1, 1, X, " + "\n"+
								 "|X, X, X, X, X, X, X, X,".trim();
			
			println("DESIRED MAP:")
			println(mapReach)
			
			
			println("ACTUAL MAP:")
			println(plannerUtil.getMap().trim())
			
			Assert.assertEquals( mapReach, plannerUtil.getMap().trim() )
			
			//CONVOYTABLE			
			delay(7500L)
			
			mapReach    ="|1, 1, 1, 1, 1, 1, 1, X, " + "\n"+
						 "|1, 1, 1, 1, 1, 1, 1, X, " + "\n"+
						 "|1, 1, r, 1, 1, 1, 1, X, " + "\n"+
						 "|1, 1, X, 1, X, 1, 1, X, " + "\n"+
						 "|1, 1, 1, 1, 1, 1, 1, X, " + "\n"+
						 "|X, X, X, X, X, X, X, X,".trim();
			
			println("DESIRED MAP:")
			println(mapReach)
			
			
			println("ACTUAL MAP:")
			println(plannerUtil.getMap().trim())
			
			Assert.assertEquals( mapReach, plannerUtil.getMap().trim() )
			
			//RETURN HOME
			delay(10000L)
			
			mapReach    ="|r, 1, 1, 1, 1, 1, 1, X, " + "\n"+
						 "|1, 1, 1, 1, 1, 1, 1, X, " + "\n"+
						 "|1, 1, 1, 1, 1, 1, 1, X, " + "\n"+
						 "|1, 1, X, 1, X, 1, 1, X, " + "\n"+
						 "|1, 1, 1, 1, 1, 1, 1, X, " + "\n"+
						 "|X, X, X, X, X, X, X, X,".trim();
			
			println("DESIRED MAP:")
			println(mapReach)
			
			println("ACTUAL MAP:")
			println(plannerUtil.getMap().trim())
			
			Assert.assertEquals( mapReach, plannerUtil.getMap().trim() )
													

  		}
	 	println("testWaiter BYE  ")  
	}
	
}//testRobotboundary