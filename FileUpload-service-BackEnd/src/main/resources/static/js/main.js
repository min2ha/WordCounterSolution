$(document).ready(function () {

    $("#btnSubmit").click(function (event) {
        //stop submit the form, we will post it manually.
        event.preventDefault();
        fire_ajax_submit();
    });

    $("#btnSubmit2").click(function (event) {
        //stop submit the form, we will post it manually.
        event.preventDefault();
        fire_ajax_submit2();
    });

    $("#btnSubmitDelete").click(function (event) {
        //stop submit the form, we will post it manually.
        event.preventDefault();
        fire_ajax_submitDelete();
    });
});

function fire_ajax_submit() {
    // Get form
    var form = $('#fileUploadForm')[0];

    var data = new FormData(form);

    //data.append("CustomField", "This is some extra data, testing");

    $("#btnSubmit").prop("disabled", true);

    $.ajax({
        type: "POST",
        enctype: 'multipart/form-data',
        url: "http://localhost:8833/fileuploadservice/api/upload/multi",
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

        alert("OKAY X1111 ! : ");

        $("#btnSubmit2").prop("disabled", true);

        $.ajax({
            type: "GET",
            url: "http://localhost:8833/fileuploadservice/api/getFileList",

            success: function (data) {

                alert("OKAY X1! : " + data);

                $("#result2").text(data);
                console.log("SUCCESS : ", data);
                $("#btnSubmit2").prop("disabled", false);

            },
            error: function (e) {

                $("#result2").text(e.responseText);
                console.log("ERROR : ", e);
                $("#btnSubmit2").prop("disabled", false);

            }
        });

        function fire_ajax_submitDelete() {

            $("#btnSubmitDelete").prop("disabled", true);

            $.ajax({
                type: "GET",
                url: "http://localhost:8855/wordcounterservice/api/getClearAllStatus",

                success: function (data) {
                    alert("OKAY X1! : " + data);
                    $("#deleteResult").text(data);
                    console.log("SUCCESS : ", data);
                    $("#btnSubmitDelete").prop("disabled", false);
                },
                error: function (e) {
                    $("#deleteResult").text(e.responseText);
                    console.log("ERROR : ", e);
                    $("#btnSubmitDelete").prop("disabled", false);

                }
            });
        }
    }