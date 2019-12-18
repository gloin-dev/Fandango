/**
 * This Ajax process the form send without reload the web
 */
$('#uploadImageForm').submit(function () {

    $.ajax({
        url: 'http://' + location.host + '/api/images',
        type: "POST",             // Type of request to be send, called as method
        data: new FormData(this), // Data sent to server, a set of key/value pairs (i.e. form fields and values)
        contentType: false,       // The content type used when sending data to the server.
        cache: false,             // To unable request pages to be cached
        processData: false,        // To send DOMDocument or non processed data file it is set to false

        success: function (json) {
            BootstrapDialog.show({
                title: "Your Image ID",
                message: json.id
            });
            clearAndRetrieveDataFromServer();
        },
        error: function (xhr, status) {
            alert('Ops, error : ' + status);
        },
        complete: function (xhr, status) {
        }
    });
    return false;
});

/**
 * This Ajax process the form send without reload the web
 */
$('#uploadFileForm').submit(function () {

    $.ajax({
        url: 'http://' + location.host + '/api/files',
        type: "POST",             // Type of request to be send, called as method
        data: new FormData(this), // Data sent to server, a set of key/value pairs (i.e. form fields and values)
        contentType: false,       // The content type used when sending data to the server.
        cache: false,             // To unable request pages to be cached
        processData: false,        // To send DOMDocument or non processed data file it is set to false

        success: function (json) {
            BootstrapDialog.show({
                title: "Your File ID",
                message: json.id
            });
        },
        error: function (xhr, status) {
            alert('Ops, error : ' + status);
        },
        complete: function (xhr, status) {
        }
    });
    return false;
});

/**
 * This function retrieve all the thumbnails from the server calling the endpoint that retrieve all images
 */
function retrieveDataFromServer() {

    $.ajax({
        url: 'http://' + location.host + '/api/images',
        type: 'GET',
        dataType: 'json',
        success: function (json) {
            for (let i in json) {
                appendImageToHtml(json[i].id);
            }
        },
        error: function (xhr, status) {
        },
        complete: function (xhr, status) {
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
        '<a class="thumbnail" href="http://' + location.host + '/api/images/' + imageId + '" rel="lightbox" ' + 'title="http://' + location.host + '/api/images/' + imageId + '">' +
        '<img class="list-img" src="http://' + location.host + '/api/thumbnails/' + imageId + '"  alt="resource"/>' +
        '</a>' +
        '</div>';

    document.getElementById('mainContent').appendChild(imagePlaceHolder);
}

/**
 * This function clear all images in the main web
 */
function clearImages() {

    const myNode = document.getElementById("mainContent");
    while (myNode.firstChild) {
        myNode.removeChild(myNode.firstChild);
    }
}

/**
 * This function clear all and retrieve new data from server
 */
function clearAndRetrieveDataFromServer() {
    clearImages();
    retrieveDataFromServer();
}


