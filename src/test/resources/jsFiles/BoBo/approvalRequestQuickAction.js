let map = new Map();
function xpathSingleEval(expression, contextNode) {
    return document.evaluate(expression, contextNode,
        null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue;
}
let rootNode = xpathSingleEval(".//div[contains(@class,'windowViewMode-normal')]//div[contains(@class,'row region-header')]", document);
let actionRibbon = xpathSingleEval(".//div[contains(@class,'actionsContainer')]", rootNode);
let listItem = document.evaluate(".//ul//li", rootNode, null, XPathResult.ORDERED_NODE_ITERATOR_TYPE, null);
let listNode = listItem.iterateNext();
while (listNode) {
    if (listNode) {
        let buttonName = xpathSingleEval(".//a//div | .//button", listNode);
        if(buttonName){
            map.set(buttonName.textContent, buttonName);
           }
       }
    listNode = listItem.iterateNext();
}
console.log(map);

return Object.fromEntries(map);