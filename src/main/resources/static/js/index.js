var userInfo;
$().ready(function() {
    $.ajax({
        type : 'GET',
        url : _basePath + "/userInfo/get",
        success : function(json) {
            userInfo = json;
            $("#userImage").attr('src',userInfo.imageUrl)
        },
        contentType : "application/json; charset=utf-8"
    });
});