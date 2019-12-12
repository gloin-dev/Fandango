/**
 * This function retrieve all the thumbnails from the server calling the endpoint that retrieve all images
 */
function retrieveDataFromServer() {

    $.ajax({
        // la URL para la petición
        url: 'http://' + location.host + '/api/images',

        // especifica si será una petición POST o GET
        type: 'GET',

        // el tipo de información que se espera de respuesta
        dataType: 'json',

        // código a ejecutar si la petición es satisfactoria;
        success: function (json) {
            for (var i in json) {
                appendImageToHtml(json[i]._id);
            }
        },
        // código a ejecutar si la petición falla;
        error: function (xhr, status) {
           // alert('Ops, existió un problema : ' + status);
        },

        // código a ejecutar sin importar si la petición falló o no
        complete: function (xhr, status) {
            //    alert('Petición realizada');
        }
    });
}

/**
 * This function append the image to the html parent
 * @param imageId
 */
function appendImageToHtml(imageId) {

    var imagePlaceHolder = document.createElement('div');

    imagePlaceHolder.innerHTML =

        '<div class="col-lg-2 col-md-2 col-xs-2 img-fluid" style="height: 150px;">' +
        '<a class="thumbnail" href="http://' + location.host + '/api/image/' + imageId + '" rel="lightbox" ' + 'title="http://' + location.host + '/api/image/' + imageId + '">' +
        '<img class="list-img" src="http://' + location.host + '/api/thumbnail/' + imageId + '" />' +
        '</a>' +
        '</div>';

    document.getElementById('maincontent').appendChild(imagePlaceHolder);
}

/**
 * This function clear all images in the main web
 */
function clearImages() {

    var myNode = document.getElementById("maincontent");
    while (myNode.firstChild) {
        myNode.removeChild(myNode.firstChild);
    }
}

/**
 * This Ajax process the form send without reload the web
 */
$('#uploadImageForm').submit(function () {

    $.ajax({
        url: 'http://' + location.host + '/api/image',
        type: "POST",             // Type of request to be send, called as method
        data: new FormData(this), // Data sent to server, a set of key/value pairs (i.e. form fields and values)
        contentType: false,       // The content type used when sending data to the server.
        cache: false,             // To unable request pages to be cached
        processData: false,        // To send DOMDocument or non processed data file it is set to false
        // código a ejecutar si la petición es satisfactoria;
        success: function (json) {
            BootstrapDialog.show({
                title: "Your Image ID",
                message: json.id
            });
            clearAndRetrieveDataFromServer();
        },
        // código a ejecutar si la petición falla;
        error: function (xhr, status) {
            alert('Ops, existió un problema : ' + status);
        },
        // código a ejecutar sin importar si la petición falló o no
        complete: function (xhr, status) {
            //  alert('Petición realizada');
        }
    });
    return false;
});

/**
 * This Ajax process the form send without reload the web
 */
$('#uploadFileForm').submit(function () {

    $.ajax({
        url: 'http://' + location.host + '/api/file',
        type: "POST",             // Type of request to be send, called as method
        data: new FormData(this), // Data sent to server, a set of key/value pairs (i.e. form fields and values)
        contentType: false,       // The content type used when sending data to the server.
        cache: false,             // To unable request pages to be cached
        processData: false,        // To send DOMDocument or non processed data file it is set to false
        // código a ejecutar si la petición es satisfactoria;
        success: function (json) {
            BootstrapDialog.show({
                title: "Your File ID",
                message: json.id
            });
        },
        // código a ejecutar si la petición falla;
        error: function (xhr, status) {
            alert('Ops, existió un problema : ' + status);
        },
        // código a ejecutar sin importar si la petición falló o no
        complete: function (xhr, status) {
           //   alert('Petición realizada');
        }
    });
    return false;
});

/**
 * This function clear all and retrieve new data from server
 */
function clearAndRetrieveDataFromServer() {
    clearImages();
    retrieveDataFromServer();
}


