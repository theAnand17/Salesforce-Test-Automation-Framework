let map = new Map();
function xpathSingleEval(expression, contextNode) {
    return document.evaluate(expression, contextNode,
        null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue;
}
let rootNode = xpathSingleEval("//div[contains(@class, 'windowViewMode-normal')]//one-record-home-flexipage2", document);
let actionRibbon = xpathSingleEval(".//records-lwc-highlights-panel//runtime_platform_actions-actions-ribbon", rootNode);
let listItem = document.evaluate(".//ul//li", actionRibbon, null, XPathResult.ORDERED_NODE_ITERATOR_TYPE, null);
let listNode = listItem.iterateNext();
while (listNode) {
    if (listNode) {
        let buttonName = xpathSingleEval(".//button", listNode);
        if(buttonName){
            map.set(buttonName.textContent, buttonName);
            if(buttonName.getAttribute('aria-expanded')) {
                let menuList = document.evaluate(".//runtime_platform_actions-action-renderer//a//span", listNode, null, XPathResult.ORDERED_NODE_ITERATOR_TYPE, null);
                let menuNode = menuList.iterateNext();
                while (menuNode) {
                     if (menuNode) {
                            map.set(menuNode.textContent, menuNode);
                     }
                     menuNode = menuList.iterateNext();
                }
            }
        }
    }
    listNode = listItem.iterateNext();
}
console.log(map)

return Object.fromEntries(map);