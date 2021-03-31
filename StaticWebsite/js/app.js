let app = (function () {
    let farenheit2celsius = function () {
        let sendData = {
            value: $("#farenheit").val(),
        };

        console.log("Sending Data...");

        $.get(
            "https://asjfmxcs7g.execute-api.us-east-1.amazonaws.com/prod",
            sendData,
            function (data, textStatus, jqXHR) {
                let html =
                    '<hr style="margin-top: 55px" /><div style="margin: 20px 0"><pre>' +
                    JSON.stringify(data, null, 2) +
                    "</pre></div><hr />";
                $("#response-field").html(html);
            },
            "json"
        );
    };

    return {
        f2c: farenheit2celsius,
    };
})();
