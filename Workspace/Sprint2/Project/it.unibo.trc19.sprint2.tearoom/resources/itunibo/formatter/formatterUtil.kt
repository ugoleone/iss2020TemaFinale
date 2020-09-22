package itunibo.formatter

import org.json.JSONObject
import org.json.JSONArray

object formatterUtil {
	
	fun formatStates(clientsState : String) : JSONArray{
		var clientsStateJSONArray = JSONArray()
		if(clientsState != "[]"){
			val clientsStateArray = clientsState.replace("[","").replace("]","").split(",")
			var i = 0
			while(i < clientsStateArray.size) {
				var temp = JSONObject()
				temp.put("id", clientsStateArray[i])
				temp.put("state", clientsStateArray[i+1])
				i = i + 2
				clientsStateJSONArray.put(temp)
			}
		}
		return clientsStateJSONArray
	}
	
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
		jo.put("clientsState", formatStates(clientsState))
		jo.put("teaServed", teaServed)
		jo.put("totalNumberOfClients", totalNumberOfClients)
		jo.put("clientsInTheRoom", clientsInTheRoom)
		
		return jo.toString()
		
 	}
	
}