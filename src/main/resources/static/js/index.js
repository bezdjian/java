$(document).ready(function () {
    //Auto call Ajax and populate most 10 most bus stops.
    var top_ten_results = $("#top-ten-results");
    var top_ten_stopNames = $("#top-ten-stopNamesUL");

    $.ajax({
        type: "get",
        url: "/getMostStops",
        dataType: 'json',
        beforeSend: function () {
            top_ten_results.innerHTML = "";
            $('#cover').show();
        },
        success: function (response) {
            //Remove loader
            $('#cover').fadeOut(1000);

            $.each(response, function (mainKey, mainValue) {
                $.each(mainValue, function (innerKey, innerValue) {
                    $.each(innerValue, function (k, v) {
                        if (mainKey === "topTenList") {
                            var p = document.createElement("p");
                            p.innerText = "Line: " + k + " has " + v + " stops";
                            var li = document.createElement("li");
                            li.classList.add("list-group-item");
                            li.appendChild(p);
                            top_ten_results.append(li);
                        }
                        if (mainKey === "stopNames") {
                            if (k === "stopName") {
                                var p1 = document.createElement("p");
                                p1.innerText = ++innerKey + ": " + v;
                                var li1 = document.createElement("li");
                                li1.classList.add("list-group-item");
                                li1.appendChild(p1);
                                top_ten_stopNames.append(li1);
                            }
                        }
                    });
                });
            });
        },
        error: function (xhr, textStatus, errorThrown) {
            $('#cover').fadeOut(1000);
            viewError(xhr.responseText);
        }
    });

    //Search stop names by line numbers
    $("#search-btn").on("click", function () {
        var search = $("#searchField").val();

        if (search === "") {
            viewError("There was an empty line number");
            return;
        }
        if(isNaN(search)){
            viewError("The value should be a valid number");
            return;
        }

        var stopNamesUL = document.getElementById("stopNamesUL");

        $.ajax({
            type: "get",
            url: "/getStops/" + search,
            dataType: 'json',
            beforeSend: function () {
                //Clear the stopName list, show the loading gif
                stopNamesUL.innerHTML = "";
            },
            success: function (response) {
                if(response.length === 0){
                    viewError("There is no bus line with number " + search);
                }
                $.each(response, function (mainKey, mainValue) {
                    $.each(mainValue, function (key, value) {
                        if (key === "stopName") {
                            var p = document.createElement("p");
                            p.innerText = ++mainKey + ": " + value;
                            var li = document.createElement("li");
                            li.classList.add("list-group-item");
                            li.appendChild(p);
                            stopNamesUL.appendChild(li);
                        }
                    });
                });
            },
            error: function (xhr, textStatus, errorThrown) {
                viewError(xhr.responseText);
            }
        });
    });
});

function viewError(error_msg) {
    $("#errorMessage").html(error_msg);
    $("#errorMessageDialog").dialog({
        title: "Warning",
        buttons: {
            'Ok': function () {
                $(this).dialog('close');
            }
        }
    });
}
