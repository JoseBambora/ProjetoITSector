$(function(){
    function getCookie(cookieName) {
        var name = cookieName + "=";
        var decodedCookie = decodeURIComponent(document.cookie);
        var cookieArray = decodedCookie.split(';');
        for (var i = 0; i < cookieArray.length; i++) {
          var cookie = cookieArray[i];
          while (cookie.charAt(0) === ' ') {
            cookie = cookie.substring(1);
          }
          if (cookie.indexOf(name) === 0) {
            return cookie.substring(name.length, cookie.length);
          }
        }
    return null; // Return null if the cookie is not found
    }
    $('#logout').click(function(){
        document.cookie = "token=; expires=Thu, 01 Jan 1970 00:00:00 UTC; path=/;";
        window.location.href = '/login/';
    })

    function init()
    {
        let value = getCookie("token");
        if(value != null)
        {
            $('#logout').show()
        }
        else
        {
            $('#logout').hide()
        }
    }
    init()
})