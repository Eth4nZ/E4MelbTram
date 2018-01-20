package rocks.eth4.e4melbtram.objects

/**
 * Created by eth4 on 19/1/18.
 */

object Model {
    data class ErrorResponse(
            val message: String,
            val status: Status
    )
    data class DeparturesResponse(
            val departures: List<Departure>,
            val status: Status
    )
    data class Departure(
            val stop_id: Int,
            val route_id: Int,
            val run_id: Int,
            val direction_id: Int,
            val disruption_ids: List<Int>,
            val scheduled_departure_utc: String,
            val estimated_departure_utc: String,
            val at_platform: Boolean,
            val platform_number: String,
            val flags: String
    )
    data class Outlet(
            val outlet_distance: Int,
            val outlet_name: String,
            val outlet_business: String,
            val outlet_latitude: Int,
            val outlet_longitude: Int,
            val outlet_suburb: String
    )
    data class Route(
            val route_name: String,
            val route_number: String,
            val route_type: Int,
            val route_id: Int
    )
    data class RouteTypesResponse(
            val route_types: List<RouteType>,
            val status: Status
    )
    data class RouteType(
            val route_type_name: String,
            val route_type: Int
    )
    data class SearchResponse(
            val stops: List<Stop>,
            val routes: List<Route>,
            val outlets: List<Outlet>,
            val status: Status
    )
    data class Status(
            val version: String,
            val health: Int
    )
    data class Stop(
            val stop_distance: Int,
            val stop_name: String,
            val stop_id: Int,
            val route_type: Int,
            val stop_latitude: Int,
            val stop_longitude: Int
    )
}