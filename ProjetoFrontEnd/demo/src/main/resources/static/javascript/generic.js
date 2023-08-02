$(function(){
    $('#goback').click(function(){
        event.preventDefault();
        window.history.go(-1);
    })
})