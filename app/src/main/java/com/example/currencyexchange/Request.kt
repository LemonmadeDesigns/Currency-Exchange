package com.example.currencyexchange

class Request(
    // ALL VARIABLE BELOW REFER TO DATA FROM JSON FILE: SEE README.md

    // SUCCESS OR FAILURE
    var result: String,

    // LAST TIME DATA WAS UPDATED
    var time_last_update_utc: String,

    // LAST TIME DATA WILL BE UPDATED
    var time_next_update_utc: String,

    //
    var base_code: String,

    // RATES OF CURRENCY
    var rates: Currency
)