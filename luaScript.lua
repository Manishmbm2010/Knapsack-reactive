wrk.method = "POST"
wrk.body   = "{\"problem\":{\"capacity\":10,\"weights\":[5,4,3],\"values\":[100,50,200]}}"
wrk.headers["Content-Type"] = "application/json"
