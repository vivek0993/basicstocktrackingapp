package com.stocktracking.app

const val STOCK_DATA_SUCCESS_RESPONSE = "{\"success\":true,\"data\":[{\"sid\":\"RELI\",\"price\":2328.15,\"close\":2363.75,\"change\":-35.6,\"high\":2359.9,\"low\":2309,\"volume\":3318352,\"date\":\"2021-11-23T05:05:21.000Z\"},{\"sid\":\"TCS\",\"price\":3432.4,\"close\":3458.4,\"change\":-26,\"high\":3450.05,\"low\":3407.8,\"volume\":439254,\"date\":\"2021-11-23T05:05:20.000Z\"},{\"sid\":\"ITC\",\"price\":231.1,\"close\":230.9,\"change\":0.2,\"high\":232.7,\"low\":229.15,\"volume\":4926430,\"date\":\"2021-11-23T05:05:21.000Z\"},{\"sid\":\"HDBK\",\"price\":1520.4,\"close\":1515.35,\"change\":5.05,\"high\":1524.25,\"low\":1496.35,\"volume\":1945893,\"date\":\"2021-11-23T05:05:21.000Z\"},{\"sid\":\"INFY\",\"price\":1722.4,\"close\":1759.4,\"change\":-37,\"high\":1739,\"low\":1710,\"volume\":2501291,\"date\":\"2021-11-23T05:05:20.000Z\"}]}"

const val STOCK_DATA_FAILURE_RESPONSE = "{\"success\":false,\"data\":null,\"error\":\"Missing sid in request\",\"errorType\":\"INVALID_INPUT\"}"