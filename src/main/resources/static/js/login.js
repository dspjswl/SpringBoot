function linkDemoLogin() {
    $.get(_basePath+'/oauth/demoLogin',function (data) {
        window.location.href = _basePath + data;
    });
}

function linkQQLogin() {
    $.get(_basePath+'/oauth/githubLogin',function (data) {
        window.location.href = data;
    });
}

function linkGithubLogin() {
    $.get(_basePath+'/oauth/githubLogin',function (data) {
        window.location.href = data;
    });
}

function refreshCaptcha() {
    $("#kaptcha").attr("src", "images/kaptcha.jpg?t=" + Math.random());
}