window.onload = function () {
    //inputAndImgWithClass();


    // 监听表单提交事件
    $('#addForm').submit(function () {
        $.post('/article/addArticle', $('#addForm').serialize(), function (res) {
            if (res.code == 200){
                // 添加成功
                alert(res.msg);
                // 跳转到用户列表展示页面
                location.href = 'article.html';
            }else {
                alert('添加失败！' + res.msg);
            }
        });
        return false;
    })
}



// 给表单元素绑定样式和操作
// function inputAndImgWithClass(){
//     // 获取登录页面的输入框元素
//     var inps = document.querySelectorAll('.input_class');
//
//     // 1.2给输入框绑定点击事件(对上面的改进)
//     for (var i = 0; i < inps.length - 1; i++) {
//         inps[i].onclick = function () {
//             this.className = 'input_clsss input_click'
//         };
//         inps[i].onblur = function () {
//             this.className = 'input_clsss'
//         };
//     }
// }