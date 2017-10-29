$(document).ready(function () {


    //---------------- File Uploader REST WS

    $("#btnSubmit").click(function (event) {

        event.preventDefault();
        fire_ajax_submit();
    });

    $("#btnSubmit2").click(function (event) {

        alert("File list");
        event.preventDefault();
        fire_ajax_submit2();
    });

    //----------------- WordCouter REST WS

    $("#btnSubmitClearResults").click(function (event) {

        alert("Clear Results - delete files and NULL datastructures");
        event.preventDefault();
        fire_ajax_SubmitClearResults();
    });

    $("#btnSubmitResultsToListTop").click(function (event) {

        alert("Map To Sorted List");
        event.preventDefault();
        fire_ajax_getMapToSortedListStatus();
    });

    $("#btnSubmitWordCountProcessStatus").click(function (event) {

        alert("Word Count Process - create datastructure from all files in upload folder");
        event.preventDefault();
        fire_ajax_getWordCountProcessStatus();
    });

    $("#btnSubmitWriteResultsToFile").click(function (event) {

        alert("Output To File - result.txt");
        event.preventDefault();
        fire_ajax_getResultOutputToFileStatus();
    });

});


function fire_ajax_submit() {
    // Get form
    var form = $('#fileUploadForm')[0];

    var data = new FormData(form);

    data.append("CustomField", "This is some extra data, testing");

    $("#btnSubmit").prop("disabled", true);

    $.ajax({
        type: "POST",
        enctype: 'multipart/form-data',
        url: "http://localhost:8833/fileuploadservice/api/upload/multi",

        //jsonpCallback:'uploadFileMulti',
        //dataType: 'jsonp',

        // url: "/api/upload/multi",
        data: data,
        //http://api.jquery.com/jQuery.ajax/
        //https://developer.mozilla.org/en-US/docs/Web/API/FormData/Using_FormData_Objects
        processData: false, //prevent jQuery from automatically transforming the data into a query string
        contentType: false,
        cache: false,
        timeout: 600000,
        success: function (data) {

            $("#result").text(data);
            console.log("SUCCESS : ", data);
            $("#btnSubmit").prop("disabled", false);

        },
        error: function (e) {

            $("#result").text(e.responseText);
            console.log("ERROR : ", e);
            $("#btnSubmit").prop("disabled", false);

        }

    });
}


function fire_ajax_submit2() {

    $("#btnSubmit2").prop("disabled", true);

    $.ajax({
        type: "GET",
        url: "http://localhost:8833/fileuploadservice/api/getFileList",
        jsonpCallback: 'getFileList',
        dataType: 'jsonp',

        success: function (response) {

                var trHTML = '<ul class="list-group">';
                $.each(response, function (i, item) {
                    trHTML += '<li class="list-group-item">' + item + '</li>';
                });
            trHTML+='</ul>';

               $('#wordcountstats').append(trHTML);


            console.log("SUCCESS : ", response);
            $("#btnSubmit2").prop("disabled", false);

        },
        error: function (e) {

            $("#fileListResult").text(e.responseText);
            console.log("ERROR : ", e);
            $("#btnSubmit2").prop("disabled", false);

        }
    });
}

    function fire_ajax_SubmitClearResults() {
        $("#btnSubmitClearResults").prop("disabled", true);
        $.ajax({
            type: "GET",
            url: "http://localhost:8855/wordcounterservice/api/getClearAllStatus",
            jsonpCallback: 'getClearAllStatus',
            dataType: 'jsonp',

            success: function (data) {



                $("#getResultDiv5").text(data);
                console.log("SUCCESS : ", data);
                $("#btnSubmitClearResults").prop("disabled", false);

                //location.reload();
            },
            error: function (e) {

                $("#getResultDiv5").text(e.responseText);
                console.log("ERROR : ", e);
                $("#btnSubmitClearResults").prop("disabled", false);

            }
        });

    }


    function fire_ajax_getMapToSortedListStatus() {

        $("#btnSubmitResultsToListTop").prop("disabled", true);
        $.ajax({
            type: "GET",
            url: "http://localhost:8855/wordcounterservice/api/getMapToSortedListStatus",
            jsonpCallback: 'getMapToSortedListStatus',
            dataType: 'jsonp',

            success: function (response) {

                var trHTML = '<ul class="list-group">';
                $.each(response, function (i, item) {
                    var words = item.split("      ");
                    trHTML += '<li class="list-group-item justify-content-between">' + words[1] + '<span class="badge badge-default badge-pill">' + words[0] + '</span></li>';
                });
                trHTML+='</ul>';

                $('#wordcountstatsEnhanced').append(trHTML);

                console.log("SUCCESS : ", response);
                $("#btnSubmitResultsToListTop").prop("disabled", false);
            },
            error: function (e) {

                $("#getResultDiv7").text(e.responseText);
                console.log("ERROR : ", e);
                $("#btnSubmitResultsToListTop").prop("disabled", false);

            }
        });
    }

function fire_ajax_getWordCountProcessStatus() {

    $("#btnSubmitWordCountProcessStatus").prop("disabled", true);
    $.ajax({
        type: "GET",
        url: "http://localhost:8855/wordcounterservice/api/getWordCountProcessStatus",
        jsonpCallback: 'getWordCountProcessStatus',
        dataType: 'jsonp',

        success: function (response) {

            $('#getResultDivWordCountProcessStatus').append(response);

            console.log("SUCCESS : ", response);
            $("#btnSubmitWordCountProcessStatus").prop("disabled", false);
        },
        error: function (e) {

            $("#getResultDivWordCountProcessStatus").text(e.responseText);
            console.log("ERROR : ", e);
            $("#btnSubmitWordCountProcessStatus").prop("disabled", false);

        }
    });
}

function fire_ajax_getResultOutputToFileStatus() {

    $("#btnSubmitWriteResultsToFile").prop("disabled", true);
    $.ajax({
        type: "GET",
        url: "http://localhost:8855/wordcounterservice/api/getResultOutputToFileStatus",
        jsonpCallback: 'getResultOutputToFileStatus',
        dataType: 'jsonp',

        success: function (response) {

            $('#getResultDivgetResultOutputToFileStatus').append(response);

            console.log("SUCCESS : ", response);
            $("#btnSubmitWriteResultsToFile").prop("disabled", false);
        },
        error: function (e) {

            $("#getResultDivgetResultOutputToFileStatus").text(e.responseText);
            console.log("ERROR : ", e);
            $("#btnSubmitWriteResultsToFile").prop("disabled", false);

        }
    });
}

