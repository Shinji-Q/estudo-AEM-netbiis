function addEventOpenImageInZoomMode() {
    $('.carousel-image').off('click').on('click', function() {
        $('.carousel').carousel('pause')

        let src = $(this).attr('src')
    
        $('#image-view').attr('src', src)

        $('#show-image-view-mode').addClass("show")
    })
}

function addEventCloseImageInZoomMode() {
    $('#show-image-view-mode').off('click').on('click', function() {
        $('.carousel').carousel('cycle')

        $('#show-image-view-mode').removeClass("show")
    })
}

addEventCloseImageInZoomMode()
addEventOpenImageInZoomMode()