package itunibo.formatter

import org.json.JSONObject

object formatterUtil {
	
	@JvmStatic fun formatJson( robotState : String, xRobot : String, yRobot: String, direction: String,
							   teatable1State : String, teatable2State : String,
							   serviceDeskState : String, clientsState: String,
							   teaServed : String,
							   totalNumberOfClients : String,
							   clientsInTheRoom : String ) :String{
		val jo = JSONObject()
		jo.put("robotState", robotState)
		jo.put("xRobot", xRobot)
		jo.put("yRobot", yRobot)
		jo.put("direction", direction)
		jo.put("teatable1State", teatable1State)
		jo.put("teatable2State", teatable2State)
		jo.put("serviceDeskState", serviceDeskState)
		jo.put("clientsState", clientsState)
		jo.put("teaServed", teaServed)
		jo.put("totalNumberOfClients", totalNumberOfClients)
		jo.put("clientsInTheRoom", clientsInTheRoom)
		
		return jo.toString()
		
 	}
	
}