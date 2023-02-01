// 给游戏分类里面的a标签绑定点击事件
function addClickListenerToGenresList() {
    var a_list = document.querySelector('.genres-list').querySelectorAll('a')
    // console.log(a_list);
    // 给每一个a标签绑定点击事件
    for (var i = 0; i < a_list.length; i++) {
        a_list[i].onclick = function () {
            // console.log('OK');
            // 先清除每一个a标签的样式
            for (var j = 0; j < a_list.length; j++) {
                a_list[j].className = ''
            }
            // 再让被点击的元素加上元素
            // 谁被点击，this对象就是谁
            this.className = 'current-default'
        }
    }
}
