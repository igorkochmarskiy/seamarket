function loadItems(){
    $.get( "/seamarket/shop/items", function( data, statusText, xhr ) {
        console.log('status' + xhr.status);
        if (xhr.status == 200) {
            $("#items").prepend( data );
        }else{
            alert('sorry, try again later');
        }
    });

}