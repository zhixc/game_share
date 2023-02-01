window.onload = function () {
    changeVideo();

    var gid = getParameter('gid')
    var isDeleted  = getParameter('isDeleted')

    console.log(gid)
    console.log(isDeleted)

    // 上传视频
    $('#updateBtn').click(function () {
        // 发起ajax请求，上传视频
        var formData = new FormData();
        formData.append('uploadFile', $('#uploadFile')[0].files[0]);

        // isDeleted先给一个0，gid先赋值 1
        formData.append('gid', gid)
        formData.append('isDeleted', isDeleted)

        console.log(formData);
        $.ajax({
            type: 'post',
            url: '/gameVideo/upload',
            data: formData,
            cache: false,
            processData: false,
            contentType: false,
            success: function (response) {
                if (response.code == 200){
                    alert('上传成功！');
                    // console.log(response.extend.url)
                    window.location.href = 'game.html'
                }else {
                    $('#errorMsg').html(response.msg)
                }
            }
        })

    })

};
// 视频更换绑定事件
function changeVideo(){
    var uploadFileElement = document.getElementById('uploadFile');
    uploadFileElement.onchange = function () {
        var file = this.files[0];
        if (!!file) {
            var reader = new FileReader(); // 图片文件转换为base64
            reader.readAsDataURL(file);//用文件加载器加载文件
            reader.onload = function () { // 显示视频
                document.getElementById("file_video").src = this.result;
            }
        }
    }
}