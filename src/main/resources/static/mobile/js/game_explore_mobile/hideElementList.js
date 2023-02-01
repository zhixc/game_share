
// 改善下面的元素数组函数，做成一个公共的隐藏函数
function hideElementList(elementList) {
    for (var i = 0; i < elementList.length; i++) {
        elementList[i].style.display = 'none'
    }
}

// 隐藏所有的tabContainer元素函数
// function hideTabContainer(tabContainers) {
//     for (var i = 0; i < tabContainers.length; i++) {
//         tabContainers[i].style.display = 'none'
//     }
// }

// 隐藏所有listInfos里面的元素函数
// function hideListInfos(listInfos) {
//     for (var i = 0; i < listInfos.length; i++) {
//         listInfos[i].style.display = 'none'
//     }
// }