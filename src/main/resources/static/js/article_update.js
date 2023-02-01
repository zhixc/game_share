window.onload = function () {
    findArticleByAId()

    // 监听表单提交事件
    $('#addForm').submit(function () {
        // var obj = $('#addForm').serialize()
        // console.log(obj)
        $.post('/article/updateArticle', $('#addForm').serialize(), function (res) {
            if (res.code == 200){
                // 添加成功
                alert(res.msg);
                // 跳转到文章列表展示页面
                location.href = 'article.html';
            }else {
                alert('修改失败！' + res.msg);
            }
        });
        return false;
    })
}

// 查询文章信息并回回显数据
function findArticleByAId() {
    // 从地址栏路径获取aid
    var aid = getParameter('aid');
    // 从地址栏获取 isDeleted
    var isDeleted = getParameter('isDeleted')
    // 从地址栏获取 isChecked
    var isChecked = getParameter('isChecked')

    // 发起ajax请求，查询游戏信息
    $.get('/article//findArticleByAId', {aid: aid, isDeleted: isDeleted, isChecked: isChecked}, function (res) {
        var article = res.extend.article;
        $('#aid').val(article.aid);
        $('#title').val(article.title);
        $('#content').val(article.content);
        $('input[name=isDeleted]').val([article.isDeleted]);
        $('input[name=isChecked]').val([article.isChecked]);

    })
}
