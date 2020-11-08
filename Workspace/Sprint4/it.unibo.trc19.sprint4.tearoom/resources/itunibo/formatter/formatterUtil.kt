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
				temp.put("currentState", clientsStateArray[i+1])
				temp.put("nextState", clientsStateArray[i+2])
				temp.put("lock", clientsStateArray[i+3])
				i = i + 4
				clientsStateJSONArray.put(temp)
			}
		}
		return clientsStateJSONArray
	}
	
	fun formatOrders(orders : String) : JSONArray{
		var ordersJSONArray = JSONArray()
		if(orders != "[]"){
			val ordersArray = orders.replace("[","").replace("]","").split(",")
			var i = 0
			while(i < ordersArray.size) {
				var temp = JSONObject()
				temp.put("id", ordersArray[i])
				temp.put("order", ordersArray[i+1])
				temp.put("ready", ordersArray[i+2])
				i = i + 3
				ordersJSONArray.put(temp)
			}
		}
		return ordersJSONArray
	}
	
	fun formatTableState(tableState : String) : JSONObject{
		var tableStateJSON = JSONObject()
		val tableStateArray = tableState.replace("[","").replace("]","").split(",")
		tableStateJSON.put("state", tableStateArray[0])
		tableStateJSON.put("remainingTime", tableStateArray[1])
		tableStateJSON.put("seatedClient", tableStateArray[2])
		return tableStateJSON
	}
	
	@JvmStatic fun formatJson( robotState : String, xRobot : String, yRobot: String, direction: String,
							   teatable1State : String, teatable2State : String,
							   serviceDeskState : String, orders : String,
							   clientsState: String,
							   teaServed : String,
							   totalNumberOfClients : String,
							   clientsInTheRoom : String,
							   withdraws : String,
							   raspIP : String) :String{
		val jo = JSONObject()
		jo.put("robotState", robotState)
		jo.put("xRobot", xRobot)
		jo.put("yRobot", yRobot)
		jo.put("direction", direction)
		jo.put("teatable1State", formatTableState(teatable1State))
		jo.put("teatable2State", formatTableState(teatable2State))
		jo.put("serviceDeskState", serviceDeskState)
		jo.put("orders", formatOrders(orders))
		jo.put("clientsState", formatStates(clientsState))
		jo.put("teaServed", teaServed)
		jo.put("totalNumberOfClients", totalNumberOfClients)
		jo.put("clientsInTheRoom", clientsInTheRoom)
		jo.put("withdraws", withdraws)
		jo.put("raspIP", raspIP.removeSurrounding("\'"))
		return jo.toString()
		
 	}
	
}