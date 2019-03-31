function loadItems(){
    $.get( "/seamarket/shop/items", function( data ) {
        $("#items").prepend( data );
    });
}