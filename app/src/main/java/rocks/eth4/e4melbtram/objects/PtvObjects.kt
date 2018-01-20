package rocks.eth4.e4melbtram.objects

/**
 * Created by eth4 on 19/1/18.
 */

object Model {
    data class RouteTypes(
            val route_types: List<RouteType>,
            val status: Status
    )
    data class RouteType(
            val route_type_name: String,
            val route_type: Int
    )
    data class Status(
            val version: String,
            val health: Int
    )
}