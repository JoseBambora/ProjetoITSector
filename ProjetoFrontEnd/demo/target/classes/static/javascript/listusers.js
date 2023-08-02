$(function(){
    $('.user').click(function(){
        let id = $(this).attr('id')
        window.location.href='/users/'+id
    })
})