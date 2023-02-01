
// 给tab-item绑定点击，切换内容事件函数
function addClickListenerToTabItem() {
    // 获取tab项元素数组
    var tabItems = document.querySelectorAll('.tab-item')

    // 获取tab内容项元素数组
    var tabContainers = document.querySelectorAll('.tab-container')

    // for循环遍历
    for (var i = 0; i < tabItems.length; i++) {
        // 给tab-container的div绑定自定义属性
        tabItems[i].setAttribute('index', i)

        tabItems[i].onclick = function () {
            // console.log('ok');

            // 先清除所有的tabItem项目的类名current
            for (var j = 0; j < tabItems.length; j++) {
                // console.log('进入了');

                tabItems[j].className = 'tab-item'
            }
            // console.log('出来了');
            // 让当前被点击的元素样式类名为 tab-item current
            this.className = 'tab-item current'

            // 获取当前元素的自定义属性值
            var index = this.getAttribute('index')
            // console.log(index);

            // 隐藏其他的tabContainer元素
            hideElementList(tabContainers)
            // 让当前要显示的显示
            tabContainers[index].style.display = 'block'

        }
    }

}

// 改善下面的元素数组函数，做成一个公共的隐藏函数
function hideElementList(elementList) {
    for (var i = 0; i < elementList.length; i++) {
        elementList[i].style.display = 'none'
    }
}