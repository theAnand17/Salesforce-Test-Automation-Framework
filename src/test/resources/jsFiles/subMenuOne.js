let map = new Map();
function xpathSingleEval(expression, contextNode) {
    return document.evaluate(expression, contextNode,
        null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue;
}
let rootNode = xpathSingleEval("//div[contains(@class, 'oneAppNavContainer')]", document);
let listItem = document.evaluate(".//one-app-nav-bar-item-root", rootNode, null, XPathResult.ORDERED_NODE_ITERATOR_TYPE, null);
let listNode = listItem.iterateNext();
while (listNode) {
    if (listNode) {
        let button = xpathSingleEval(".//one-app-nav-bar-menu-item//a", listNode);
        let buttonName = xpathSingleEval(".//one-app-nav-bar-menu-item//span", listNode);
        if(buttonName && button){
            map.set(buttonName.textContent, button);
            if(button.getAttribute('aria-expanded') === "true"){
                let createItem = xpathSingleEval(".//one-app-nav-bar-menu-item//a", listNode);
                let createItemName = xpathSingleEval(".//one-app-nav-bar-menu-item//span//span", listNode);
                map.set(createItemName.textContent, createItem);
              }
        }
    }
    listNode = listItem.iterateNext();
}
console.log(map)

return Object.fromEntries(map);